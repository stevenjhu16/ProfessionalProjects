package edu.nyu.cs.ym1695;

/**
 * The class that perform snake functionality
 * @author Sherry Ma & Steven Hu
 * @version 1.0
 *
 */
public class Snake{
	/**
	 * The maximum length of the snake
	 * It cannot be changed
	 */
	final private int maxSnakeLength = 300;
	/**
	 * The array of x coordinates of the snake's body
	 */
	private int[] x = new int[maxSnakeLength];
	/**
	 * The array of y coordinates of the snake's body
	 */
	private int[] y = new int[maxSnakeLength];
	/**
	 * The snake's body length. The initial length is 2
	 */
	private int SnakeLength = 2;
	/**
	 * The x coordinates of the snake's head
	 */
	private int headX;
	/**
	 * The y coordinates of the snake's head
	 */
	private int headY;
	/**
	 * The size of the snake, per unit
	 */
	final private int width = 20;
	/**
	 * The direction the snake is headed
	 */
	private String snakeDirection;
	/**
	 * The snake's speed
	 */
	private int speed;
	/**
	 * The game score
	 */
	private int score = 0;
	/**
	 * The HP of the snake
	 */
	private int HP = 100;
	
	// Getters and Setters
	/**
	 * Get the game score
	 * @return this.score -- The score of the game
	 */
	public int getScore() {
		return this.score;
	}
	/**
	 * Set the game score
	 * @param score the current score
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/**
	 * Set the speed of the snake
	 * @param speed the current speed
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	/**
	 * Set the length of the snake
	 * @param speed the current length
	 */
	public void setLength(int length) {
		this.SnakeLength = length;
	}
	/**
	 * Get the speed of the snake
	 * @return this.speed -- current speed of the snake
	 */
	public int getSpeed() {
		return this.speed;
	}
	/**
	 * Get the x coordinate array of the snake
	 * @return this.speed -- current x coordinate array of the snake
	 */
	public int[] getX() {
		return this.x;
	}
	/**
	 * Get the y coordinate array of the snake
	 * @return this.speed -- current y coordinate array of the snake
	 */
	public int[] getY() {
		return this.y;
	}
	/**
	 * Get the snake's size per unit
	 * @return this.width snake's size per unit
	 */
	public int getWidth() {
		return this.width;
	}
	/**
	 * Get the snake's length
	 * @return this.SnakeLength -- snake's length
	 */
	public int getLength() {
		return this.SnakeLength;
	}
	/**
	 * Get the HP of the snake
	 * @return this.HP -- the current HP
	 */
	public int getHP() {
		return this.HP;
	}
	/**
	 * Set the HP of the snake
	 * @param HP the current HP
	 */
	public void setHP(int HP) {
		this.HP = HP;
	}
	/**
	 * Set the direction of snake specified by the user
	 * @param direction the current direction
	 * @return this.snakeDirection -- the new direction
	 */
	public String setDirection(String direction) {
		this.snakeDirection = direction;
		return this.snakeDirection;
	}
	
	// Construct a snake
	/**
	 * Set the initial speed, head position, first cell of the length array, and direction of the snake
	 */
	public Snake() {
		this.speed =5;
		this.headX = 200;
		this.headY = 200;
		this.x[0] = this.headX;
		this.y[0] = this.headY;
		this.snakeDirection = "up";
	}
	/**
	 * Move the snake one unit towards user-specified direction
	 */
	public void move() {
		switch(this.snakeDirection){
        case "down":
            this.headY += this.width;
            break;
        case "up":
            this.headY -= this.width;
            break;
        case "left":
            this.headX -= this.width;
            break;
        case "right":
            this.headX += this.width;
            break;
		}
	}

	/**
	 * Update the body of the snake
	 */
	public void snakeNewBody() {
        for(int i=this.SnakeLength-1; i>0; i--){
            this.x[i] = this.x[i-1];
            this.y[i] = this.y[i-1];
        }

        //store snake's new head
        this.y[0] = this.headY;
        this.x[0] = this.headX;
	}

	/**
	 * Draw the snake
	 * @param snake the snake
	 * @param game the game
	 */
	public static void drawSnake(Snake snake, Game game) {
		// this is the snake's head
        game.fill(255,0,0); // red head
        game.rect(snake.getX()[0],snake.getY()[0],snake.getWidth(),snake.getWidth());

        // this is the snake's body
        game.fill(255,255,255); // white body
        for(int i=1; i<snake.getLength(); i++){
            game.rect(snake.getX()[i],snake.getY()[i],snake.getWidth(),snake.getWidth());
        }
	}
}


