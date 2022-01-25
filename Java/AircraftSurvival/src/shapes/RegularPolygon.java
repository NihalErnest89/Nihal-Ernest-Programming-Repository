package shapes;
import java.awt.Color;

import processing.core.PApplet;
/**
 * This creates a regular polygon of any number of sides and length
 * @author nernest036
 *
 */
public class RegularPolygon extends Shape
{
	private int numSides;
	private double sideLength;
	private double centralAngle;

	/**
	 * Constructs a regular polygon at (0,0) with length 100 and 3 sides
	 */
	public RegularPolygon()
	{
		super(0, 0);
		numSides = 3;
		sideLength = 100;
		centralAngle = (Math.PI * 2.0)/this.numSides;
	}
	/**
	 * Constructs a regular polygon at (x,y) with length and sides
	 * @param x x coordinate of center
	 * @param y y coordinate of center
	 * @param sideLength length of each side
	 * @param numSides number of sides
	 */
	public RegularPolygon(double x, double y, double sideLength, int numSides)
	{
		super(x, y);
		this.numSides = numSides;
		this.sideLength = sideLength;
		centralAngle = (Math.PI * 2.0)/this.numSides;

	}
	/**
	 * draws the regular polygon
	 */
	public void draw(PApplet marker)
	{
		super.draw(marker);
		Circle boi = new Circle(getX(), getY(), calcr() * 2);
		Color xx = new Color(250, 0, 0);
		boi.changeFillColor(xx);
		Circle big = new Circle(getX(), getY(), calcR() * 2);
		boi.fill(false);
		big.fill(false);
		double startingAngle = Math.PI / 2;
//		boi.draw(marker);
//		big.draw(marker);
		Line l1 = Line.createAngleLine(getX(), getY(), startingAngle, calcr());
	    double x22 = l1.getX2();
		double y22 = l1.getY2();
		for(int i = 0; i <= numSides; i++)
		{	
			Line l = Line.createAngleLine(getX(), getY(), startingAngle, calcr());
			l.changeFillColor(xx);
			Line bigL = Line.createAngleLine(getX(), getY(), startingAngle - centralAngle / 2.0, calcR());
			Line side1 = new Line(bigL.getX2(), bigL.getY2(), l.getX2(), l.getY2());
			Line side2 = new Line(x22, y22, bigL.getX2(), bigL.getY2());
			side1.draw(marker);
			side2.draw(marker);
	//		l.draw(marker);
	//		bigL.draw(marker);
		    x22 = l.getX2();
			y22 = l.getY2();		
			startingAngle += centralAngle;
		}
	}
//	public void drawBoundingCircles(PApplet drawer) FINISH LATER
//	{    
//		
//	} 
	/**
	 * Calculates the distance from the center to the center of edges
	 * @return r the distance from the center to the center of edges
	 */
	public double calcr()
	{
		double r = sideLength / (2.0 * Math.tan(centralAngle/2));
		return r;
	}
	/**
	 * Calculates the distance from the center to the vertices
	 * @return R the distance from the center to the vertices
	 */
	public double calcR()
	{
		double R = sideLength / (2.0 * Math.sin(centralAngle/2.0));
		return R;
	}
	/**
	 * Accessor for the perimeter/circumference of the shape
	 * @return perimeter
	 */
	public double calcPerimeter()
	{
		return numSides * sideLength;
	}
	public double getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}
	public double getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	public Rectangle getBoundingRectangle() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean isPointInside(double x,double y) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void rotateAboutCenter(double angRad) {
		// TODO Auto-generated method stub
		
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
