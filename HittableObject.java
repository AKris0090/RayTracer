package ImageCreators;

import AABB.AABB;
import AABB.retObject;
import Vector.Vector3D;

public interface HittableObject {
    Vector3D attenuation = new Vector3D();
    Ray scattered = new Ray();

    boolean checkHit(Ray r, double tMin, double tMax, Hittable.hitRecord hRec);
    retObject boundingBox(double tim0, double tim1, AABB outputBox);
}
