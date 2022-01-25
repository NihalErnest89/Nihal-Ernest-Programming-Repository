
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;
import screens.*;
import shapes.*;
import stats.*;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import Sprites.*;

/**
 * Represents every object that is drawn on the PApplet, and also handles
 * certain interactions between players and enemies/bases
 * 
 * @author Nihal Ernest, Fabio Eirea, and Krishan Patel
 *
 */
public class DrawingSurface extends PApplet {

	private int yeet = 1;
	public int currentScreen;
	Healthbar h, k, kamikazeHealth, m, bossHealth, n, o, p;
	Plane player, boss, enemy1, enemy2, enemy3, enemy4, enemy5, kamikaze1;
	Base base1, base2, base3;
	ArrayList<Plane> enemies;
	ArrayList<Plane> enemyKamikazes;
	ArrayList<PImage> controls;
	Scenery green1, green2, green3;
	private ArrayList<Integer> keys;
	private Screen s;
	private Color menuFillColor = Color.YELLOW;
	private boolean firstTime, singleFire;
	private Profile currentProfile, p1, p2, p3;
	private Rectangle[] onScreenRectangles;// these rectangles function as buttons
	private Shape[] onScreenShapes;
	private Bullet bullet;
	private int AftTimer;
	private double score;
	private PImage bush, tree, biplane, jet, kamikaze, enemyFighter, advancedFighter, menuBackground, bAsE;
	private int enemyFireRate;
	private boolean wave1down, wave2down, wave3down, wave1active, wave2active, wave3active, bossWaveActive,
			bossWaveDown;
	private MouseEvent event;
	private boolean gulag;

	public DrawingSurface(Profile p1, Profile p2, Profile p3) {

		score = 0;
		enemies = new ArrayList<Plane>();
		enemyKamikazes = new ArrayList<Plane>();
		controls = new ArrayList<PImage>();
		width = 1000;
		height = 800;
		AftTimer = 0;
		enemyFireRate = 20;
		h = new Healthbar(width / 2 - (width / 12), 9 * height / 10, width / 6, 15, 150, 150, true);
		k = new Healthbar(width / 2 + 50, height / 2, width / 10, 10, 150, 150, true);
		kamikazeHealth = new Healthbar(width / 2 + 50, height / 2, width / 10, 10, 150, 99, true);
		m = new Healthbar(width / 2 + 50, height / 2, width / 10, 10, 150, 150, true);
		n = new Healthbar(width / 2 + 50, height / 2, width / 10, 10, 150, 150, true);
		o = new Healthbar(width / 2 + 50, height / 2, width / 10, 10, 150, 150, true);
		p = new Healthbar(width / 2 + 50, height / 2, width / 10, 10, 150, 150, true);
		bossHealth = new Healthbar(width / 2 + 50, height / 2 + 20, width / 10, 10, 150, 500, true);
		player = new Plane(width / 2 - 35, height / 3, 50, 60, h, true);
		enemy1 = new Plane(width / 2, 0, 50, 40, k, false);
		enemy2 = new Plane(width / 2 - 35, 0, 50, 40, m, false);
		enemy3 = new Plane(width / 2 + 35, 0, 50, 40, n, false);
		enemy4 = new Plane(width / 2 - 105, -50, 50, 40, o, false);
		enemy5 = new Plane(width / 2 - 35, 0, 50, 40, p, false);
		kamikaze1 = new Plane(width / 2 + 35, 0, 50, 40, kamikazeHealth, false);
		green1 = new Scenery(width / 3, (int) (Math.random() * height), 70, 90);
		green2 = new Scenery(2 * width / 3, (int) (Math.random() * height), 70, 90);
		green3 = new Scenery(width / 2, (int) (Math.random() * height), 70, 60);
		keys = new ArrayList<Integer>();
		gulag = false;
		base1 = new Base(500, -1500, 120, 120);
		base2 = new Base(700, -4000, 120, 120);
		base3 = new Base(200, -7000, 120, 120);

		currentScreen = 0;
		firstTime = true;
		currentProfile = new Profile("Unnamed");

		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}

	public void settings() {

	}

	/**
	 * The statements in the setup() function execute once when the program begins
	 **/
	public void setup() {
		bush = loadImage("bush.png");
		tree = loadImage("tree.png");
		biplane = loadImage("biplane.png");
		jet = loadImage("jet.png");
		kamikaze = loadImage("Rbiplane.png");
		enemyFighter = loadImage("Rjet.png");
		bAsE = loadImage("base.png");
		advancedFighter = loadImage("RadvancedJet.png");

		menuBackground = loadImage("menuBackground.jpg");

		controls.add(loadImage("ControlsIcons/Controls.png"));
//		controls.add(loadImage("resources/ControlsIcons/UpArrow.png"));
//		controls.add(loadImage("resources/ControlsIcons/DownArrow.png"));
//		controls.add(loadImage("resources/ControlsIcons/LeftArrow.png"));
//		controls.add(loadImage("resources/ControlsIcons/RightArrow.png"));
//		controls.add(loadImage("resources/ControlsIcons/f.png"));
//		controls.add(loadImage("resources/ControlsIcons/r.png"));
//		controls.add(loadImage("resources/ControlsIcons/x.png"));
//		controls.add(loadImage("resources/ControlsIcons/Spacebar.png"));

		green1.accelerate(0, 5);
		green2.accelerate(0, 5);
		green3.accelerate(0, 5);

		wave1down = false;
		wave2down = false;
		wave3down = false;
		bossWaveDown = false;
		wave1active = false;
		wave2active = false;
		wave3active = false;
		bossWaveActive = false;
	}

	// The statements in draw() are executed until the
	// program is stopped. Each statement is executed in
	// sequence and after the last line is read, the first
	// line is executed again.
	public void draw() {
		background(Color.WHITE.getRGB()); // Clear the screen with a black background
//		float xRatio = width / 800f;
//		float yRatio = height / 500f;
//		scale(xRatio, yRatio);

		if (currentScreen == 0) {// Display the Main Menu

			image(menuBackground, 0, 0, width, height);
			s = new MainMenu(width, height, menuFillColor);
			s.draw(this);
			this.onScreenRectangles = s.getOnScreenRectangles();
//			

		} else if (currentScreen == 1) { // PLAY OPTIONS
			image(menuBackground, 0, 0, width, height);

			s = new Play(width, height, menuFillColor);
			s.draw(this);
			this.onScreenRectangles = s.getOnScreenRectangles();
//			

		} else if (currentScreen == 2) {// Controls Screen
			image(menuBackground, 0, 0, width, height);

			s = new Controls(width, height, menuFillColor, controls, event);
			s.draw(this);
			this.onScreenRectangles = s.getOnScreenRectangles();
			event = null;

		} else if (currentScreen == 3) {// Settings Screen
			image(menuBackground, 0, 0, width, height);

			s = new Settings(width, height, menuFillColor);
			s.draw(this);
			this.onScreenRectangles = s.getOnScreenRectangles();

		} else if (currentScreen == 4) { // DISPLAY GAME WITH CORRECT SETTINGS
//			s = new Game(width,height, menuFillColor);
//			s.draw(this);
			background(Color.LIGHT_GRAY.getRGB());
			Rectangle back;
			back = new Rectangle((width - width / 10.), (height - height / 5.), width / 15.0, width / 20.);
			back.changeFillColor(menuFillColor);

			if (green1.inBounds(width, height) == 4) {
				green1.setPoint(Math.random() * (width / 3), -Math.random() * (height / 2));
			}
			if (green2.inBounds(width, height) == 4) {
				green2.setPoint(Math.random() * (width / 3) + 2 * width / 3, -Math.random() * (height / 2));
			}
			if (green3.inBounds(width, height) == 4) {
				green3.setPoint(Math.random() * (width / 3) + width / 3, -Math.random() * (height / 2));
			}

			green1.draw(this, tree);
			green2.draw(this, tree);
			green3.draw(this, bush);
			green1.move();
			green2.move();
			green3.move();

			boolean enemiesAlive = false;
			for (Plane enemy : enemies) {
				if (enemy == null || enemy.getHealthbar().isAlive())
					enemiesAlive = true;
			}
			for (Plane enemy : enemyKamikazes) {
				if (enemy.getHealthbar() != null && enemy.getHealthbar().isAlive())
					enemiesAlive = true;
			}
			textSize(30);

			if (!wave1down && wave1active) {
				textSize(25);
				fill(0, 0, 255);
//				System.out.println("why is this not active");
				text("Wave 1: The Lone Ace", 5, 9 * height / 10);
				if (!enemiesAlive)
					wave1down = true;
			}
			if (!wave1down && !wave1active) {
				enemies.add(enemy1);
//				System.out.println("Wave 1 starting");
				wave1active = true;
			}
			if (wave1down && !wave2active && !wave2down) {
				score += 100;
//				System.out.println("Wave 2 starting");
				wave1active = false;
				enemies.add(enemy2);
				enemies.add(enemy3);
				wave2active = true;
			}
			for (Plane enemy : enemies) {
				if (enemy == null || enemy.getHealthbar().isAlive())
					enemiesAlive = true;
			}
			for (Plane enemy : enemyKamikazes) {
				if (enemy.getHealthbar() != null && enemy.getHealthbar().isAlive())
					enemiesAlive = true;
			}
			if (wave2active) {
				textSize(25);
				fill(0, 0, 255);
				text("Wave 2: Double trouble", 5, 9 * height / 10);
				if (!enemiesAlive) {
					score += 100;
//					System.out.println("Wave 3 starting");
					wave2down = true;
					wave2active = false;
					enemies.add(enemy4);
					enemies.add(enemy5);
					enemyKamikazes.add(kamikaze1);
					wave3active = true;
				}
			}
			for (Plane enemy : enemies) {
				if (enemy == null || enemy.getHealthbar().isAlive())
					enemiesAlive = true;
			}
			for (Plane enemy : enemyKamikazes) {
				if (enemy.getHealthbar() != null && enemy.getHealthbar().isAlive())
					enemiesAlive = true;
			}
			if (wave3active) {
				textSize(25);
				fill(0, 0, 255);
				text("Wave 3: 'Kaze", 5, 9 * height / 10);
				if (!enemiesAlive) {
					wave3active = false;
					score += 100;
//					System.out.println("Boss wave starting");
					player.getHealthbar().reset();
					boss = new Plane(width / 2, -180, 180, 180, bossHealth, false);
					fill(0, 0, 255);

					bossWaveActive = true;
				}
			}
			for (Plane enemy : enemies) {
				if (enemy == null || enemy.getHealthbar().isAlive())
					enemiesAlive = true;
			}
			for (Plane enemy : enemyKamikazes) {
				if (enemy.getHealthbar() != null && enemy.getHealthbar().isAlive())
					enemiesAlive = true;
			}
			if (bossWaveActive) {
				textSize(25);
				fill(0, 0, 255);
				text("BOSS FIGHT!", 5, 9 * height / 10);
			}
			if (bossWaveActive && !boss.getHealthbar().isAlive()) {

				if (score == 500)
					gulag = true;

			}
			if (bossWaveActive && !boss.getHealthbar().isAlive()) {
				bossWaveActive = false;
//					System.out.println("Boss wave defeated, GG");
				currentScreen = 10;
				bossWaveDown = true;

			}
			if (base1.getHealthbar().isAlive()) {
				base1.setVelocity(0, 5);
				base1.draw(this, bAsE);
				base1.move();
				base1.drag();
			}
			if (base2.getHealthbar().isAlive()) {
				base2.setVelocity(0, 5);
				base2.draw(this, bAsE);
				base2.move();
				base2.drag();
			}
			if (base3.getHealthbar().isAlive()) {
				base3.setVelocity(0, 5);
				base3.draw(this, bAsE);
				base3.move();
				base3.drag();
			}

			yeet++;
			if (yeet == enemyFireRate)
				yeet = 1;
			if (boss != null && bossWaveActive == true) {
				if (!boss.getGun().canFire())
					boss.getGun().reload();
				if (yeet == 1 || yeet == 10) { // shoot
					boss.fire();
					boss.fire();
					boss.fire();
				}
				if (boss.getY() < 40)
					boss.accelerate(0, 0.2);
				if (player.intersects(boss)) {
					player.getHealthbar().decreaseHealth(2);
					boss.getHealthbar().decreaseHealth(2);
				}

				// tries to get in same x-position
				if (boss.getX() >= player.getX() - 30)
					boss.accelerate(-2, 0);
				else if (boss.getX() <= player.getX() + 30)
					boss.accelerate(2, 0);

				boss.draw(this, advancedFighter);
				boss.move();
				boss.drag();

				Bullet[] fired = boss.returnFiredBullets();// enemy bullets being drawn
				if (fired != null) {
					for (int a = 0; a < fired.length; a++) {
						if (fired[a] != null) {
							fired[a].setVelocity(0, +6);
							if (fired[a].intersects(player)) {
								player.getHealthbar().decreaseHealth(fired[a].getDmg());
								fired[a] = null;
							} else
								fired[a].draw(this);
						}
					}
				}
			}
			if (gulag) {
				fill(255, 0, 0);
				textSize(50);
				text("YEEEEEEEEEEEEEEEE\nEEEEEEEEEEEEEEEEE\nEEEEEEEEEEEEEEEEE\nEEEEEEEEEEEEEEEEE\nEEEEEEEEEEEEEEEET",
						50, 50);
			}

			for (Plane enemy : enemies) { // enemy plane movement - Fabio Eirea
				if (enemy.getHealthbar().isAlive() && player != null) {
					enemy.setVelocity(0, 0.8);
					for (Plane p : enemies) {
						if (p != enemy && p.getY() < enemy.getY() + 10 && p.getY() > enemy.getY() - 10) {
							if (p.getX() > enemy.getX() && p.getX() < enemy.getX() + 50) {
								enemy.accelerate(-1.5, 0);
							} else if (p.getX() < enemy.getX() && p.getX() > enemy.getX() - 50) {
								enemy.accelerate(1.5, 0);
							} else {
							}
						}
					}
					if (enemy.getY() < player.getY() - 200) { // if ahead of player, tries to get in same x-position and
																// shoot
						if (enemy.getX() >= player.getX() + 20)
							enemy.accelerate(-1.5, 0);
						else if (enemy.getX() <= player.getX() - 20)
							enemy.accelerate(1.5, 0);
						else if (yeet == 1)
							enemy.fire();

					} else if (enemy.getY() < player.getY() + 130) { // if close to the player, tries to dodge out of
																		// the way
						if (enemy.getX() > player.getX() - 70 && enemy.getX() < player.getX() + 35
								&& enemy.getX() > 10) {
							enemy.accelerate(-1.5, 0);
							if (yeet == 1)
								enemy.fire();
						} else if (enemy.getX() < player.getX() + 35 && enemy.getX() > player.getX() + 35
								&& enemy.getX() < width - 90) {
							enemy.accelerate(1.5, 0);
							if (yeet == 1)
								enemy.fire();
						}

					}
					if (enemy.getY() > height) {
						enemy.moveTo(enemy.getX(), -100);
						enemy.getGun().moveTo(enemy.getGun().getX(), -60);
					}
					if (player.intersects(enemy)) {
						player.getHealthbar().decreaseHealth(1);
						enemy.getHealthbar().decreaseHealth(2);
					}
//					for(Plane p : enemies) {// collision code for enemy planes with enemy planes
//						if(p!=null) {
//							if(p.intersects(enemy) && p != enemy) {// if Plane p intersects an enemy plane that is
//								}
//							}
//					}
					enemy.draw(this, enemyFighter);
					enemy.move();
					enemy.drag();

					Bullet[] fired = enemy.returnFiredBullets();// enemy bullets being drawn
					if (fired != null) {
						for (int a = 0; a < fired.length; a++) {
							if (fired[a] != null) {
								fired[a].setVelocity(0, +6);
								if (fired[a].intersects(player)) {
									player.getHealthbar().decreaseHealth(fired[a].getDmg());
									fired[a] = null;
								} else
									fired[a].draw(this);
							}
						}
					}
				}

			}

			for (Plane enemy : enemyKamikazes) {
				if (enemy.getHealthbar().isAlive() && player != null) {
					enemy.setVelocity(0, 2);
					if (enemy.getY() < player.getY()) { // Tries to ram player
						if (enemy.getX() >= player.getX() + 35)
							enemy.accelerate(-2.5, 0);
						else if (enemy.getX() <= player.getX() - 10)
							enemy.accelerate(2.5, 0);
						else
							enemy.accelerate(0, 0.5);
					}
					if (enemy.getY() > height) {// enemies resetting to top
						enemy.moveTo(enemy.getX(), -100);
						enemy.getGun().moveTo(enemy.getGun().getX(), -60);
					}
					if (player.intersects(enemy)) {// if the planes intersect each other
						player.getHealthbar().decreaseHealth(4);
						enemy.getHealthbar().decreaseHealth(2);
					}

					enemy.draw(this, kamikaze);
					enemy.move();
					enemy.drag();

					Bullet[] fired = enemy.returnFiredBullets();
					if (fired != null) {
						for (int a = 0; a < fired.length; a++) {
							if (fired[a] != null) {
								fired[a].setVelocity(0, +6);
								if (fired[a].intersects(player)) {
									player.getHealthbar().decreaseHealth(fired[a].getDmg());
									fired[a] = null;
								} else
									fired[a].draw(this);
							}
						}
					}
				}

			}

			onScreenRectangles = new Rectangle[1];
			onScreenRectangles[0] = back;

			if (player != null && player.getHealthbar().isAlive()) {

				if (isPressed(KeyEvent.VK_F)) { // This activates a speed boost for the aircraft
					if (AftTimer < 100) {
						player.afterburner(7);
						textSize(20);
						text("Afterburner: On", 0, 7 * height / 10);
						// System.out.println(AftTimer);
						AftTimer++;
					} else {
						player.afterburner(4);
					}
				} else {
					AftTimer = 0;
					player.afterburner(4);
				}
				if (player.inBounds(width, height) == 1) // These deal with the player exceeding window bounds - Nihal {
					player.accelerate(-1, 0); //
				else if (player.inBounds(width, height) == 2) //
					player.accelerate(1, 0);
				else if (player.inBounds(width, height) == 3) //
					player.accelerate(0, 1);
				else if (player.inBounds(width, height) == 4) //
					player.accelerate(0, -1);
				else // }
				{
					if (isPressed(KeyEvent.VK_LEFT))
						player.accelerate(-0.5, 0);
					if (isPressed(KeyEvent.VK_RIGHT))
						player.accelerate(0.5, 0);
					if (isPressed(KeyEvent.VK_UP))
						player.accelerate(0, -0.5);
					if (isPressed(KeyEvent.VK_DOWN))
						player.accelerate(0, 0.5);

				}
				player.draw(this, jet);
				player.move();
				player.drag();

				Bullet[] fired = player.returnFiredBullets();
				if (fired != null) {
					for (int a = 0; a < fired.length; a++) {
						if (fired[a] != null) {
							fired[a].setVelocity(0, -6);
							for (Plane enemy : enemies) {
								if (fired[a] != null && fired[a].intersects(enemy) && enemy.getHealthbar().isAlive()) {
									enemy.getHealthbar().decreaseHealth(fired[a].getDmg());
									fired[a] = null;
								} else if (fired[a] != null)
									fired[a].draw(this);
							}
							for (Plane enemy : enemyKamikazes) {
								if (fired[a] != null && fired[a].intersects(enemy) && enemy.getHealthbar().isAlive()) {
									enemy.getHealthbar().decreaseHealth(fired[a].getDmg());
									fired[a] = null;
								} else if (fired[a] != null)
									fired[a].draw(this);
							}
							if (fired[a] != null && fired[a].intersects(boss) && boss.getHealthbar().isAlive()) {
								boss.getHealthbar().decreaseHealth(fired[a].getDmg());
								fired[a] = null;
							} else if (fired[a] != null)
								fired[a].draw(this);
						}
					}
				}
			} else
				player = null;

//			base.draw(this, null);
			back.draw(this);

			fill(Color.WHITE.getRGB());
			textSize(20);
			fill(Color.BLUE.getRGB());
			textAlign(PApplet.CENTER, PApplet.TOP);
			text("Back", (float) (back.getX() + back.getWidth() / 2), (float) back.getY());
			textAlign(PApplet.LEFT, PApplet.TOP);

			score += 0.1;
			text("Score: " + (int) score, 5, 0);
			if (player == null || !player.getHealthbar().isAlive()) {
				currentScreen = 9;

			}
//			if (!enemiesAlive) {
//				currentScreen = 9;
//				System.out.println("YOU WIN, EPIC GAMES");
//			}
		}

		else if (currentScreen == 5) { // Select levels Screen
			image(menuBackground, 0, 0, width, height);

			s = new SelectLevel(width, height, menuFillColor);
			s.draw(this);
			this.onScreenRectangles = s.getOnScreenRectangles();

		} else if (currentScreen == 6) { // Upgrade Aircraft Screen
			image(menuBackground, 0, 0, width, height);

			s = new UpgradeAircraft(width, height, menuFillColor);
			s.draw(this);
			this.onScreenRectangles = s.getOnScreenRectangles();

		} else if (currentScreen == 7) {// the Save Screen
			image(menuBackground, 0, 0, width, height);

			s = new Save(width, height, menuFillColor, currentProfile, p1, p2, p3);
			s.draw(this);
			this.onScreenRectangles = s.getOnScreenRectangles();

		} else if (currentScreen == 8) {// the Load Screen
			image(menuBackground, 0, 0, width, height);

			s = new Load(width, height, menuFillColor, currentProfile, p1, p2, p3);
			s.draw(this);
			this.onScreenRectangles = s.getOnScreenRectangles();

		} else if (currentScreen == 9) {// the Game Over Screen
			s = new GameOver(width, height, menuFillColor);
			s.setScore(score);
			s.draw(this);
			this.onScreenRectangles = s.getOnScreenRectangles();
		} else if (currentScreen == 10) {
			s = new GoodGame(width, height, menuFillColor);
			s.setScore(score);
			s.draw(this);
			this.onScreenRectangles = s.getOnScreenRectangles();
		} else if (currentScreen == 11) {// Pause Screen
			s = new PauseScreen(width, height, menuFillColor);
			s.draw(this);
			this.onScreenRectangles = s.getOnScreenRectangles();
		} else {
			stroke(Color.BLACK.getRGB());
			text("Screen not created", width / 2, height / 2);
		}
	}

	public void keyPressed() {
		keys.add(keyCode);

		if (currentScreen == 4) {
			if (key == 'x') {
				if (player != null)
					player.reset(50, 50);
				else {
					player = new Plane(width / 2 - 35, height / 3, 70, 60, h, true);
					player.getHealthbar().reset();
				}
				for (Plane enemy : enemies) {
					if (enemy != null)
						enemy.reset(400, 100);
					else {
						enemy = new Plane(width / 2 - 35, height / 2, 90, 100, k, false);
						enemy.getHealthbar().reset();
					}
				}
				for (Plane enemy : enemyKamikazes) {
					if (enemy != null)
						enemy.reset(400, 100);
					else {
						enemy = new Plane(width / 2 - 35, height / 2, 90, 100, k, false);
						enemy.getHealthbar().reset();
					}
				}
			} else if (key == 'r') {
				if (player != null)
					player.getGun().reload();
			} else if (key == 'p') {
				currentScreen = 11;
			}

			if (isPressed(KeyEvent.VK_SPACE) && singleFire && player != null) {
				player.fire();
				singleFire = false;
			}
		}else if(currentScreen == 11) {
			if(key == 'p') {// un-paused
				currentScreen = 4;
			}
		}

	}

	public void keyReleased() {
		singleFire = true;
		if (key == 'b') {
//			System.out.println("bombs away");
			if (player.intersects(base1) && base1.getHealthbar().isAlive()) {
				base1.getHealthbar().setIsAlive(false);
//				System.out.println("hit");
				score += 100;
			}
			if (player.intersects(base2) && base2.getHealthbar().isAlive()) {
				base2.getHealthbar().setIsAlive(false);
				score += 100;

			}
			if (player.intersects(base3) && base3.getHealthbar().isAlive()) {
				base3.getHealthbar().setIsAlive(false);
				score += 100;

			}
		}
		while (keys.contains(keyCode))
			keys.remove(new Integer(keyCode));
	}

	public boolean isPressed(Integer code) {
		return keys.contains(code);
	}

	public void mouseClicked() {

	}

	public void mousePressed() {

	}

	public void mouseReleased() {
		if (currentScreen == 0) {// MainMenu
			if (onScreenRectangles[0].isPointInside(mouseX, mouseY)) {// exit program
				System.exit(0); // ends the program
			} else if (onScreenRectangles[1].isPointInside(mouseX, mouseY)) {// GO to Play screen
				currentScreen = 1;
			} else if (onScreenRectangles[2].isPointInside(mouseX, mouseY)) {// GO to Controls screen
				currentScreen = 2;
			} else if (onScreenRectangles[3].isPointInside(mouseX, mouseY)) {
				currentScreen = 3;

			}
		} else if (currentScreen == 1) {// Play
			if (onScreenRectangles[0].isPointInside(mouseX, mouseY)) {
				currentScreen = 0;
			} else if (onScreenRectangles[1].isPointInside(mouseX, mouseY)) {
				currentScreen = 4;
			} else if (onScreenRectangles[2].isPointInside(mouseX, mouseY)) {
				currentScreen = 5;
			} else if (onScreenRectangles[3].isPointInside(mouseX, mouseY)) {
				currentScreen = 6;
			}

		} else if (currentScreen == 2) {// Controls
			if (onScreenRectangles[0].isPointInside(mouseX, mouseY)) {
				currentScreen = 0;
			}

		} else if (currentScreen == 3) {// Settings
			if (onScreenRectangles[0].isPointInside(mouseX, mouseY)) {
				currentScreen = 0;
			} else if (onScreenRectangles[1].isPointInside(mouseX, mouseY)) {
				currentScreen = 7;
			} else if (onScreenRectangles[2].isPointInside(mouseX, mouseY)) {
				currentScreen = 8;
			}

		} else if (currentScreen == 4) {// the game with correct settings
			if (onScreenRectangles[0].isPointInside(mouseX, mouseY)) {
				currentScreen = 1;
			}
		} else if (currentScreen == 5) {// Select Mission
			if (onScreenRectangles[0].isPointInside(mouseX, mouseY)) {
				currentScreen = 0;
			} else if (onScreenRectangles[0].isPointInside(mouseX, mouseY)) {
				// load lvl1
				currentScreen = 4;
			} else if (onScreenRectangles[1].isPointInside(mouseX, mouseY)) {
				// load lvl2
				currentScreen = 4;
//			} else if (onScreenRectangles[2].isPointInside(mouseX, mouseY)) {
//				// load lvl3
//				currentScreen = 4;
//			} else if (onScreenRectangles[3].isPointInside(mouseX, mouseY)) {
//				// load lvl4
//				currentScreen = 4;
//			} else if (onScreenRectangles[4].isPointInside(mouseX, mouseY)) {
//				// load lvl5
//				currentScreen = 4;
			}
		} else if (currentScreen == 6) {// Upgrade Aircraft
			if (onScreenRectangles[0].isPointInside(mouseX, mouseY)) {
				currentScreen = 1;
			} else if (onScreenRectangles[1].isPointInside(mouseX, mouseY)) {
				// cursor on coins TBD
			} else if (onScreenRectangles[2].isPointInside(mouseX, mouseY)) {
				// increase max hp
			} else if (onScreenRectangles[3].isPointInside(mouseX, mouseY)) {
				// increase maxSpeed
			} else if (onScreenRectangles[4].isPointInside(mouseX, mouseY)) {
				// increase weapons dmg or whatever
			}
		} else if (currentScreen == 7) { // SaveScreen
			Rectangle l1, l2, l3;
			l1 = new Rectangle(width / 10, height / 3, width / 4, height / 3);
			l2 = new Rectangle(l1.getX() + l1.getWidth() * 1.25, l1.getY(), l1.getWidth(), l1.getHeight());
			l3 = new Rectangle(l2.getX() + l2.getWidth() * 1.25, l2.getY(), l2.getWidth(), l2.getHeight());

			if (onScreenRectangles[0].isPointInside(mouseX, mouseY)) {
				currentScreen = 3;
			} else if (onScreenRectangles[1].isPointInside(mouseX, mouseY)) {
				if (onScreenRectangles[1] == l1) {
					// save into existing profile
					// System.out.println(" == l1");
				} else {
					// create new profile
					int ans = JOptionPane.showConfirmDialog(null,
							"Do you wish to Save our current Progress and override any data saved in this profile?",
							"Saving Progress", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

					if (ans == JOptionPane.YES_OPTION) {
						p1.setCoins(currentProfile.getCoins());
						p1.setFurthestLevel(currentProfile.getFurthestLevel());
						p1.setMaxHP(currentProfile.getMaxHP());
						p1.setMaxSpeed(currentProfile.getMaxSpeed());
						if (currentProfile.getProfileName().contains("Unnamed")
								|| currentProfile.getProfileName().contains("")) {
							String answer = JOptionPane.showInputDialog(null, "Please Enter a Name for the new Profile",
									"Creating Profile", JOptionPane.QUESTION_MESSAGE);
							if (answer != null) {
								// System.out.println(answer);
								if (answer.equals("")) {
									JOptionPane.showMessageDialog(null, "Invalid Name", "Error",
											JOptionPane.ERROR_MESSAGE);
								} else {
									p1.setProfileName(answer);
								}
							}
						} else
							p1.setProfileName(currentProfile.getProfileName());
						p1.setWeapondmg(currentProfile.getCoins());
						p1.saveProfile(1);
					}
				}

			} else if (onScreenRectangles[2].isPointInside(mouseX, mouseY)) {
				if (onScreenRectangles[2] == l2) {
					// save into existing profile
					// System.out.println(" == l2");
				} else {
					// create new profile
					int ans = JOptionPane.showConfirmDialog(null,
							"Do you wish to Save our current Progress and override any data saved in this profile?",
							"Saving Progress", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

					if (ans == JOptionPane.YES_OPTION) {
						p2.setCoins(currentProfile.getCoins());
						p2.setFurthestLevel(currentProfile.getFurthestLevel());
						p2.setMaxHP(currentProfile.getMaxHP());
						p2.setMaxSpeed(currentProfile.getMaxSpeed());
						if (currentProfile.getProfileName().contains("Unnamed")
								|| currentProfile.getProfileName().contains("")) {
							String answer = JOptionPane.showInputDialog(null, "Please Enter a Name for the new Profile",
									"Creating Profile", JOptionPane.QUESTION_MESSAGE);
							if (answer != null) {
								// System.out.println(answer);
								if (answer.equals("")) {
									JOptionPane.showMessageDialog(null, "Invalid Name", "Error",
											JOptionPane.ERROR_MESSAGE);
								} else {
									p2.setProfileName(answer);
								}
							}
						} else
							p2.setProfileName(currentProfile.getProfileName());
						p2.setWeapondmg(currentProfile.getCoins());
						p2.saveProfile(3);
					}
				}
			} else if (onScreenRectangles[3].isPointInside(mouseX, mouseY)) {
				if (onScreenRectangles[3] == l3) {
					// save into existing profile
					// System.out.println(" == l3");
				} else {
					// create new profile
					int ans = JOptionPane.showConfirmDialog(null,
							"Do you wish to Save our current Progress and override any data saved in this profile?",
							"Saving Progress", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

					if (ans == JOptionPane.YES_OPTION) {
						p3.setCoins(currentProfile.getCoins());
						p3.setFurthestLevel(currentProfile.getFurthestLevel());
						p3.setMaxHP(currentProfile.getMaxHP());
						p3.setMaxSpeed(currentProfile.getMaxSpeed());
						if (currentProfile.getProfileName().contains("Unnamed")
								|| currentProfile.getProfileName().contains("")) {
							String answer = JOptionPane.showInputDialog(null, "Please Enter a Name for the new Profile",
									"Creating Profile", JOptionPane.QUESTION_MESSAGE);
							if (answer != null) {
								// System.out.println(answer);
								if (answer.equals("")) {
									JOptionPane.showMessageDialog(null, "Invalid Name", "Error",
											JOptionPane.ERROR_MESSAGE);
								} else {
									p1.setProfileName(answer);
								}
							}
						} else
							p3.setProfileName(currentProfile.getProfileName());
						p3.setWeapondmg(currentProfile.getCoins());
						p3.saveProfile(3);
					}
				}
			}
		} else if (currentScreen == 8) {

			Rectangle l1, l2, l3, load1, load2, load3;

			l1 = new Rectangle(width / 10, height / 3, width / 4, height / 3);
			l2 = new Rectangle(l1.getX() + l1.getWidth() * 1.25, l1.getY(), l1.getWidth(), l1.getHeight());
			l3 = new Rectangle(l2.getX() + l2.getWidth() * 1.25, l2.getY(), l2.getWidth(), l2.getHeight());
			load1 = new Rectangle(l1.getX() + l1.getWidth() / 4, l1.getY() + l1.getHeight() * 1.125, l1.getWidth() / 2,
					l1.getHeight() / 5);
			load2 = new Rectangle(l2.getX() + l2.getWidth() / 4, l2.getY() + l2.getHeight() * 1.125, l2.getWidth() / 2,
					l2.getHeight() / 5);
			load3 = new Rectangle(l3.getX() + l3.getWidth() / 4, l3.getY() + l3.getHeight() * 1.125, l3.getWidth() / 2,
					l3.getHeight() / 5);
			if (onScreenRectangles[0].isPointInside(mouseX, mouseY)) {
				currentScreen = 3;
			} else if (onScreenRectangles[1].isPointInside(mouseX, mouseY)) {

				// System.out.println(onScreenRectangles[1].getX() + " " +
				// onScreenRectangles[1].getY() + " "
				// + onScreenRectangles[1].getWidth() + " " +
				// onScreenRectangles[1].getHeight());
				// System.out.println("Inside Load: " + load1.getX() + " " + load1.getY() + " "
				// + load1.getWidth() + " "
				// + load1.getHeight());
				if (onScreenRectangles[1].hasSameParameters(load1)) {

					int ans = JOptionPane.showConfirmDialog(null, "Do you wish to load this profile", "Load Profile",
							JOptionPane.YES_NO_OPTION);
					if (ans == JOptionPane.YES_OPTION) {
						currentProfile.loadProfile(1);
						JOptionPane.showMessageDialog(null, "Profile: " + currentProfile.getProfileName() + " Loaded",
								"Load Profile", JOptionPane.INFORMATION_MESSAGE, null);
					}
					if (ans == JOptionPane.NO_OPTION) {
						JOptionPane.showMessageDialog(null, "Profile Loading Canceled", "Load Profile",
								JOptionPane.INFORMATION_MESSAGE, null);
					}
				}

			} else if (onScreenRectangles[2].isPointInside(mouseX, mouseY)) {

				if (onScreenRectangles[2].hasSameParameters(load2)) {
					int ans = JOptionPane.showConfirmDialog(null, "Do you wish to load this profile", "Load Profile",
							JOptionPane.YES_NO_OPTION);
					if (ans == JOptionPane.YES_OPTION) {
						currentProfile.loadProfile(2);
						JOptionPane.showMessageDialog(null, "Profile: " + currentProfile.getProfileName() + " Loaded",
								"Load Profile", JOptionPane.INFORMATION_MESSAGE, null);
					}
					if (ans == JOptionPane.NO_OPTION) {
						JOptionPane.showMessageDialog(null, "Profile Loading Canceled", "Load Profile",
								JOptionPane.INFORMATION_MESSAGE, null);
					}
				}
			} else if (onScreenRectangles[3].isPointInside(mouseX, mouseY)) {

				if (onScreenRectangles[3].hasSameParameters(load3)) {
					int ans = JOptionPane.showConfirmDialog(null, "Do you wish to load this profile", "Load Profile",
							JOptionPane.YES_NO_OPTION);
					if (ans == JOptionPane.YES_OPTION) {
						currentProfile.loadProfile(3);
						JOptionPane.showMessageDialog(null, "Profile: " + currentProfile.getProfileName() + " Loaded",
								"Load Profile", JOptionPane.INFORMATION_MESSAGE, null);
					}
					if (ans == JOptionPane.NO_OPTION) {
						JOptionPane.showMessageDialog(null, "Profile Loading Canceled", "Load Profile",
								JOptionPane.INFORMATION_MESSAGE, null);
					}

				}
			}
		} else if (currentScreen == 9) {
			if (onScreenRectangles[0].isPointInside(mouseX, mouseY)) {
				currentScreen = 0;
				reset();
			} else if (onScreenRectangles[1].isPointInside(mouseX, mouseY)) {
				System.exit(0);
			}
		} else if (currentScreen == 10) {
			if (onScreenRectangles[0].isPointInside(mouseX, mouseY)) {
				currentScreen = 0;
				reset();
			} else if (onScreenRectangles[1].isPointInside(mouseX, mouseY)) {
				System.exit(0);
			}
		} else if (currentScreen == 11) {//Pause Screen
			if (onScreenRectangles[0].isPointInside(mouseX, mouseY))
				currentScreen = 0;
		}
	}

	public void mouseDragged() {
	}

	public void mouseWheel(MouseEvent e) {
		event = e;
	}

	/**
	 * Resets the map by re-initializing everything
	 */
	public void reset() {
		score = 0;
		enemies = new ArrayList<Plane>();
		enemyKamikazes = new ArrayList<Plane>();
		width = 1000;
		height = 800;
		AftTimer = 0;
		enemyFireRate = 20;
		h = new Healthbar(width / 2 - (width / 12), 9 * height / 10, width / 6, 15, 150, 150, true);
		k = new Healthbar(width / 2 + 50, height / 2, width / 10, 10, 150, 150, true);
		kamikazeHealth = new Healthbar(width / 2 + 50, height / 2, width / 10, 10, 150, 49, true);
		m = new Healthbar(width / 2 + 50, height / 2, width / 10, 10, 150, 150, true);
		n = new Healthbar(width / 2 + 50, height / 2, width / 10, 10, 150, 150, true);
		o = new Healthbar(width / 2 + 50, height / 2, width / 10, 10, 150, 150, true);
		p = new Healthbar(width / 2 + 50, height / 2, width / 10, 10, 150, 150, true);
		bossHealth = new Healthbar(width / 2 + 50, height / 2, width / 10, 10, 150, 500, true);
		player = new Plane(width / 2 - 35, height / 3, 50, 60, h, true);
		boss = null;
		enemy1 = new Plane(width / 2, 0, 50, 40, k, false);
		enemy2 = new Plane(width / 2 - 35, 0, 50, 40, m, false);
		enemy3 = new Plane(width / 2 + 35, 0, 50, 40, n, false);
		enemy4 = new Plane(width / 2 - 105, -50, 50, 40, o, false);
		enemy5 = new Plane(width / 2 - 35, 0, 50, 40, p, false);
		kamikaze1 = new Plane(width / 2 + 35, 0, 50, 40, kamikazeHealth, false);
		green1 = new Scenery(width / 3, (int) (Math.random() * height), 70, 90);
		green2 = new Scenery(2 * width / 3, (int) (Math.random() * height), 70, 90);
		green3 = new Scenery(width / 2, (int) (Math.random() * height), 70, 60);
		keys = new ArrayList<Integer>();
		base1 = new Base(500, -1500, 120, 120);
		base2 = new Base(700, -4000, 120, 120);
		base3 = new Base(200, -7000, 120, 120);

		currentScreen = 0;
		firstTime = true;

		green1.accelerate(0, 5);
		green2.accelerate(0, 5);
		green3.accelerate(0, 5);

		wave1down = false;
		wave2down = false;
		wave3down = false;
		bossWaveDown = false;
		wave1active = false;
		wave2active = false;
		wave3active = false;
		bossWaveActive = false;

	}

}
