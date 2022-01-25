/*
 * Coded recently
 */
import java.awt.*;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;
public class MovingImage 
{
	private int x, y;
	private int width, height;
	private Image image;
	private boolean isVisible;
	
	public MovingImage(String name, int x, int y, int width, int height)
	{
		this((new ImageIcon(name)).getImage(),x,y,width,height);
	}

	public MovingImage(Image img, int x, int y, int width, int height) 
	{
		image = img;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		isVisible = true;
	}

	public void toggleVisibility() {
		isVisible = !isVisible;
	}
	
	public void moveByAmount(int x, int y) {
		this.x += x;
		this.y += y;
	}
	
	public void applyWindowLimits(int windowWidth, int windowHeight) {
		x = Math.min(x,windowWidth-this.width);
		y = Math.min(y,windowHeight-this.height);
		x = Math.max(0,x);
		y = Math.max(0,y);
	}
	
	public boolean isPointInImage(int mouseX, int mouseY) {
		if (mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height)
			return true;
		return false;
	}
	
	public void resize(int w, int h) {
		width = w;
		height = h;
	}
	
	public void draw(Graphics g, ImageObserver io) {
		if (isVisible)
			g.drawImage(image,x,y,width,height,io);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean isVisible() {
		return isVisible;
	}
	
}