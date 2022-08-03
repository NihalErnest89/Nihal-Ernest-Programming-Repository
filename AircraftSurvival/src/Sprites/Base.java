package Sprites;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PImage;
import shapes.PhysicsShape;
import shapes.Rectangle;
import shapes.Shape;
import stats.Healthbar;

public class Base {
	PhysicsShape p;
	Healthbar h;
//	Gun g;
	Bullet[] fired;
	private int index;
	private int inBounds;

	/**
	 * Creates a new Base with its own moveable shape, healthbar, and a gun with a
	 * certain type of bullet
	 * 
	 * @param p
	 * @param h
	 * @param g
	 * @param b
	 */
	public Base(double x, double y, double width, double height) {
		p = new PhysicsShape(new Rectangle(x, y, width, height) , Color.BLUE, Color.BLACK);
		
		
		this.h = new Healthbar(x, y+height*1.5, width*1.5, height/10, 200, 200, true);
//		this.g = new Gun(x+width/2-width/20, y+height, width/10, height/2, 12, 30);
		
//		this.g.setBulletType(new Bullet(g.getX(), g.getY(), 20));
//		fired = new Bullet[g.getMaxBulletsPerClip()];
		index = 0;
	}
/**
 * Accelerates the base by an x and y vector
 * @param x horizontal acceleration
 * @param y vertical acceleration
 */
	public void accelerate(double x, double y) {

		p.accelerate(x, y);
//		g.barrel.accelerate(x, y);
		h.getPhysicsShape().accelerate(x, y);
	}
	/**
	 * Sets the velocity of the base
	 * @param x horizontal velocity
	 * @param y
	 */
	public void setVelocity(double x, double y) {
		p.setVelocity(x, y);
//		g.barrel.setVelocity(x, y);
		h.getPhysicsShape().setVelocity(x, y);
	}
	/**
	 * Moves the base by the set velocities
	 */
	public void move() {
		p.move();
//		g.barrel.move();
		h.getPhysicsShape().move();
	}


	public void draw(PApplet marker, PImage img) {
		UpdateHealth();
		if (h.isAlive()) {
			h.draw(marker);
			if(img!=null)
			marker.image(img, (float) p.getX(), (float) p.getY(), (float) p.getWidth(), (float) p.getHeight());
			else
				p.draw(marker);
//			g.draw(marker);
//			if (!g.canFire())
//				g.reload();
//			if (index > g.getMaxBulletsPerClip())
//				index = 0;

		} else {
//			this.p = null;
//			this.g = null;
//			this.h = null;
		}
	}

	/**
	 * Adds drag force to the Base to prevent constant velocity
	 */
	public void drag() {
		p.drag();
//		g.barrel.drag();
		h.getPhysicsShape().drag();

	}

	/**
	 * Checks to see if the plane's position is still within the window
	 * 
	 * @param width  width of the screen
	 * @param height height of the screen
	 * @return returns an int that shows where it exceeds bounds or if it's in. (0-
	 *         in bounds, 1- right side, 2- Left side, 3- Below, 4- Above)
	 */
	public int inBounds(int width, int height) {
		if ((p.getX() >= width - p.getWidth())) { // If the base goes too far to the right
			inBounds = 1;
		} else if (p.getX() <= 0) // If the plane goes too far to the left
			inBounds = 2;
		else if (p.getY() <= 0) // If the plane goes too far up
			inBounds = 3;
		else if (p.getY() >= height - p.getHeight()) // if the plane goes too far down
			inBounds = 4;
		else // If the plane is in bounds
			inBounds = 0;
		return inBounds;
	}

	/**
	 * Returns the physics shape of the tree
	 * 
	 * @return the physics shape of the tree
	 */
	public Shape getShape() {
		return p.getShape();
	}

	/**
	 * Checks to see if the base is intersecting another object
	 * @param other the other object
	 * @return whether or not they intersect
	 */
	public boolean intersects(Object other) {
		boolean intersects;
		if (other instanceof Plane) {
			Plane temp = (Plane) other;
			intersects = this.getShape().intersects(temp.getPlaneShape());
			return intersects;
		} else if (other instanceof Bullet) {
			Bullet temp = (Bullet) other;
			intersects = this.getShape().intersects(temp.getBulletShape());
			return intersects;
		}

		return false;
	}

	/**
	 * Returns the healthbar of the base
	 * @return the healthbar of the base
	 */
	public Healthbar getHealthbar() {
		return h;
	}

//	public Shape getBarrel() {
//		return this.g.barrel.getShape();
//	}

	/**
	 * Updates the healthbar of the base
	 */
	public void UpdateHealth() {
		h.setIsAlive(!h.getisZero());
		h.updateProgressbar();
	}

	public Bullet[] returnFiredBullets() {
		return fired;
	}

//	public Gun getGun() {
//		return g;
//	}
/**
 * Reset the base and it's healthbar to a certain position
 * @param x x position of reset
 * @param y y position of reset
 */
	public void reset(double x, double y) {
//		this.g.reload();
		this.getShape().setPoint(x, y);
		this.getHealthbar().reset();

//		this.getBarrel().setPoint(
//				this.getShape().getX() + this.getShape().getWidth() / 2 - this.getShape().getWidth() / 20,
//				this.getShape().getY() + this.getShape().getHeight());

	}

}
