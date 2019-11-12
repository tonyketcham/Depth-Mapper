package com.github.depthMapper.Pipeline;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.Video;

import com.github.depthMapper.FileIO.FileManager;
import com.github.depthMapper.FileIO.JPEG;
import com.github.depthMapper.Launcher.Debug;

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
	 */
	public static Mat ECCalignment(Mat template, Mat input)
	{
		Mat alignedInput = new Mat();
		
		//Think of aligning a stack of images as a video tracking problem
		int warpMode = Video.MOTION_EUCLIDEAN;
		Mat warpMatrix = new Mat();
		//Mat maskMatrix = Mat.zeros(template.size(), CvType.CV_32F);
		
		 
		// Initialize identity matrix
		if (warpMode == Video.MOTION_HOMOGRAPHY) {
		    warpMatrix = Mat.eye(3, 3, CvType.CV_32F); //3x3 warp matrix
		} else {
		    warpMatrix = Mat.eye(2, 3, CvType.CV_32F); //2x3 warp matrix
		}
		
		//Termination criteria
		int maxIterations = 5000; //maximum iterations or elements
		double terminationEps = 1e-10; //desired accuracy
		
		TermCriteria killCondition = new TermCriteria(TermCriteria.COUNT + TermCriteria.EPS, maxIterations, terminationEps);
		
		//Finds the geometric transformation between the template image and input image
		Video.findTransformECC(template, input, warpMatrix, warpMode, killCondition, null, 1);
		
		//Warps the input to the template image, thus aligning the two
		if (warpMode != Video.MOTION_HOMOGRAPHY) {
			Imgproc.warpAffine(input, alignedInput, warpMatrix, input.size(), Imgproc.WARP_INVERSE_MAP + Imgproc.INTER_LINEAR);
		} else {
			Imgproc.warpPerspective(input, alignedInput, warpMatrix, input.size(), Imgproc.WARP_INVERSE_MAP + Imgproc.INTER_LINEAR);
		}
		
		
		return alignedInput;
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
		Alignment.ECCalignment(stack.get(0), stack.get(1));

		Debug.println();
		
	
		System.out.println("Success!");
	}
}
