package com.github.depthMapper.Pipeline;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * (Depth Mapper using OpenCV 4.1.1)
 * 
 * Computes the gradient map (local rates of change) of an image.
 * @author Tony Ketcham (ketcham@pnw.edu)
 * @version 1.0
 *
 */
public class GradientMap {

	/**
	 * Generates a gradient map of the input image.
	 * @param imageBGR input BGR image
	 * @return absoluteDeltaMap scaled, 8-bit output
	 */
	public static Mat generate(Mat imageBGR)
	{
		int kernelSize = 5;
		double blurRadius = 5;
		
		Mat imageGrayscale = new Mat();
		Imgproc.cvtColor(imageBGR, imageGrayscale, Imgproc.COLOR_BGR2GRAY);
		
		Mat blurredImage = new Mat();
		Imgproc.GaussianBlur(imageGrayscale, blurredImage, new Size(blurRadius, blurRadius), 0);
		
		Mat deltaMap = new Mat();
		Imgproc.Laplacian(blurredImage, deltaMap, CvType.CV_64F, kernelSize, 1, 0);
		
		Mat absoluteDeltaMap = new Mat();
		Core.convertScaleAbs(deltaMap, absoluteDeltaMap);

		System.out.println("  Generated gradient map.");
		return absoluteDeltaMap;
	}
}
