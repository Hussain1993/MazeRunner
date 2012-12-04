package app.mazerunner;

public class ColumnArray {
	
	private boolean[] array; 
	
	public ColumnArray(){
		this(false, false, false, false, false);
	}
	
	public ColumnArray(boolean pos1, boolean pos2, boolean pos3, boolean pos4, boolean pos5){
		array = new boolean[]{pos1, pos2, pos3, pos4, pos5};
	}
	
	public boolean get(int i){
		return array[i];
	}
	
	public void set(int pos, boolean value){
		array[pos] = value;
	}
	
	public String toString(){
		return array[0] + " " + array[1] + " " + array[2] + " " + array[3] 
				+ " " + array[4] + " ";
	}

}
