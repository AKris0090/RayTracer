package ImageCreators;

import AABB.AABB;
import AABB.retObject;
import ImageCreators.Materials.Material;
import Vector.Vector3D;

public class Box implements HittableObject{

    public Vector3D boxMin;
    public Vector3D boxMax;
    Hittable sides;

    public Box(Vector3D p0, Vector3D p1, Material mat){
        sides = new Hittable();
        sides.worldObjects.add(new XYRect(p0.x, p1.x, p0.y, p1.y, p1.z, mat));
        sides.worldObjects.add(new XYRect(p0.x, p1.x, p0.y, p1.y, p0.z, mat));

        sides.worldObjects.add(new XZRect(p0.x, p1.x, p0.z, p1.z, p1.y, mat));
        sides.worldObjects.add(new XZRect(p0.x, p1.x, p0.z, p1.z, p0.y, mat));

        sides.worldObjects.add(new YZRect(p0.y, p1.y, p0.z, p1.z, p1.x, mat));
        sides.worldObjects.add(new YZRect(p0.y, p1.y, p0.z, p1.z, p0.x, mat));
    }

    @Override
    public boolean checkHit(Ray r, double tMin, double tMax, Hittable.hitRecord hRec) {
        return sides.hitAnything(r, tMin, tMax, hRec);
    }

    @Override
    public retObject boundingBox(double tim0, double tim1, AABB outputBox) {
        outputBox = new AABB(boxMin, boxMax);
        return new retObject(true, outputBox);
    }
}
