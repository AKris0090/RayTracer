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
import Vector.Vector3D;

public interface HittableObject {
    Vector3D attenuation = new Vector3D();
    Ray scattered = new Ray();

    boolean checkHit(Ray r, double tMin, double tMax, Hittable.hitRecord hRec);
    retObject boundingBox(double tim0, double tim1, AABB outputBox);
}
