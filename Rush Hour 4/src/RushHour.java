/*
	 * Coded by Nihal Ernest
	 * 5/11/2019
	 */
	import java.awt.*;
	import java.awt.event.*;
	import javax.swing.*;
	
	public class RushHour extends JPanel implements KeyListener {
	
		public static final int DRAWING_WIDTH = 800;
		public static final int DRAWING_HEIGHT = 600;
	
		private Image bCar;
		private Car blueCar;
		private Obstacles grayCar;
		private MovingLanes movingLanes;
		
		private int carX, carY;
	
		private boolean leftKeyPressed, rightKeyPressed, upKeyPressed, downKeyPressed;
	
		public RushHour() {
			super();
			bCar = (new ImageIcon("blue-car.png")).getImage();
			carX = 175;
			carY = 400;
			blueCar = new Car(carX, carY);
			movingLanes = new MovingLanes(250, 0);
			grayCar = new Obstacles(175, 200);
		}
	
		public void paintComponent(Graphics g) {
			
			super.paintComponent(g);
			int width = getWidth();
			int height = getHeight();
	
			double ratioX = (double) width / DRAWING_WIDTH;
			double ratioY = (double) height / DRAWING_HEIGHT;
	
			Graphics2D g2 = (Graphics2D) g;
			g2.scale(ratioX, ratioY);
	
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(150, 0, 500, 600);
	
			g.setColor(Color.BLACK);
			g.drawRect(150, 0, 500, 600);
			
			g.drawImage(bCar, carX, carY, 50, 100, this);
		//	blueCar.draw(g, this);
			movingLanes.draw(g, this);
			grayCar.draw(g,  this);
		}
	
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				//leftKeyPressed = true;
				carX = carX - 10;
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			//	rightKeyPressed = true;
				carX = carX + 10;
		//	} else if (e.getKeyCode() == KeyEvent.VK_UP) {
		//		upKeyPressed = true;
		//	} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
		//		downKeyPressed = true;
			}
			repaint();
		}
	
		public void keyReleased(KeyEvent e) {
		/*	if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftKeyPressed = false;
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightKeyPressed = false;
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				upKeyPressed = false;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downKeyPressed = false;
		}*/
		}
	
		public void keyTyped(KeyEvent arg0) {
	
		}
		
	/*	  public void run() {
			  	while(true) {
			  		// MAKE A CHANGE
			  		if (leftKeyPressed) {
			  			blueCar.moveLeft(50);
			  	  	} else if (rightKeyPressed) {
			  	  		blueCar.moveLeft(50);
			  	  	} if (upKeyPressed) {
			  	  		blueCar.moveLeft(50);
			  	  	}  
	
			  		
			  		// SHOW THE CHANGE
			  		repaint();
			  		
			  		
			  		// WAIT
			  		try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			  		
			  	}
			  }*/
	//	
	//	TRYING TO GET A MOVING OBJECT TO WORK
	//	public void moveCar() {
	//		while (true) {
	//			if (upKeyPressed) {
	//				blueCar.drive(10);
	//			} else if (downKeyPressed) {
	//				blueCar.drive(-10);
	//			}
	//
	//			repaint();
	//		}
	//	}
	
		public static void main(String[] args) {
			JFrame window = new JFrame("Rush Hour 4");
			window.setBounds(100, 100, 800, 600);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			RushHour panel = new RushHour();
			panel.setBackground(Color.GREEN);
			window.add(panel);
			window.setResizable(false);
			window.setVisible(true);
			
			window.addKeyListener(panel);
			//panel.run();
		}
	
	}