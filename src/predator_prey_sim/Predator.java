package predator_prey_sim;

import util.Helper;
import java.awt.*;
import java.awt.Color;
//uses the movement class so predator can move...
public class Predator extends Movement {

	//sets x and y
	public Predator (int x, int y){
		super(x, y);
	}

	//makes it move randomly
	public void moveRandomly(World world){
		
		//this allows it to change direction if 
		//double value is less than or equal to 
		//.05 (%5)

		if(Helper.nextDouble() <= .05){
			//changes direction
			this.changeDirection();
		}
		//moves through the movement class, 
		//moves one space, but I did it this way so 
		//later i can make it move like 10 spaces
		//to make it go crazy fast
		super.move(world, 1);
	}

	 /* Move towards prey if seen within 15 squares of current direction, same as moving forward */
    public void chase(World world) {
    	super.move(world, 1);
    }
	
    //checks to see if it should even chase it, or it needs to chase the prey 
    public boolean shouldChase(World world) {
        for (Prey p : world.getPreys()) {
            if (this.isPreyNear(p, world)) {
                return true;
            }
        }
        return false;
    }
    //i didnt use the below functions I tried doing the death and reproduction
    //this way but it didnt work at all 
    //checks to see if 5%, to later kill 
	public boolean isKilled(World world) {
    	if(Helper.nextDouble() <= .05){
			return true;
		}
		
		else {
			return false;
		}
	}
	//checks to see if 1%, to reproduce later
	public boolean isRepro() {
    	if(Helper.nextDouble() <= .01){
			return true;
		}
		
		else {
			return false;
		}
	}
	

}
