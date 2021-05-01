package ImageCreators;

import AABB.AABB;
import AABB.retObject;
import ImageCreators.Materials.Material;
import Vector.Vector3D;

public class YZRect implements HittableObject {

    private Material m;
    private double y0;
    private double y1;
    private double z0;
    private double z1;
    private double k;

    public YZRect() {
    }

    public YZRect(double y0, double y1, double z0, double z1, double k, Material m) {
        this.z0 = z0;
        this.z1 = z1;
        this.y0 = y0;
        this.y1 = y1;
        this.k = k;
        this.m = m;
    }

    @Override
    public boolean checkHit(Ray r, double tMin, double tMax, Hittable.hitRecord hRec) {
        double t = (k - r.getOrigin().x) / r.getDirection().x;
        if (t < tMin || t > tMax) {
            return false;
        }
        double y = r.getOrigin().y + (t * r.getDirection().y);
        double z = r.getOrigin().z + (t * r.getDirection().z);
        if (y < y0 || y > y1 || z < z0 || z > z1) {
            return false;
        }
        hRec.u = (y - y0) / (y1 - y0);
        hRec.v = (z - z0) / (z1 - z0);
        hRec.t = t;
        Vector3D outward_normal = new Vector3D(1.0f, 0.0f, 0.0f);
        hRec.setFaceNormal(r, outward_normal);
        hRec.m = this.m;
        hRec.point = r.getAt(t);
        return true;
    }

    @Override
    public retObject boundingBox(double tim0, double tim1, AABB outputBox) {
        outputBox = new AABB(new Vector3D((float) (k - 0.0001), (float) y0, (float) z0), new Vector3D((float) (k + 0.0001), (float) y1, (float) z1));
        return new retObject(true, outputBox);
    }
}
