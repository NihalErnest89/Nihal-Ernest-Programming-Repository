package screens;
import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import shapes.*;

public abstract class Screen extends PApplet  {
	
	 
	protected ArrayList<Integer> keys;
	protected int width, height;
	protected Color menuColor;
	protected Rectangle[] onScreenRectangles;
	protected double score;
	protected String name;
	/** Creates a new Screen using width, height, menucolor and name
	 * 
	 * @param width width of screen
	 * @param height height of screen
	 * @param menuColor
	 * @param name
	 */
	public Screen(int width, int height, Color menuColor, String name) {
		this.width = width;
		this.height = height;
		this.menuColor = menuColor;
		this.name = name;
	}
/**
 * Draws this screen
 * @param marker
 */
	public abstract void draw(PApplet marker); 
	/**
	 * Adds rectangles to the array
	 * @param r rectangle array
	 */
	public void setOnScreenRectangles(Rectangle[] r) {
		this.onScreenRectangles = r;
	}
	/**
	 * 
	 * @return the rectangle array filled with rectangles
	 */
	public Rectangle[] getOnScreenRectangles() {
		return this.onScreenRectangles;
	}
	
	public boolean isPressed(Integer code) {
		return keys.contains(code);
	}
	
	public void keyReleased() {
		while (keys.contains(keyCode))
			keys.remove(new Integer(keyCode));
	}
	public abstract void keyPressed(ArrayList<Integer> keys);
	
	public  ArrayList<Integer> getKeysPresssed(){
		return this.keys;
	}
/**	Sets the score
 * @param score the score that the user has
 */
	public void setScore(double score) {
	
		this.score = score;
		
	}
	/**
	 * 
	 * @return the Name of the Screen
	 */
	public String getName() {
		return this.name;
	}
		
	
}
