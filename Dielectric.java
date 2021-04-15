package ImageCreators.Materials;

import ImageCreators.Hittable;
import ImageCreators.Ray;
import Vector.Vector3D;
import Vector.VectorMath;

public class Dielectric implements Material {

    public final VectorMath vm = new VectorMath();
    public Vector3D albedo;
    public double ir;

    public Dielectric(double indexReferaction){
        this.ir = indexReferaction;
    }

    @Override
    public MaterialInfo scatter(Ray input, Hittable h, Vector3D attenuation, Ray scattered) {
        attenuation = new Vector3D(1.0f, 1.0f, 1.0f);
        double refractionRatio = h.frontFace ? (1.0 / ir) : ir;

        Vector3D unitDir = vm.normalize(input.getDirection());
        double cos = Math.min(vm.dotProduct(vm.multiply(unitDir, -1.0f), h.hRec.normal), 1.0f);
        double sin = Math.sqrt(1.0 - cos * cos);

        boolean cannotRefract = refractionRatio * sin > 1.0;
        Vector3D dir;
        double random = Math.random() / (Math.nextDown(1.0) + 1.0);
        if (cannotRefract || vm.reflectance(cos, refractionRatio) > random){
            dir = vm.reflect(unitDir, h.hRec.normal);
        } else {
            dir = vm.refract(unitDir, h.hRec.normal, refractionRatio);
        }

        scattered = new Ray(h.hRec.point, dir, input.getTime());
        return new MaterialInfo(scattered, attenuation, h, true);
    }
}
