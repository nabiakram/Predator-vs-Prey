package predator_prey_sim;

import util.Helper;

import java.awt.*;
import java.awt.Color;

//creates instance
public abstract class Movement{
	int x;
	int y;
	int direction;
	
	//gets the x-y coordinates
	public Movement(int x, int y){
		//to get x
		this.x = x;
		//to get y
		this.y = y;
		//to change direction
		this.direction = Helper.nextInt(4);
	}
	//used to change direction for preys and predators
	protected void changeDirection(){
		//changes direction
		this.direction = Helper.nextInt(4);
	}
	//used for preys when they see a predator 
	protected void reverseDirection(){
		this.direction = (this.direction + 2) % 4;
	}
	//checks to see if there is a prey in 15 spaces for the predator
	//if it is near and there is a wall or not 
	public boolean isPreyNear(Movement c, World world) {
        int xDist = this.x - c.getX();
        int yDist = this.y - c.getY();

        // North which means other thing is above (yDist is positive)
        if (this.direction == 0 && xDist == 0 && yDist > 0 && yDist < 15) {
            // Check for wall within space between this creature and other creature
            for (int i = this.y; i > c.getY(); i--) {
                if (world.isWall(this.x, i)) {
                    return false;
                }
            }
            return true;
        }
        // East which means other thing is to the right (xDist is negative)
        if (this.direction == 1 && yDist == 0 && xDist > -15 && xDist < 0) {
            for (int i = this.x; i < c.getX(); i++) {
                if (world.isWall(i, this.y)) {
                    return false;
                }
            }
            return true;
        }

        
        // South: other thing is below (yDist is negative)
        if (this.direction == 2 && xDist == 0 && yDist > -15 && yDist < 0) {
            for (int i = this.y; i < c.getY(); i++) {
                if (world.isWall(this.x, i)) {
                    return false;
                }
            }
            return true;
        }
        // West: other thing is to the left (xDist is positive)
        if (this.direction == 3 && yDist == 0 && xDist > 0 && xDist < 15) {
            for (int i = this.x; i > c.getX(); i--) {
                if (world.isWall(i, this.y)) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    //same function as above but this one is for preys to see if they are within 10 spaces
    public boolean isPredNear(Movement c, World world) {
        int xDist = this.x - c.getX();
        int yDist = this.y - c.getY();

        // North: other thing is above (yDist is positive)
        if (this.direction == 0 && xDist == 0 && yDist > 0 && yDist < 10) {
            // Check for wall within space between this creature and other creature
            for (int i = this.y; i > c.getY(); i--) {
                if (world.isWall(this.x, i)) {
                    return false;
                }
            }
            return true;
        }
        // East: other thing is to the right (xDist is negative)
        if (this.direction == 1 && yDist == 0 && xDist > -10 && xDist < 0) {
            for (int i = this.x; i < c.getX(); i++) {
                if (world.isWall(i, this.y)) {
                    return false;
                }
            }
            return true;
        }
        // South: other thing is below (yDist is negative)
        if (this.direction == 2 && xDist == 0 && yDist > -10 && yDist < 0) {
            for (int i = this.y; i < c.getY(); i++) {
                if (world.isWall(this.x, i)) {
                    return false;
                }
            }
            return true;
        }
        // West: other thing is to the left (xDist is positive)
        if (this.direction == 3 && yDist == 0 && xDist > 0 && xDist < 10) {
            for (int i = this.x; i > c.getX(); i--) {
                if (world.isWall(i, this.y)) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    //this checks to see if there is another thing right next to it, this will be used to 
    //change the prey to predators later
    public boolean isAdjacent(Movement c) {
        int xDist = Math.abs(this.x - c.getX());
        int yDist = Math.abs(this.y - c.getY());
        
        // If the different types of creatures are next to each other or colliding
        // Diagonals don't count
        if (xDist == 0 && yDist <= 1) {
            // Same column, only one row apart or zero
            return true;
        } else if (yDist == 0 && xDist <= 1) {
            // Same row, only one column apart or zero
            return true;
        }
        return false;
    }
	//this is used to move the prey and predators 
	public void move(World world, int numSpace){
		//gets the x and y coord
		int nextX = this.x;
		int nextY = this.y;
		
		// moves north
		if(this.direction == 0){
			nextY = this.y - numSpace; 
		}
			//moves east
			else if(this.direction == 1){
				nextX = this.x + numSpace; 
			}
			//moves south
			else if(this.direction == 2){
				nextY = this.y + numSpace; 
			}
			//moves west 
			else if(this.direction == 3){
				nextX = this.x - numSpace; 
			}
		//checks to see if there is a wall if there
		//is then change the direction of the predator or prey
		if(world.isWall(nextX, nextY)){
			this.changeDirection();
		}

		else{
			this.x=nextX;
			this.y=nextY;
		}

	}

	//makes function for subclasses (prey and predator)
	public abstract void moveRandomly(World world);

	//gets the x coordinate
	public int getX(){
		return this.x;
	}
	//gets the y coordinate
	public int getY(){
		return this.y;
	}
}
