package predator_prey_sim;

import util.Helper;
import java.awt.*;
import java.awt.Color;
//uses movement class to help prey move 
public class Prey extends Movement {
    //set color variable for changing the offspring later on 
    private Color color;
        //initializing variables of prey
	public Prey(int x, int y) {
        super(x, y);
        color= Helper.newRandColor();
    }
    //initializing variables for prey depending on input
    public Prey(int x, int y, Color c) {
        super(x, y);
        color= c;
    }
    //used for offspring color change
    public Color getColor(){
        return color;
    }
    //main function of prey will move depending on the rules (10% chance of moving)
    public void moveRandomly(World world) {
        // 10 percent chance of turning
        if (Helper.nextDouble() <= .1) {
            this.changeDirection();
        }
	    //moves one space in world 
        super.move(world, 1);
    }
    //function to run away from predators 
    public void runAway(World world) {
        this.reverseDirection();
        super.move(world, 2);
    }
    //function to check if it should run away 
    public boolean shouldRun(World world) {
        for (Predator pr : world.getPredators()) {
            if (this.isPredNear(pr, world)) {
                return true;
            }
        }
        return false;
    }
    //this is part of the conversion from predator to prey 
    public boolean isEaten(World world) {
    	for (Predator pr : world.getPredators()) {
            if (this.isAdjacent(pr)) {
                return true;
            }
        }
        return false;
    }
    //i dont think i ended up using this for reporduction, which I just did in world 
    public boolean isRepro(World world) {
    	if(Helper.nextDouble() <= .10){
			return true;
		}
		
		else {
			return false;
		}
	}

}
