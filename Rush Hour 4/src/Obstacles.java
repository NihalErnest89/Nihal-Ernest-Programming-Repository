import java.awt.Image;

public class Obstacles extends FallingImage {

	private double velX, velY;
	
	public Obstacles(int x, int y)
	{
		super("gray-car.png", x, y, 50, 100);
		velX = 0;
		velY = 0;
	}
	
}
