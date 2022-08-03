package screens;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import shapes.Rectangle;

/**
 * Represents a victory screen that is shown after defeating the boss
 * @author Nihal Ernest
 *
 */
public class GoodGame extends Screen {

	private double score;
	public GoodGame(int width, int height, Color menuColor) {
		super(width, height, menuColor,"GoodGame!");
		score = 0;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(PApplet marker) {
		// TODO Auto-generated method stub
	
		Rectangle menu, exit;

		menu = new Rectangle(width / 2.0 - width / 6, ((height / 2)),
				width / 3.0, height / 10.0);

		exit = new Rectangle(menu.getX(), menu.getY() + (menu.getHeight() * 1.5), menu.getWidth(),
				menu.getHeight());

		menu.changeFillColor(menuColor);
		exit.changeFillColor(menuColor);

		menu.draw(marker);
		exit.draw(marker);

		onScreenRectangles = new Rectangle[2];
		onScreenRectangles[0] = menu;
		onScreenRectangles[1] = exit;

		marker.fill(Color.BLACK.getRGB());

		marker.textSize((float)menu.getWidth() / 3);
		marker.text("Well Played!", (float) (width/2), (float) height / 7);
		
		marker.textSize((float)menu.getWidth() / 6);
		marker.text("Score: " + (int)score, (float) (width/2), (float) height / 3);

		marker.textAlign(PApplet.CENTER, PApplet.TOP);
		if(menu.getWidth() / 5 < (2 * menu.getHeight() / 3))
			marker.textSize((float)menu.getWidth() / 5);
		else
			marker.textSize((float) (2 * menu.getHeight()/ 3));
		marker.text("Return to Menu", (float) (menu.getX() + menu.getWidth() / 2), (float) menu.getY());
		marker.text("Exit", (float) (exit.getX() + exit.getWidth() / 2), (float) exit.getY());
	}

	public void setScore(double score)
	{
		this.score = score;
	}

	@Override
	public void keyPressed(ArrayList<Integer> keys) {
		// TODO Auto-generated method stub
		
	}

}
