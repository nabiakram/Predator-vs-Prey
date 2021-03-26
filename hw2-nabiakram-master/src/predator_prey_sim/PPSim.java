package predator_prey_sim;

import util.DotPanel;
import util.Helper;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;


/*
 * You must add a way to represent humans.  When there is not a zombie apocalypse occurring, humans
 * should follow these simple rules:
 * 		if (1 in 10 chance):
 * 			turn to face a random direction (up/down/left/right)
 * 		Move in the current direction one space if not blocked by a wall
 *
 * We will add additional rules for dealing with sighting or running into zombies later.
 */

//have to add keyListener and mouseListener for pressing a key and mouse
public class PPSim extends JFrame implements KeyListener, MouseListener {

	private static final long serialVersionUID = -5176170979783243427L;

	/** The Dot Panel object you will draw to */
	protected static DotPanel dp;

	/* Define constants using static final variables */
	public static final int MAX_X = 100;
	public static final int MAX_Y = 100;
	public static final int DOT_SIZE = 6;
	private static final int NUM_PREY = 10;
	private static final int NUM_PREDATORS = 5;

	/*
	 * This fills the frame with a "DotPanel", a type of drawing canvas that
	 * allows you to easily draw squares for predators and circles for prey
	 * to the screen.
	 */
	public World ppworld;

	public PPSim() {
		this.setSize(MAX_X * DOT_SIZE, MAX_Y * DOT_SIZE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Predator Prey World");

		/* Create and set the size of the panel */
		dp = new DotPanel(MAX_X, MAX_Y, DOT_SIZE);

		/* Add the panel to the frame */
		Container cPane = this.getContentPane();
		cPane.add(dp);
		
		//initializing

		addKeyListener(this);
		addMouseListener(this);

		/* Initialize the DotPanel canvas:
		 * You CANNOT draw to the panel BEFORE this code is called.
		 * You CANNOT add new widgets to the frame AFTER this is called.
		 */
		this.pack();
		dp.init();
		dp.clear();
		dp.setPenColor(Color.red);
		this.setVisible(true);

		/* Create our city */
		ppworld = new World(MAX_X, MAX_Y, NUM_PREY, NUM_PREDATORS);

		/* This is the Run Loop (aka "simulation loop" or "game loop")
		 * It will loop forever, first updating the state of the world
		 * (e.g., having humans take a single step) and then it will
		 * draw the newly updated simulation. Since we don't want
		 * the simulation to run too fast for us to see, it will sleep
		 * after repainting the screen. Currently it sleeps for
		 * 33 milliseconds, so the program will update at about 30 frames
		 * per second.
		 */
		while(true)
		{
			// Run update rules for world and everything in it
			ppworld.update();
			// Draw to screen and then refresh
			ppworld.draw();
			dp.repaintAndSleep(55);

		}
	}

	public static void main(String[] args) {
		/* Create a new GUI window  */
		new PPSim();
	}

	//sees if you press either enter or space and resets simulation for enter and 
	//space will reset color of the  canvas of the world 
	@Override
	public void keyPressed(KeyEvent e) {
        // Reset world if user presses Enter
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			ppworld = new World(MAX_X, MAX_Y, NUM_PREY, NUM_PREDATORS);
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			ppworld.canvasColor = Helper.newRandColor();
		}


	}
	//have to have this or else wont work, gives error
	@Override
	public void keyTyped(KeyEvent e) {

	}
	//have to have this or else wont work, gives error
	@Override
	public void keyReleased(KeyEvent e) {

	}
	//have to have this or else wont work, gives error
	@Override
	public void mouseClicked(MouseEvent e) {
	    // Scale down coordinates by factor of dot size 6
		ppworld.addPredator(e.getX()/6, e.getY()/6);
		//ppworld.addPrey(e.getX()/6, e.getY()/6);
	}
	//have to have this or else wont work, gives error
	@Override
	public void mouseEntered(MouseEvent e) {}
	//have to have this or else wont work, gives error
	@Override
	public void mouseExited(MouseEvent e) {}
	//have to have this or else wont work, gives error
	@Override
	public void mousePressed(MouseEvent e) {}
	//have to have this or else wont work, gives error
	@Override
	public void mouseReleased(MouseEvent e) {}
	

}

