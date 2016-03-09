/*
 * File: Breakout.java
 * ------------------- 
 * This file implements the game of Breakout.
 * 
 * Programmer: Peter Lock
 * Date: 28-02-2016
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.MediaTools;
import acm.util.RandomGenerator;

import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/**Constant variables**/
	
	public static final int APPLICATION_WIDTH = 402;
	public static final int APPLICATION_HEIGHT = 600;
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;
	
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;
	private static final int PADDLE_Y_OFFSET = 30;

	private static final int NBRICKS_PER_ROW = 10;
	private static final int NBRICK_ROWS = 10;
	private static final int BRICK_SEP = 4;
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;
	private static final int BRICK_HEIGHT = 8;

	private static final int BALL_RADIUS = 10;
	private static final int BRICK_Y_OFFSET = 70;

	private static final int NTURNS = 3;

	public void run() {
		
		this.setSize(WIDTH, HEIGHT);
		
	
		for(int i = 0; i < NTURNS; i++){
			
			setUpGame();
			playGame();	
			if(brickCounter == 0){
				System.out.println(brickCounter);
				ball.setVisible(false);
				winnerMessage();
				break;
			}
			if(brickCounter > 0) {
				removeAll();
			}
		}
		if(brickCounter > 0){
			gameOverMessage();
		}
		
	}
	
	/** Method name: winnerMessage()
	 * ----------------------------
	 * This method prints the message 'You Win' when all of the bricks have been removed.
	 * Precondition: Bounce the ball of ever brick.
	 * Postcondition: Display the message 'You Win'.
	 */
	private void winnerMessage() {
		GLabel winner = new GLabel("You Win", getWidth()/2, getHeight()/2);
		winner.setFont("Arial-64");
		winner.move(-winner.getWidth()/2, -winner.getHeight()/2);
		winner.setColor(Color.RED);
		add(winner);		
	}

	/** Method name: gameOverMessage()
	 * ------------------------------
	 * This method prints the message 'Game Over' when the ball goes below the paddle, into the
	 * out-of-bounds area 3 times.
	 * Precondition: Ball must go below the paddle 3 times.
	 * Postcondition: Displays the message 'Game Over', then the program ends.
	 */
	private void gameOverMessage() {	
		GLabel gameOver = new GLabel("Game Over", getWidth()/2, getHeight()/2);
		gameOver.setFont("Arial-48");
		gameOver.move(-gameOver.getWidth()/2, -gameOver.getHeight()/2 + gameOver.getAscent()/2);
		gameOver.setColor(Color.RED);
		add(gameOver);
	}

	/** Method name: setUpGame()
	 * ------------------------
	 * This method adds the bricks to the screen, adds the paddle to the screen, adds the ball
	 * to the screen and initializes the variable 'brickCounter' to 100 - the number of bricks on 
	 * the screen.
	 * Precondition: Must be invoked.
	 * Postcondition: Displays the game on the screen.
	 */
	private void setUpGame(){
		addAllBricks();
		addPaddle();
		addBall();	
		brickCounter = 100;
	}
	/** Method name: playGame()
	 * -----------------------
	 * This method contains the play component of the program. The method waits for a mouse click
	 * event to be called, then it sets the velocity of the downward trajectory of the ball.  While
	 * bricks are on the screen, and the ball has not fallen off the bottom of the screen, the game
	 * continues.
	 * Precondition: Must be invoked.
	 * Postcondition: The game has been played and a conclusion has been reached.
	 */
	private void playGame() {
		waitForClick();
		intializeVelocity();
		while(true){
			moveBall();
			if(ball.getY() >= getHeight()) break;
			
			if(brickCounter == 0) break;
		}		
	}
	/** Method name: moveBall()
	 * -----------------------
	 * This method is responsible for moving the ball on the screen.  The method starts by declaring
	 * a variable to hold the sound file for the 'bounce' noise. It then moves the ball, passing as
	 * parameters the vx (velocity x) and vy (velocity y) parameters to the internal move() method.
	 * The method contains a number of test conditions. The first statement says that if the balls 
	 * position is less then or equal to the left wall, or the ball is more than or equal to the right
	 *  wall, then the balls vx (horizontal velocity) is reversed (the ball goes in the other direction). 
	 * The second if statement says that if the ball is less then or equal to the top wall, reverse its 
	 * vy (vertical velocity). After the 'collider' object is declared which will store any object
	 * that the ball bumps (collides) into. The next two if statements are simple, if the ball collides
	 * into the paddle reverse its vy (vertical velocity). If the ball bumps into something, and an object
	 * is returned to 'collider' (GObject container field), and that object is not the 'paddle' then
	 * it must be a brick. In this case the 'brick' (GRect object) is removed from the screen and the
	 * brickCounter variable decreases by one. Every time the ball bounces of either the paddle or a
	 * brick the 'bounce' sound is played.
	 * Precondition: Must be invoked.
	 * Postcondition: The ball is moved by vx, and vy respectively, and related processing is completed.
	 */
	private void moveBall() {
		
		AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
		ball.move(vx, vy);
		
		if ((ball.getX() <= 0) || (ball.getX() >= (getWidth() - BALL_RADIUS*2))) {
			bounceClip.play();
			vx = -vx;
		}
		if ((ball.getY() - vy <= 0 && vy < 0 )) {
			bounceClip.play();
			vy = -vy;
		}
		
		GObject collider = getCollidingObject();
		
		if (collider == paddle) {
			if(ball.getY() >= getHeight() - (PADDLE_Y_OFFSET + PADDLE_HEIGHT + BALL_RADIUS*2) && ball.getY()
					< getHeight() - (PADDLE_Y_OFFSET + PADDLE_HEIGHT + BALL_RADIUS*2) + 4) {
				vy = -vy;	
				bounceClip.play();
			}
			
		} else if (collider != null) {
			remove(collider); 
			brickCounter--;
			vy = -vy;
			bounceClip.play();
			increaseVelocity();
		}
		pause (DELAY);
	}
	/**Method name: increaseVelicity()
	 * -------------------------------
	 * This method increases the speed of the ball when the remaining ball count is either 90 or 45.
	 * Precondition: brickCounter must be either 90  or 45 respectively.
	 * Postcondition: The velocity of the ball is increased by 2 each time the condition in the if 
	 * statement is met.
	 */
	private void increaseVelocity() {
		if(brickCounter % 45 == 0){	
			vx = vx * 2;
		}
	}
	/**Method name: initializeVelocity()
	 * ---------------------------------
	 * Initializes the velocity of the balls vx speed.
	 * Precondition: Must be invoked.
	 * Postcondition: The balls vx value (velocity) is initialized.
	 */
	private void intializeVelocity() {
		vx = rgen.nextDouble(1.0, 3.0);
		// 0.5 in the parentheses will set the probability to 50%.
		if(rgen.nextBoolean(0.5)) vx = -vx;
	}


	/** Method name: private GObject getCollidingObject()
	 * -------------------------------------------------
	 * This method checks to see whether a collision has occurred at
	 * any of the four corners of the 'circle'. If something has been
	 * found at any of these four points then a collision has occurred. 
	 */
	private GObject getCollidingObject() {

		if((getElementAt(ball.getX(), ball.getY())) != null) return getElementAt(ball.getX(), ball.getY());
	      
		if (getElementAt( (ball.getX() + BALL_RADIUS*2), ball.getY()) != null ) return getElementAt(ball.getX() + BALL_RADIUS*2, ball.getY());
	      
		if(getElementAt(ball.getX(), (ball.getY() + BALL_RADIUS*2)) != null ) return getElementAt(ball.getX(), ball.getY() + BALL_RADIUS*2);
	      
		if(getElementAt((ball.getX() + BALL_RADIUS*2), (ball.getY() + BALL_RADIUS*2)) != null ) return getElementAt(ball.getX() + BALL_RADIUS*2, ball.getY() + BALL_RADIUS*2);
		
		/* Returns null if no object is present*/
	    return null;
	      
	}
	/**Method name: addALLBricks()
	 * ------------------------
	 * This method adds the bricks to the canvas.
	 * Precondition: Must be invoked.
	 * Postcondition: Ten by ten rows of colored bricks are added to the canvas, with an offset position
	 * from the top of the screen.
	 */
	private void addAllBricks() {
		
		for(int row = 0; row < NBRICKS_PER_ROW; row++){
			
			for(int brickNumber = 0; brickNumber<NBRICKS_PER_ROW; brickNumber++){
				
				int x = (brickNumber * BRICK_WIDTH) + (brickNumber*BRICK_SEP);		
				int y = BRICK_Y_OFFSET + (row * (BRICK_HEIGHT + BRICK_SEP));
				
				addBrick(x + BRICK_SEP/2,y, row);	
			}
		}
	}	
	/**Method name: addBall()
	 * ----------------------
	 * Adds the ball to the canvas.
	 * Precondition: Must be invoked.
	 * Postcondition: Ball is added to the canvas.
	 */
	private void addBall() {
		int x = getWidth()/2 - BALL_RADIUS;
		int y = BRICK_Y_OFFSET + (NBRICK_ROWS * BRICK_HEIGHT) + BRICK_SEP * NBRICK_ROWS-1;
		ball = new GOval(x, y, BALL_RADIUS*2, BALL_RADIUS*2);
		ball.setFilled(true);
		add(ball);
	}
	/**addPaddle()
	 * -----------
	 * Adds the paddle to the screen.
	 * Precondition: Must be invoked.
	 * Postcondition: The paddle is added to the screen.
	 */
	private void addPaddle() {
		
		int x = getWidth()/2 - PADDLE_WIDTH/2;
		int y = getHeight() - (PADDLE_Y_OFFSET + PADDLE_HEIGHT);
		
		paddle = new GRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);	
		paddle.setColor(Color.BLACK);
		add(paddle);
	
		
		addMouseListeners();
	}	
	
	/** Method name: mouseMoved(MouseEvent e)
	 * -------------------------------------
	 * This method allows the paddle to track the mouse when it is moved.
	 */
	public void mouseMoved(MouseEvent e) {
		
		/* The mouse tracks the middle point of the paddle. 
		 * The if statement becomes true if the paddles center position is within
		 * the boundaries of the screen.
		 */
		
		if ((e.getX() < getWidth() - PADDLE_WIDTH/2) && (e.getX() > PADDLE_WIDTH/2)) {
			paddle.setLocation(e.getX() - PADDLE_WIDTH/2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}		
	}
	/**Method name: addBrick(int x, int y, int row)
	 * --------------------------------------------
	 * This method adds the individual bricks to the wall. After each call of this method a single brick
	 * (1 brick) is added to the canvas. The brick color is determined by calling the method getColor
	 * which returns the color of the brick based upon the bricks row number. The row number of the brick
	 * is passed from this method to the method getColor.
	 * @param x The x position of the brick.
	 * @param y The y position of the brick.
	 * @param row The row number the brick will be placed in.（Used to get the appropriate brick color for 
	 * each brick.
	 * Precondition: Receives the x and y positions and row number of the brick as parameters.
	 * Postcondition: A brick with the appropriate color is added to the screen in the x and y positions given.
	 */
	private void addBrick(int x, int y, int row) {
		GRect brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
		brick.setFilled(true);
		brick.setColor(getColor(row));
		brick.setFillColor(getColor(row));
		add(brick);
	}
	/**Method name: getColor(int row)
	 * ------------------------------
	 * This method returns the color of the brick to the calling method based upon the row number it 
	 * receives as a parameter.
	 * @param row The row number the brick will be placed in.
	 * @return The color to paint the brick.
	 */
	private Color getColor(int row) {
		switch(row){
		case 0: case 1: return Color.RED;
		case 2: case 3: return Color.ORANGE;
		case 4: case 5: return Color.YELLOW;
		case 6: case 7: return Color.GREEN;
		case 8: case 9: return Color.CYAN;
		default: return null;
		}		
	}
	
	/*Instance variables*/
	private GRect paddle;
	private GOval ball;
	private double vx = 1.0;
	private double vy = 3.0;
	private int brickCounter = 100;
	private static final int DELAY = 10;	
	private RandomGenerator rgen = RandomGenerator.getInstance();
}
