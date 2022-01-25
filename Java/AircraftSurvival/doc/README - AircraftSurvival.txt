Aircraft Survival
Authors: Krishan Patel, Nihal Ernest, Fabio Eirea
Revision: 5/18/20


Introduction: 
This program is a game that takes the exhilaration of being a wartime pilot and allows everyday people to enjoy the basics aspects. It uses a 2D bird’s eye view to allow players to view the world around them and explore it as the map scrolls forward. It has missions that the player can partake in flying around trying to take out enemy camps while battling enemy aircrafts[a], with the final battle being enemy bases. Anyone can enjoy this thrilling video game if they are in need of entertainment.


Instructions:
To navigate menus:
Menu interface Rough Draft




What is it?
Our project is a 2D bird’s eye view airplane game where the player has to avoid enemy planes by dodging their attacks and dealing with them while destroying enemy bases under a level specific time limit.[b]


Rules
* Players have to destroy all enemy bases and aircraft without dying to proceed to the next wave and eventually take on the boss to get the highest score.
* If the player takes on more damage than the value of his hit points then the player “dies” and must restart that level. 
Controls


























Button
	What it does
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
	

Features List (THE ONLY SECTION THAT CANNOT CHANGE LATER):
Must-have Features:
[These are features that we agree you will definitely have by the project due date. A good final project would have all of these completed. At least 5 are required. Each feature should be fully described (at least a few full sentences for each)]
* Three levels that has varying degrees of difficulty
   *  Easy - It will have a couple of enemy planes that are not very good at shooting and have lower hitpoints. They will shoot at the current position of the player. No enemy bases.
   * Medium - better enemy planes they now shoot slightly in front of the user and take direction into account but not speed. A few enemy camps appear and can also shoot back exactly like the planes. 
   * Hard - More planes and [c]camps [d]than Medium. Planes don't just try to follow the plane but try specific tactics such as prefiring to force the user to go a certain way. They take direction and speed into their shooting. The final boss is an entire enemy base.
* Proper weapon systems set up. Machine guns and bombs for the player
   * The player can press keys to fire guns or drop bombs. Players will need to reload every so often and can run out of ammo. Bombs can only damage ground targets while bullets can only hit air targets.
* Enemies that are autonomous
   * Enemy decision making is not solely dependent on the user. 
* A save and loading system to keep track of progress. Player stats and game data will be stored in a .txt file.
*  Afterburners-Temporary speed boost that grants a lot of speed but can be used once every minute. A progress bar will show when players can activate this boost.[e]


Want-to-have Features:
[These are features that you would like to have by the project due date, but you’re unsure whether you’ll hit all of them. A good final project would have perhaps half of these completed. At least 5 are required. Again, fully describe each.]
*  Local multiplayer. Multiple people up to 4[f] can play the game at the same time. Each player has different key binds on the same keyboard.
* An upgrade system based on points collected by the player when achieving certain goals. This will allow players to upgrade aircraft with stuff such as more guns or better engines
*  Special ammo such as incendiary rounds or heavy rounds.
*  Multiple types of enemy planes, such as light fighters, aircraft that will be designed for speed, interceptors, planes that will have increased damage, etc...
*  [g]The map will be randomly generated


Stretch Features:
[These are features that we agree a fully complete version of this program would have, but that you probably will not have time to implement. A good final project does not necessarily need to have any of these completed at all. At least 3 are required. Again, fully describe each.]
* Multiple maps
* Implement an adjustable quality feature
* Allow users to adjust frame rate to increase performance on less powerful devices.




Class List:
[This section lists the Java classes that make up the program and very briefly describes what each represents. It’s totally fine to put this section in list format and not to use full sentences.]
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


Credits:
[Gives credit for project components. This includes both internal credit (your group members) and external credit (other people, websites, libraries). To do this:
* List the group members and describe how each member contributed to the completion of the final program. This could be classes written, art assets created, leadership/organizational skills exercises, or other tasks. Initially, this is how you plan on splitting the work.
* Give credit to all outside resources used. This includes downloaded images or sounds, external java libraries, parent/tutor/student coding help, etc.]


Outside Resources
* 



* Nihal
   * Will work on the plane class and movements
   * Working on scenery using PhysicsShape
   * Visuals and images used in the project
* Fabio
   * Working on the enemies
   * Combat system
* Krishan[h]
   * Will work on the Main menu. 
   * Saving and loading Profiles
   * Other menus such as controls
[a]I need much more specific information. I admit that I cannot imagine the gameplay much from this description. Is it top-down? Side-view? Is it first-person like I'm in the plane?
[b]This answers some of my question. Let's move it up to the intro and expand on it.
[c]In general, I like this list of must-haves
[d]There's bases? Is this mentioned above?
[e]In general, I like this list of must-haves
[f]this is not a rigid number, just to be clear. it will change if circumstances arise.
[g]Expand with 1 more idea, and make the ideas you have more specific rather than general categories (this is what I'm grading based on, so it need to be less like "multiple types of enemies" and more like "at least 2 additional types of enemy planes that differ in how they move and react to the player")
[h]Must have a plan on how you will split the work.