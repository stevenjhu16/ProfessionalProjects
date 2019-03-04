package edu.nyu.cs.ym1695;
import processing.core.*;

/**
 * The class that perform user interaction
 * @author Sherry Ma & Steven Hu
 * @version 1.0
 *
 */
public class Game extends PApplet{
	/**
	 * Window width
	 */
	final private int w = 1000;
	/**
	 * Window height
	 */
	final private int h = 600;
	/**
	 * Background image
	 */
	private PImage galaxy;
	/**
	 * Photo of Steven
	 */
	private PImage Steven;
	/**
	 * Photo of Sherry
	 */
	private PImage Sherry;
	/**
	 * Determiner of whether the game has started
	 */
	private boolean gameStart = false;
	/**
	 * Determiner of whether the game has ended
	 */
	private boolean gameEnd = false;
	/**
	 * Determiner of whether the mode buttons are shown
	 */
	private boolean buttonShown = false;
	/**
	 * Determiner of whether the mode buttons are chosen
	 */
	private boolean buttonChosen = false;
	/**
	 * Instantiate the snake
	 */
	Snake snake = new Snake();
	/**
	 * Instantiate the snake
	 */
	Apple apple = new Apple();
	/**
	 * Instantiate the Mushroom
	 */
	Mushroom mushroom = new Mushroom();
	/**
	 * The function that executes first
	 * @param args
	 */
	public static void main(String[] args) {
		PApplet.main("edu.nyu.cs.ym1695.Game"); //execute the game
		
	}
	
	/**
	 * Setting the window size value and image values
	 */
	public void settings() {
		this.size(this.w, this.h);
		galaxy = this.loadImage("src/galaxy.png");
		Steven = this.loadImage("src/Steven.png");
		Sherry = this.loadImage("src/Sherry.jpg");
	}
	
	/**
	 * Set up the background, welcome words, and instructions on how to continue
	 */
	public void setup() {
		this.background(255,255,255);
		this.image(this.galaxy, 0, 0);
		this.textSize(40);
		this.fill(255,255,255);
		this.text("Welcome to Gluttonous Snake", 200, 100);
		this.text("Click Steven for Help", 270, 220);
		this.text("Click Sherry to Start", 275, 270);
		this.image(this.Steven,200,300,200,200);
		this.image(this.Sherry,600,300,200,200);
	}
	
	/**
	 * Handles user activities and and refresh the screen accordingly
	 */
	public void draw() {
		if (mousePressed && mouseX >=200 && mouseX <=400 && mouseY >=300 && mouseY <=500 && this.buttonShown == false) {
			//if pressed Steven, go to "Help"
			this.showHelp(); 
		}
		else if ((mousePressed && mouseX >=600 && mouseX <=800 && mouseY >=300 && mouseY <=500) || (keyPressed && key =='b')&& this.buttonShown == false) {
			//if pressed Sherry, start the game
			this.image(this.galaxy, 0, 0,1000,600);
			String s = "Please choose a mode:";
			this.fill(255,255,255);
			this.textSize(40);
			this.text(s, 270, 200);
			this.showButton(200,"Easy");
			this.showButton(400,"Medium");
			this.showButton(600,"Hard");
			
		}
		if (mousePressed && this.buttonChosen) {
			this.gameStart = true;
		}
		if (gameStart) {
			
			// refresh the screen
			this.image(this.galaxy, 0, 0,1000,600);
			
			// controls the speed of the snake
			this.frameRate(snake.getSpeed());
			
			// move the snake
			snake.move();
			
			// draw the foods
			Food.drawFood(apple,snake,this);
			Food.drawFood(mushroom,snake,this);
			
			// store the body 
			snake.snakeNewBody();
			
			// draw the snake
			this.noStroke();
			Snake.drawSnake(snake,this);
			
			// show the HP and score
			this.showText();
			
			if(this.die()){ // if snake dies
			   this.showEnd(); // end the game
			   }
		}
		

			}
	
	/**
	 * Execute when key is pressed.
	 * Set the direction of the snake according to which one of the following keys is pressed:
	 * w,a,s,d
	 */
	public void keyPressed() {
		// check the key code
		        switch(key){
		            case 'A':
		            case 'a':
		                snake.setDirection("left");
		                break;
		            case 'D':
		            case 'd':
		            	snake.setDirection("right");
		                break;
		            case 'S':
		            case 's':
		            	snake.setDirection("down");
		                break;
		            case 'W':
		            case 'w':
		            	snake.setDirection("up");
		                break;
		        }

		} 
	/**
	 * Execute when mouse is clicked.
	 * Set buttonChosen to be true when the user clicked the "Easy", "Medium", or "Hard" button
	 */
	public void mouseClicked() {
		if (this.buttonShown){
			if(this.mouseX >=200 && this.mouseX <=370 && this.mouseY >=300 && this.mouseY <=350) {
				snake.setSpeed(3);
				this.buttonChosen = true;
			}
			else if (this.mouseX >=400 && this.mouseX <=570 && this.mouseY >=300 && this.mouseY <=350) {
				snake.setSpeed(5);
				this.buttonChosen = true;
			}
			else if (this.mouseX >=600 && this.mouseX <=770 && this.mouseY >=300 && this.mouseY <=350) {
				snake.setSpeed(7);
				this.buttonChosen = true;
			} 

		}
		
	}
	
	/**
	 * Print helpful information on how to play the game
	 */
	public void showHelp() {
		this.image(this.galaxy, 0, 0,1000,600);
		String text = "1. Press 'w' to move upwards;\n"
				+ "2. Press 's' to move downwards;\n"
				+ "3. Press 'a' to move leftwards;\n"
				+ "4. Press 'd' to move rightwards;\n"
				+ "5. Apples are red;\n"
				+ "6. Be carefrul about the colorful but toxic mushrooms...\n"
				+ "   Some dramatically increase your length and others increase your speed\n"
				+ "   And some look really just like apples!\n"
				+ "7. Do not eat yourself or crush onto the wall;\n"
				+ "8. Good luck. Press 'b' to start.";
		this.fill(255,255,255);
		this.textSize(20);
		this.text(text, 200, 100);
	}
	/**
	 * Print buttons to the screen
	 * @param x how far right should the button be
	 * @param mode the text indicating which mode
	 */
	public void showButton(int x,String mode) {
		this.fill(220,220,220);
		this.rect(x,300,170, 50);
		this.textSize(30);
		this.fill(0,0,0);
		this.text(mode,x+20,330);
		this.buttonShown = true;
		
	}
	/**
	 * Determine if the snake has died and the game should end
	 * @return this.gameEnd the determiner of whether the game should end
	 */
	public boolean die(){
	    // hit the wall
	    if(snake.getX()[0]<0 || snake.getX()[0]>= this.w || snake.getY()[0]<0 || snake.getY()[0]>= this.h){
	    	this.gameEnd = true;
	    }

	    //eat itself
	    if(snake.getLength()>2){
	        for(int i=1; i<snake.getLength(); i++){
	            if(snake.getX()[0]==snake.getX()[i] && snake.getY()[0] == snake.getY()[i]){
	                this.gameEnd = true;
	            }
	        }
	    }
	    // poison to death
	    if (snake.getHP() <=0) {
	    	this.gameEnd = true;
	    }
	    return this.gameEnd;
	}


	/**
	 * Print the snake's HP and the user's current score to the screen
	 */
	public void showText() {
		this.fill(255,255,255);
		this.textSize(25);
		String s = "HP:"+ snake.getHP();
		String s2 = "Score:" +snake.getScore();
		this.text(s, 40, 40);
		this.text(s2, 40, 80);
	}
	
	/**
	 * Print the end-of-the-game message and the score
	 */
	public void showEnd() {
		this.background(0,0,0);
		this.fill(255,255,255);
		this.textSize(50);
		String end = "Sorry, you lose.";
		this.text(end, 300, 250);
		String s = "Final Score:"+ snake.getScore();
		this.text(s, 320, 350);
	}
}


