//package app.mazerunner;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import android.app.Activity;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.widget.TableLayout;
//import android.widget.TableRow;
//import android.widget.TextView;
///**
// * This class stores the score
// * for the user using sharedPreferences
// * to store the score
// * @author Jayen
// */
//public class Score extends Activity {
//	
//	public static final String scorePrefName="GameScore";//sharedPref name
//	private NameScore scoreValue;// this variable stores the name and the score of the current player	
//	private TableLayout scoreLayout;
//	private SharedPreferences score;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.scorelayout);
//		
//		scoreLayout = (TableLayout) findViewById(R.id.tableScoreLayout);
//		
//		//Restore the score
//		score = getSharedPreferences(scorePrefName,0);
//		
//		try{
//			Map<String,String> scoreMap = (Map<String, String>) score.getAll();
//			System.out.println("NO NULL POINTER EXCEPTION!!!!!");
//			List<String> scoreList = new ArrayList<String>(scoreMap.values());
//			
//			for(int i=0; i<scoreList.size();i++)
//			{
//				TableRow row = (TableRow)scoreLayout.getChildAt(i);
//				TextView textview = (TextView)row.getChildAt(0);
//				textview.setText(scoreList.get(i));
//			}
//		}
//		/*
//		 * If there is no score in the shraredPreference score
//		 * NullPointerException is thrown.
//		 * Add predefined data when there is no data.
//		 */
//		catch(NullPointerException e){
//			System.out.println("Null  pointer exception");
//			setPredefinedScore();
//		}
//	}
//
//	@Override
//	protected void onStop() {
//		// TODO Auto-generated method stub
//		
//		super.onStop();
//		/*
//		 * Create an editor object to make the score changes
//		 * All objects are form the andoir.context.Context
//		 */
//		setPredefinedScore();
//	}	
//	/**
//	 * Method to create and set the
//	 * predefined score for the game
//	 */
//	private void setPredefinedScore()
//	{
//		
//		SharedPreferences.Editor editor = score.edit();
//		//create predefined data
//		NameScore mylesScore = new NameScore("Myles",10000);
//		NameScore hussainScore = new NameScore("Hussain",8200);
//		NameScore jayenScore = new NameScore("Jayen",7600);
//		NameScore sunnyScore = new NameScore("Sunny",6900);
//		NameScore malcolmScore = new NameScore("Malcolm",6500);
//		NameScore alexScore = new NameScore("Alex",6200);
//		NameScore gabriellaScore = new NameScore("Gabriella",6000);
//		NameScore haniScore = new NameScore("Hani",5750);
//		NameScore jayScore = new NameScore("Jay",5500);
//		NameScore owenScore = new NameScore("Owen",5450);
//		
//		//insert all the scores into the editor of the sharedPrefs
//		//editor.putString(key,value)
//		editor.putString(mylesScore.getKey(),mylesScore.getKey());
//		editor.putString(hussainScore.getKey(),hussainScore.getKey());
//		editor.putString(jayenScore.getKey(),jayenScore.getKey());
//		editor.putString(sunnyScore.getKey(),sunnyScore.getKey());
//		editor.putString(malcolmScore.getKey(),malcolmScore.getKey());
//		editor.putString(alexScore.getKey(),alexScore.getKey());
//		editor.putString(gabriellaScore.getKey(),gabriellaScore.getKey());
//		editor.putString(haniScore.getKey(),haniScore.getKey());
//		editor.putString(jayScore.getKey(),jayScore.getKey());
//		editor.putString(owenScore.getKey(),owenScore.getKey());
//		
//		//commit the edits
//		editor.commit();
//		
//		@SuppressWarnings("unchecked")
//		Map<String,String> scoreMap = (Map<String, String>) score.getAll();
//		List<String> scoreList = new ArrayList<String>(scoreMap.values());
//		
//		for(int i=0; i<scoreList.size();i++)
//		{
//			TableRow row = (TableRow)scoreLayout.getChildAt(i);
//			TextView textview = (TextView)row.getChildAt(0);
//			textview.setText(scoreList.get(i));
//			System.out.println(scoreList.get(i));
//		}
//	}
//}	

