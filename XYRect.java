package ImageCreators;

import AABB.AABB;
import AABB.retObject;
import ImageCreators.Materials.Material;
import Vector.Vector3D;

public class XYRect implements HittableObject {

    private Material m;
    private double x0;
    private double x1;
    private double y0;
    private double y1;
    private double k;

    public XYRect() {
    }

    public XYRect(double x0, double x1, double y0, double y1, double k, Material m) {
        this.x0 = x0;
        this.x1 = x1;
        this.y0 = y0;
        this.y1 = y1;
        this.k = k;
        this.m = m;
    }

    @Override
    public boolean checkHit(Ray r, double tMin, double tMax, Hittable.hitRecord hRec) {
        double t = (k - r.getOrigin().z) / r.getDirection().z;
        if (t < tMin || t > tMax) {
            return false;
        }
        double x = r.getOrigin().x + t * r.getDirection().x;
        double y = r.getOrigin().y + t * r.getDirection().y;
        if (x < x0 || x > x1 || y < y0 || y > y1) {
            return false;
        }
        hRec.u = (x - x0) / (x1 - x0);
        hRec.v = (y - y0) / (y1 - y0);
        hRec.t = t;
        Vector3D outward_normal = new Vector3D(0.0f, 0.0f, 1.0f);
        hRec.setFaceNormal(r, outward_normal);
        hRec.m = this.m;
        hRec.point = r.getAt(t);
        return true;
    }

    @Override
    public retObject boundingBox(double tim0, double tim1, AABB outputBox) {
        outputBox = new AABB(new Vector3D((float) x0, (float) y0, (float) (k - 0.0001)), new Vector3D((float) x1, (float) y1, (float) (k + 0.0001)));
        return new retObject(true, outputBox);
    }
}
