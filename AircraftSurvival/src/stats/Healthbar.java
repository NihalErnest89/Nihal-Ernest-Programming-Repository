package stats;

import java.awt.Color;

import processing.core.PApplet;

/**
 * A class that creates a healthbar that can be used by aircraft, bases or other entities
 * @author Krishan Patel
 *
 */
public class Healthbar extends Progressbar {

	private boolean isAlive;

	public Healthbar(double x, double y, double width, double height, double currentHealth, double maxHealth,
			boolean draw) {
		super(x, y, width, height, maxHealth, maxHealth, Color.GREEN, draw);

		isAlive = !super.getisZero();
		// TODO Auto-generated constructor stub
	}

	public boolean isAlive() {
		return isAlive;
	}

	/**
	 * Increases the health in the healthbar by a certain number of hitpoints
	 * @param hp the number of hitpoints by which the health should increase
	 */
	public void increaseHealth(double hp) {
		super.increase(hp);
		isAlive = !super.getisZero();
	}
	/**
	 * Decreases the health in the healthbar by a certain number of hitpoints
	 * @param hp the number of hitpoints by which the health should decrease
	 */
	public void decreaseHealth(double hp) {
		super.decrease(hp);
		isAlive = !super.getisZero();
	}

	public void draw(PApplet marker) {
		isAlive = !super.getisZero();
		if(isAlive) super.draw(marker);
	}

	/**
	 * Sets a boolean of whether the healthbar is at alive or dead status
	 * @param x a boolean that determines whether the healthbar is alive or dead
	 */
	public void setIsAlive(boolean x) {
		this.isAlive = x;
	}
	
}
