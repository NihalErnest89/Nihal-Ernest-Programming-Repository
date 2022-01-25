package shapes;

import processing.core.PApplet;

/**
 * This class draws a rectangle and can return its information
 * 
 * @author Nihal Ernest
 * @version 9/27/2019
 */
public class Rectangle extends Shape {
	private double w, h;

//	private int r, g, b;
	/**
	 * Constructs a rectangle with x, y, width and height set to zero
	 */
	public Rectangle() {
		super(0, 0);
		w = 0;
		h = 0;
	}

	/**
	 * Constructs a Rectangle object with the top left corner at (x,y) width and
	 * height determined by their corresponding-named variables
	 */
	public Rectangle(double x, double y, double width, double height) {
		super(x, y);

		w = width;
		h = height;
	}

	/**
	 * Draws the rectangle
	 * 
	 * @param marker
	 * @post post the color fill is set to (r,g,b) values and stroke weight is set
	 *       to strokeWeight
	 */
	public void draw(PApplet marker) {
		super.draw(marker);
		marker.rect((float) getX(), (float) getY(), (float) w, (float) h);
	}

	/**
	 * Returns the perimeter of the rectangle
	 * 
	 * @return perimeter
	 */
	public double getPerimeter() {
		double perimeter = 2 * ((w - getX()) + (h - getY()));
		return perimeter;
	}

	/**
	 * Returns the area of the rectangle
	 * 
	 * @return area
	 */
	public double getArea() {
		double area = (w - getX()) * (h - getY());
		return area;
	}

	/**
	 * Determines if the x and y coordinates are within a rectangle
	 * 
	 * @param x x-coordinate of point
	 * @param y y-coordinate of point
	 * @return result the result of whether the point is in the rectangle
	 */
	public boolean isPointInside(double x, double y) {
		boolean result = false;

		if ((x >= getX() && x <= getX() + w) && (y >= getY() && y <= getY() + h)) {
			result = true;
		}
		return result;
	}

	/**
	 * Moves the rectangle by x units horizontally and y units vertically
	 * 
	 * @param x horizontal translation of rectangle
	 * @param y vertical translation of rectangle
	 */

	/**
	 * Changes the scaling of the height of this rectangle
	 * 
	 * @param factor this number is multiplied by the size of the current height
	 *               rectangle to rescale it
	 */
	public void scaleHeight(double factor) {
		h *= factor;

	}

	/**
	 * Changes the scaling of the width of this Rectangle
	 * 
	 * @param factor this number is multiplied by the size of the current width
	 *               rectangle to rescale it
	 */
	public void scaleWidth(double factor) {
		w *= factor;
	}

	/**
	 * Changes this rectangles width
	 * 
	 * @param newWidth the new width
	 */
	public void setWidth(double newWidth) {
		this.w = newWidth;
	}

	/**
	 * Changes this rectangles height
	 * 
	 * @param newHeight the new height
	 */
	public void setHeight(double newHeight) {
		this.h = newHeight;
	}

	/**
	 * returns the width of the shape
	 * 
	 * @return w width of shape
	 */

	public double getWidth() {
		// TODO Auto-generated method stub
		return w;
	}

	/**
	 * returns the height of the shape
	 * 
	 * @return h height of shape
	 */
	public double getHeight() {
		// TODO Auto-generated method stub
		return h;
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
		return null;
	}

	@Override
	public void rotateAboutCenter(double angRad) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean intersects(Shape other) {
		// TODO Auto-generated method stub
		boolean intersects;
		if (other instanceof Line) {
			intersects = other.intersects(this);
			return intersects;
		} else if (other instanceof Rectangle) {

			boolean c1 = this.getX() <= other.getX() && other.getX() <= this.getX() + this.getWidth();
			boolean c2 = this.getY() <= other.getY() && other.getY() <= this.getY() + this.getHeight();

			boolean c3 = other.getX() <= this.getX() && this.getX() <= other.getX() + other.getWidth();
			boolean c4 = other.getY() <= this.getY() && this.getY() <= other.getY() + other.getHeight();

			if (c1 && c2) {
				return true;
			} else if (c3 && c4) {
				return true;
			} else {
				return false;
			}
		} else {
			System.out.println("Error Unknown Shape");
			return false;
		}
	}

	public boolean intersectsOnTop(Shape other) {
		if (other instanceof Rectangle) {
			Rectangle temp = (Rectangle) other;
			Line top = new Line(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY());
			return top.intersects(temp);
		} else 
			return false;
	
	}

	public boolean intersectsOnLeft(Shape other) {
		if (other instanceof Rectangle) {
			Rectangle temp = (Rectangle) other;
			Line left = new Line(this.getX(), this.getY(), this.getX() , this.getY()+this.getHeight());
			return left.intersects(temp);
		} else 
			return false;
	}

	public boolean intersectsOnRight(Shape other) {
		if (other instanceof Rectangle) {
			Rectangle temp = (Rectangle) other;
			Line right = new Line(this.getX()+this.getWidth(), this.getY(), this.getX() + this.getWidth(), this.getY()+this.getHeight());
			return right.intersects(temp);
		} else 
			return false;
	}

	public boolean intersectsOnBottom(Shape other) {
		if (other instanceof Rectangle) {
			Rectangle temp = (Rectangle) other;
			Line bottom = new Line(this.getX(), this.getY()+this.getHeight(), this.getX() + this.getWidth(), this.getY()+this.getHeight());
			return bottom.intersects(temp);
		} else 
			return false;
	}

	public boolean hasSameParameters(Rectangle other) {
		boolean p = false;
		if (other.getX() == this.getX())
			if (other.getY() == this.getY())
				if (other.getWidth() == this.getWidth())
					if (other.getHeight() == this.getHeight())
						p = true;
		return p;
	}
}
