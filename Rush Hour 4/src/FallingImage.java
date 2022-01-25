    
/*
 * Coded by Han Lee
 *
 * Falling image that defines all the falling stuff
 */
 
 import java.awt.*;
 import java.awt.image.ImageObserver;
 import javax.swing.ImageIcon;
 
 public class FallingImage extends MovingImage
 {
	 
     public FallingImage(String name, int x, int y, int width, int height)
     {
    	 super((new ImageIcon(name)).getImage(), x , y , width , height);
    	 
     }
 
     public void fall(int speed)
     {
    	 
     }
 }
