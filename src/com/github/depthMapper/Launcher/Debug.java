package com.github.depthMapper.Launcher;

/**
 * (Depth Mapper using OpenCV 4.1.1)
 *
 * @author Tony Ketcham (ketcham@pnw.edu)
 * @version 1.0
 *
 */
public class Debug {
	
	public static boolean on = false;
	
	public static void on() {
		on = true;
	}
	
	public static void off() {
		on = false;
	}
	
	/**
	 * Prints a dashed separator line
	 */
	public static void println() {
		if (on) {
		System.out.println("---------------------");
		} else {			}
	}
	
	/**
	 * syso.println() wrapper
	 * @param string
	 */
	public static void println(String string) {
		if (on) {
		System.out.println(string);
		} else {			}
	}
}
