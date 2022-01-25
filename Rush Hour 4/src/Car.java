/*
 * Coded by Nihal Ernest
 * 
 * Final Test
 */
import java.awt.*;

public class Car extends MovingImage {

	// FIELDS
	private boolean inLane;

	// CONSTRUCTOR
	public Car(int x, int y) 
	{
		super("blue-car.png", x, y, 50, 100);

	}
	
	// METHODS
	public void moveLeft(int x) 
	{
		moveByAmount(x, 0);
	}

}
