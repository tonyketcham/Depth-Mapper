package FileIO;

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
 * Parses image EXIF data using an enhanced Java integration of Phil Harvey's ExifTool
 * @see https://github.com/mjeanroy/exiftool
 * @see https://static.javadoc.io/com.github.mjeanroy/exiftool-lib/2.2.1/com/thebuzzmedia/exiftool/ExifTool.html
 *
 * @author Tony Ketcham (ketcham@pnw.edu)
 * @version 1.0
 *
 */
public class ExifParser {
	
	private final static String slash = File.separator;
	private static String path = System.getProperty("user.dir") + slash + "src" + slash + "exiftool(-k)";

    public static void main(String[] args) throws Exception {

    	System.out.println(System.getProperty("user.dir"));

        ExifTool exifTool = new ExifToolBuilder()
        	.withPath(path)
            .withPoolSize(10)  // Allow 10 process
            .enableStayOpen()
            .build();

        // Start 10 threads and use exifTool in parallel.
        // ...
    }
}