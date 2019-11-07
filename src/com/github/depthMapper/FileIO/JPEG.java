package com.github.depthMapper.FileIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import com.github.depthMapper.Launcher.Debug;
import com.github.depthMapper.Pipeline.ExifMat;
import com.thebuzzmedia.exiftool.Tag;

/**
 * (Depth Mapper using OpenCV 4.1.1)
 * 
 * File manager (I/O) for JPG files.
 *
 * @author Tony Ketcham (ketcham@pnw.edu)
 * @version 1.0
 *
 */
public class JPEG implements FileManager {
	
	@Override
	public File[] grabDirectory(String path) throws Exception 
	{
		try {
			File directory = new File(path);
		
			if (!directory.exists()) 
				throw new FileNotFoundException("Directory not found.");
			 else { 
				Debug.println("Input files found.");
				return directory.listFiles();
			 }
		} catch (FileNotFoundException e) {
			System.out.println(e);
			return null;
		}
	}
	
	@Override
	public ArrayList<Mat> createStack(File... files) throws Exception 
	{
		ArrayList<Mat> imageStack = new ArrayList<Mat>();
		try {
			for (File file : files) {		
				imageStack.add(Imgcodecs.imread(file.getCanonicalPath()));
			}
		
			if (imageStack.isEmpty())
				throw new NullPointerException("No files were loaded.");
			
		} catch (NullPointerException e) {
			System.out.println(e);
			return null;
		}
		Debug.println("Image stack created.");
		Debug.println();
		return imageStack;
	}
	
	/**
	 * Extracts EXIF data from files and returns an array list holding each image's EXIF data.
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Map<Tag, String>> extractExif(File...files) throws Exception 
	{
		ArrayList<Map<Tag, String>> extractedExifList = new ArrayList<Map<Tag, String>>();
		ExifParser exif = new ExifParser();
		
		try {
			for (File file : files) {
				extractedExifList.add(exif.parse(file));
			}
			
			if (extractedExifList.isEmpty())
				throw new NullPointerException("No files were loaded.");
			
		} catch (NullPointerException e) {
			System.out.println(e);
			return null;
		}
		Debug.println("\tEXIF data extracted.");
		Debug.println();
		return extractedExifList;
		
	}

	//Currently not useful since Focus Distance is not recorded by many cameras. We must assume a user-formed input for now.
	@Override
	public ArrayList<ExifMat> sortInput(ArrayList<Mat> imageStack, ArrayList<Map<Tag, String>> exifStack, Tag exifTag) throws Exception {
		ArrayList<ExifMat> stackWithExif = new ArrayList<ExifMat>();
		try {
			if (imageStack.isEmpty()) {
				throw new NullPointerException("Empty image stack.");
			} else if (imageStack.isEmpty()) {
				throw new NullPointerException("Empty exif stack.");
			} else {
				
				//combine the two ArrayLists as a list of ExifMats
				for (int i = 0; i < imageStack.size(); i++) {
					stackWithExif.add(new ExifMat(imageStack.get(i), exifStack.get(i)));	
				}
				
				//sort the stack with exif
				
			}
		} catch (NullPointerException e) {
			System.out.println(e);
			return null;
		}
		
		return null;
	}

	@Override
	public boolean writeOutput(Mat image, String path) throws Exception 
	{
		try {
			if (image.empty()) 
				throw new NullPointerException("Image to be saved is empty.");
			else if (path.isEmpty()) 
				throw new NullPointerException("Save path is not defined.");
			else {
				Imgcodecs.imwrite(path + ".jpg", image);
				Debug.println("\tSaved file as JPEG.");
				return true;
			}
		} catch (NullPointerException e) {
			System.out.println(e);
			return false;
		}

	}

}
