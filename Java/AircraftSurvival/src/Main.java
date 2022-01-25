import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import stats.Profile;

public class Main {

	public static void main(String[] args) {

		// Check to see if there are any txt files and load them into the profiles.
		Profile p1, p2, p3;

		File f1 = new File("Slot 1");
		File f2 = new File("Slot 2");
		File f3 = new File("Slot 3");

		p1 = new Profile("Unnamed 1");
		p2 = new Profile("Unnamed 2");
		p3 = new Profile("Unnamed 3");

		if (f1.exists())
			p1.loadProfile(1);
		else {}
		if (f2.exists())
			p2.loadProfile(2);
		if (f3.exists())
			p3.loadProfile(3);

		p1.saveProfile(1);
		p2.saveProfile(2);
		p3.saveProfile(3);

		DrawingSurface drawing = new DrawingSurface(p1, p2, p3);
		PApplet.runSketch(new String[] { "" }, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame) canvas.getFrame();
		
		window.setTitle("Aicraft Survival");
		window.setSize(1000, 800);
		window.setLocation(100, 100);
		window.setMinimumSize(new Dimension(200, 200));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);

		window.setVisible(true);
	}

}
