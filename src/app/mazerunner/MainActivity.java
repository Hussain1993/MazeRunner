package app.mazerunner;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * The Main activity of the Maze Runner app
 * This activity is the main menu of the game
 * @author Jayen
 */
public class MainActivity extends Activity {

	private static final int START_GAME_DIALOG = 0;
	private static Boolean first;
	private SharedPreferences prefs;
	private Button startGameButton;
	private Button statsButton;
	private Button storeButton;
	private Button achievementsButton;
	private Button exitButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	System.out.println("HElLO WORLD");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = this.getSharedPreferences("app.mazerunner", Context.MODE_PRIVATE);
        final String booleanKey = "com.mazerunner.booleanKey";
        first = prefs.getBoolean(booleanKey, true);
        
        //setup startGameButton
        //===============================================================================================
        startGameButton=(Button) findViewById(R.id.Game);//find the startGame button by id
        //add the onClickListener for the startGame
        startGameButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(prefs.getBoolean(booleanKey, true) == true)
				{
					prefs.edit().putBoolean(booleanKey, false).apply();
					showDialog(START_GAME_DIALOG);
				}
				else
				{
					startActivity(new Intent(getBaseContext(), Game.class));
				}
				first = prefs.getBoolean(booleanKey, true);
			}
		});
        //================================================================================================    
        
        //setup statsButton
        //=================================================================================================
        statsButton=(Button) findViewById(R.id.Stats);//find the startGame button by id
        //add the onClickListener for the startGame
        statsButton.setOnClickListener(new OnClickListener() {
			
        	@Override
			public void onClick(View v) {
        		startActivity(new Intent(getBaseContext(),Stats.class));
			}
		});
        //=================================================================================================

        //setup storeButton
        //=================================================================================================
        storeButton=(Button) findViewById(R.id.Store);//find the startGame button by id
        //add the onClickListener for the startGame
        storeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(),Store.class));
			}
		});
        //=================================================================================================
        
        //setup optionsButton
        //=================================================================================================
        achievementsButton=(Button) findViewById(R.id.achievements);//find the startGame button by id
        //add the onClickListener for the startGame
        achievementsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), Achievements.class));
			}
		});
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

    /* (non-Javadoc)
	 * @see android.app.Activity#onCreateDialog(int)
	 */
	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		switch(id)
		{
		case START_GAME_DIALOG: return new AlertDialog.Builder(this)
		.setTitle(R.string.instrctions_title)
		.setMessage(R.string.instrctions)
		.setPositiveButton("Start Game", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				startActivity(new Intent(getBaseContext(), Game.class));
				dialog.dismiss();
			}
		})
		.create();
		}
		return null;
	}
    
    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        //return true;
    //}
}
