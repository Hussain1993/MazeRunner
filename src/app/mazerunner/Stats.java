package app.mazerunner;

import android.app.Activity;
import android.os.Bundle;
/**
 * This class is for the stats
 * screen of the game.
 * @author Jayen
 */
public class Stats extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statslayout);
	}
}
