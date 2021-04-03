package ImageCreators.Materials;

import ImageCreators.Hittable;
import ImageCreators.Ray;
import Vector.Vector3D;
import Vector.VectorMath;

public class Material {
    public Vector3D albedo;
    public Ray scattered;
    public Vector3D attenuation;
    public String materialName;
    private final VectorMath vm = new VectorMath();
    public double fuzz = -1.0;

    public Material(Vector3D albedo, String name) {
        this.albedo = albedo;
        this.materialName = name;
    }

    public Material(Vector3D albedo, double f, String name) {
        this.albedo = albedo;
        this.materialName = name;
        if (f < 1) {
            this.fuzz = f;
        } else {
            this.fuzz = 1.0f;
        }
    }


    public boolean scatter(Ray input, Hittable h) {
        if (materialName.equals("lambertian")) {
            Vector3D scatteredDir = vm.add(h.hRec.normal, vm.randomVectorInUnitCircle(-1, 1));

            //CATCHING DEGENERATE SCATTER DIRECTION
            if (vm.nearZero(scatteredDir)) {
                scatteredDir = h.hRec.normal;
            }

            this.scattered = new Ray(h.hRec.point, scatteredDir);
            attenuation = this.albedo;
            return true;
        } else if (materialName.equals("metal")) {
            Vector3D reflected = vm.reflect(vm.normalize(input.getDirection()), h.hRec.normal);
            if (this.fuzz >= 0.0) {
                scattered = new Ray(h.hRec.point, vm.add(reflected, vm.multiply(vm.randomVectorInUnitCircle(-1, 1), (float) fuzz)));
            } else {
                scattered = new Ray(h.hRec.point, reflected);
            }
            attenuation = this.albedo;
            return ((vm.dotProduct(scattered.getDirection(), h.hRec.normal)) > 0.0f);
        } else {
            return false;
        }
    }
}
