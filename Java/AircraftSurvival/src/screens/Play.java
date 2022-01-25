package screens;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import shapes.Rectangle;

public class Play extends Screen {
/**
 * Creates a new Play Screen with play data loaded
 * @param width
 * @param height
 * @param menuColor
 */
	public Play(int width, int height, Color menuColor) {
		super(width, height, menuColor, "Play");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(PApplet marker) {
		// TODO Auto-generated method stub
		Rectangle playNextMission, selectMission, upgradeAircraft, back;

		playNextMission = new Rectangle(width / 2.0 - ((width - (width / 5.0)) / 6.0),
				((height / 2) - (height / 5.0)), (width - (width / 5.0)) / 3.0, height / 10.0);
		selectMission = new Rectangle(playNextMission.getX(),
				playNextMission.getY() + playNextMission.getHeight() * 1.5, playNextMission.getWidth(),
				playNextMission.getHeight());
//		upgradeAircraft = new Rectangle(selectMission.getX(),
//				selectMission.getY() + selectMission.getHeight() * 1.5, selectMission.getWidth(),
//				selectMission.getHeight());
		back = new Rectangle((width - width / 10.), (height - height / 5.), width / 15.0, width / 20.);

		playNextMission.changeFillColor(menuColor);
		selectMission.changeFillColor(menuColor);
//		upgradeAircraft.changeFillColor(menuColor);
		back.changeFillColor(menuColor);

		playNextMission.draw(marker);
		selectMission.draw(marker);
//		upgradeAircraft.draw(marker);
		back.draw(marker);

		if(playNextMission.getHeight() / 2 < playNextMission.getWidth() / 9)
			marker.textSize((float)(playNextMission.getHeight()/2));
		else
			marker.textSize((float)(playNextMission.getWidth()/9));
		marker.fill(Color.BLACK.getRGB());
		marker.textAlign(PApplet.CENTER, PApplet.TOP);
		marker.text("Play Next Mission", (float) (playNextMission.getX() + playNextMission.getWidth() / 2),
				(float) playNextMission.getY());
		marker.text("Select Mission", (float) (selectMission.getX() + selectMission.getWidth() / 2),
				(float) selectMission.getY());
//		marker.text("Upgrade Aircraft", (float) (upgradeAircraft.getX() + upgradeAircraft.getWidth() / 2),
//				(float) upgradeAircraft.getY());
		marker.text("Back", (float) (back.getX() + back.getWidth() / 2), (float) back.getY());

		onScreenRectangles = new Rectangle[4];
		onScreenRectangles[0] = back;
		onScreenRectangles[1] = playNextMission;
		onScreenRectangles[2] = selectMission;
//		onScreenRectangles[3] = upgradeAircraft;
	}


	@Override
	public void keyPressed(ArrayList<Integer> keys) {
		// TODO Auto-generated method stub
		
	}

}
