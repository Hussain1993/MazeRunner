package app.mazerunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import android.graphics.Typeface;
import app.mazerunner.gameclasses.Coin;
import app.mazerunner.gameclasses.PowerItem;

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
	private static float old_scroll = horizontal_scroll;
	
	/*
	 *  After every (SPEED_INCREASE_RATE) seconds, the speed of the ball
	 *  will increase by a certain amount.
	 *  See the 'speedTimer' object in the onCreateScene() method for more.
	*/
	private static final float SPEED_INCREASE_RATE = 5.0f;
	
	// Necessary for speedup/down power items
	private boolean disableSpeedup = false;
	
	private TimerHandler speedFixer;
	
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
	private ITextureRegion bCoinTextureRegion;
	private ITextureRegion sCoinTextureRegion;
	private ITextureRegion gCoinTextureRegion;
	private ITextureRegion[] mPowerItemTextureRegion;
	
	private ITextureRegion mHUDBarTextureRegion;
	
	private Font fontScore;
	private static final int FONT_SIZE = 48;
	
	private Sprite sBall;
	private Sprite[][] sWall;
	private ArrayList<Sprite> currentWalls;
	
	private ArrayList<PowerItem> currentPowerItems;
	
	private ArrayList<Coin> currentCoins;
	
	/*
	 *  Types of power items!
	 *  Change variable identifiers once we've decided on power items.
	 *  Until then, I'm reserving numbers 0-4 for power-ups and
	 *  numbers 5-9 for power-downs.
	*/
	public static final int SPEED_DOWN = 0;
	public static final int TYPE_1 = 1;
	public static final int TYPE_2 = 2;
	public static final int TYPE_3 = 3;
	public static final int TYPE_4 = 4;
	public static final int SPEED_UP = 5;
	public static final int TYPE_6 = 6;
	public static final int TYPE_7 = 7;
	public static final int TYPE_8 = 8;
	public static final int TYPE_9 = 9;
	
	
	// Types of coins
	public static final int BRONZE = 20;
	public static final int SILVER = 21;
	public static final int GOLD = 22;
	
	// Used for update score
	private TimerHandler scoreTimer;
	private int iterations = 0;
	
	int counterthingy = 0;
	ITextureRegion[] powerUpTextureRegion;
	
	private Text textScore;
	private int score = 0;
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
			
			// The power ups
			mPowerItemTextureRegion = new ITextureRegion[10];
			for (int i = 0; i < 10; i = i + 5){
				BitmapTextureAtlas bta = new BitmapTextureAtlas(
						this.getTextureManager(), 32, 32);
				mPowerItemTextureRegion[i] = 
						BitmapTextureAtlasTextureRegionFactory.createFromAsset(
								bta, this, "gfx/powerItem_" + i + ".png", 0, 0);

				bta.load();
			}
			
			// Bronze coin
			ITexture bCoin = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				
				@Override
				public InputStream open() throws IOException {
					return getAssets().open("gfx/coin_0.png");
				}
			});
			
			// Silver coin
			ITexture sCoin = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				
				@Override
				public InputStream open() throws IOException {
					return getAssets().open("gfx/coin_1.png");
				}
			});
			
			// Gold coin
			ITexture gCoin = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
							
			@Override
				public InputStream open() throws IOException {
						return getAssets().open("gfx/coin_2.png");
					}
			});
			
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
			ITexture pUp = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				
				@Override
				public InputStream open() throws IOException {
					return getAssets().open("gfx/power_up.png");
				}
			});
			ITexture pDown = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				
				@Override
				public InputStream open() throws IOException {
					return getAssets().open("gfx/power_down.png");
				}
			});
			ITexture hudBar = new BitmapTexture(this.getTextureManager(), 
					new IInputStreamOpener() {
						@Override
						public InputStream open() throws IOException {
							return getAssets().open("gfx/hud_bar.png");
						}
				}
			);
			// Create font for HUD
			final ITexture fontTexture = new BitmapTextureAtlas(
					this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
			fontScore  = new Font(this.getFontManager(),
					fontTexture, 
					Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 
					FONT_SIZE, true, Color.WHITE);
			
			/*
			 * Load these the textures that you just got into VRAM, whatever that is
			 * (Virtual RAM? I think data disappears upon 'finish'ing of activity - Sunny)
			 */
			fontScore.load();
			hudBar.load();
			wall.load();
			pUp.load();
			pDown.load();
			background.load();
			ball.load();
			bCoin.load();
			sCoin.load();
			gCoin.load();
			
			this.mBackgroundTexttureRegion = TextureRegionFactory.extractFromTexture(background);
			this.mBallTextureRegion = TextureRegionFactory.extractFromTexture(ball);
			this.mWallTextureRegion = TextureRegionFactory.extractFromTexture(wall);
			this.bCoinTextureRegion = TextureRegionFactory.extractFromTexture(bCoin);
			this.sCoinTextureRegion = TextureRegionFactory.extractFromTexture(sCoin);
			this.gCoinTextureRegion = TextureRegionFactory.extractFromTexture(gCoin);		
			this.mHUDBarTextureRegion = TextureRegionFactory.extractFromTexture(hudBar);
		
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
	
	//Animation variables for the bounceback animation
	boolean animating = false;
	float currentX = 0.0f, currentY = 0.0f, endX = 0.0f, endY = 0.0f;
	boolean subtractX = false, subtractY = false;
	int totalTime = 0; float normalisedXInterval = 0.0f, normalisedYInterval = 0.0f;
		
	/**
	 * GAME LOGIC GOES HERE
	 */
	@Override
	protected Scene onCreateScene() {
		Sprite backgroundSprite = new Sprite(CAMERA_WIDTH/2, 100, mBackgroundTexttureRegion, getVertexBufferObjectManager());//Set the background of the screen.
		scene.attachChild(backgroundSprite);//Add the background to the scene

		// HUD setup
		HUD hudScore = new HUD();
		
		Sprite sprHUDBar = new Sprite(0,0, mHUDBarTextureRegion, getVertexBufferObjectManager());
		sprHUDBar.setSize(CAMERA_WIDTH, CAMERA_HEIGHT/8);
		textScore = new Text(16, 16, fontScore, "Score: 0", 500,
				this.getVertexBufferObjectManager());
		
		hudScore.attachChild(sprHUDBar);
		hudScore.attachChild(textScore);
		
		camera.setHUD(hudScore);
		
		// Initialise arraylists that hold power ups/downs visible on screen
		 currentPowerItems = new ArrayList<PowerItem>();
		 		 
		// and coins!
		 currentCoins = new ArrayList<Coin>();
		 
		// Readies array that holds wall sprites
		sWall = new Sprite[50][50]; 
			
		/* This ArrayList is used because when referencing walls for collision,
		* we'll want to be careful about 'null' values in the array above.
		*/
		currentWalls = new ArrayList<Sprite>();
			
		// Create maze walls
		createWalls(0, 0);
		
		/**
		 * All sprite creation
		 */
		sBall = new Sprite(5, 400, mBallTextureRegion, getVertexBufferObjectManager()) { //Make the ball a sprite also
			
			/**
			 * Called each time the screen is 'refreshed'.
			 * We use this method to animate the ball from one position
			 * to another when a collision is detected (ie for the
			 * 'bounce-back' effect).
			 */
			protected void onManagedUpdate(float pSecondsElapsed) {
				if (animating) {
					//Increment the values toward the final endpoint
					if (!subtractX) currentX += normalisedXInterval; else currentX -= normalisedXInterval;
					if (!subtractY) currentY += normalisedYInterval; else currentY -= normalisedYInterval;
					
					//Check if we're done moving the ball
					boolean doneWithX = (!subtractX && currentX > endX) || (subtractX && currentX < endX);
					boolean doneWithY = (!subtractY && currentY > endY) || (subtractY && currentY < endY);
					animating = !(doneWithX && doneWithY); //If not, continue animating
					
					//Update the ball's position
					this.setPosition(currentX, currentY);
				}
			}
			
			/**
			 * Handles what occurs when the ball is touched.
			 * I'm not really sure how it works yet...
			 * ~Sunny
			 */
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY){
				// Make sure the ball hasn't collided with a wall
				
				// System.out.println("OnAreaTouched - x:" + (int)pSceneTouchEvent.getX() + ", y:" + (int)pSceneTouchEvent.getY() + ", localX:" + (int)pTouchAreaLocalX + ", localY:" + (int)pTouchAreaLocalY);
				
				// COMMENTED OUT FOR NOW - SUNNY
				
				if (!animating) {
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

						checkPowerItemTouched();
						checkCoinTouched();
					}
					else {
						System.out.println("OnAreaTouched: Collision detected, trying to ignore touches for now");
						//Collision detected
						//If the ball collides, animate to previous "safe" location.
						count = 0;
						
						//this.setPosition(previousTouchx, previousTouchy);
						
						//Set up variables for animation
						animating = true;
						currentX = pSceneTouchEvent.getX() - this.getWidth() / 2; //Get the current x position of the ball
						currentY = pSceneTouchEvent.getY() - this.getHeight() / 2; //Get the current y position
						endX = previousTouchx; //Get the final x position (previous safe location)
						endY = previousTouchy; //Get the final y position
						subtractX = endX < currentX; //Whether or not we are subtracting from or adding to the
						subtractY = endY < currentY; //current value to get to the end-position
						
						/*
						 * The line below calculates the time (in terms of number of frames)
						 * needed for the ball to travel - this 'time' is obtained as a function
						 * of the total distance to be travelled by the ball (simple Pythagorean a^2 + b^2 = c^2)
						 * divided by 5. (We could experiment with this value, up to you guys)
						 */
						totalTime = (int)(Math.sqrt(Math.pow((endX - currentX), 2) + Math.pow((endY - currentY), 2)) / 5);
						
						//The number of x and y-coordinates to change each time the frame changes
						normalisedXInterval = Math.abs(endX - currentX) / totalTime;
						normalisedYInterval = Math.abs(endY - currentY) / totalTime;
						
						/**
						// Recreates grid, but isn't this a bit processor-intensive?
						grid = new int[height][width]; 
						// Exits screen
						finish(); 
						**/
						return false;
					}
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
				return false;
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
            	/* Alter the formula below to change how speed
            	 * increases
            	 * Modify the SPEED_INCREASE_RATE variable (at the top of this
            	 * class) to change how often the game speeds up
                 */
            	
            	if (disableSpeedup== false){
                   horizontal_scroll = horizontal_scroll 
                		   + horizontal_scroll * 0.4f;
            	}
            	 camera.setMaxVelocityX(horizontal_scroll);
                 iterations++;
            }
	    });
	    scene.registerUpdateHandler(speedTimer);
	   
	    // Score Handler - score changes here
	    scoreTimer = new TimerHandler(0.25f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
            	score++;
            	redrawScore();
            	if (iterations < 20){
            		// Rate of increase of score will need to stop growing 
            		// after a while;
            		// That's what 'iterations' is for
            		// scoreTimer.setTimerSeconds(10/horizontal_scroll);
            	}
            	
            }
	    });
	    scene.registerUpdateHandler(scoreTimer);
	    
	    // Destroys sprites that go off-screen
	    TimerHandler resourceHandler = new TimerHandler(5.0f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
            	/* Use of iterator allows for concurrent modification
            	 i.e. if we simply removed elements from the ArrayList,
            	 the program would crash. */
            	Iterator<Sprite> it = currentWalls.iterator();
            	while (it.hasNext()){
            		Sprite spr = it.next();
            		// if wall is off screen
            		if (camera.getCenterX()-spr.getX()-spr.getWidth() > CAMERA_WIDTH/2){
                 		scene.detachChild(spr); // Goodbye sprite!
                 		
                 		it.remove(); 
                 	}
            	}
            	// System.gc(); // Garbage collection
            }
	    });
	    scene.registerUpdateHandler(resourceHandler);
	    
	    // Handles powers items
	    TimerHandler powerHandler = new TimerHandler(1.0f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
				
            }
	    });
	    scene.registerUpdateHandler(powerHandler);
	    
	    // Power Item Handler
	    
	    // Revert back to original speed after 5 seconds
	     speedFixer = new TimerHandler(5.0f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
            	System.out.println("BEFORE: " + camera.getMaxVelocityX());
            	horizontal_scroll = old_scroll;
            	disableSpeedup = false;
            	camera.setMaxVelocityX(horizontal_scroll);
            	System.out.println("AFTER: " + camera.getMaxVelocityX());
            	
            	scene.unregisterUpdateHandler(pTimerHandler);
            }
	    });
	    
	    // SpeedupHandler
	    
	    
	    // SpeeddownHandler
	     
	     // Handles powers items
		    TimerHandler newGridHandler = new TimerHandler(1.0f, true, new ITimerCallback() {
	            @Override
	            public void onTimePassed(TimerHandler pTimerHandler) {
					if (sBall.getX() > newX){
						System.out.println("Ball X = " + sBall.getX());
						createWalls(newX, 0);
					}
	            }
		    });
		    scene.registerUpdateHandler(newGridHandler);
		    
		    
	    return scene;
	} // END OF onCreateScene()


	// height of grid
	public static int height = 7;
	// width of grid
	public static int width = 50;
	
	// 2-D grid; 1 values indicate path, 0 indicates wall
	static int[][] grid = new int[height][width];
	
	// Random int generator for creating new path
	static Random random = new Random();
	
	/**
	 * Creates the grid array for the maze that the ball travels through.
	 * Values of '1' within the array correspond to areas the ball can traverse.
	 * Values of '0' correspond to walls.
	 */
	void createMazeArray(int x){
		
		// Starts in middle of height of grid
		grid[x][0] = 1;
		grid[x][1] = 1;
		
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

	private int newX = 0;
	
	void createWalls(int x, int y){
		/* Creates the grid array using the Advanced Maze Generation Algorithm (TM) above 
		 * perfected by the Alty Boys duo (Hani and Sunny). 
		*/
		this.createMazeArray(height/2); 
		int xInterval = 128; // Each wall piece has a width of 128 pixels
		int yInterval = 128; // ..and a height of 128 pixels. Hence these intervals.
		

		/**
		 * For loop that generates walls.
		 */
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				if(grid[i][j]!=1){ // If Wall
					sWall[i][j] = new Sprite(x, y, mWallTextureRegion, getVertexBufferObjectManager());
					currentWalls.add(sWall[i][j]);
					scene.attachChild(sWall[i][j]);
				}
				else { // Power-ups/downs!
					// Generate a random number between 0 and 99
					int randomNo = random.nextInt(100); 
					
					/*
					 * 1/10 chance an item will appear at a particular spot.
					 * Out of that, a 1/10 chance of an item of a certain type  
					 * appearing. 
					 * EDIT: Changed for now, as we have only implemented two power items at the moment
					 * 
					 */
					if (randomNo == 0 && randomNo == 5){
						PowerItem powerItem = new PowerItem(x+48, y+48,
								mPowerItemTextureRegion[randomNo], 
								getVertexBufferObjectManager(), randomNo);
						scene.attachChild(powerItem);
						currentPowerItems.add(powerItem);
						
					} 
					else if (randomNo >= 10 && randomNo < 18){
						// 5% chance of bronze coin appearing
						Coin bronzeCoin = new Coin(x+48, y+48,
								bCoinTextureRegion, 
								getVertexBufferObjectManager(), 20);
						scene.attachChild(bronzeCoin);
						// Functionality for keeping track of coins?
						currentCoins.add(bronzeCoin);
						
					}
					
					else if (randomNo >=20 && randomNo < 23){
						// 3% chance of silver coin
						Coin silverCoin = new Coin(x+48, y+48,
								sCoinTextureRegion, 
								getVertexBufferObjectManager(), 21);
						scene.attachChild(silverCoin);
						currentCoins.add(silverCoin);
					}
					else if (randomNo >= 30 && randomNo < 31){
						// 1% chance of gold coin appearing
						Coin goldCoin = new Coin(x+48, y+48,
								gCoinTextureRegion, 
								getVertexBufferObjectManager(), 22);
						scene.attachChild(goldCoin);
						currentCoins.add(goldCoin);
						
					}
					
				}
				
				x = x + xInterval;
				
			}
			newX=x;
			x = 0;
			y = y + yInterval;
		}
		
		

		System.out.println("NewX =" + newX);
	
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
	 * Handles what occurs when a power item is touched by the ball,
	 * and removes said item
	 */
	void checkPowerItemTouched(){
		Iterator<PowerItem> pIterator = currentPowerItems.iterator();
		while (pIterator.hasNext()){
			PowerItem powerItem =  pIterator.next();
			// I love how this reads like natural language ^_^
			if (sBall.collidesWith(powerItem)){ 
				
				// Take powerItem's type and do stuff with it
				int type = powerItem.getType();
				
				switch(type){
				case SPEED_UP:
					disableSpeedup = true;
					old_scroll = horizontal_scroll;
					horizontal_scroll = horizontal_scroll*2;
					camera.setMaxVelocityX(horizontal_scroll);
					scene.registerUpdateHandler(speedFixer);
					break;
				case SPEED_DOWN:
					disableSpeedup = true;
					old_scroll = horizontal_scroll;
					System.out.println("SPEED BEFORE = " + camera.getMaxVelocityX());
					horizontal_scroll = horizontal_scroll/2;
					camera.setMaxVelocityX(horizontal_scroll);
					System.out.println("SPEED AFTER = " + camera.getMaxVelocityX());
					scene.registerUpdateHandler(speedFixer);
					break;
				 default: break;
				}
				// 
			
				
				//
				scene.detachChild(powerItem);
				pIterator.remove();
				System.out.println("Number of power-items: " + currentPowerItems.size());
			}
			
		}
	}
	
	/**
	 * Handles what occurs when a coin is touched by the ball,
	 * and removes said coin
	 */
	void checkCoinTouched(){
		Iterator<Coin> cIterator = currentCoins.iterator();
		while (cIterator.hasNext()){
			Coin coin =  cIterator.next();
			// I love how this reads like natural language ^_^
			if (sBall.collidesWith(coin)){ 
				
				// Take coin's type and do stuff with it
				
				// 
				
				//
				scene.detachChild(coin);
				cIterator.remove();
			}
			
		}
	}
	
	/**
	 * Re-displays score on screen
	 * Should be invoked whenever score is changed
	 */
	void redrawScore(){
    	textScore.setText("Score: " +score);
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