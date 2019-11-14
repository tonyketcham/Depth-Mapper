package com.github.depthMapper.Launcher;

import java.io.File;
import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.photo.Photo;

import com.github.depthMapper.FileIO.FileManager;
import com.github.depthMapper.FileIO.JPEG;
import com.github.depthMapper.Pipeline.Alignment;
import com.github.depthMapper.Pipeline.DepthShader;
import com.github.depthMapper.Pipeline.Mapper;
import com.github.depthMapper.Pipeline.Utils;

/**
 * Depth Mapper using OpenCV 4.1.1
 *
 * @author Tony Ketcham (ketcham@pnw.edu)
 * @version 1.0
 *
 */
public class Launcher {

	public static void main(String[] args) throws Exception
	{
		System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
		
		//replace with input test image absolute path
		String path = "C:\\Users\\Tony\\eclipse-workspace\\Depth Mapper\\src\\testImages\\Shallow (High Count) [Processed] subset";

		System.out.println("Depth Mapper v1.0");
		Debug.on();
		Debug.println();
		
		FileManager io = new JPEG();
		File[] files = io.grabDirectory(path);
		ArrayList<Mat> stack = io.createStack(files);
		
		stack = Utils.denoiseAll(stack, 5);
		
	//	stack = Alignment.ECCalignAll(stack);
		
		Mapper mapper = new Mapper(stack);
		Mat[] output = mapper.generate();
		Debug.println();
		
		
		//Denoising of depth map. This implementation goes overboard and changes the map to an off-white.
//		Debug.println("Denoising depth map...");		
//		Mat denoisedDepthMap = new Mat();
//		Photo.fastNlMeansDenoising(output[1], denoisedDepthMap, 3, 21);
//		Debug.println();
		
		System.out.println("Saving Focus Stack...");
		io.writeOutput(output[0], path + File.separator + "Focus Stacked");
		System.out.println("Saving Depth Map...");
		io.writeOutput(output[1], path + File.separator + "Depth Map");
		System.out.println("---------------------");
		System.out.println("Success!");
	}

}

