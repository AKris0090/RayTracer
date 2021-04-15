package AABB;

import ImageCreators.Ray;
import Vector.Vector3D;

public class AABB {

    public Vector3D minimum;
    public Vector3D maximum;

    public AABB(){
    }

    public AABB(Vector3D a, Vector3D b){
        this.minimum = a;
        this.maximum = b;
    }

    public boolean hit(Ray r, double tMin, double tMax){
        for (int i = 0; i < 3; i++) {
            double t0;
            double t1;
            if (i == 0){
                t0 = Math.min(((minimum.getX() - r.getOrigin().x) / (r.getDirection().getX())),
                        ((maximum.getX() - r.getOrigin().getX()) / r.getOrigin().getX()));
                t1 = Math.max(((minimum.getX() - r.getOrigin().x) / (r.getDirection().getX())),
                        ((maximum.getX() - r.getOrigin().getX()) / r.getOrigin().getX()));
            } else if (i == 1){
                t0 = Math.min(((minimum.getY() - r.getOrigin().y) / (r.getDirection().getY())),
                        ((maximum.getY() - r.getOrigin().getY()) / r.getOrigin().getY()));
                t1 = Math.max(((minimum.getY() - r.getOrigin().y) / (r.getDirection().getY())),
                        ((maximum.getY() - r.getOrigin().getY()) / r.getOrigin().getY()));
            } else {
                t0 = Math.min(((minimum.getZ() - r.getOrigin().z) / (r.getDirection().getZ())),
                        ((maximum.getZ() - r.getOrigin().getZ()) / r.getOrigin().getZ()));
                t1 = Math.max(((minimum.getZ() - r.getOrigin().z) / (r.getDirection().getZ())),
                        ((maximum.getZ() - r.getOrigin().getZ()) / r.getOrigin().getZ()));
            }
            tMin = Math.max(t0, tMin);
            tMax = Math.min(t1, tMax);
            if (tMax <= tMax){
                return false;
            }
        }
        return true;
    }

    public Vector3D getMinimum() {
        return minimum;
    }

    public Vector3D getMaximum() {
        return maximum;
    }

}
