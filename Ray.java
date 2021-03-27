import Vector.Vector3D;
import Vector.VectorMath;

public class Ray {

    private final Vector3D origin;
    private final Vector3D direction;
    private final VectorMath m = new VectorMath();

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
