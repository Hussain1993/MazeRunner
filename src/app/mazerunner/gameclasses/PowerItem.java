package app.mazerunner.gameclasses;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class PowerItem extends Sprite {
	
	// I guess type goes here
	public static final int TYPE_1 = 0;
	
	private int type;
	
	public PowerItem(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, int t) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		type = t;
	}
	
	public int getType(){
		return this.type;
	}

	
	
}
