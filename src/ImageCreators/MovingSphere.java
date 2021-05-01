package ImageCreators;

import AABB.AABB;
import AABB.AABBMath;
import AABB.retObject;
import ImageCreators.Materials.Material;
import Vector.Vector3D;
import Vector.VectorMath;

public class MovingSphere implements HittableObject{

    public Vector3D center0;
    public Vector3D center1;
    double time0;
    double time1;
    double rad;
    Material mat;
    VectorMath vm = new VectorMath();
    AABBMath aabbmath = new AABBMath();

    @Override
    public boolean checkHit(Ray r, double tMin, double tMax, Hittable.hitRecord hRec) {
        Vector3D oc = vm.sub(r.getOrigin(), centerAt(r.getTime()));
        float aVal = vm.dotProduct(r.getDirection(), r.getDirection());
        float halfBVal = (vm.dotProduct(oc, r.getDirection()));
        float cVal = (float) (oc.getLengthSquared() - (rad * rad));
        float discriminant = (halfBVal * halfBVal) - (aVal * cVal);
        if (discriminant < 0.0f) {
            return false;
        }
        double sqrtDisc = Math.sqrt(discriminant);

        //CLOSES ROOT, ACCEPTABLE RANGE (TMIN - TMAX)
        double root = (-halfBVal - sqrtDisc) / aVal;
        if (root < tMin || root > tMax) {
            root = (-halfBVal + sqrtDisc) / aVal;
            if (root < tMin || root > tMax){
                return false;
            }
        }

        hRec.t = root;
        hRec.point = r.getAt(hRec.t);
        Vector3D outwardNormal = vm.divide((vm.sub(hRec.point, centerAt(r.getTime()))), (float) rad);
        hRec.setFaceNormal(r, outwardNormal);
        hRec.m = this.mat;

        return true;
    }

    @Override
    public retObject boundingBox(double tim0, double tim1, AABB outputBox) {
        AABB box0 = new AABB(vm.sub(centerAt(tim0), new Vector3D((float) rad, (float) rad, (float) rad)), vm.add(centerAt(tim0), new Vector3D((float) rad, (float) rad, (float) rad)));
        AABB box1 = new AABB(vm.sub(centerAt(tim1), new Vector3D((float) rad, (float) rad, (float) rad)), vm.add(centerAt(tim1), new Vector3D((float) rad, (float) rad, (float) rad)));
        outputBox = aabbmath.surroundingBox(box0, box1);
        return new retObject(true, outputBox);
    }

    public MovingSphere(Vector3D cen0, Vector3D cen1, double time0, double time1, double r, Material m){
        this.center0 = cen0;
        this.center1 = cen1;
        this.time0 = time0;
        this.time1 = time1;
        this.rad = r;
        this.mat = m;
    }

    public Vector3D centerAt(double time){
        return vm.add(center0, vm.multiply((vm.sub(center1, center0)), (float) ((time - time0) / (time1 - time0))));
    }
}
