package app.mazerunner;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
/**
 * 	!!!!!!!!!!!!!!!!!!!!!!PLEASE IGNORE THIS CLASS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * @author Hussain
 *
 */
public class Circle extends Sprite {
	private int mWeight;
	public Circle(int weight,float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		this.mWeight = weight;
	}
	
	public int getmWeight(){
		return this.mWeight;
	}
	
	

}
