package predator_prey_sim;

import util.Helper;

import java.awt.*;
import java.util.ArrayList;
import java.awt.Color;



public class World {

	private int width, height;
	protected Color canvasColor;

	//will check if there is wall or not 
	private boolean walls[][];
	//to insert prey
	private ArrayList<Prey> preyList = new ArrayList<>();
	//to insert predator
	private ArrayList<Predator> predatorList = new ArrayList<>();

	/**
	 * Create a new City and fill it with buildings and people.
	 * @param w width of city
	 * @param h height of city
	 * @param numPrey number of prey
	 * @param numPredator number of predators
	 */
	public World(int w, int h, int numPrey, int numPredator) {
		width = w;
		height = h;
		canvasColor = Helper.newRandColor();
		//checks if cordinate is a wall 
		walls = new boolean [w][h];

		// Add Prey and Predators to the world.
		populate(numPrey, numPredator);
	}
	//this is to check if there is a wall present or not 
	public boolean isWall (int x, int y){
		if((x < 0) || (x >= width -1) || (y < 0) || (y >= height-1)){
			return true;
		}
		else{
			return walls[x][y];
		}
	}

	/**
	 * Generates numPrey random prey and numPredator random predators 
	 * distributed throughout the world.
	 * Prey must not be placed outside canavas!
	 *
	 * @param numPrey the number of prey to generate
	 * @param numPredator the number of predators to generate
	 */
	
	//populates the respective number of prey and predators depending on 
	//how many you set in it 
	//then checks to see if there is a wall, if there is then it gets another int for x and y
	//if there isnt then it adds a new instance of prey or predator to their array list 
	private void populate(int numPrey, int numPredators){
		
		for (int i=0; i< numPredators; i++){
			int x= Helper.nextInt(width);
			int y= Helper.nextInt(height);

			while(walls[x][y]){
				x = Helper.nextInt(width);
				y = Helper.nextInt(height);
			}
			predatorList.add(new Predator(x,y));
		}

	  	
	  	for (int i=0; i< numPrey; i++){
			int x= Helper.nextInt(width);
			int y= Helper.nextInt(height);
	
			while(walls[x][y]){
				x = Helper.nextInt(width);
				y = Helper.nextInt(height);
			}
			preyList.add(new Prey(x,y));
			//System.out.println(preyList);
		}	
	}
	//this changes a prey into a predator after the functions have been called to see if 
	//any have been eaten (prey within 1 square of predator)
	public void zombify() {
		//creates array list for ones that will be dead
		ArrayList<Prey> eatenPrey = new ArrayList<>();
		
		for (Prey p : preyList) {	
				if (p.isEaten(this)) {
					// Add to temp array list of dead prey
					eatenPrey.add(p);
				}
		}
		//found this function to remove all from javadoc...super helpfull!
		preyList.removeAll(eatenPrey);
		
		// Turn all of the eaten prey into predators
		for (Prey prey : eatenPrey) {
			predatorList.add(new Predator(prey.getX(), prey.getY()));
		}

	}
	//checks to see if it should reproduce (following rules, but i changed the percentages to make it easier to see)
	public boolean reproduce(boolean b){
		double per = Helper.nextDouble();
		if(b){
			if(per <= .01){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			if(per <= .001){
				return true;
			}
			else{
				return false;
			}
		}
	}
	//boolean to check if the predators should randomly die (i changed the percentages to make it easier to see)
	public boolean randDeath(){
		double per = Helper.nextDouble();
		if(per <= .004){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Draw all the predators and prey.
	 */
	public void draw(){
		/* Clear the screen */
		PPSim.dp.clear(canvasColor);
		// Draw predators and pray


		//for loop for predators 
		for(Predator pr : predatorList){
			//set color as red
			PPSim.dp.setPenColor(Color.RED);
			//sets predator
			PPSim.dp.drawSquare(pr.x, pr.y, PPSim.dp.RED);
		}

		//same thing as above but for prey p.x
		for(Prey p : preyList){
			//assignes random color to them 
			PPSim.dp.drawCircle(p.x, p.y, p.getColor());
		}
		
	}

	public void update() {
        // Move humans, zombies
		//array list for dead pred and reproducting prey and predators
		ArrayList<Predator> killPred = new ArrayList<>();
		ArrayList<Predator> reproPred = new ArrayList<>();

		ArrayList<Prey> reproPrey = new ArrayList<>();
	//checks to see if it should run away and if thats true it runs away else it moves randomly
        for (Prey p : preyList) {

            if (p.shouldRun(this)) {
                p.runAway(this);
            }

            else {
			p.moveRandomly(this);
            }
		//checks to see if reproducing is true, if it is then adds a new prey to reproPrey array list 
            if(reproduce(true)){
            	if(reproduce(true)){
            		reproPrey.add(new Prey(p.getX(), p.getY()));
            	}
            	else{
            		reproPrey.add(new Prey (p.getX(), p.getY(), p.getColor()));
            	}
            }
            p.moveRandomly(this);
		}
		//adds back the reproprey into the original prey array list, had trouble with this, you cant have it in the 
		//for loop with everything because you cant change a list inside of a forloop that calls the list 
		for(Prey p : reproPrey){
            	preyList.add(p);
        }

		//Pred : die (%5) and reproduce (%1) 
		//Prey : reproduce (10%) offspring diff color (10%)
		//checks to see if it should run away and if thats true it runs away else it moves randomly
		for (Predator pr : predatorList) {
		   
		    if (pr.shouldChase(this)) {
		        pr.chase(this);
            }

            else {
				pr.moveRandomly(this);
           }

			pr.moveRandomly(this);
		

		if(reproduce(false)){
           	reproPred.add(new Predator(pr.getX(), pr.getY()));
           }
		//sees if random death is true, if it is then adds it to pred list 
           if(randDeath()){
           	killPred.add(pr);
           }
           }
	   //adds to predator list 
           for(Predator pr: reproPred){
           	predatorList.add(pr);
           }
	   //removes the ones that need to be filled that came up as true 
           predatorList.removeAll(killPred);

		//predatorList.removeAll(killPred);
		// Handle infection process
		this.zombify();
    }
	//for getting the prey list 
	public ArrayList<Prey> getPreys(){
		return this.preyList;
	}
	//for getting the predator list 
	public ArrayList<Predator> getPredators(){
		return this.predatorList;
	}

	//when click mouse, uses this function to add a new predator 
	public void addPredator(int x, int y) {
		if (!isWall(x, y)) {
			predatorList.add(new Predator(x, y));
		}
	}
	//when click mouse, uses this function to add a new prey 
	public void addPrey(int x, int y){
		if(!isWall(x, y)) {
			preyList.add(new Prey(x, y));
		}
	}

}
