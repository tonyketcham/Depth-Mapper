package com.github.depthMapper.FileIO;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import org.opencv.core.Mat;

import com.github.depthMapper.Pipeline.ExifMat;
import com.thebuzzmedia.exiftool.Tag;

/**
 * (Depth Mapper using OpenCV 4.1.1)
 * 
 * Interface for managing file IO
 *
 * @author Tony Ketcham (ketcham@pnw.edu)
 * @version 1.0
 *
 */
public interface FileManager {
	
	/**
	 * Loads a directory of files.
	 * @param path path to file directory
	 * @return array array of files
	 * @throws Exception Directory not found
	 */
	public File[] grabDirectory(String path) throws Exception;
	
	/**
	 * Creates an image stack as a stack of matrices.
	 * @param files input images
	 * @return ArrayList of OpenCV matrices
	 * @throws Exception No input to stack
	 */
	public ArrayList<Mat> createStack(File...files) throws Exception;
	
	/**
	 * Sorts the image stack as a function of an EXIF parameter. Ideally we would use focus distance as the variable to sort by.
	 * @param imageStack stack of input images
	 * @return imageStack sorted image stack
	 * @throws Exception No input to sort
	 * @deprecated Focus Distance is not recorded by many cameras
	 */
	public ArrayList<ExifMat> sortInput(ArrayList<Mat> imageStack, ArrayList<Map<Tag, String>> exifStack, Tag exifTag) throws Exception;
	
	/**
	 * 
	 * @param imageStack
	 * @return
	 * @throws Exception
	 */
	public boolean writeOutput(Mat image, String path) throws Exception;


}
