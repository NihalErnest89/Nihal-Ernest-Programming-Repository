package screens;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import shapes.Rectangle;

public class Settings extends Screen {
/**
 * Creates new Screen with settings data loaded.
 * @param width
 * @param height
 * @param menuColor
 */
	public Settings(int width, int height, Color menuColor) {
		super(width, height, menuColor,"Settings");
		// TODO Auto-generated constructor stub
	}

	public void draw(PApplet marker) {
		// TODO Auto-generated method stub
		Rectangle title, save, load, back;

		title = new Rectangle(width / 10.0, height / 20.0, (width - (width / 5.0)), height / 10.0);
		save = new Rectangle(width / 2.0 - ((width - (width / 5.0)) / 6.0), ((height / 2) - (height / 5.0)),
				(width - (width / 5.0)) / 3.0, height / 10.0);
		load = new Rectangle(save.getX(), save.getY() + save.getHeight() * 1.5, save.getWidth(), save.getHeight());
		back = new Rectangle((width - width / 10.), (height - height / 5.), width / 15.0, width / 20.);

		title.changeFillColor(menuColor);
		save.changeFillColor(menuColor);
		load.changeFillColor(menuColor);
		back.changeFillColor(menuColor);

		title.draw(marker);
		save.draw(marker);
		load.draw(marker);
		back.draw(marker);

		marker.fill(Color.BLACK.getRGB());
		marker.textSize((float)title.getHeight());
		marker.textAlign(CENTER, TOP);
		marker.text("Settings", (float) (title.getX() + title.getWidth() / 2), (float) title.getY());

		marker.textSize((float)back.getHeight());
		marker.text("Save", (float) (save.getX() + save.getWidth() / 2), (float) save.getY());
		marker.text("Load", (float) (load.getX() + load.getWidth() / 2), (float) load.getY());
		marker.text("Back", (float) (back.getX() + back.getWidth() / 2), (float) back.getY());

		onScreenRectangles = new Rectangle[3];

		onScreenRectangles[0] = back;
		onScreenRectangles[1] = save;
		onScreenRectangles[2] = load;
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
