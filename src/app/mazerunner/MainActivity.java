package app.mazerunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * The Main activity of the Maze Runner app
 * This activity is the main menu of the game
 * @author Jayen
 */
public class MainActivity extends Activity {

	private Button startGameButton;
	private Button statsButton;
	private Button storeButton;
	//private Button optionsButton;
	private Button exitButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	System.out.println("HElLO WORLD");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //setup startGameButton
        //===============================================================================================
        startGameButton=(Button) findViewById(R.id.Game);//find the startGame button by id
        //add the onClickListener for the startGame
        startGameButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), Game.class));
			}
		});
        //================================================================================================    
        
        //setup statsButton
        //=================================================================================================
        //statsButton=(Button) findViewById(R.id.Stats);//find the startGame button by id
        //add the onClickListener for the startGame
        //statsButton.setOnClickListener(new OnClickListener() {
			
        	//@Override
			//public void onClick(View v) {
        		//startActivity(new Intent(getBaseContext(),Stats.class));
			//}
		//});
        //=================================================================================================

        //setup storeButton
        //=================================================================================================
        //storeButton=(Button) findViewById(R.id.Store);//find the startGame button by id
        //add the onClickListener for the startGame
        //storeButton.setOnClickListener(new OnClickListener() {
			
			//@Override
			//public void onClick(View v) {
				//startActivity(new Intent(getBaseContext(),Extras.class));
			//}
		//});
        //=================================================================================================
        
        //setup optionsButton
        //=================================================================================================
        //optionsButton=(Button) findViewById(R.id.Options);//find the startGame button by id
        //add the onClickListener for the startGame
        //optionsButton.setOnClickListener(new OnClickListener() {
			
			//@Override
			//public void onClick(View v) {
				//startActivity(new Intent(getBaseContext(), Options.class));
			//}
		//});
        //=================================================================================================
        
        //setup exitButton
        //=================================================================================================
        exitButton=(Button) findViewById(R.id.Exit);//find the startGame button by id
        //add the onClickListener for the startGame
        exitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent exitIntent = new Intent(Intent.ACTION_MAIN);
				exitIntent.addCategory(Intent.CATEGORY_HOME);
				exitIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(exitIntent);
			}
		});
        //=================================================================================================
    }

    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        //return true;
    //}
}
