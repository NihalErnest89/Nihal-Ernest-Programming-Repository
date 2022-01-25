package Sprites;

import java.awt.Color;

import processing.core.PApplet;
import shapes.PhysicsShape;
import shapes.Rectangle;
/**
 * This class handles the guns that are used by aircraft, bases and other entities
 * @author Nihal Ernest, Krishan Patel, and Fabio Eirea
 *
 */
public class Gun {

	PhysicsShape barrel;
	Bullet b;

	private int maxClips;

	private int maxBulletsPerClip;

	private int currentClips;

	private int currentBulletsInClip;

	public Gun(double x, double y, double width, double height, int maxClips, int maxBulletsPerClip) {

		barrel = new PhysicsShape(new Rectangle(x, y, width, height), Color.BLACK, Color.BLACK);

		this.maxClips = maxClips;
		this.maxBulletsPerClip = maxBulletsPerClip;
		this.currentClips = this.maxClips;
		this.currentBulletsInClip = this.maxBulletsPerClip;

	}

	/**
	 * Sets a type of bullet for a gun
	 * @param b a type of bullet
	 */
	public void setBulletType(Bullet b) {
		this.b = b;

	}

	/**
	 * Moves the gun to a specific point based on x,y coordinates
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public void moveTo(double x, double y) {
		barrel.setPoint(x, y);
	}
/**
 * Returns the x coordinate of the gun
 * @return x coordinate of the gun
 */
	public double getX() {
		return barrel.getX();
	}

	/**
	 * Returns the y coordinate of the gun
	 * @return y coordinate of the gun
	 */
	public double getY() {
		return barrel.getY();
	}
/**
 * Fires a bullet
 * @return a bullet that is fired
 * 
 */
	public Bullet fireBullet() {
		if (canFire()) {
			// System.out.println(b.getBulletPhysicsShape().getVy());
			Bullet bullet = new Bullet(barrel.getX(), barrel.getY() + barrel.getHeight() + 50, b.getDmg());
			currentBulletsInClip--;
			return bullet;
		} else {

			return null;
		}
	}
/**
 * Checks to see if a gun can fire a bullet based on ammo
 * @return whether or not the gun can fire
 */
	public boolean canFire() {

		if (currentBulletsInClip != 0)
			return true;

		else
			return false;
	}

	/**
	 * Reloads the gun
	 */
	public void reload() {
		if (canReload()) {
			currentClips--;
			currentBulletsInClip = maxBulletsPerClip;
		}else {
			System.out.println("no more Ammo");
		}

	}
/**
 * Checks to see if the gun can reload
 * @return whether or not the gun can reload
 */
	public boolean canReload() {
		if (currentClips != 0)
			return true;
		else
			return false;
	}
	/**
	 * Returns the clip size of the gun
	 * @return maximum bullets in a clip
	 */
	public int getMaxBulletsPerClip() {
		return maxBulletsPerClip;
	}
	/**
	 * Sets the maximum bullets in a clip
	 * @param max maximum bullets in a clip
	 * 
	 */
	public void setMaxBulletsPerClip(int max) {
		maxBulletsPerClip = max;
	}
	/**
	 * Draws the gun on the PApplet 
	 * @param marker the PApplet on which the gun should be drawn
	 */
	public void draw(PApplet marker) {
		// TODO Auto-generated method stub

		barrel.draw(marker);
//		marker.fill(Color.RED.getRGB());
//		marker.stroke(Color.BLACK.getRGB());
//		marker.ellipse((float)barrel.getX(),(float) (barrel.getY()+barrel.getHeight()), 10f, 10f);
	}
}
