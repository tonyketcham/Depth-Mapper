package com.github.depthMapper.Pipeline;

import java.util.ArrayList;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import com.github.depthMapper.Launcher.Debug;

import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;


/**
 * (Depth Mapper using OpenCV 4.1.1)
 *
 * Maps pixels from the image stack to a single matrix to form an all-in-focus focus stack image.
 * Maps depth from the image stack to a grayscale 8-bit matrix associated to the focus stacked image.
 * 
 * @author Tony Ketcham (ketcham@pnw.edu)
 * @version 1.0
 *
 */
public class Mapper {

	private ArrayList<Mat> inputs = new ArrayList<Mat>();

	public Mapper(ArrayList<Mat> inputs)
	{
//		this.inputs.add(inputs.get(0));
//		
//		for (int i = 0; i < inputs.size() - 1; ++i) {
//			this.inputs.add(Alignment.ECCalignment(inputs.get(i), inputs.get(i+1)));
//		}
		
		this.inputs = inputs;
	}
	
	public void setInputs(ArrayList<Mat> inputs)
	{
//		this.inputs.add(inputs.get(0));
//		
//		for (int i = 0; i < inputs.size() - 1; ++i) {
//			this.inputs.add(Alignment.ECCalignment(inputs.get(i), inputs.get(i+1)));
//		}
		
		this.inputs = inputs;
	}
	
	/**
	 * Generates a focal stack and a depth map (if desired), both of which are output as elements of an array respectively.
	 * @param depthMap Create a depth map or not
	 * @return
	 */
	public Mat[] generate(boolean depthMap)
	{
		try {		
			if(inputs.isEmpty())
				throw new NullPointerException("No input given to the focus stacking algorithm.");
			else
			{
				Mat[] gradient = new Mat[inputs.size()];
				
				System.out.println("Computing the gradient map of:");
				//finds gradient map of each image in the input ArrayList
				for (int i = 0 ; i < inputs.size() ; i++)
				{
					System.out.println("Image [" + i + "]");
					gradient[i] = GradientMap.generate(inputs.get(i));
				}
				
				//init empty focal stack Mat
				Mat focalStack = Mat.zeros(inputs.get(0).size(), inputs.get(0).type()); 
				//init empty 8-bit grayscale depth map Mat
				Mat map = Mat.zeros(inputs.get(0).size(), CvType.CV_8U); 
				
				//feed the input ArrayList size to the depth shader for range interpolation
				DepthShader depth = new DepthShader(inputs.size());
				
				Debug.println("Calculating focal stack and depth map matricies...");
				//progress tracker
				try (ProgressBar pb = new ProgressBar("Calculating", gradient[0].cols() * gradient[0].rows(), ProgressBarStyle.ASCII)){
					
					//fill focal stack, depth map Mats
					for(int y = 0; y < gradient[0].cols(); y++)
					{
						for(int x = 0; x < gradient[0].rows(); x++)
						{
							int image2sample = -1;
							double delta = Double.MIN_VALUE;
							for (int i = 0; i < gradient.length; i++)
							{
								//finds which image's pixel to sample at a given coordinate
								if(delta == Double.MIN_VALUE || gradient[i].get(x,y)[0] > delta)
								{
									delta = gradient[i].get(x,y)[0];
									image2sample = i;
								}	
							}
							//places the pixel of the sharpest image in the stack at a given coordinate
							focalStack.put(x, y, inputs.get(image2sample).get(x, y));
							
							if(depthMap) {
								//shades the depth of the image with the sharpest pixel at a given coordinate
								map.put(x, y, depth.map(image2sample));
							}
							pb.step();
						}
					}
				}
				Mat[] output = {focalStack, map};
				return output;
			}
		} catch (NullPointerException e) {
			System.out.println(e);
			return null;
		}
	}
	
	/**
	 * Generates a focal stack image with an associated depth map.
	 * @return array focal stack (element 0), depth map (element 1)
	 */
	public Mat[] generate()
	{
		return generate(true);
	}
}
