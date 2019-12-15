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
		String s = File.separator;
		
		//replace with input test image absolute path
		String path = "." + s + "src" + s + "TestImages";

		System.out.println("Depth Mapper v1.0.34");
		Debug.on();
		Debug.println();
		
		FileManager io = new JPEG();
		File[] files = io.grabDirectory(path);
		ArrayList<Mat> stack = io.createStack(files);
		
		Mapper mapper = new Mapper(stack);
		Mat[] output = mapper.generate();
		Debug.println();
		
		System.out.println("Saving Focus Stack...");
		io.writeOutput(output[0], path + File.separator + "Focus Stacked");
		System.out.println("Saving Depth Map...");
		io.writeOutput(output[1], path + File.separator + "Depth Map");
		System.out.println("---------------------");
		System.out.println("Success!");
	}

}

