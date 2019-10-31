package FileIO;

import java.io.File;
import java.util.ArrayList;

/**
 * (Depth Mapper using OpenCV 4.1.1)
 * 
 * Interface for managing file IO
 *
 * @author Tony Ketcham (ketcham@pnw.edu)
 * @version 1.0
 *
 */
public interface fileManagement {
	
	public ArrayList<?> formInput(File...files);
	public ArrayList<?> sortInput(ArrayList<?> imageStack);
	public boolean writeOutput(ArrayList<?> imageStack);


}
