package shapes;

import processing.core.PApplet;
/**
 * 
 * @author Fabio Eirea
 * @version 5/3/2020
 * 
 */
public class TurnableRectangle extends Shape {
	private float angle;	//The angle it's looking at, in radians, measured clockwise form the vertical
	private float sideLength;
	private float x;
	private float y;
	
	public TurnableRectangle(float x, float y, float angle, float sideLength) {
		super(x, y);
		this.angle = angle;
		this.sideLength = sideLength;
		this.x = x;
		this.y = y;
	}
	public void draw(PApplet p)
	{
		Line side1 = Line.createAngleLine(x, y, angle, sideLength);
		Line side2 = Line.createAngleLine(side1.getX2(), side1.getY2(), angle - Math.PI / 2, sideLength);
		Line side3 = Line.createAngleLine(side2.getX2(), side2.getY2(), angle + Math.PI, sideLength);
		Line side4 = Line.createAngleLine(side3.getX2(), side3.getY2(), angle + Math.PI / 2, sideLength);
		p.quad((float)getX(), (float)getY(), (float)side1.getX2(), (float)side1.getY2(), (float)side2.getX2(), (float)side2.getY2(), (float)side3.getX2(), (float)side3.getY2());

	}
	public void rotate(double angle)
	{
		this.angle += angle;
	}
	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return sideLength;
	}
	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return sideLength;
	}
	@Override
	public Rectangle getBoundingRectangle() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void rotateAboutCenter(double angRad) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean isPointInside(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean intersects(Shape other) {
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