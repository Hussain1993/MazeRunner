package app.mazerunner.gameclasses;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class PowerItem extends Sprite {
	
	public static final int TYPE_1 = 0;
	
	private int type;
	
	public PowerItem(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, int t) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		type = t;
	}


	
	
}
