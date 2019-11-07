package com.github.depthMapper.Pipeline;

import java.util.Map;

import org.opencv.core.Mat;

import com.thebuzzmedia.exiftool.Tag;
import com.thebuzzmedia.exiftool.core.StandardTag;

/**
 * (Depth Mapper using OpenCV 4.1.1)
 *
 * Data type for a Mat bound to a Map of its Exif data.
 * 
 * @author Tony Ketcham (ketcham@pnw.edu)
 * @version 1.0
 *
 */
public class ExifMat {

	private Mat image = null;
	private Map<Tag, String> exif = null;
	
	public ExifMat(Mat image, Map<Tag, String> exif){
		this.image = image;
		this.exif = exif;
	}
	
	public Mat getMat() {
		return image;
	}
	
	public Map<Tag, String> getExif() {
		return exif;
	}
	
	public class SortByFocusDistance implements java.util.Comparator<ExifMat>{

		@Override
		public int compare(ExifMat a, ExifMat b) {
			return Integer.parseInt(a.getExif().get(StandardTag.APERTURE)) - Integer.parseInt(b.getExif().get(StandardTag.APERTURE));
		}

	}

}
