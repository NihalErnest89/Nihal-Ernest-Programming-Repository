import java.awt.Image;

public class MovingLanes extends MovingImage {

	private double velY;
	
	public MovingLanes(int x, int y) {
		super("dashed-line.png", x, y, 50, 200);
		velY = 10;
	}
	
	public void scroll(MovingImage MovingLanes) {
		moveByAmount(0, (int)velY);
	}


}