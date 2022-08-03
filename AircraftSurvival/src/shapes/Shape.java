package shapes;

import java.awt.Color;

import processing.core.PApplet;

/**
 * The super class of shapes
 * 
 * @author nernest036, Krishan Patel
 *
 */
public abstract class Shape {
	// FIELDS
	private double x, y, orientation;
	private int strokeWeight;
	private Color fillColor, strokeColor;
	private boolean fill;
	// CONSTRUCTORS

	public Shape(double x, double y) {
		this.x = x;
		this.y = y;
		orientation = 0; 
		strokeWeight = 1;
		fill = true;
		this.fillColor = Color.WHITE;
		this.strokeColor = Color.BLACK;
	}

	/**
	 * @post the stroke weight and stroke are modified
	 * @param drawer
	 */
	public void draw(PApplet drawer) {
		drawer.strokeWeight((float) strokeWeight);
		drawer.stroke(strokeColor.getRGB());
		if (fill == true) {
			drawer.fill(fillColor.getRGB());
		} else {
			drawer.noFill();
		}
	}

	/**
	 * Changes the internal Color of the shape
	 * @param newFillColor the new desired interior color
	 */
	public void changeFillColor(Color newFillColor) {
		this.fillColor = newFillColor;
	}
	/**Changes the outline Color of the shape
	 * 
	 * @param newStrokeColor the new desired outline color
	 */
	public void changeStrokeColor(Color newStrokeColor) {
		this.strokeColor = newStrokeColor;
	}
	/**
	 * Changes the fill option of the shape
	 * 
	 * @param f boolean for fill or no fill
	 */
	public void fill(boolean f) {
		this.fill = f;
	}

	/**
	 * Changes the stroke weight of the shapes in pixels
	 * 
	 * @param s the stroke weight
	 */
	public void changeStrokeWeight(int s) {
		this.strokeWeight = s;
	}

	/**
	 * returns the width of the shape
	 * 
	 * @return width the width of the shape
	 */
	public abstract double getWidth();

	/**
	 * returns the height of the shape
	 * 
	 * @return height the height of the shape
	 */
	public abstract double getHeight();

	public abstract Rectangle getBoundingRectangle();
/**Rotates the shape around its center.
 * 
 * @param angRad the new angle in RADIANS
 */
	public abstract void rotateAboutCenter(double angRad); 
		
	
	public void setPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Color getFillColor() {
		return this.fillColor;
	}
	public Color getStrokeColor() {
		return this.strokeColor;
	}
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	/**
	 * Determines if the x and y coordinates are within a rectangle
	 * 
	 * @param x x-coordinate of point
	 * @param y y-coordinate of point
	 * @return result the result of whether the point is in the rectangle
	 */
	public abstract boolean isPointInside(double x, double y);

	/**
	 * Accessor for the area of the shape
	 * 
	 * @return area
	 */
	public double getArea() {
		return 0;
	}
	/**
	 * Accessor for the perimeter/circumference of the shape
	 * 
	 * @return perimeter
	 */
	public double getPerimeter() {
		return 0;
	}
	public int getStrokeWeight() {
		return strokeWeight;
	}

	/**
	 * @return the orientation
	 */
	public double getOrientation() {
		return orientation;
	}

	/**
	 * @param orientation the orientation to set
	 */
	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}
	/**
	 * Returns true if this shape is intersecting with the other shape
	 * @param other the Shape to be tested for intersection
	 * @return boolean
	 */
	public abstract boolean intersects(Shape other);
	public abstract boolean intersectsOnTop(Shape other);
	public abstract boolean intersectsOnLeft(Shape other);
	public abstract boolean intersectsOnRight(Shape other);
	public abstract boolean intersectsOnBottom(Shape other);
	public abstract void setWidth(double d);
	public abstract void setHeight(double d);


	}

