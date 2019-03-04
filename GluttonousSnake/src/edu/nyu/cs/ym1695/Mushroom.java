package edu.nyu.cs.ym1695;
import java.util.Random;

/**
 * The class that perform mushroom functionality
 * @author Sherry Ma & Steven Hu
 * @version 1.0
 *
 */
public class Mushroom extends Food{
	
	/**
	 * Set random color, xy coordinate, and type to 0(mushroom)
	 */
	public Mushroom() {
		super();
		this.setFoodType(0);
	}
	/**
	 * Overriding method from the food class
	 * Update the snake if mushroom is eaten
	 */
	public void foodEaten(Snake snake) {
		// if snake eats the mushroom
		// TOXIC!
	    if( snake.getX()[0] == super.getFoodX() && snake.getY()[0] == super.getFoodY()){
	    	// check which kind of mushroom the snake gets
	    	if (super.getR()>=115 || super.getG()>=115 || super.getB()>=115) {
	    		
	    		// increase the speed
	            snake.setSpeed(snake.getSpeed()+3);
	            
	    	}
	    	else {
	    		
	    		// increase the length
	    		snake.setLength(snake.getLength()+5);
	    	}
	    	// both mushrooms will decrease its HP
	    	snake.setHP(snake.getHP()-10);
	    	// set another mushroom
	    	super.setFood();
	    	// choose another color
	        this.setColor();
	        // the maximum speed is 20
	        if (snake.getSpeed() >=20) {
	        	snake.setSpeed(20); 
	            }
	        
	    }
	}
	/**
	 * Set random color for mushroom
	 */
	public void setColor() {
		Random rand = new Random();
        super.setR(rand.nextInt(235));
        super.setG(rand.nextInt(235));
        super.setB(rand.nextInt(235));
	}
	
	
}


