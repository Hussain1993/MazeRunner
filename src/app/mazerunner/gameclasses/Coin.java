package app.mazerunner.gameclasses;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Coin extends Sprite {
	
	// Types of coins
	public static final int BRONZE = 20;
	public static final int SILVER = 21;
	public static final int GOLD = 22;
	
	private int type;
	
	public Coin(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, int t) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		type = t;
	}
	
	public int getValue(){
		return type;
	}
	
}