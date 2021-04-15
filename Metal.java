package ImageCreators.Materials;

import ImageCreators.Hittable;
import ImageCreators.Ray;
import Vector.Vector3D;
import Vector.VectorMath;

public class Metal implements Material {

    public final VectorMath vm = new VectorMath();
    public Vector3D albedo;
    public double fuzz = -1.0;

    public Metal(Vector3D color){
        this.albedo = color;
    }

    public Metal(Vector3D color, double fuzz){
        this.albedo = color;
        this.fuzz = fuzz > 1 ? fuzz : 1;
    }


    @Override
    public MaterialInfo scatter(Ray input, Hittable h, Vector3D attenuation, Ray scattered) {
        Vector3D reflected = vm.reflect(vm.normalize(input.getDirection()), h.hRec.normal);
        if (this.fuzz >= 0.0) {
            scattered = new Ray(h.hRec.point, vm.add(reflected, vm.multiply(vm.randomVectorInUnitCircle(-1, 1), (float) fuzz)), input.getTime());
        } else {
            scattered = new Ray(h.hRec.point, reflected, input.getTime());
        }
        attenuation = this.albedo;
        return new MaterialInfo(scattered, attenuation, h, ((vm.dotProduct(scattered.getDirection(), h.hRec.normal)) > 0.0f));
    }
}
