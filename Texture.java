package Textures;

import Vector.Vector3D;

public interface Texture {

    Vector3D value(double u, double v, Vector3D p);
}
