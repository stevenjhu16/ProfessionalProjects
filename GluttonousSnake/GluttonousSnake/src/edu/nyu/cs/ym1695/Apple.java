package edu.nyu.cs.ym1695;

/**
 * The class that perform apple functionality
 * @author Sherry Ma & Steven Hu
 * @version 1.0
 *
 */
public class Apple extends Food{
	/**
	 * Set color to red, coordinates to random, and type to 1/(apple)
	 */
	public Apple() {
		super(255, 0, 0);
		this.setFoodType(1);
	}
	
	/**
	 *
	 * Update the snake if apple is eaten
	 */
	public void foodEaten(Snake snake) {
       super.foodEaten(snake);
        
     }
	

}


