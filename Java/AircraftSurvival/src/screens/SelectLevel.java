/**
 * 
 */
package screens;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import shapes.Rectangle;

/**
 * @author krish
 *
 */
public class SelectLevel extends Screen {

	/**Creates a new screen
	 * @param width
	 * @param height
	 * @param menuColor
	 */
	public SelectLevel(int width, int height, Color menuColor) {
		super(width, height, menuColor, "SelectLevel");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(PApplet marker) {
		// TODO Auto-generated method stub
		Rectangle lvl1, lvl2, lvl3, lvl4, lvl5, back;

		lvl1 = new Rectangle(width / 10.0, height / 3.0, width / 10., height / 10);
		lvl2 = new Rectangle(lvl1.getX() + lvl1.getWidth() * 1.25, lvl1.getY(), lvl1.getWidth(), lvl1.getHeight());
		lvl3 = new Rectangle(lvl2.getX() + lvl2.getWidth() * 1.25, lvl2.getY(), lvl2.getWidth(), lvl2.getHeight());
		lvl4 = new Rectangle(lvl3.getX() + lvl3.getWidth() * 1.25, lvl3.getY(), lvl3.getWidth(), lvl3.getHeight());
		lvl5 = new Rectangle(lvl4.getX() + lvl4.getWidth() * 1.25, lvl4.getY(), lvl4.getWidth(), lvl4.getHeight());
		back = new Rectangle((width - width / 10.), (height - height / 5.), width / 15.0, width / 20.);

		lvl1.changeFillColor(menuColor);
		lvl2.changeFillColor(menuColor);
		lvl3.changeFillColor(menuColor);
		lvl4.changeFillColor(menuColor);
		lvl5.changeFillColor(menuColor);
		back.changeFillColor(menuColor);

		lvl1.draw(marker);
//		lvl2.draw(marker);
//		lvl3.draw(marker);
//		lvl4.draw(marker);
//		lvl5.draw(marker);
		back.draw(marker);

		marker.fill(Color.BLACK.getRGB());
		if(lvl1.getHeight() / 2 < lvl1.getWidth() / 3)
			marker.textSize((float)lvl1.getHeight() / 2);
		else
			marker.textSize((float)lvl1.getWidth() / 3);
		marker.textAlign(PApplet.CENTER, PApplet.TOP);
		marker.text("Back", (float) (back.getX() + back.getWidth() / 2), (float) back.getY());
		marker.text("LVL 1", (float) (lvl1.getX() + lvl1.getWidth() / 2), (float) lvl1.getY());
//		marker.text("LVL 2", (float) (lvl2.getX() + lvl2.getWidth() / 2), (float) lvl2.getY());
//		marker.text("LVL 3", (float) (lvl3.getX() + lvl3.getWidth() / 2), (float) lvl3.getY());
//		marker.text("LVL 4", (float) (lvl4.getX() + lvl4.getWidth() / 2), (float) lvl4.getY());
//		marker.text("LVL 5", (float) (lvl5.getX() + lvl5.getWidth() / 2), (float) lvl5.getY());

		onScreenRectangles = new Rectangle[6];
		onScreenRectangles[0] = back;
		onScreenRectangles[1] = lvl1;
//		onScreenRectangles[2] = lvl2;
//		onScreenRectangles[3] = lvl3;
//		onScreenRectangles[4] = lvl4;
//		onScreenRectangles[5] = lvl5;
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
