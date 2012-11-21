package app.mazerunner;

public class NameScore {
	
	private String name;
	private int score;
	
	public NameScore(String name,int score)
	{
		this.name=name;
		this.score=score;
	}
	/**
	 * Method to return the key for the
	 * user and his score
	 * @return String
	 */
	public String getKey()
	{
		return name+" "+score;
	}
	/**
	 * Method to get the user name
	 * @return String
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * Method to get the user score
	 * @return int
	 */
	public int getScore()
	{
		return score;
	}

}
