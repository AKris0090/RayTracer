package ImageCreators.Materials;

import ImageCreators.Hittable;
import ImageCreators.Ray;
import Textures.SolidColor;
import Textures.Texture;
import Vector.Vector3D;

public class DiffuseLight implements Material {

    public Texture emit;

    public DiffuseLight(Texture a) {
        this.emit = a;
    }

    public DiffuseLight(Vector3D color) {
        this.emit = new SolidColor(color);
    }

    @Override
    public Vector3D emitted(double u, double v, Vector3D p) {
        return emit.value(u, v, p);
    }

    @Override
    public MaterialInfo scatter(Ray input, Hittable h, Vector3D attenuation, Ray scattered) {
        return new MaterialInfo(scattered, attenuation, h, false);
    }
}
