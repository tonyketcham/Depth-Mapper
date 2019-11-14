package com.github.depthMapper.Pipeline;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;

import com.github.depthMapper.Launcher.Debug;

import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;

/**
 * Depth Mapper using OpenCV 4.1.1
 *
 * @author Tony Ketcham (ketcham@pnw.edu)
 * @version 1.0
 *
 */
public class Utils {

	/**
	 * Downscales an image by a given percentage.
	 * @param input 
	 * @param scale downscale percentage
	 * @return
	 */
	public static Mat downscale(Mat input, double scale) throws Exception 
	{
		if (scale >= 1) {
			throw new IllegalArgumentException("Given scale is greater than or equal to 1:" + scale + ". This will not downscale the image.");
		}
		Mat dst = new Mat();
		Size size = new Size((int) input.cols() * scale, (int) input.rows() * scale);
		Imgproc.resize(input, dst, size, Imgproc.INTER_AREA);
		return dst;
		
	}
	
	public static Mat denoise(Mat input, int strength) {
		Mat output = new Mat();
		Photo.fastNlMeansDenoisingColored(input, output, strength, 1, 9, 21); //continue fiddling with these numbers to find the most appropriate settings.
		return output;
	}
	
	public static ArrayList<Mat> denoiseAll(ArrayList<Mat> inputs, int strength) {
		Debug.println("Denoising stack...");
		ArrayList<Mat> outputs = new ArrayList<Mat>();
		try (ProgressBar pb = new ProgressBar("Aligning images", inputs.size(), ProgressBarStyle.ASCII))
		{
			for (Mat input : inputs) {
				outputs.add(denoise(input, strength));
				pb.step();
			}
		}
		return outputs;
	}
	
	public static ArrayList<Mat> denoiseMulti(ArrayList<Mat> inputs, int strength) {
		ArrayList<Mat> outputs = new ArrayList();
		Mat temp = new Mat();
		for (int i = 0; i < inputs.size(); ++i) {
			Photo.fastNlMeansDenoisingColoredMulti(inputs, temp, i,  1, strength);
			outputs.add(temp);
		}
		return outputs;
	}
}
