package stats;

import java.awt.Color;

import org.w3c.dom.css.Rect;

import processing.core.PApplet;
import shapes.PhysicsShape;
import shapes.Rectangle;
import shapes.Shape;

//Coded by Krishan Patel
public class Progressbar {

	
	private PhysicsShape outer, inner;
	private double maxNum, currentNum;

	private boolean isZero, draw;

	public Progressbar(double x, double y, double width, double height,double currentNum, double maxHealth, Color innerFillColor,
			boolean draw) {
		isZero = false;
		this.draw = draw;
		this.maxNum = maxHealth;
		this.currentNum = currentNum;
		outer = new PhysicsShape(new Rectangle(x, y, width, height), null, Color.BLACK);
		outer.getShape().fill(false);
		

		inner =  new PhysicsShape( new Rectangle(x, y, 0, outer.getHeight()),innerFillColor,Color.BLACK );
		inner.getShape().fill(true);
		

	}
	public void setPoint(double x, double y)
	{
		inner.setPoint(x, y);
		outer.setPoint(x, y);
	}
	public double getX() {
		return outer.getShape().getX();
	}

	public double getY() {
		return outer.getShape().getY();
	}

	public double getWidth() {
		return outer.getShape().getWidth();
	}

	public double getHeight() {
		return outer.getShape().getHeight();
	}

	public Color getFillColor() {
		return inner.getShape().getFillColor();
	}

	public void decrease(double hp) {
		if (this.currentNum > 0) {
			this.currentNum = this.currentNum - hp;

		} else
			isZero = true;
		updateProgressbar();

	}

	public void increase(double hp) {

		if (currentNum < maxNum) {
			this.currentNum = this.currentNum + hp;
			isZero = false;
		}

		updateProgressbar();
	}

	public void updateProgressbar() {
		double percentage = (double) (this.currentNum / this.maxNum);
		inner.setPoint(outer.getX(), outer.getY());
		inner.getShape().setWidth(outer.getWidth() * percentage);
	}

	public boolean getisZero() {
		return isZero;
	}

	public double getMax() {
		return this.maxNum;
	}

	public void reset() {
		this.isZero = false;
		this.currentNum = this.maxNum;
		updateProgressbar();
		
	}

	public void draw(PApplet marker) {
		updateProgressbar();
		if (draw) {

			outer.draw(marker);
			inner.draw(marker);
			for (double x = 5; x > 0; x--) {
				marker.noFill();
				marker.stroke(Color.black.getRGB());
				marker.rect((float) getX(), (float) getY(), (float) (x * getWidth() / 5), (float) getHeight());
			}
		}
		// TBD
	}
	public PhysicsShape getPhysicsShape() {
		return this.outer;
	}
	public Shape getShape() {
		return this.outer.getShape();
	}
}
