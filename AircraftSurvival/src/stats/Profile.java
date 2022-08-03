package stats;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.io.Reader;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import processing.core.PApplet;
import shapes.Rectangle;

public class Profile {

	private double maxHP;

	private double maxSpeed;

	private double weapondmg;

	private double furthestLevel;

	private int coins;
	private boolean status;

	private String profileName;

	/**
	 * Creates a new profile with default
	 * 
	 * @param profileName
	 */
	public Profile(String profileName) {
		// default profile
		this.setMaxHP(0);
		this.setMaxSpeed(0);
		this.setWeapondmg(0);
		this.setFurthestLevel(1);
		this.setCoins(0);
		this.setProfileName(profileName);
		this.setStatus(true);
	}

	/**
	 * Creates a new profile with Custom attributes
	 * 
	 * @param maxHP max hp
	 * @param maxSpeed max speed
	 * @param weapondmg weaopn dmg
	 * @param furthestLevel furthest level
	 * @param coins current amount of coins
	 * @param slot saving slot
	 */
	public Profile(double maxHP, double maxSpeed, double weapondmg, double furthestLevel, int coins,
			String profileName) {

		this.setMaxHP(maxHP);
		this.setMaxSpeed(maxSpeed);
		this.setWeapondmg(weapondmg);
		this.setFurthestLevel(furthestLevel);
		this.setCoins(coins);
		this.setProfileName(profileName);
		this.setStatus(true);

	}

	/**
	 * Reads the Profile data from a txt file
	 * 
	 * @param profileName the file to read from
	 * @return a Profile with custom attributes.
	 */
	public void loadProfile(int slot) {
		String line, s;
		BufferedReader in;
		Profile p;
		try {
			if (slot == 1) {
				in = new BufferedReader(new FileReader("Slot 1"));
			} else if (slot == 2) {
				in = new BufferedReader(new FileReader("Slot 2"));
			} else if (slot == 3) {
				in = new BufferedReader(new FileReader("Slot 3"));
			} else {
				in = null;
			}
			line = in.readLine();
			
			while (line != null) {
				if (line.contains("ProfileName:")) {
					s = line.substring(line.indexOf(' ') + 1);
					this.setProfileName(s);

				} else if (line.contains("MaxHP:")) {
					s = line.substring(line.indexOf(' ') + 1);
					this.setMaxHP((Double.parseDouble(s)));
				} else if (line.contains("MaxSpeed:")) {
					s = line.substring(line.indexOf(' ') + 1);
					this.setMaxSpeed((Double.parseDouble(s)));
				} else if (line.contains("WeaponDmg:")) {
					s = line.substring(line.indexOf(' ') + 1);
					this.setMaxHP((Double.parseDouble(s)));
				} else if (line.contains("FurthestLevel:")) {
					s = line.substring(line.indexOf(' ') + 1);
					this.setMaxHP((Double.parseDouble(s)));
				} else if (line.contains("Coins:")) {
					s = line.substring(line.indexOf(' ') + 1);
					this.setMaxHP((Integer.parseInt(s)));
				}
				line = in.readLine();
			}
			in.close();
		} catch (IOException e) {
			System.out.println("Problems reading profile.\nDefault Profile loaded.");
//			if(slot ==1)
//			  p = new Profile("Slot 1");
//			else if(slot==2)
//			  p = new Profile("Slot 2");
//			else if(slot==3)
//			  p = new Profile("Slot 3");
//			th = p;
		}

	}

	/**
	 * Saves a current profile
	 * 
	 * @param profileName the name under which you wish to save it
	 */
	public void saveProfile(int slot) {
		String pathName = "";
		FileWriter f = null;
		// Search for profileName
		try {

			if (slot == 1) {
				pathName = "Slot 1";
			} else if (slot == 2) {
				pathName = "Slot 2";
			} else if (slot == 3) {
				pathName = "Slot 3";
			} else {
				System.out.println("Error Slot number incorrect");
			}
			f = new FileWriter(new File(pathName));
		} catch (IOException e) {
			System.out.println("Problem in creating file");
		}

		try {

			f.write("ProfileName: " + this.getProfileName() + "\n");
			f.write("MaxHP: " + this.getMaxHP() + "\n");
			f.write("MaxSpeed: " + this.getMaxSpeed() + "\n");
			f.write("WeaponDmg: " + this.getWeapondmg() + "\n");
			f.write("Furthestlevel: " + this.getFurthestLevel() + "\n");
			f.write("Coins: " + this.getCoins() + "\n");
			f.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Problem in writing file");
		}
	}

	public void draw(PApplet marker, double x, double y, double width, double height) {
		marker.fill(Color.WHITE.getRGB());
		double scaling = 1;
		marker.text("" + this.getProfileName(), (float) x, (float) (y - height / 20.0));
		marker.fill(Color.BLACK.getRGB());
		marker.text("MaxHP: " + this.getMaxHP(), (float) x, (float) (y + y * scaling / 5.0));
		scaling++;
		marker.text("MaxSpeed: " + this.getMaxSpeed(), (float) x, (float) (y + y * scaling / 5.0));
		scaling++;
		marker.text("WeaponDmg: " + this.getWeapondmg(), (float) x, (float) (y + y * scaling / 5.0));
		scaling++;
		marker.text("Furthestlevel: " + this.getFurthestLevel(), (float) x, (float) (y + y * scaling / 5.0));
		scaling++;
		marker.text("Coins: " + this.getCoins(), (float) x, (float) (y + y * scaling / 5.0));
	}

	public void printProfile() {
		System.out.println(this.getProfileName());
		System.out.println(this.getCoins());
		System.out.println(this.getFurthestLevel());
		System.out.println(this.getMaxHP());
		System.out.println(this.getMaxSpeed());
		System.out.println(this.getWeapondmg());
		
	}
	/**
	 * @return the maxHP
	 */
	public double getMaxHP() {
		return maxHP;
	}

	/**
	 * @param maxHP the maxHP to set
	 */
	public void setMaxHP(double maxHP) {
		this.maxHP = maxHP;
	}

	/**
	 * @return the maxSpeed
	 */
	public double getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * @param maxSpeed the maxSpeed to set
	 */
	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	/**
	 * @return the weapondmg
	 */
	public double getWeapondmg() {
		return weapondmg;
	}

	/**
	 * @param weapondmg the weapondmg to set
	 */
	public void setWeapondmg(double weapondmg) {
		this.weapondmg = weapondmg;
	}

	/**
	 * @return the furthestLevel
	 */
	public double getFurthestLevel() {
		return furthestLevel;
	}

	/**
	 * @param furthestLevel the furthestLevel to set
	 */
	public void setFurthestLevel(double furthestLevel) {
		this.furthestLevel = furthestLevel;
	}

	/**
	 * @return the coins
	 */
	public int getCoins() {
		return coins;
	}

	/**
	 * @param coins the coins to set
	 */
	public void setCoins(int coins) {
		this.coins = coins;
	}

	/**
	 * @return the profileName
	 */
	public String getProfileName() {
		return profileName;
	}

	/**
	 * @param profileName the profileName to set
	 */
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	/**
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}
}
