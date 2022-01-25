/**
 * 
 */
package screens;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import shapes.Rectangle;
import stats.Profile;

/**
 * @author krishan
 *
 */
public class Load extends Screen {
	Profile currentProfile, p1, p2, p3;

	/**Creates a new Screen with 
	 * @param width
	 * @param height
	 * @param menuColor
	 */
	public Load(int width, int height, Color menuColor, Profile currentProfile, Profile p1, Profile p2, Profile p3) {
		super(width, height, menuColor,"Load");
		this.currentProfile = currentProfile;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(PApplet marker) {
		// TODO Auto-generated method stub
		onScreenRectangles = new Rectangle[8];

		Rectangle back, l1, l2, l3, title, load1, load2, load3;

		back = new Rectangle((width - width / 5.), (height - height / 5.), width / 15.0, width / 20.);

		l1 = new Rectangle(width / 10, height / 3, width / 4, height / 3);
		l2 = new Rectangle(l1.getX() + l1.getWidth() * 1.25, l1.getY(), l1.getWidth(), l1.getHeight());
		l3 = new Rectangle(l2.getX() + l2.getWidth() * 1.25, l2.getY(), l2.getWidth(), l2.getHeight());

		title = new Rectangle(width / 10.0, height / 20.0, (width - (width / 5.0)), height / 10.0);

		l1.changeFillColor(menuColor);
		l2.changeFillColor(menuColor);
		l3.changeFillColor(menuColor);
		back.changeFillColor(menuColor);
		title.changeFillColor(menuColor);

		l1.draw(marker);
		l2.draw(marker);
		l3.draw(marker);
		back.draw(marker);
		title.draw(marker);

		onScreenRectangles[0] = back;

		marker.textAlign(PApplet.CENTER, PApplet.TOP);
		marker.textSize((float)back.getHeight());
		marker.fill(Color.BLACK.getRGB());
		marker.text("Load", (float) (title.getX() + title.getWidth() / 2), (float) title.getY());
		marker.textSize(20);
		marker.fill(Color.WHITE.getRGB());
		marker.text("Click on any Profile Slot to Load that File", (float) (title.getX() + title.getWidth() / 2),
				(float) (title.getY() + title.getHeight()));
		marker.fill(Color.BLACK.getRGB());
		marker.text("Back", (float) (back.getX() + back.getWidth() / 2), (float) back.getY());

		marker.textAlign(PApplet.CENTER, PApplet.LEFT);
		marker.fill(Color.WHITE.getRGB());

		if (p1.getStatus() == false) {
			marker.fill(Color.WHITE.getRGB());

			marker.text("" + p1.getProfileName(), (float) (l1.getX() + l1.getWidth() / 2),
					(float) (l1.getY() - l1.getY() / 20));
			Rectangle newProfile1 = new Rectangle(l1.getX() + l1.getWidth() / 4, l1.getY(), l1.getWidth() / 2,
					l1.getHeight() / 5);
			newProfile1.changeFillColor(Color.BLUE);
			newProfile1.draw(marker);

			onScreenRectangles[1] = newProfile1;
		} else {

			load1 = new Rectangle(l1.getX() + l1.getWidth() / 4, l1.getY() + l1.getHeight() * 1.125, l1.getWidth() / 2,
					l1.getHeight() / 5);
			load1.changeFillColor(menuColor);
			load1.draw(marker);

			// System.out.println("Inside Load: "+load1.getX() +" "+load1.getY()+" "+load1.getWidth()+" "+load1.getHeight());
			marker.fill(Color.WHITE.getRGB());
			p1.draw(marker, l1.getX() + l1.getWidth() / 2, l1.getY(), l1.getWidth(), l1.getHeight());
			marker.textAlign(PApplet.CENTER, PApplet.TOP);
			marker.text("Load", (float) (load1.getX() + load1.getWidth() / 2), (float) load1.getY());
			marker.textAlign(PApplet.CENTER, PApplet.LEFT);
			onScreenRectangles[1] = load1;

		}
		if (p2.getStatus() == false) {
			marker.fill(Color.WHITE.getRGB());

			marker.text("" + p2.getProfileName(), (float) (l2.getX() + l2.getWidth() / 2),
					(float) (l2.getY() - l2.getY() / 20));
			Rectangle newProfile2 = new Rectangle(l2.getX() + l2.getWidth() / 4, l2.getY(), l2.getWidth() / 2,
					l2.getHeight() / 5);
			newProfile2.changeFillColor(Color.BLUE);
			newProfile2.draw(marker);
			onScreenRectangles[2] = newProfile2;
		} else {
			load2 = new Rectangle(l2.getX() + l2.getWidth() / 4, l2.getY() + l2.getHeight() * 1.125, l2.getWidth() / 2,
					l2.getHeight() / 5);
			load2.changeFillColor(menuColor);
			load2.draw(marker);

			p2.draw(marker, l2.getX() + l2.getWidth() / 2, l2.getY(), l2.getWidth(), l2.getHeight());
			marker.textAlign(PApplet.CENTER, PApplet.TOP);
			marker.text("Load", (float) (load2.getX() + load2.getWidth() / 2), (float) load2.getY());
			marker.textAlign(PApplet.CENTER, PApplet.LEFT);
			onScreenRectangles[2] = load2;
		}
		if (p3.getStatus() == false) {
			marker.fill(Color.WHITE.getRGB());

			marker.text("" + p3.getStatus(), (float) (l3.getX() + l3.getWidth() / 2),
					(float) (l3.getY() - l3.getY() / 20));
			Rectangle newProfile3 = new Rectangle(l3.getX() + l3.getWidth() / 2, l3.getY(), l3.getWidth() / 2,
					l3.getHeight() / 5);
			newProfile3.changeFillColor(Color.BLUE);
			newProfile3.draw(marker);
			onScreenRectangles[3] = newProfile3;
		} else {
			load3 = new Rectangle(l3.getX() + l3.getWidth() / 4, l3.getY() + l3.getHeight() * 1.125, l3.getWidth() / 2,
					l3.getHeight() / 5);
			load3.changeFillColor(menuColor);
			load3.draw(marker);
			p3.draw(marker, l3.getX() + l3.getWidth() / 2, l3.getY(), l3.getWidth(), l3.getHeight());
			marker.textAlign(PApplet.CENTER, PApplet.TOP);
			marker.text("Load", (float) (load3.getX() + load3.getWidth() / 2), (float) load3.getY());
			marker.textAlign(PApplet.CENTER, PApplet.LEFT);
			onScreenRectangles[3] = load3;
		}
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
