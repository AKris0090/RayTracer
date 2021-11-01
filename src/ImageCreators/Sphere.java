/*
 * Copyright (c) 2021.
 *
 * Arjun Krishnan 10/31/2021
 * See my other coding projects at: akrishnan.netlify.app
 * Questions, email me at: artk0090@gmail.com
 */

package ImageCreators;

import AABB.AABB;
import AABB.retObject;
import ImageCreators.Materials.Lambertian;
import ImageCreators.Materials.Material;
import Textures.Texture;
import Textures.returnDouble;
import Vector.Vector3D;
import Vector.VectorMath;

public class Sphere implements HittableObject{

    public float radius;
    public Vector3D center;
    private final VectorMath vm = new VectorMath();
    private final Material material;

    public Sphere(float radius, Vector3D center, Material m) {
        this.radius = radius;
        this.center = center;
        this.material = m;
    }

    public Sphere(float radius, Vector3D center, Texture tex) {
        this.radius = radius;
        this.center = center;
        this.material = new Lambertian(tex);
    }

    public Sphere(float radius, Vector3D center) {
        this.radius = radius;
        this.center = center;
        this.material = null;
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
        returnDouble ret = getSphereUV(outwardNormal);
        hRec.u = ret.getU();
        hRec.v = ret.getV();
        hRec.m = this.material;

        return true;
    }

    @Override
    public retObject boundingBox(double tim0, double tim1, AABB outputBox) {
        outputBox = new AABB(vm.sub(center, new Vector3D(radius, radius, radius)), vm.add(center, new Vector3D(radius, radius, radius)));
        return new retObject(true, outputBox);
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

    public returnDouble getSphereUV(Vector3D p) {
        double theta = Math.acos(vm.multiply(p, -1.0f).getY());
        double phi = Math.atan2(vm.multiply(p, -1.0f).getZ(), p.getX() + Math.PI);

        double u = phi / (2 * Math.PI);
        double v = theta / Math.PI;
        return new returnDouble(u, v);
    }
}
