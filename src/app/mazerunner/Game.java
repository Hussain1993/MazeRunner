package app.mazerunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

import android.util.Log;

/**
 * This class is the actual game. 
 * As such, most game logic goes here.
 * Maze Runner
 * @author Sunny/Hussain/Hani
 */
public class Game extends SimpleBaseGameActivity  {

	// The grand-daddy object. Everything graphical takes place on the scene.
	final Scene scene = new Scene();
	/*
	 * Set the size of the game that will run on the device 
	 */
	private static int CAMERA_WIDTH = 1400;
	private static int CAMERA_HEIGHT = 800;
	
	private static final float INITIAL_SCROLL = 20.0f;
	
	// Scrolling speed 
	private static float horizontal_scroll = INITIAL_SCROLL;
	
	/*
	 *  After every (SPEED_INCREASE_RATE) seconds, the speed of the ball
	 *  will increase by a certain amount.
	 *  See the 'speedTimer' object in the onCreateScene() class for more.
	*/
	private static final float SPEED_INCREASE_RATE = 5.0f;
	
	// Sets the view of the map the game is played in
	final SmoothCamera camera = new SmoothCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, horizontal_scroll, 0, 0);
	
	/* 
	 * Boundaries corresponding to the edges of the screen that the ball
	 * cannot pass. Implemented just in case.
	 */
	private static final int UPPER_BOUNDARY = 10;
	private static final int LOWER_BOUNDARY = CAMERA_HEIGHT - 130;
	private static final int LEFT_BOUNDARY = 2;
	
	/*
	 * These are the sprites and Textures for the
	 * background and also the ball that is placed on the maze
	 */
	private ITextureRegion mBackgroundTexttureRegion;
	private ITextureRegion mWallTextureRegion;
	private ITextureRegion mBallTextureRegion;
	
	private Sprite sBall;
	private Sprite[][] sWall;
	private ArrayList<Sprite> currentWalls;
	

	
	/**
 	* Init method
 	*/
	@Override
	public EngineOptions onCreateEngineOptions() {
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	}

	/**
	 * Method involved in graphics creation
	 */
	@Override
	protected void onCreateResources() {
		try {
			ITexture background = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				
				@Override
				public InputStream open() throws IOException {
					return getAssets().open("gfx/background.png");//Get the background image from the gfx to be loaded
				}
			});
			ITexture ball = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				
				@Override
				public InputStream open() throws IOException {
					return getAssets().open("gfx/ball.png");//Get the ball  image from the gfx folder be loaded
				}
			});
			
			ITexture wall = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				@Override
				public InputStream open() throws IOException {
					return getAssets().open("gfx/tile_default.png");//Get the square image from the gfx folder be loaded
				}
			});
			
			/*
			 * Load these the textures that you just got into VRAM, whatever the fuck that is
			 * (Virtual RAM? I think data disappears upon 'finish'ing of activity - Sunny)
			 */
			wall.load();
			background.load();
			ball.load();
			this.mBackgroundTexttureRegion = TextureRegionFactory.extractFromTexture(background);
			this.mBallTextureRegion = TextureRegionFactory.extractFromTexture(ball);
			this.mWallTextureRegion = TextureRegionFactory.extractFromTexture(wall);
		} // END OF try block
		catch (IOException e) {
			Debug.e(e);
		}
	} // END OF onCreateResources()

	/**
	 * Displays images on screen.
	 * Also contains game logic, but I have a feeling this is supposed to go somewhere else.
	 */
	
	//Variables to hold the previous non-collided coordinates
	float previousTouchx=0;
	float previousTouchy=0;
	int count = 10;
	
	@Override
	protected Scene onCreateScene() {
		Sprite backgroundSprite = new Sprite(CAMERA_WIDTH/2, 100, mBackgroundTexttureRegion, getVertexBufferObjectManager());//Set the background of the screen.
		scene.attachChild(backgroundSprite);//Add the background to the scene

		createWalls();
		
		/**
		 * All sprite creation
		 */
		sBall = new Sprite(5, 400, mBallTextureRegion, getVertexBufferObjectManager()){//Make the ball a sprite also
			/**
			 * Handles what occurs when the ball is touched.
			 * I'm not really sure how it works yet...
			 * ~Sunny
			 */
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY){
				// Make sure the ball hasn't collided with a wall
				if (!thereIsCollision(this)){
					//Every 10 consecutive non-colliding touch events, store the coordinates of the ball
					if (count == 10){
						previousTouchx = pSceneTouchEvent.getX() - this.getWidth() / 2;
						previousTouchy = pSceneTouchEvent.getY() - this.getHeight() / 2;
						count = 0;
					}
					
					// Moves the ball. Again, not 100% sure how this works.
					this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
					count++;

				}
				else {
					//If the ball collides, move to previous "safe" location.
					count = 0;
					this.setPosition(previousTouchx, previousTouchy);
					/**
					// Recreates grid, but isn't this a bit processor-intensive?
					grid = new int[height][width]; 
					// Exits screen
					finish(); 
					**/
				}
				
				/*
				 * To make sure that the ball does not cross the edges of the screen
				 * (Note: No constraints on right edge as the ball is supposed to move 
				 * to the right)
				 */
				if (this.getX() < LEFT_BOUNDARY){
					this.setPosition(LEFT_BOUNDARY, this.getY());
				}
				if (this.getY() < UPPER_BOUNDARY){
					this.setPosition(this.getX(), UPPER_BOUNDARY);
				}
				if (this.getY() > LOWER_BOUNDARY){
					this.setPosition(this.getX(), LOWER_BOUNDARY);
				}
				return true;
			} // END OF onAreaTouched()
		}; // END OF sBall definition
		
		//Add the ball to the screen
		scene.attachChild(sBall); 
		// Allows ball to be moved when touched
		scene.registerTouchArea(sBall); 
		// Seems to improve touch response
		scene.setTouchAreaBindingOnActionDownEnabled(true); 
		
		// SCROLLING!!!!
	    TimerHandler scrollTimer = new TimerHandler(0.003f, true, new ITimerCallback() {
            
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                    camera.setCenter(camera.getCenterX()+horizontal_scroll,camera.getCenterY());
                    // If the ball goes off-screen...
                    if (camera.getCenterX()-sBall.getX()-sBall.getWidth() > CAMERA_WIDTH/2){
                    		lostTheGame();
                    	}
                   
            }
	    });
	    scene.registerUpdateHandler(scrollTimer);

	    // Controls the speed increase of the ball
	    TimerHandler speedTimer = new TimerHandler(SPEED_INCREASE_RATE, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
            	/* This is currently set to double the speed after every 5 seconds
                 * But we should probably change it to something a bit less
                 * quick
                 */
                   horizontal_scroll = horizontal_scroll * 2;
                   camera.setMaxVelocityX(horizontal_scroll);
            }
	    });
	    scene.registerUpdateHandler(speedTimer);
	    
	    return scene;
	} // END OF onCreateScene()


	// height of grid
	public static int height = 7;
	// width of grid
	public static int width = 20;
	
	// 2-D grid; 1 values indicate path, 0 indicates wall
	static int[][] grid = new int[height][width];
	
	// Random int generator for creating new path
	static Random random = new Random();
	
	/**
	 * Creates the grid array for the maze that the ball travels through.
	 * Values of '1' within the array correspond to areas the ball can traverse.
	 * Values of '0' correspond to walls.
	 */
	void createMazeArray(){
		
		// Starts in middle of height of grid
		grid[height/2][0] = 1;
		grid[height/2][1] = 1;
		
		// Goes through every column
		for (int j = 1; j < width-1; j++){
			int i = 1;
			while (grid[i][j+1] != 1) { // Keeps trying to change numbers until there is at least one '1` to the right
                if (grid[i][j] == 1){
                    int bla = random.nextInt(3);
                    // there are three possible cases:
                    // (0) the number to the right becomes 1
                    // (1) the number above becomes 1
                    // (2) the number below becomes 1
                    // If case(0) is reached, the while loop terminates
                    switch (bla){
						case 0:
							grid[i][j+1] = 1; 
							break;
						case 1:
							// Additional check to prevent the ball's path from being too wide
                            if(grid[i-1][j-1] != 1){
                                grid[i-1][j] = 1;
                            }
							break;
						case 2:
							// Additional check to prevent the ball's path from being too wide
                            if(grid[i+1][j-1] != 1){
                                grid[i+1][j] = 1;
                            }
                    }
                }
                i++;
                if (i == height-2) 	i = 1; // if bottom of grid is reached, return to top
			}; // END OF while loop
		} // END OF for loop
	} // END OF createMazeArray()

	void createWalls(){
		/* Creates the grid array using the Advanced Maze Generation Algorithm (TM) above 
		 * perfected by the Alty Boys duo (Hani and Sunny). 
		*/
		this.createMazeArray(); 
		int xInterval = 128; // Each wall piece has a width of 128 pixels
		int yInterval = 128; // ..and a height of 128 pixels. Hence these intervals.
		int x = 0; // Starts off at the 
		int y = 0; // Top-left corner of the screen
		
		// Readies array that holds wall sprites
		sWall = new Sprite[50][50]; 
		
		/* This ArrayList is used because when referencing walls for collision,
		 * we'll want to be careful about 'null' values in the array above.
		*/
		currentWalls = new ArrayList<Sprite>();
		
		/**
		 * For loop that generates walls.
		 * The numbers '7' and '10' will need to be altered to lengthen the maze, I think
		 */
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				if(grid[i][j]!=1){
				sWall[i][j] = new Sprite(x, y, mWallTextureRegion, getVertexBufferObjectManager());
				currentWalls.add(sWall[i][j]);
				scene.attachChild(sWall[i][j]);}
				x = x + xInterval;
			}
			x = 0;
			y = y + yInterval;
		}
	} // END OF createWalls();
	
	/**
	 * Largely self-explanatory
	 * @param ball The ball
	 * @return true if the ball collides with the wall, false if it doesn't
	 */
	boolean thereIsCollision(Entity ball){
		for (Sprite wall: currentWalls){
			if (sBall.collidesWith(wall)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Controls what happens when one of the losing conditions occurs.
	 */
	void lostTheGame(){
		horizontal_scroll = INITIAL_SCROLL;
    	grid = new int[height][width]; 
    	finish();
	}
} // END OF GAME class definition