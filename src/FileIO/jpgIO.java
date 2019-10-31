package FileIO;

import java.io.File;
import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * Depth Mapper using OpenCV 4.1.1
 * 
 * Input/Output for JPG files.
 *
 * @author Tony Ketcham (ketcham@pnw.edu)
 * @version 1.0
 *
 */
public class jpgIO implements fileManagement {

	public ArrayList<Mat> imageStack = new ArrayList<Mat>();
	private String path;
	
	@Override
	public ArrayList<Mat> formInput(File... files) {
		for (File file : files) {		
			String fileName = file.getName();
			imageStack.add(Imgcodecs.imread(path + fileName));
		}
		return null;
	}

	@Override
	public ArrayList<Mat> sortInput(ArrayList<?> imageStack) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean writeOutput(ArrayList<?> imageStack) {
		// TODO Auto-generated method stub
		return false;
	}

}
