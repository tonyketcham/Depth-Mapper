package com.github.depthMapper.Pipeline;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.core.TermCriteria;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.Video;

import com.github.depthMapper.FileIO.FileManager;
import com.github.depthMapper.FileIO.JPEG;
import com.github.depthMapper.Launcher.Debug;

import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;

/**
 * (Depth Mapper using OpenCV 4.1.1)
 *
 * Aligns similar images using an Affine warp.
 *
 * @author Tony Ketcham (ketcham@pnw.edu)
 * @version 1.0
 *
 */
public class Alignment {
	//Set first image in the stack as the image to which we should align
	

	/**
	 * Aligns an input to a template image input by estimating the geometric transformation between the two images.
	 * @param template image being used as an unchanged basis for transformation
	 * @param input image being transformed
	 * @return aligned input image
	 * @throws Exception 
	 */
	public static Mat ECCalignment(Mat template, Mat input)
	{
		Mat alignedInput = Mat.zeros(template.size(), template.type());
		Mat templateGray = Mat.zeros(template.size(), CvType.CV_8UC1);
		Mat inputGray = Mat.zeros(template.size(), CvType.CV_8UC1);
		
		Imgproc.cvtColor(template, templateGray, Imgproc.COLOR_BGR2GRAY);
		Imgproc.cvtColor(input, inputGray, Imgproc.COLOR_BGR2GRAY);
		
		//Think of aligning a stack of images as a video tracking problem
		int warpMode = Video.MOTION_AFFINE;
		Mat warpMatrix = new Mat();
		
		 
		// Initialize identity matrix
		if (warpMode == Video.MOTION_HOMOGRAPHY) {
		    warpMatrix = Mat.eye(3, 3, CvType.CV_32FC1); //3x3 warp matrix
		} else {
		    warpMatrix = Mat.eye(2, 3, CvType.CV_32FC1); //2x3 warp matrix
		}
		
		//Termination criteria
		int maxIterations = 2500; //maximum iterations or elements
		double terminationEps = 1e-4; //desired accuracy
		
		TermCriteria killCondition = new TermCriteria(TermCriteria.COUNT + TermCriteria.EPS, maxIterations, terminationEps);
		
		//Finds the geometric transformation between the template image and input image
		Video.findTransformECC(templateGray, inputGray, warpMatrix, warpMode, killCondition, templateGray, 1);

		
		
		//Warps the input to the template image, thus aligning the two
		if (warpMode != Video.MOTION_HOMOGRAPHY) {
			Imgproc.warpAffine(input, alignedInput, warpMatrix, input.size(), Imgproc.WARP_INVERSE_MAP + Imgproc.INTER_LINEAR);
		} else {
			Imgproc.warpPerspective(input, alignedInput, warpMatrix, input.size(), Imgproc.WARP_INVERSE_MAP + Imgproc.INTER_LINEAR);
		}
		
		
		return alignedInput;
	}
	
	/**
	 * Aligns all images in a stack through ECC (rather slow for large images [15MP+]).
	 * @param input image stack
	 * @return aligned image stack
	 */
	public static ArrayList<Mat> ECCalignAll(ArrayList<Mat> input)
	{
		
		Debug.println("Aligning image stack...");
		ArrayList<Mat> newStack = new ArrayList<>();
		newStack.add(input.get(0));
		
		//progress tracker
		try (ProgressBar pb = new ProgressBar("Aligning images", input.size(), ProgressBarStyle.ASCII))
		{
			for(int i = 0; i < input.size() - 1; i++) {
				newStack.add(Alignment.ECCalignment(newStack.get(i), input.get(i+1)));
				pb.step();
			}
			pb.step();
		}
		Debug.println("Stack aligned.");
		Debug.println();
		return newStack;
	}
	
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
	
	public static void main(String[] args) throws Exception
	{
		System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
		
		//replace with input test image absolute path
		String path = "C:\\Users\\Tony\\eclipse-workspace\\Depth Mapper\\src\\testImages\\Shallow (High Count) [Processed]\\temp";

		System.out.println("Alignment Test");
		Debug.on();
		Debug.println();
		
		FileManager io = new JPEG();
		File[] files = io.grabDirectory(path);
		ArrayList<Mat> stack = io.createStack(files);
		Mat output = Alignment.ECCalignment(stack.get(0), stack.get(1));

		Debug.println();
		
		System.out.println("Saving aligned image...");
		io.writeOutput(output, path + File.separator + "Aligned");
	
		System.out.println("Success!");
	}
}
