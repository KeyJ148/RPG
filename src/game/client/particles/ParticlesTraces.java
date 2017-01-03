package game.client.particles;

import engine.Vector2;
import engine.obj.Obj;
import engine.obj.components.particles.Part;
import engine.obj.components.particles.ParticlesGeometry;
import org.newdawn.slick.Color;

import java.util.Random;

public class ParticlesTraces extends ParticlesGeometry {

    public ParticlesTraces(Obj obj){
        super(obj);
        Random rand = new Random();
        for (int i=0; i<2+rand.nextInt(4); i++){
            Part part = new Part();
            Vector2<Integer> relativePosition = getObj().position.getRelativePosition();
            part.x = relativePosition.x;
            part.y = relativePosition.y;
            part.direction = getObj().position.getDirectionDraw()-180 -30+rand.nextInt(60);
            part.speed = 50+rand.nextInt(15);
            part.width = 2;
            part.height = 2;
            part.life = 2;
            part.color = new Color(130+rand.nextInt(40), 55+rand.nextInt(40), rand.nextInt(20));
            part.type = Part.Type.FILL;

            parts.add(part);
        }
    }

    @Override
    public void updateChild(long delta, Part part) {
        part.color = new Color(part.color.r, part.color.g, part.color.b, (float) part.life/1);
    }
}
