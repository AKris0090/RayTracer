package ImageCreators;

import ImageCreators.Materials.Material;
import Vector.Vector3D;
import Vector.VectorMath;

import java.util.ArrayList;

public class Hittable {
    public boolean frontFace;
    public VectorMath vm = new VectorMath();
    public ArrayList<HittableObject> worldObjects = new ArrayList<>();
    public hitRecord hRec;

    public Hittable() {
        hRec = new hitRecord();
    }

    public class hitRecord {
        public Vector3D point;
        public Vector3D normal;
        public Material m;
        double t;

        public hitRecord() {
        }

        public void setFaceNormal(Ray r, Vector3D outwardNormal) {
            frontFace = vm.dotProduct(r.getDirection(), outwardNormal) < 0;
            if (frontFace) {
                normal = outwardNormal;
            } else {
                normal = vm.multiply(outwardNormal, -1.0f);
            }
        }
    }

    public boolean hitAnything(Ray r, double tMin, double tMax, hitRecord hRec) {
        hitRecord tempRec = new hitRecord();
        boolean hitAnything = false;
        double closestTValSoFar = tMax;

        for (HittableObject h : worldObjects) {
            if (h.checkHit(r, tMin, closestTValSoFar, hRec)) {
                hitAnything = true;
                closestTValSoFar = tempRec.t;
                hRec = tempRec;
            }
        }

        return hitAnything;
    }
}
