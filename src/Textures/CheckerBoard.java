/*
 * Copyright (c) 2021.
 *
 * Arjun Krishnan 10/31/2021
 * See my other coding projects at: akrishnan.netlify.app
 * Questions, email me at: artk0090@gmail.com
 */

package Textures;

import Vector.Vector3D;

public class CheckerBoard implements Texture {

    public Texture even;
    public Texture odd;

    public CheckerBoard(){
    }

    public CheckerBoard(Texture even, Texture odd){
        this.even = even;
        this.odd = odd;
    }

    public CheckerBoard(Vector3D c1, Vector3D c2){
        this.even = new SolidColor(c1);
        this.odd = new SolidColor(c2);
    }

    @Override
    public Vector3D value(double u, double v, Vector3D p) {
        double sines = Math.sin(10 * p.getX()) * Math.sin(10 * p.getY()) * Math.sin(10 * p.getZ());
        if (sines < 0){
            return odd.value(u, v, p);
        } else {
            return even.value(u, v, p);
        }
    }
}
