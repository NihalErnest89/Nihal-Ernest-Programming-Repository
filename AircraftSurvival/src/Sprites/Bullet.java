package Sprites;

import java.awt.Color;
import java.awt.Stroke;

import processing.core.PApplet;
import shapes.Line;
import shapes.PhysicsShape;
import shapes.Rectangle;
import shapes.Shape;

/**
 * This class represents the bullet fired from a gun of a player, enemy, or other entity
 * @author Nihal Ernest, Fabio Eirea, Krishan Patel
 *
 */
public class Bullet {

	PhysicsShape p;

	double dmg;

	public Bullet(double x1, double y1, double bulletdmg) {

		this.dmg = bulletdmg;

		p = new PhysicsShape(new Rectangle(x1, y1 - 40, 5, 40), Color.YELLOW, Color.YELLOW);
		

	}
	/**
	 * returns the damage that the bullet deals
	 * @return bullet damage
	 */
	public double getDmg() {
		return this.dmg;
	}
	/**
	 * moves the bullet
	 */
	public void move() {
		p.move();
	}
	/**
	 * Sets the velocity of the bullet
	 * @param vx: x-velocity
	 * @param vy: y-velocity
	 */
	public void setVelocity(double vx, double vy) {
		p.setVelocity(vx, vy);
	}
	/**
	 * draw the bullet
	 * @param marker: The DrawingSurface
	 */
	public void draw(PApplet marker) {
		
		p.move();
		p.draw(marker);
	}

	/**
	 * Adds drag force to the bullet to prevent constant velocity
	 */
	public void drag() {
	}
	
	/**
	 * Returns the shape of by the bullet
	 * @return the shape
	 */
	public Shape getBulletShape() {
		return p.getShape();
	}
	/**
	 * Returns the PhysicsShape object of the bullet
	 * @return the PhysicsShape object of the bullet
	 */
	public PhysicsShape getBulletPhysicsShape() {
		return p;
	}
	
	/**
	 * Checks whether it hit a plane
	 * @param other the plane being checked
	 * @return whether the plane got hit by the bullet
	 */
	public boolean intersects(Plane other) {
		boolean intersects = false;
		if (other != null) {
			if (other.getPlaneShape() instanceof Rectangle)
				intersects = other.getPlaneShape().intersects(getBulletShape());
		}
		return intersects;

	}
}
