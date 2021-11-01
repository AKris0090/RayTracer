/*
 * Copyright (c) 2021.
 *
 * Arjun Krishnan 10/31/2021
 * See my other coding projects at: akrishnan.netlify.app
 * Questions, email me at: artk0090@gmail.com
 */

package ImageCreators.Materials;

import ImageCreators.Hittable;
import ImageCreators.Ray;
import Textures.SolidColor;
import Textures.Texture;
import Vector.Vector3D;
import Vector.VectorMath;

public class Lambertian implements Material {

    public final VectorMath vm = new VectorMath();
    public Texture albedo;

    public Lambertian(Vector3D a){
        this.albedo = new SolidColor(a);
    }

    public Lambertian(Texture t){
        this.albedo = t;
    }


    @Override
    public MaterialInfo scatter(Ray input, Hittable h, Vector3D attenuation, Ray scattered) {
        Vector3D scatteredDir = vm.add(h.hRec.normal, vm.randomVectorInUnitCircle(-1, 1));

        //CATCHING DEGENERATE SCATTER DIRECTION
        if (vm.nearZero(scatteredDir)) {
            scatteredDir = h.hRec.normal;
        }

        scattered = new Ray(h.hRec.point, scatteredDir, input.getTime());
        attenuation = albedo.value(h.hRec.u, h.hRec.v, h.hRec.point);
        return new MaterialInfo(scattered, attenuation, h, true);
    }
}
