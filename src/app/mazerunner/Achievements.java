package app.mazerunner;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
/**
 * This class is for the options
 * menu for the game
 * @author Jayen
 */
public class Achievements extends Activity {

	TextView achievement1;
	TextView achievement2;
	TextView achievement3;
	TextView achievement4;
	TextView achievement5;
	TextView achievement6;
	TextView achievement7;
	TextView achievement8;
	TextView achievement9;
	TextView achievement10;
	TextView achievement11;
	TextView achievement12;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.achievementslayout);
		
		achievement1=(TextView)findViewById(R.id.achievements1);
		achievement2=(TextView)findViewById(R.id.achievements2);
		achievement3=(TextView)findViewById(R.id.achievements3);
		achievement4=(TextView)findViewById(R.id.achievements4);
		achievement5=(TextView)findViewById(R.id.achievements5);
		achievement6=(TextView)findViewById(R.id.achievements6);
		achievement7=(TextView)findViewById(R.id.achievements7);
		achievement8=(TextView)findViewById(R.id.achievements8);
		achievement9=(TextView)findViewById(R.id.achievements9);
		achievement10=(TextView)findViewById(R.id.achievements10);
		achievement11=(TextView)findViewById(R.id.achievements11);
		achievement12=(TextView)findViewById(R.id.achievements12);
	}
}
