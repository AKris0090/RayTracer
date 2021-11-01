/*
 * Copyright (c) 2021.
 *
 * Arjun Krishnan 10/31/2021
 * See my other coding projects at: akrishnan.netlify.app
 * Questions, email me at: artk0090@gmail.com
 */

package Textures;

import Vector.Vector3D;

public interface Texture {

    Vector3D value(double u, double v, Vector3D p);
}
