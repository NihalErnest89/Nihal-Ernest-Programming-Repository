package Sprites;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import processing.core.PApplet;
import processing.core.PImage;
import shapes.*;

import stats.Healthbar;

/**
 * This is the alpha verison of the plane class. It is currently a very
 * bare-bones class, but images and more will be implemented in future versions
 * 
 * @version Beta
 * @author Nihal Ernest, Krishan Patel
 *
 */
public class Plane {

	PhysicsShape p;
	Healthbar h;
	Gun g;
	Bullet[] fired;

	private double orientation;
	private int inBounds;
	private int index;
	private Bullet bulletType;
	private boolean user;
	private double startX, startY;

	public Plane(int x, int y, int width, int height, Healthbar h, boolean user) {
		p = new PhysicsShape(new Rectangle(x, y, width, height), Color.BLUE, Color.BLACK);
		this.h = h;
		startX = x;
		startY = y;
		
		index = 0;
		this.user = user;
		bulletType = new Bullet(-10, -10, 10);

		if (isUser()) {
			g = new Gun(p.getX() + p.getWidth() / 2 - p.getWidth() / 20, p.getY(), p.getWidth() / 14,
					-p.getHeight() / 4, 10, 30);
		} else {
			g = new Gun(p.getX() + p.getWidth() / 2 - p.getWidth() / 20, p.getY() + p.getHeight(), p.getWidth() / 14,
					p.getHeight() / 4, 99999, 50);
		}
		fired = new Bullet[50];

		g.setBulletType(bulletType);

	}


	/**
	 * Moves the plane by changing its position based on velocities
	 */
	public void move() {
		p.move();
		g.barrel.move();
		if (!isUser())
			h.getPhysicsShape().move();
	}
	/**
	 * moves the plane to a specific location
	 * @param x: the x-position where the plane will be moved
	 * @param y: the x-position where the plane will be moved
	 */
	public void moveTo(double x, double y) {
		p.setPoint(x, y);
	}

	/**
	 * Returns the X-position of the plane
	 */
	public double getX() {
		return p.getX();
	}

	/**
	 * Returns the Y-position of the plane
	 */
	public double getY() {
		return p.getY();
	}
	/**
	 * Sets the velocity of the plane
	 * @param x: the new x-velocity of the plane
	 * @param y: the new y-velocity of the plane
	 */
	public void setVelocity(double x, double y) {
		p.setVelocity(x, y);
		g.barrel.setVelocity(x, y);
		if (!isUser())
			h.getPhysicsShape().setVelocity(x, y);
	}

	/**
	 * accelerates the aircraft
	 * 
	 * @param x the x component of acceleration
	 * @param y the y component of acceleration
	 */
	public void accelerate(double x, double y) {
		orientation = p.getDirectionOfTravel();
		p.accelerate(x, y);
		g.barrel.accelerate(x, y);
		if (!isUser())
			h.getPhysicsShape().accelerate(x, y);
	}
	/**
	 * makes the plane shoot
	 */
	public void fire() {
		if(index== g.getMaxBulletsPerClip()) {
			index=0;
		}
		fired[index] = g.fireBullet();
		index++;
	}
/**
 * Draws the required components for the aircraft
 * @param marker The PApplet on which the aircraft should be drawn
 * @param img The image that represents the aircraft
 */
	public void draw(PApplet marker, PImage img) {
		UpdatePlaneHealth();
		h.draw(marker);
		
		if (h.isAlive()) {
			h.draw(marker);
			marker.image(img, (float) p.getX(), (float) p.getY(), (float) p.getWidth(), (float) p.getHeight());
			if (!isUser()) {
				h.setPoint(p.getX() - p.getWidth() / 3, p.getY() - h.getHeight());
				if (!g.canFire())
					g.reload();
			} else {
				if (!g.canFire()) {
					marker.textSize(20);
					marker.fill(Color.RED.getRGB());
					marker.text("RELOAD!!!", 0, 8 * marker.height / 10);
				

				}

				
		//		g.draw(marker);

				
			
			}
		} else {
			
//			this.p = null;
//			this.g = null;
//			this.h = null;
		}
	}

	/**
	 * Adds drag force to the plane to prevent constant velocity
	 */
	public void drag() {
		p.drag();
		g.barrel.drag();
		if (!isUser())
			h.getPhysicsShape().drag();

	}

	/**
	 * Increases the speed of the plane by increasing the terminal velocity of the
	 * physics shape
	 * 
	 * @param x: speed
	 */
	public void afterburner(double x) {
		p.setTV(x);
		g.barrel.setTV(x);

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
		if ((p.getX() >= width - p.getWidth())) { // If the plane goes too far to the right
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
	public Shape getPlaneShape() {
		return p.getShape();
	}

	/**
	 * Checks to see if the aircraft intersects another object
	 * @param other another object
	 * @return a boolean representing whether or not the objects intersect
	 */
	public boolean intersects(Object other) {
		boolean intersects;
		if (other instanceof Plane) {
			Plane temp = (Plane) other;
			intersects = this.getPlaneShape().intersects(temp.getPlaneShape());
			return intersects;
		} else if (other instanceof Bullet) {
			Bullet temp = (Bullet) other;
			intersects = this.getPlaneShape().intersects(temp.getBulletShape());
			return intersects;
		}
		else if(other instanceof Base) {
			Base gulag = (Base)other;
			intersects = this.getPlaneShape().intersects(gulag.getShape());
			return intersects;
		}

		return false;
	}
	/**
	 * Returns the plane's healthbar, to use health functions such as
	 * checking whether is alive, or repairing the plane
	 * @return the plane's healthbar
	 */
	public Healthbar getHealthbar() {
		return h;
	}
	/**
	 * Returns the shape used by the gun barrel used by the plane
	 * @return the plane's gun's barrel's shape
	 */
	public Shape getBarrel() {
		return this.g.barrel.getShape();
	}
	/**
	 * updates the plane's health
	 */
	public void UpdatePlaneHealth() {
		h.setIsAlive(!h.getisZero());
		h.updateProgressbar();
	}
	/**
	 * Returns all the bullets fired by the plane
	 * @return an array containing all the bullets fired by this plane
	 */
	public Bullet[] returnFiredBullets() {
		return fired;
	}
	/**
	 * Returns the plane's gun
	 * @return the plane's gun
	 */
	public Gun getGun() {
		return g;
	}
	/**
	 * Resets the plane, and sends it to a specific position
	 * @param x: the new x-position for the plane
	 * @param y: the new y-position for the plane
	 */
	public void reset(double x, double y) {
		this.g.reload();
		this.getPlaneShape().setPoint(x, y);
		this.getHealthbar().reset();
		if (isUser())
			this.getBarrel().setPoint(this.getPlaneShape().getX() + this.getPlaneShape().getWidth() / 2
					- this.getPlaneShape().getWidth() / 20, this.getPlaneShape().getY());
		else {
			this.getBarrel().setPoint(
					this.getPlaneShape().getX() + this.getPlaneShape().getWidth() / 2
							- this.getPlaneShape().getWidth() / 20,
					this.getPlaneShape().getY() + this.getPlaneShape().getHeight());
		}

	}
	/**
	 * resets the plane to its original state and position
	 */
	public void reset() {
		g.canReload();
		h.reset();
		getPlaneShape().setPoint(startX, startY);
		if (isUser())
			this.getBarrel().setPoint(this.getPlaneShape().getX() + this.getPlaneShape().getWidth() / 2
					- this.getPlaneShape().getWidth() / 20, this.getPlaneShape().getY());
		else {
			this.getBarrel().setPoint(
					this.getPlaneShape().getX() + this.getPlaneShape().getWidth() / 2
							- this.getPlaneShape().getWidth() / 20,
					this.getPlaneShape().getY() + this.getPlaneShape().getHeight());
		}
	}

	/**
	 * @return the user
	 */
	public boolean isUser() {
		return user;
	}
}
