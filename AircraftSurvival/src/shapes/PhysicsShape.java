package shapes;
import java.awt.Color;

import processing.core.PApplet;

/**
 * This class handles the physics of all moving objects in the program
 * @version Alpha
 * @author Krishan Patel and Nihal Ernest
 *
 *
 */
public class PhysicsShape  {

	Shape s;

	private double vx, vy, drag;
	private double TERMINAL_VELOCITY = 4;

	/**
	 * Constructs a new physics shape based on another shape, fill color, and outline color
	 * @param newShape the shape on which the physics is based
	 * @param fillColor the color which the shape should be filled with
	 * @param strokeColor the outline color of the shape
	 */
	public PhysicsShape(Shape newShape, Color fillColor, Color strokeColor) {
		this.s = newShape;
		s.changeFillColor(fillColor);
		s.changeStrokeColor(strokeColor);
		drag = 0.2;
		
}
 /**
  * Sets the position of the shape
  * @param x new x coordinate of the shape
  * @param y new y coordinate of the shape
  */
	public void setPoint(double x, double y) {
		s.setPoint(x, y);
	}
	/**
	 * Sets the Velocity of the shape
	 * @param vx the horizontal velocity
	 * @param vy the vertical velocity
	 */
	public void setVelocity(double vx, double vy) {
		this.vx = vx;
		this.vy = vy;
	}
	/**
	 * Returns the horizontal velocity of the physics shape
	 * @returnt he horizontal velocity of the physics shape
	 */
	public double getVx() {
		return vx;
	}
	/**
	 * Returns the vertical velocity of the physics shape
	 * @return the vertical velocity of the physics shape
	 */
	public double getVy() {
		return vy;
	}
	/**
	 * Changes the color of the shape
	 * @param c the new color of the shape
	 */
	public void changeColor(Color c)
	{
		s.changeFillColor(c);
	}
	/**
	 * Moves the object by adding the velocity to the position
	 */
	public void move() {
		s.setPoint(s.getX() + vx, s.getY() + vy);
	}
/**
 * Accelerates the shape by adding to the velocities as long as thy don't exceed terminal velocity
 * @param x the x component of acceleration
 * @param y the y component of acceleration
 */
	public void accelerate(double x, double y) {
		if (Math.abs(vx) < TERMINAL_VELOCITY)
			vx += x;
		if (Math.abs(vy) < TERMINAL_VELOCITY)
			vy += y;
	}
	/**
	 * Adds drag force to the plane to prevent constant velocity
	 */
	public void drag() {
		if (Math.abs(vx) < drag) // Deals with tiny horizontal drift
			vx = 0;
		if (Math.abs(vy) < drag) // Deals with tiny vertical drift
			vy = 0;

		if (vx > 0) 
		{
			vx -= drag;
		} 
		else if (vx < 0) 
		{
			vx += drag;
		}
		if (vy > 0) 
		{
			vy -= drag;
		} 
		else if (vy < 0) 
		{
			vy += drag;
		}

	}
	/**
	 * Returns the x coordinate of the shape
	 * @return x coordinate of the shape
	 */
	public double getX()
	{
		return s.getX();
	}
	/**
	 * Returns the y coordinate of the shape
	 * @return y coordinate of the shape
	 */
	public double getY()
	{
		return s.getY();
	}
	/**
	 * Returns the width of the shape
	 * @return	width of the shape
	 */
	public double getWidth()
	{
		return s.getWidth();
	}
	/**
	 * Returns height of the shape
	 * @return height of the shape
	 */
	public double getHeight()
	{
		return s.getHeight();
	}
	public void draw (PApplet marker) {
		s.draw(marker);
	}
	/**
	 * Sets a new Terminal Velocity
	 * @param x terminal velocity
	 */
	public void setTV(double x)
	{
		TERMINAL_VELOCITY = x;
	}
	public double getDirectionOfTravel() {
		double gulag;
		if(vy == 0) {
			if(vx > 0) gulag = Math.PI / 2;
			else if(vx < 0) gulag = -Math.PI / 2;
			else gulag = 0;
		}
		else gulag = Math.atan(vx/vy);
		return gulag;
	}
	public Shape getShape() {
		return s;
	}

		
	}
