package com.github.depthMapper.FileIO;

import com.thebuzzmedia.exiftool.ExifTool;
import com.thebuzzmedia.exiftool.ExifToolBuilder;
import com.thebuzzmedia.exiftool.Tag;
import com.thebuzzmedia.exiftool.core.StandardTag;
import com.thebuzzmedia.exiftool.exceptions.UnsupportedFeatureException;

import java.io.File;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * (Depth Mapper using OpenCV 4.1.1)
 * 
 * NOT YET FUNCTIONING//May not be useful since discovering most cameras do not record focus distance.
 * 
 * Parses image EXIF data using an enhanced Java integration of Phil Harvey's ExifTool
 * @see https://github.com/mjeanroy/exiftool
 * @see https://static.javadoc.io/com.github.mjeanroy/exiftool-lib/2.2.1/com/thebuzzmedia/exiftool/ExifTool.html
 *
 * @author Tony Ketcham (ketcham@pnw.edu)
 * @version 1.0
 *
 */
public class ExifParser {
	
	public static boolean debug = true;
	private final static String slash = File.separator;
	private static String path = System.getProperty("user.dir") + slash + "src" + slash + "exiftool(-k).exe";
	private static ExifTool exifTool;	


    public Map<Tag, String> parse(File image) throws Exception {
    	try {
        	if (debug) {
    		System.out.println("Launching ExifTool...");
        	System.out.println(path);
        	}
    	        exifTool = new ExifToolBuilder()
    	        	.withPath(path)
    	            //.withPoolSize(10)  // Allow 10 process
    	           // .enableStayOpen()
    	            .build(); 
    	        } catch (UnsupportedFeatureException ex) {
    	        	exifTool = new ExifToolBuilder().build(); // defaults to the most basic exifTool process instance
    	        }
    	
    	try {
        return exifTool.getImageMeta(image, asList(StandardTag.APERTURE, StandardTag.ISO));
    	} catch (Exception E) {
    		System.out.println("Error extracting Exif data from files.");
    		return null;
    	}
    }
    
    public static void main(String[] args) throws Exception {
		ExifParser exif = new ExifParser();
    	//	for (String image : args) {
    		try {
				System.out.println("Tags: " + exif.parse(new File(System.getProperty("user.dir") + slash + "src" + slash + "FileIO" + slash + "test1.jpg")));
			} finally {
				exifTool.close();
			}
    //	}
    }
}