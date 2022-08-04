# Aircraft Survival
```
Authors: Krishan Patel, Nihal Ernest, Fabio Eirea
Revision: 5/18/20
```

## Introduction: 
This program is a game that takes the exhilaration of being a wartime pilot and allows everyday people to enjoy the basics aspects. It uses a 2D bird’s eye view to allow players to view the world around them and explore it as the map scrolls forward. It has missions that the player can partake in flying around trying to take out enemy camps while battling enemy aircrafts[a], with the final battle being enemy bases. Anyone can enjoy this thrilling video game if they are in need of entertainment.


### What is it?
Our project is a 2D bird’s eye view airplane game where the player has to avoid enemy planes by dodging their attacks and dealing with them while destroying enemy bases under a level specific time limit.


## Rules
* Players have to destroy all enemy bases and aircraft without dying to proceed to the next wave and eventually take on the boss to get the highest score.
* If the player takes on more damage than the value of his hit points then the player “dies” and must restart that level. 
Controls
	Left arrow
	Shifts the plane left
	Right arrow
	Shifts the plane right
	Up arrow
	Shifts the Plane up
	Down arrow
	Shifts the Plane down
	Spacebar
	Allows the user to shoot bullets provided that the user’s plane has them in the barrel.
	R
	Reloads gun
	F
	Holding it activates temporary afterburners
	B
	Drops “bombs” from the plane that cause damage to enemy bases. Can only be activated if user has “bombs” in inventory
	Mouse
	Allows users to interact with buttons. Ex exit game, change settings, choose level, etc.
	

### Features List (THE ONLY SECTION THAT CANNOT CHANGE LATER):
Must-have Features:
* Three levels that has varying degrees of difficulty
   *  Easy - It will have a couple of enemy planes that are not very good at shooting and have lower hitpoints. They will shoot at the current position of the player. No enemy bases.
   * Medium - better enemy planes they now shoot slightly in front of the user and take direction into account but not speed. A few enemy camps appear and can also shoot back exactly like the planes. 
   * Hard - More planes and [c]camps [d]than Medium. Planes don't just try to follow the plane but try specific tactics such as prefiring to force the user to go a certain way. They take direction and speed into their shooting. The final boss is an entire enemy base.
* Proper weapon systems set up. Machine guns and bombs for the player
   * The player can press keys to fire guns or drop bombs. Players will need to reload every so often and can run out of ammo. Bombs can only damage ground targets while bullets can only hit air targets.
* Enemies that are autonomous
   * Enemy decision making is not solely dependent on the user. 
* A save and loading system to keep track of progress. Player stats and game data will be stored in a .txt file.
*  Afterburners-Temporary speed boost that grants a lot of speed but can be used once every minute. A progress bar will show when players can activate this boost.


### Want-to-have Features:
*  Local multiplayer. Multiple people up to 4[f] can play the game at the same time. Each player has different key binds on the same keyboard.
* An upgrade system based on points collected by the player when achieving certain goals. This will allow players to upgrade aircraft with stuff such as more guns or better engines
*  Special ammo such as incendiary rounds or heavy rounds.
*  Multiple types of enemy planes, such as light fighters, aircraft that will be designed for speed, interceptors, planes that will have increased damage, etc...
*  [g]The map will be randomly generated


### Stretch Features:
* Multiple maps
* Implement an adjustable quality feature
* Allow users to adjust frame rate to increase performance on less powerful devices.




## Class List:
[This section lists the Java classes that make up the program and very briefly describes what each represents.]
* DrawingSurface - Contains the bulk of the program and handles everything displayed on the screen and certain interactions between classes
* Main - The main method. Creates the required window and drawing surface
* Screens - The parent class of all screens used in the program
   * Controls - A screen that shows the controls required to play the game
   * GameOver - The game over screen that displays the score
   * Load - The screen that allows players to load profiles
   * Save - The screen that allows players to save profiles
   * Settings - The screen that displays settings
* Shapes - The parent class of the shapes used in the program
   * PhysicsShape - Used to handle the physics of all objects in the game
   * Rectangle - Allows users to create a rectangle and utilize its useful methods
* Base - An enemy base
* Bullet - The bullet fired from guns of aircraft
* Gun - The guns on aircraft
* Plane - The aircraft class
* Progressbar - A progress bar that can represent any ratio or fraction visually
   * Health Bar - A progress bar used to represent the health of planes or bases.
* Profile - Used to make a profile in the saves/loads.
Final Project UML- AircraftSurvival


## Credits:

* Nihal
   * Will work on the plane class and movements
   * Working on scenery using PhysicsShape
   * Visuals and images used in the project
* Fabio
   * Working on the enemies
   * Combat system
* Krishan
   * Will work on the Main menu. 
   * Saving and loading Profiles
   * Other menus such as controls
