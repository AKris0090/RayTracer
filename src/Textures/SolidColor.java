/*
 * Copyright (c) 2021.
 *
 * Arjun Krishnan 10/31/2021
 * See my other coding projects at: akrishnan.netlify.app
 * Questions, email me at: artk0090@gmail.com
 */

package Textures;

import Vector.Vector3D;

public class SolidColor implements Texture{

    private Vector3D colorVal;

    public SolidColor(){
    }

    public SolidColor(Vector3D c){
        this.colorVal = c;
    }

    public SolidColor(double red, double green, double blue){
        this.colorVal = new Vector3D((float) red, (float) green, (float) blue);
    }

    @Override
    public Vector3D value(double u, double v, Vector3D p) {
        return this.colorVal;
    }
}
