package LibgdxExample;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tile {

    float x,y,width, height;
    Texture texture;

    public Tile(Texture texture,float x, float y, float width, float height){
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height =height;
    }

    public void render(SpriteBatch batch){
        batch.draw(texture,x,y,width,height);
    }


}
