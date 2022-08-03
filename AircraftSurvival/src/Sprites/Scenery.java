package Sprites;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PImage;
import shapes.Circle;
import shapes.PhysicsShape;
import shapes.Rectangle;
/**
 * This is the tree class, which is currently bare-bones for other scenery later. It currently mirrors the plane class.
 * @version 1.0
 * @author Nihal Ernest
 *
 */
public class Scenery {
	PhysicsShape p;
	
	//private double vx, vy, drag;
	private double orientation;
	private int inBounds;
	public static final double TERMINAL_VELOCITY = 5;

	public Scenery(int x, int y, int width, int height)
	{
		p = new PhysicsShape(new Rectangle(x, y, width, height), Color.GREEN, Color.BLACK);
		orientation = 0;
//		drag = 0.1;
	}
	/**
	 * Moves the tree by changing its position based on velocities
	 */
	public void move()
	{
		p.move();
	}
	/**
	 * accelerates the tree
	 * @param x the x component of acceleration
	 * @param y the y component of acceleration
	 */
	public void accelerate(double x, double y)
	{
		p.accelerate(x, y);
	}
	public void draw(PApplet marker, PImage img)
	{
	//	p.draw(marker);
		marker.image(img, (float)p.getX(), (float)p.getY(), (float)p.getWidth(), (float)p.getHeight());
	}
	/**
	 * Checks to see if the treee's position is still within the window
	 * @param width width of the screen
	 * @param height height of the screen
	 * @return returns an int that shows where it exceeds bounds or if it's in. (0- in bounds, 1- right side, 2- Left side, 3- Below, 4- Above)
	 */
	public int inBounds(int width, int height) {
		if((p.getX() >= width - p.getWidth()))
		{
			inBounds = 1;
		}
		else if(p.getX() <= 0)
			inBounds = 2;
		else if(p.getY() <= 0)
			inBounds = 3;
		else if(p.getY() >= height)
			inBounds = 4;
		else
			inBounds = 0;
		return inBounds;
	}
	/**
	 * Returns the physics shape of the tree
	 * @return the physics shape of the tree
	 */
	public PhysicsShape getTreeShape() {
		return p;
	}
	/**
	 * Sets the position of the tree
	 * @param d x coordinate
	 * @param e y coordinate
	 */
	public void setPoint(double d, double e)
	{
		p.setPoint(d, e);
	}
}
