package edu.nyu.cs.ym1695;
import java.util.Random;


/**
 * The class that perform food functionality
 * @author Sherry Ma & Steven Hu
 * @version 1.0
 *
 */
public class Food {

		/**
		 * The x coordinate of the food
		 */
		private int foodX;
		/**
		 * The y coordinate of the food
		 */
		private int foodY;
		/**
		 * The R value of the RGB color of the food
		 */
		private int r;
		/**
		 * The G value of the RGB color of the food
		 */
		private int g;
		/**
		 * The B value of the RGB color of the food
		 */
		private int b;
		/**
		 * Record the position of the previous apple and mushroom
		 */
		private static int[][] historyXY = new int[2][2];
		/**
		 * Indicator of type, used to determine which cell to change in historyXY
		 */
		private int foodType = 0; //0 for mushroom, 1 for apple
		/**
		 * Randomly set the color and xy coordinate of the food
		 */
		public Food() {
			
			Random randy = new Random();
	    	this.setFood();
	        this.r = randy.nextInt(235);
	        this.g = randy.nextInt(235);
	        this.b = randy.nextInt(235);
		}
		/**
		 * Set the food to a specific color
		 * Overloaded constructor
		 * @param r the R value of the RGB color
		 * @param g the G value of the RGB color
		 * @param b the B value of the RGB color
		 */
		public Food(int r, int g, int b) {
	    	this.setFood();
	        this.r = r;
	        this.g= g;
	        this.b = b;
		}
		
		// setters and getters
		/**
		 * Get the r value of the RGB color of the food
		 * @return this.r --  the r value of the RGB color
		 */
		public int getR() {
			return this.r;
		}
		/**
		 * Set the r value of the RGB color of the food
		 * @return this.r --  the r value of the RGB color
		 */
		public void setR(int r) {
			this.r = r;
		}
		/**
		 * Get the g value of the RGB color of the food
		 * @return this.g --  the g value of the RGB color
		 */
		public int getG() {
			return this.g;
		}
		/**
		 * Set the g value of the RGB color of the food
		 * @return this.g --  the g value of the RGB color
		 */
		public void setG(int g) {
			this.g = g;
		}
		/**
		 * Get the b value of the RGB color of the food
		 * @return this.b --  the b value of the RGB color
		 */
		public int getB() {
			return this.b;
		}
		/**
		 * Set the b value of the RGB color of the food
		 * @return this.b --  the b value of the RGB color
		 */
		public void setB(int b) {
			this.b = b;
		}
		/**
		 * Randomly set the xy coordinate of the food and make sure they are not set to the same 
		 */
		public void setFood(){
			Random randy = new Random();
			int randomX = (randy.nextInt(39)+5)*20; //20 units per food, divide 1000 by 20 and then include margin
			int randomY = (randy.nextInt(19)+4)*20; //20 units per food, divide 600 by 20 and then include margin
			//set the initial values of the coordinate history if it is not previously set
			if(Food.historyXY[0]==null || Food.historyXY[1]==null) {
				int[] xyHistory = {randomX,randomY};
				Food.historyXY[this.foodType] = xyHistory;
			}
			boolean flagX = true; //determiner of whether the old x position is the same as the new position
			boolean flagY = true; //determiner of whether the old y position is the same as the new position
			while (flagX) {
				// check to see if the food generates at the same position
				if(Food.historyXY[0][0] != randomX && Food.historyXY[1][0] != randomX) {
					this.foodX = randomX;
					Food.historyXY[this.foodType][0] = randomX;
					flagX = false;
				}else {
					randomX = (randy.nextInt(39)+5)*20;
				}
				
			}//while
			while (flagY) {
				if(Food.historyXY[0][1] != randomY && Food.historyXY[1][1] != randomY) {
					this.foodY = randomY;
					Food.historyXY[this.foodType][1] = randomY;
					flagY = false;
				}
				else {
					randomY = (randy.nextInt(19)+4)*20;
				}
			}
		}
		
		/**
		 * Get the x coordinate of the food
		 * @return this.foodX -- the current x coordinate of the food
		 */
		public int getFoodX() {
			return this.foodX;
		}
		/**
		 * Get the y coordinate of the food
		 * @return this.foodY -- the current y coordinate of the food
		 */
		public int getFoodY() {
			return this.foodY;

		}
		/**
		 * Set the type of the food
		 * @param this.foodType -- the current food type
		 */
		public void setFoodType(int foodType) {
			this.foodType = foodType;
		}
		/**
		 * Get the type of the food
		 * @return this.foodType -- the current food type
		 */
		public int getFoodType() {
			return this.foodType;
		}
		
		/**
		 * Food can be eaten
		 * Most food is good
		 */
		public void foodEaten(Snake snake) {
			 if( snake.getX()[0] == this.getFoodX() && snake.getY()[0] == this.getFoodY()){
		        	
		        	// increase the length
		            snake.setLength(snake.getLength()+1);
		            //set the speed
		            snake.setSpeed(snake.getSpeed()+1);
		            
		            // draw another apple
			    	this.setFood();
			    	
			    	// get a score
			    	snake.setScore(snake.getScore()+1);
			    	
			    	// maximum speed is 20
		            if (snake.getSpeed() >=20) {
		            	snake.setSpeed(20); 
		            }
		}
		}
		
		/**
		 * Print the food
		 * @param food the food
		 * @param snake the snake
		 * @param game the game
		 */
		public static void drawFood(Food food, Snake snake, Game game) {
			food.foodEaten(snake);
			game.fill(food.getR(),food.getG(),food.getB());
			game.rect(food.getFoodX(), food.getFoodY(), snake.getWidth(), snake.getWidth());
		}
}


