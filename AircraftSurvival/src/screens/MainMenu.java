package screens;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import shapes.Rectangle;

public class MainMenu extends Screen {
/**
 * Creates a new screen with main mnue data loaded in
 * @param width
 * @param height
 * @param menuColor
 */
	public MainMenu(int width, int height,Color menuColor) {
		super(width, height, menuColor, "MainMenu");
		// TODO Auto-generated constructor stub
	}


	@Override
	public void draw(PApplet marker) {
		// TODO Auto-generated method stub
		Rectangle title, play, controls, settings, exit;
		
		
		title = new Rectangle(width / 10.0, height / 20.0, (width - (width / 5.0)), height / 10.0);
		play = new Rectangle(width / 2.0 - (title.getWidth() / 6.0), ((height / 2) - (height / 5.0)),
				title.getWidth() / 3.0, height / 10.0);
		controls = new Rectangle(play.getX(), play.getY() + (play.getHeight() * 1.5), play.getWidth(),
				play.getHeight());
		settings = new Rectangle(controls.getX(), controls.getY() + (controls.getHeight() * 1.5),
				controls.getWidth(), controls.getHeight());
		exit = new Rectangle(settings.getX(), settings.getY() + (settings.getHeight() * 1.5), settings.getWidth(),
				settings.getHeight());

		title.changeFillColor(Color.YELLOW);
		play.changeFillColor(menuColor);
		settings.changeFillColor(menuColor);
		exit.changeFillColor(menuColor);
		controls.changeFillColor(menuColor);

		title.draw(marker);
		play.draw(marker);
		controls.draw(marker);
		settings.draw(marker);
		exit.draw(marker);

		onScreenRectangles = new Rectangle[4];
		onScreenRectangles[0] = exit;
		onScreenRectangles[1] = play;
		onScreenRectangles[2] = controls;
		onScreenRectangles[3] = settings;

		marker.fill(Color.BLACK.getRGB());
		if(title.getWidth() / 12 <  2 * title.getHeight() / 3)
			marker.textSize((float)title.getWidth() / 12);
		else
			marker.textSize((float) (5 * title.getHeight() / 6));
		marker.text("AIRCRAFT SURVIVAL", (float) (title.getX() + title.getWidth() / 2), (float) title.getY());

		marker.fill(Color.BLACK.getRGB());
		marker.textAlign(PApplet.CENTER, PApplet.TOP);
		if(play.getWidth() / 5 < (2 * play.getHeight() / 3))
			marker.textSize((float)play.getWidth() / 5);
		else
			marker.textSize((float) (2 * play.getHeight()/ 3));
		marker.text("Play", (float) (play.getX() + play.getWidth() / 2), (float) play.getY());
		marker.text("Controls", (float) (controls.getX() + controls.getWidth() / 2), (float) controls.getY());
		marker.text("Settings", (float) (settings.getX() + settings.getWidth() / 2), (float) settings.getY());
		marker.text("Exit", (float) (exit.getX() + exit.getWidth() / 2), (float) exit.getY());
	}

	@Override
	public void keyPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(ArrayList<Integer> keys) {
		// TODO Auto-generated method stub
		
	}

	
}
