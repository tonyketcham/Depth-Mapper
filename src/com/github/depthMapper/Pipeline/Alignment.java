package com.github.depthMapper.Pipeline;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.opencv.core.Mat;

/**
 * (Depth Mapper using OpenCV 4.1.1)
 *
 * Aligns similar images using homography.
 *
 * @author Tony Ketcham (ketcham@pnw.edu)
 * @version 1.0
 *
 */
public class Alignment {
	//Set first image in the stack as the image to which we should align
	
	//
	
	public ArrayList<Mat> ECCalignment(ArrayList<Mat> inputs)
	{
		ArrayList<Mat> grayStack = new ArrayList<>();
		
		Iterator<Mat> iterator = inputs.iterator();
		
		//Deep clone the inputs
		while(iterator.hasNext()){
		    grayStack.add((Mat)iterator.next().clone());  
		}
		
//		int warp_mode = MOTION_EUCLIDEAN;
		
		
		
		
		
		return inputs;
	}
}
