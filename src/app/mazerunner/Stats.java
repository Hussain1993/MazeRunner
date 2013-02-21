package app.mazerunner;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
/**
 * This class is for the stats
 * screen of the game.
 * @author Jayen
 */
public class Stats extends Activity {

	SharedPreferences data;
	TextView bonus;
	TextView highestScore;
	TextView mostCoins;
	TextView highestDistance;
	TextView totalGames;
	TextView totalDistance;
	TextView totalCoins;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statslayout);
		data=getSharedPreferences(Game.PREFS,0);
		
		bonus=(TextView) findViewById(R.id.statsBonusTV);
		bonus.setText(getString(R.string.bonus)+data.getInt(Game.BONUS,0));
	}
}
