# Predator Prey Simulation

**Classes and Functionality** 

_PPSIM_

This class actually runs the program. It continuously updates the program and also implements keyListner and mouseListener. These allow the user to add prey to the screen (mouse click), reset the simulation (press enter), and change the color of the background (press space bar). In addition I added my special feature here. When you click on the screen, both a prey and predator will be added to the screen (for this please remove '//' from the mouseClicked function). 

_World_

This class helps do basically everything for the program by using the other classes. It starts by making an array list of both predators and prey. It checks to see if there is wall there before adding them. Then it populates the world and draws each of the different predators and prey. It also keeps track of every single entity. Then it updates the world and makes changes (for example, if a prey is eaten by a predator, if a prey or predator needs to reproduce, and if a predator needs to die). 

_Movement_

This class holds everything that had to do with movement in the program. It helps the prey/predators move randomly, checks to see if there is a wall anywhere it is headings, if there is a prey or predator in the near vicinity (according to rules), helps to change direction if a prey sees a predator. I thought this was the best way to do it so both the predator and prey classes could use this class and help move however they need. This class also holds the function to add a new predator on every mouse click.

_Predator_

This class declares the variables for predators, sees if there is a prey next to it, and to see if it should case it. It is able to move through the use of the Movement class, which it is extended to.

_Prey_

This class is very similar to the predator class. There are a couple of changes, due to the requirements for prey. Prey checks to see if there is a predator in the vicinity and if it should run, if it needs to run then it moves 2 spaces, not just one. Then it goes to move randomly. If it is within one square of a predator then the function in prey "isEaten" becomes true and changes that prey into a predator. Otherwise, it will move randomly. It has one color, and the offspring have a 10% chance of being a different color than the parent.

_Special Feature_

My special feature is located in the mouseClicked function, where it adds the predators based on the mouse click. I decided to also add a prey to every location along with the predator.
