import java.awt.Color;
import java.util.ArrayList;

import Sprites.Plane;
import processing.core.PApplet;
import screens.Screen;
import shapes.Rectangle;

public class PauseScreen extends Screen {

	Rectangle MainMenuButton;	
	public PauseScreen(int width, int height, Color menuColor) {
		super(width, height, menuColor, "Pause");
		// TODO Auto-generated constructor stub
		
		MainMenuButton = new Rectangle(width/2- width/10,height/2- height/20, width/5, height/10);
		MainMenuButton.changeFillColor(Color.YELLOW);
		MainMenuButton.changeStrokeColor(Color.BLACK);
		MainMenuButton.changeStrokeWeight(1);
	}

	@Override
	public void draw(PApplet marker) {
		// TODO Auto-generated method stub
//	MainMenuButton.changeFillColor(Color.YELLOW);
//	MainMenuButton.changeStrokeColor(Color.BLACK);
	
	MainMenuButton.draw(marker);
	marker.textSize(20);
	
	marker.fill(Color.BLACK.getRGB());
	marker.text("Exit to Main Menu", width/2- width/12, height/2);
	marker.textSize(50);
	marker.text("Press 'P' to unpause ", width/2 - width / 5, height/4);
	onScreenRectangles = new Rectangle[1];
	onScreenRectangles[0] = MainMenuButton;
		
	}

	@Override
	public void keyPressed(ArrayList<Integer> keys) {
		// TODO Auto-generated method stub

	}

}
