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
import Vector.Vector3D;

public interface Material {
    default Vector3D emitted(double u, double v, Vector3D p){
        return new Vector3D(0.0f, 0.0f, 0.0f);
    }

    MaterialInfo scatter(Ray input, Hittable h, Vector3D attenuation, Ray scattered);
}
