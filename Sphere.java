package ImageCreators;

import Vector.Vector3D;
import Vector.VectorMath;
import processing.core.PApplet;

public class Sphere implements hittableObject{

    public float radius;
    public Vector3D center;
    private final VectorMath vm = new VectorMath();

    public Sphere(float radius, Vector3D center) {
        this.radius = radius;
        this.center = center;
    }

    @Override
    public boolean checkHit(Ray r, double tMin, double tMax, Hittable.hitRecord hRec) {
        Vector3D originToPoint = vm.sub(r.getOrigin(), center);
        float aVal = vm.dotProduct(r.getDirection(), r.getDirection());
        float halfBVal = (vm.dotProduct(originToPoint, r.getDirection()));
        float cVal = vm.dotProduct(originToPoint, originToPoint) - (radius * radius);
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
        Vector3D outwardNormal = vm.divide((vm.sub(hRec.point, center)), radius);
        hRec.setFaceNormal(r, outwardNormal);

        return true;
    }

    public double checkInSphereValue(Ray r) {
        Vector3D originToPoint = vm.sub(r.getOrigin(), center);
        float aVal = vm.dotProduct(r.getDirection(), r.getDirection());
        float halfBVal = (vm.dotProduct(originToPoint, r.getDirection()));
        float cVal = vm.dotProduct(originToPoint, originToPoint) - (radius * radius);
        float discriminant = (halfBVal * halfBVal) - (aVal * cVal);
        if (discriminant < 0.0){
            return -1.0;
        } else {
            return (((-halfBVal) - Math.sqrt(discriminant)) / (aVal));
        }
    }
}
