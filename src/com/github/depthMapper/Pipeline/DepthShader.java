package com.github.depthMapper.Pipeline;

import com.github.depthMapper.Launcher.Debug;

/**
 * (Depth Mapper using OpenCV 4.1.1)
 *
 * Interpolates the number of input images to the range of 0-255 possible 8-bit values of gray, 
 * with the first image in the stack being closest (white) and the last being farthest (black).
 * 
 * @author Tony Ketcham (ketcham@pnw.edu)
 * @version 1.0
 *
 */
public class DepthShader {
	
	private int numInputs = 0;
	private static int[] depth;
	static final int numDepthValues = 255; //possible shade values of an 8-bit grayscale map
	
	public DepthShader(int numInputs)
	{
		this.numInputs = numInputs;
		depth = new int[numInputs];
		Debug.println();
		Debug.println("Interpolating depth bins to grayscale values...");
		for (int i = 0; i < numInputs; i++) {
			depth[i] = interpolate(i);
		}
		Debug.println("Values interpolated.");
		Debug.println();
		
	}
	
	public void setNumInputs(int numInputs)
	{
		this.numInputs = numInputs;
	}
	
	private int interpolate(int imageIndex) 
	{	
		return ((numInputs - 1) - imageIndex) * numDepthValues / (numInputs - 1);
	}
 

	public int map(int imageIndex)
	{
		return depth[imageIndex];
	}
}
