package ImageCreators;

import AABB.AABB;
import AABB.retObject;
import ImageCreators.Materials.Material;
import Vector.Vector3D;

public class XZRect implements HittableObject{

    private Material m;
    private double x0;
    private double x1;
    private double z0;
    private double z1;
    private double k;

    public XZRect() {
    }

    public XZRect(double x0, double x1, double z0, double z1, double k, Material m) {
        this.z0 = z0;
        this.z1 = z1;
        this.x0 = x0;
        this.x1 = x1;
        this.k = k;
        this.m = m;
    }

    @Override
    public boolean checkHit(Ray r, double tMin, double tMax, Hittable.hitRecord hRec) {
        double t = (k - r.getOrigin().y) / r.getDirection().y;
        if (t < tMin || t > tMax) {
            return false;
        }
        double x = r.getOrigin().x + t * r.getDirection().x;
        double z = r.getOrigin().z + t * r.getDirection().z;
        if (x < x0 || x > x1 || z < z0 || z > z1) {
            return false;
        }
        hRec.u = (x - x0) / (x1 - x0);
        hRec.v = (z - z0) / (z1 - z0);
        hRec.t = t;
        Vector3D outward_normal = new Vector3D(0.0f, 1.0f, .0f);
        hRec.setFaceNormal(r, outward_normal);
        hRec.m = this.m;
        hRec.point = r.getAt(t);
        return true;
    }

    @Override
    public retObject boundingBox(double tim0, double tim1, AABB outputBox) {
        outputBox = new AABB(new Vector3D((float) x0, (float) (k - 0.0001), (float) z0), new Vector3D((float) x1, (float) (k + 0.0001), (float) z1));
        return new retObject(true, outputBox);
    }
}
