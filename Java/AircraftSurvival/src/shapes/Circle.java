package shapes;

import processing.core.PApplet;

/**
 * This class draws a circle and can return its information
 * 
 * @author Nihal Ernest
 * @version 9/27/2019
 *
 */
public class Circle extends Shape {
	private double d;
//	private int r, g, b;

	public Circle() {
		super(0, 0);
		d = 0;
	}

	public Circle(double x, double y, double z) {
		super(x, y);
		d = z;
	}

	/**
	 * Draws the circle at x,y with diameter d, and sets the color to r,g,b values
	 * 
	 * @param marker
	 * @post marker sets fill to (r,g,b). x,y set to center
	 */
	public void draw(PApplet marker) {
		super.draw(marker);
		marker.ellipseMode(processing.core.PConstants.RADIUS);
		marker.ellipse((float) getX(), (float) getY(), (float) d / 2, (float) d / 2);
	}

	/**
	 * Returns the circumference of the circle
	 * 
	 * @return circumference
	 */
	public double getPerimeter() {
		double perimeter = Math.PI * d;
		return perimeter;
	}

	/**
	 * Returns the area of the circle
	 * 
	 * @return area
	 */
	public double getArea() {
		double area = Math.pow((Math.PI * (d / 2.0)), 2);
		return area;
	}

	/**
	 * Determines if the x and y coordinates are within a circle
	 * 
	 * @param x x-coordinate of point
	 * @param y y-coordinate of point
	 * @return result the result of whether the point is in the circle
	 */
	public boolean isPointInside(double x, double y) {
		boolean result = false;
		if ((x > getX() && x < getX() + d) && (y > getY() && y < getY() + d)) {
			result = true;
		}
		return result;
	}

	/**
	 * Moves the circle by x units horizontally and y units vertically
	 * 
	 * @param x horizontal translation of circle
	 * @param y vertical translation of circle
	 */

	/**
	 * Changes the color of the circle
	 * 
	 * @param x the red color component
	 * @param y the green color component
	 * @param z the blue color component
	 * @post The color used in fill is set to (r,g,b)
	 */

	/**
	 * Changes the scaling of the circle
	 * 
	 * @param factor this number is multiplied by the size of the circle to rescale
	 *               it
	 */
	public void scale(double factor) {
		d *= factor;
	}

	@Override
	/**
	 * returns the diameter of the shape
	 * 
	 * @return w diameter of shape
	 */
	public double getWidth() {
		// TODO Auto-generated method stub
		return d;
	}

	@Override
	/**
	 * returns the diameter of the shape
	 * 
	 * @return h diameter of shape
	 */
	public double getHeight() {
		// TODO Auto-generated method stub
		return d;
	}

	@Override
	/**
	 * makes a rectangle around the shape (like a hitbox)
	 * 
	 * @return Rectangle returns a rectangle with the same width, height, and
	 *         location around the shape.
	 */
	public Rectangle getBoundingRectangle() {
		// TODO Auto-generated method stub
		return new Rectangle(getX() - (d / 2), getY() - (d / 2), d, d);
	}

	@Override
	public void rotateAboutCenter(double angRad) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean intersects(Shape other) {
		// TODO Auto-generated method stub

		double xCenter, yCenter, radius;
		xCenter = this.getX();
		yCenter = this.getY();
		radius = this.d / 2;

		boolean intersects = false;
		for (double angle = 0; ((intersects == false) && (angle <= 2 * Math.PI)); angle = angle + Math.PI / 100) {
			Line test = Line.createAngleLine(xCenter, yCenter, angle, radius);
			intersects = test.intersects(other);
		}
		return intersects;
	}

	@Override
	public void setWidth(double d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHeight(double d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean intersectsOnTop(Shape other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean intersectsOnLeft(Shape other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean intersectsOnRight(Shape other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean intersectsOnBottom(Shape other) {
		// TODO Auto-generated method stub
		return false;
	}
}
