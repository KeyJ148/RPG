package engine.obj.components.particles;

import engine.obj.Obj;
import engine.obj.components.Component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class Particles extends Component {

    public Set<Part> parts = new HashSet<>();
    public boolean rotate = false;

    public Particles(Obj obj) {
        super(obj);
    }

    @Override
    public void update(long delta){
        Iterator<Part> iterator = parts.iterator();
        while(iterator.hasNext()){
            Part part = iterator.next();
            part.x = part.x + (part.speed * Math.cos(Math.toRadians(part.direction)) * ((double) delta/1000000000));
            part.y = part.y - (part.speed * Math.sin(Math.toRadians(part.direction)) * ((double) delta/1000000000));
            part.life -= ((double) delta)/1000000000L;//“.к. на вход подаетс€ в нано-секундах, а хранитс€ в секундах
            if (part.life <= 0) iterator.remove();
            else updateChild(delta, part);
        }
    }

    //ћетод переопредел€етс€ в наследниках дл€ обработки каждый степ
    public void updateChild(long delta, Part part){}
}
