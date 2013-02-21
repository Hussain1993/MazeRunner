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
		
		highestScore=(TextView) findViewById(R.id.highestScoreTV);
		highestScore.setText(getString(R.string.highScore)+" "+data.getInt(Game.SCORE_HIGHEST,0));
		
		mostCoins=(TextView) findViewById(R.id.mostCoinsTV);
		mostCoins.setText(getString(R.string.mostCoins)+" "+data.getInt(Game.COINS_HIGHEST,0));
		
		highestDistance=(TextView) findViewById(R.id.highestDistanceTV);
		highestDistance.setText(getString(R.string.highestDistance)+"  "+data.getFloat(Game.DISTANCE_HIGHEST,0));
		
		totalGames=(TextView) findViewById(R.id.totalGamesTV);
		totalGames.setText(getString(R.string.totalGames)+"  "+data.getInt(Game.NUMBEROFGAMESPLAYED,0));
		
		totalDistance=(TextView) findViewById(R.id.totalDistanceTV);
		totalDistance.setText(getString(R.string.totalDistance)+" "+data.getFloat(Game.DISTANCE_OVERALL,0));
		
		totalCoins=(TextView) findViewById(R.id.totalCoinsTV);
		totalCoins.setText(getString(R.string.totalCoins)+"  "+data.getInt(Game.COINS_OVERALL,0));
	}
}
