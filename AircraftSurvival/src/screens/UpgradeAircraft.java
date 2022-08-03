package screens;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import shapes.Rectangle;
import stats.Progressbar;

public class UpgradeAircraft extends Screen {
/**
 * Creates a new screen with UpgradeAircarft data loaded in
 * @param width
 * @param height
 * @param menuColor
 */
	public UpgradeAircraft(int width, int height, Color menuColor) {
		super(width, height, menuColor,"UpgradeAircraft");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(PApplet marker) {
		// TODO Auto-generated method stub
		Rectangle img, coins, hp, hpPlus, maxSpeed, maxSpeedPlus, weapons, weaponsPlus, back;

		img = new Rectangle(width / 10, height / 10, (width / 2 - width / 5), (height / 3.0));
		coins = new Rectangle(img.getX(), img.getY() + img.getHeight() * 1.1, img.getWidth(),
				img.getHeight() / 4.0);
		hp = new Rectangle(img.getX(), coins.getY() + coins.getHeight() * 1.5, img.getWidth(),
				img.getHeight() / 2.0);
		hpPlus = new Rectangle(hp.getX() + hp.getWidth() / 1.5, hp.getY() + hp.getHeight() / 4, hp.getWidth() / 8,
				hp.getHeight() / 3);
		maxSpeed = new Rectangle(hp.getX() + hp.getWidth() * 1.25, hp.getY(), hp.getWidth(), hp.getHeight());
		maxSpeedPlus = new Rectangle(maxSpeed.getX() + maxSpeed.getWidth() / 1.5,
				maxSpeed.getY() + maxSpeed.getHeight() / 4, maxSpeed.getWidth() / 8, maxSpeed.getHeight() / 3);
		weapons = new Rectangle(hp.getX(), hp.getY() + hp.getHeight() * 1.25, hp.getWidth(), hp.getHeight());
		weaponsPlus = new Rectangle(weapons.getX() + weapons.getWidth() / 1.5,
				weapons.getY() + weapons.getHeight() / 4, weapons.getWidth() / 8, weapons.getHeight() / 3);
		back = new Rectangle((width - width / 10.), (height - height / 5.), width / 15.0, width / 20.);

		Progressbar maxHPBar = new Progressbar(img.getX() + img.getWidth() * 1.25, img.getY(), width / 4,
				height / 20, 10, 10, Color.YELLOW,true);
		Progressbar maxSpeedBar = new Progressbar(maxHPBar.getX(), maxHPBar.getY() + maxHPBar.getHeight() * 2,
				maxHPBar.getWidth(), maxHPBar.getHeight(), 10, 10, Color.YELLOW,true);
		Progressbar maxWeaponsDMGBar = new Progressbar(maxSpeedBar.getX(),
				maxSpeedBar.getY() + maxSpeedBar.getHeight() * 2, maxSpeedBar.getWidth(), maxSpeedBar.getHeight(),
				10, 10, Color.YELLOW,true);

		img.changeFillColor(menuColor);
		coins.changeFillColor(menuColor);
		hp.changeFillColor(menuColor);
		maxSpeed.changeFillColor(menuColor);
		weapons.changeFillColor(menuColor);
		back.changeFillColor(menuColor);

		weaponsPlus.changeFillColor(Color.WHITE);
		hpPlus.changeFillColor(Color.WHITE);
		maxSpeedPlus.changeFillColor(Color.WHITE);

		img.draw(marker);
		coins.draw(marker);
		hp.draw(marker);
		hpPlus.draw(marker);
		maxSpeed.draw(marker);
		maxSpeedPlus.draw(marker);
		weapons.draw(marker);
		weaponsPlus.draw(marker);
		back.draw(marker);

		maxHPBar.draw(marker);
		maxSpeedBar.draw(marker);
		maxWeaponsDMGBar.draw(marker);
		marker.textSize((float)back.getHeight());
		marker.fill(Color.BLACK.getRGB());
		marker.text("Back", (float) (back.getX() + back.getWidth() / 2), (float) back.getY());
		marker.textAlign(PApplet.RIGHT, PApplet.TOP);
		marker.text("IMG", (float) (img.getX() + img.getWidth() / 2), (float) img.getY());
		marker.text("Coins: ", (float) (coins.getX() + coins.getWidth() / 2), (float) coins.getY());
		marker.text("HP: ", (float) (hp.getX() + hp.getWidth() / 2), (float) hp.getY());
		marker.text("Max Speed: ", (float) (maxSpeed.getX() + maxSpeed.getWidth() / 2), (float) maxSpeed.getY());
		marker.text("Weapons: ", (float) (weapons.getX() + weapons.getWidth() / 2), (float) weapons.getY());
		marker.textAlign(PApplet.CENTER, PApplet.TOP);
		marker.text("HP:", (float) (maxHPBar.getX() + maxHPBar.getWidth() / 2),
				(float) (maxHPBar.getY() - maxHPBar.getHeight()));
		marker.	text("Max Speed:", (float) (maxSpeedBar.getX() + maxSpeedBar.getWidth() / 2),
				(float) (maxSpeedBar.getY() - maxSpeedBar.getHeight()));
		marker.text("Weapon Damage:", (float) (maxWeaponsDMGBar.getX() + maxWeaponsDMGBar.getWidth() / 2),
				(float) (maxWeaponsDMGBar.getY() - maxWeaponsDMGBar.getHeight()));

		onScreenRectangles = new Rectangle[5];
		onScreenRectangles[0] = back;
		onScreenRectangles[1] = coins;
		onScreenRectangles[2] = hpPlus;
		onScreenRectangles[3] = maxSpeedPlus;
		onScreenRectangles[4] = weaponsPlus;
	}

	
	@Override
	public void keyPressed(ArrayList<Integer> keys) {
		// TODO Auto-generated method stub
		
	}

}
