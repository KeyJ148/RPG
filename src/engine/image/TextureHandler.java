package engine.image;

import engine.io.Logger;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

public class TextureHandler {
	
	public Texture texture;
	public String path;
	public Mask mask;

	public TextureHandler(String path) {
		try {
			this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(path));
			Logger.println("Load image \"" + path + "\" complited", Logger.Type.DEBUG_IMAGE);
		} catch (IOException e1) {
			Logger.println("Image \"" + path + "\" not loading", Logger.Type.ERROR);
		}
		this.path = path;
		this.mask = new Mask(path, getWidth(), getHeight());
	}
	
	public int getWidth(){
		return texture.getImageWidth();
	}
	
	public int getHeight(){
		return texture.getImageHeight();
	}
	
}
