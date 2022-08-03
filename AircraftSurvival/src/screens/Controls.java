/**
 * 
 */
package screens;

import java.awt.Color;
import processing.event.MouseEvent;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import shapes.PhysicsShape;
import shapes.Rectangle;

/**
 * @author krish
 *
 */
public class Controls extends Screen {
	ArrayList<PImage> controls;
	MouseEvent event;
	private boolean moveUp, moveDown;

	PhysicsShape  title, back, background, keysColumn, description;
	private double scroll = 0;

	/**Creates a new screen with Controls
	 * @param width
	 * @param height
	 * @param menuColor
	 * @param controls
	 */
	public Controls(int width, int height, Color menuColor, ArrayList<PImage> controls, MouseEvent e) {
		super(width, height, menuColor, "Controls");
		event = e;
		this.controls = controls;

		title = new PhysicsShape(new Rectangle(width / 10.0, height / 20.0, (width - (width / 5.0)), height / 10.0),
				menuColor, Color.BLACK);

		back = new PhysicsShape(new Rectangle((width - width / 10.), (height - height / 5.), width / 15.0, width / 20.),
				menuColor, Color.BLACK);
		background = new PhysicsShape(new Rectangle(title.getX(), title.getY() + title.getHeight() * 1.25,
				title.getWidth(), height - (title.getY() + title.getHeight() * 1.25)), menuColor, Color.BLACK);
		keysColumn = new PhysicsShape(
				new Rectangle(background.getX(), background.getY(), background.getWidth() / 5, background.getHeight()),
				menuColor, Color.BLACK);
		description = new PhysicsShape(new Rectangle(background.getX() + background.getWidth() / 5, background.getY(),
				4 * background.getWidth() / 5, background.getHeight()), menuColor, Color.BLACK);
//		System.out.println("ran");
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * Draws the Controls Screen
	 */
	public void draw(PApplet marker) { // Once we get an idea of the keybinds, we can implement images of the controls
			
		// - Nihal
		// TODO Auto-generated method stub
	
	//	System.out.println(scroll);
		mouseWheel(event);

		background.move();
		keysColumn.move();
		description.move();
		background.draw(marker);
		keysColumn.draw(marker);
		description.draw(marker);

		/*
		 * Pasting Images below
		 */
		int scale = 0;
		for (PImage img : controls) {

			marker.image(img, (float) (keysColumn.getX()),
					(float) (keysColumn.getY() + keysColumn.getHeight() / 20 + keysColumn.getHeight() / 5 * scale));
			scale++;
		}

		onScreenRectangles = new Rectangle[2];
		onScreenRectangles[0] = (Rectangle) (back.getShape());
		onScreenRectangles[1] = (Rectangle) title.getShape();
		title.draw(marker);
		back.draw(marker);
		/*
		 * Drawing Text on the screen below
		 */
		marker.fill(Color.BLACK.getRGB());
		marker.textAlign(PApplet.CENTER, PApplet.TOP);
		marker.textSize((float) back.getHeight() / 2);
		marker.text("Back", (float) (back.getX() + back.getWidth() / 2), (float) back.getY());

		marker.textSize((float) title.getHeight());
		marker.text("Controls", (float) (title.getX() + title.getWidth() / 2), (float) title.getY());
		}
		// Draw the Controls here


	@Override
	public void keyPressed() {
		// TODO Auto-generated method stub

	}

	public void mouseWheel(MouseEvent event) {
		// TODO Auto-generated method stub
		if (event != null) {
			System.out.println(event.getCount());
			if (event.getCount() > 0) {
				scroll+=10;
			} else if (event.getCount() < 0) {
				scroll-=10;
			} else {
				
			}
		}
		this.event = null;

	}

	@Override
	public void keyPressed(ArrayList<Integer> keys) {
		// TODO Auto-generated method stub

	}

}
