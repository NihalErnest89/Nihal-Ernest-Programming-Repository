package shapes;

import processing.core.PApplet;

/**
 * This class draws a line and can return its information
 * 
 * @author Nihal Ernest
 * @version 9/27/2019
 *
 */
public class Line extends Shape {
	private float x2, y2, inX, inY;

	public Line(double mX, double mY, double mX2, double mY2) {
		super(mX, mY);
		x2 = (float) mX2;
		y2 = (float) mY2;
	}

	public static Line createAngleLine(double x, double y, double angle, double length) {
		double x2 = x + (length * Math.cos(angle));
		double y2 = y + (length * Math.sin(angle));
		Line yo = new Line(x, y, x2, y2);
		return yo;
	}

	/**
	 * 
	 * @param surface The PApplet that is used to draw
	 */
	public void draw(PApplet surface) {
		super.draw(surface);
		surface.stroke(this.getStrokeColor().getRGB());
		surface.line((float) getX(), (float) getY(), x2, y2);
		surface.ellipse(inX, inY, 5, 5);
	}

	/**
	 * Changes the end point of the line to the mouse pointer location
	 * 
	 * @param mouseX x coordinate of mouse
	 * @param mouseY y coordinate of mouse
	 */
	public void setPoint2(double mouseX, double mouseY) {
		x2 = (float) mouseX;
		y2 = (float) mouseY;

	}

	public double getX2() {
		return x2;
	}

	public double getY2() {
		return y2;
	}

	/**
	 * Checks to see if a the coordinates of a line overlap the coordinates of
	 * another line
	 * 
	 * @param l2 the second line used to check for intersection
	 * @return isIntersect tells if the points intersect
	 */
	private boolean intersects(Line l2) {
		boolean isIntersect = false;
		inX = (float) (((getX() * y2 - getY() * x2) * (l2.getX() - l2.x2)
				- (getX() - x2) * (l2.getX() * l2.y2 - l2.getY() * l2.x2))
				/ ((getX() - x2) * (l2.getY() - l2.y2) - (getY() - y2) * (l2.getX() - l2.x2)));
		inY = (float) (((getX() * y2 - getY() * x2) * (l2.getY() - l2.y2)
				- (getY() - y2) * (l2.getX() * l2.y2 - l2.getY() * l2.x2))
				/ ((getX() - x2) * (l2.getY() - l2.y2) - (getY() - y2) * (l2.getX() - l2.x2)));

		if (((inX >= getX() && inX <= x2) || (inX <= getX() && inX >= x2))
				&& ((inY >= l2.getY() && inY <= l2.y2) || (inY <= l2.getY() && inY >= l2.y2))) {
			isIntersect = true;
			if ((inY >= getY() && inY <= y2) || (inY <= getY() && inY >= y2)) {
				isIntersect = true;
			} else {
				isIntersect = false;
			}
		}

		else if (((l2.getY() == l2.y2 && getY() == y2) || (l2.getX() == l2.x2 && getX() == x2))
				&& (getX() == l2.getX() && getY() == l2.getY())) {
			isIntersect = true;
		}
//		else if((l2.getY() == l2.y2 && getY() == y2) || (l2.getX() == l2.x2 && getX() == x2))
//		{
//			isIntersect = false;
//		}
		return isIntersect;
	}

	@Override
	/**
	 * @return width the x component (width) of the line
	 */
	public double getWidth() {
		// TODO Auto-generated method stub
		return Math.abs(x2 - getX());
	}

	@Override
	/**
	 * @return height the y component (height) of the line
	 */
	public double getHeight() {
		// TODO Auto-generated method stub
		return Math.abs(y2 - getY());
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
		return new Rectangle(getX(), getY(), x2, y2);
	}

	@Override
	public boolean isPointInside(double x, double y) {
		// TODO Auto-generated method stub
		boolean result = false;
		if ((x >= getX() && x <= getX() + x2) && (y >= getY() && y <= getY() + y2)) {
			result = true;
		}
		return result;
	}

	@Override
	public void rotateAboutCenter(double angRad) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean intersects(Shape other) {
		boolean intersects = false;
		if (other instanceof Rectangle) {
			Rectangle r = new Rectangle(other.getX(), other.getY(), other.getWidth(), other.getHeight());
			
			double sign = (r.getWidth() / Math.abs(r.getWidth()));

			for (double shift = 0;  (intersects == false) && (Math.abs(shift) > Math.abs(r.getWidth())); shift++) {
				Line test = new Line(other.getX() + (sign * shift), other.getY(), other.getX() + (sign * shift),
						other.getY() + other.getHeight());
				intersects = this.intersects(test);
			}
			return intersects;
		}
		else if(other instanceof Circle) {
			intersects = other.intersects(this);
			return intersects;
		}
		else if(other instanceof Line)
			intersects = this.intersects(other);
		else {
			System.out.println("UnknownShape. Please Code the intersection");
		}

		// TODO Auto-generated method stub
		return false;
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
