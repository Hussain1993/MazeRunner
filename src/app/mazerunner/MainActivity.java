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
	private Button scoreButton;
	private Button optionsButton;
	private Button exitButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setup startGameButton
        //===============================================================================================
        startGameButton=(Button) findViewById(R.id.Game);//find the startGame button by id
        //add the onClickListener for the startGame
        startGameButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getBaseContext(), Game.class));
			}
		});
        //================================================================================================    
        
        //setup scoreButton
        //================================================================================================
        scoreButton=(Button) findViewById(R.id.Score);//find the startGame button by id
        //add the onClickListener for the startGame
        scoreButton.setOnClickListener(new OnClickListener() {
			
        	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getBaseContext(), Score.class));
			}
		});
        //=================================================================================================

        //setup optionsButton
        //================================================================================================
        optionsButton=(Button) findViewById(R.id.Option);//find the startGame button by id
        //add the onClickListener for the startGame
        optionsButton.setOnClickListener(new OnClickListener() {
			
        	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getBaseContext(), Options.class));
			}
		});
        //=================================================================================================

        //setup exitButton
        //================================================================================================
        exitButton=(Button) findViewById(R.id.Exit);//find the startGame button by id
        //add the onClickListener for the startGame
        exitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent exiIntent = new Intent(Intent.ACTION_MAIN);
				exiIntent.addCategory(Intent.CATEGORY_HOME);
				exiIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(exiIntent);
			}
		});
        //=================================================================================================
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}