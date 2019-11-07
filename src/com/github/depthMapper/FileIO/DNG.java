package com.github.depthMapper.FileIO;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import org.opencv.core.Mat;

import com.github.depthMapper.Pipeline.ExifMat;
import com.thebuzzmedia.exiftool.Tag;

/**
 * Depth Mapper using OpenCV 4.1.1
 *
 * Input/Output for DNG RAW files.
 * 
 * @author Tony Ketcham (ketcham@pnw.edu)
 * @version 1.0
 *
 */
public class DNG implements FileManager {

	@Override
	public File[] grabDirectory(String path) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Mat> createStack(File... files) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ExifMat> sortInput(ArrayList<Mat> imageStack, ArrayList<Map<Tag, String>> exifStack, Tag exifTag)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean writeOutput(Mat image, String path) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
