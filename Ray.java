package ImageCreators;

import Vector.Vector3D;
import Vector.VectorMath;

public class Ray {

    private Vector3D origin = new Vector3D();
    private Vector3D direction = new Vector3D();
    private final VectorMath m = new VectorMath();

    public Ray (){
    }

    public Ray (Vector3D origin, Vector3D direction){
        this.origin = origin;
        this.direction = direction;
    }

    public Vector3D getOrigin() {
        return origin;
    }

    public Vector3D getDirection() {
        return direction;
    }

    public Vector3D getAt(double t){
        return m.add(origin, (m.multiply(direction, (float) (t))));
    }
}
