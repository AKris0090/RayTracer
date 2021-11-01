/*
 * Copyright (c) 2021.
 *
 * Arjun Krishnan 10/31/2021
 * See my other coding projects at: akrishnan.netlify.app
 * Questions, email me at: artk0090@gmail.com
 */

package SceneGenerator;

import ImageCreators.Hittable;
import ImageCreators.Sphere;
import Textures.CheckerBoard;
import Textures.Texture;
import Vector.Vector3D;

public class TwoSpheres {

    public TwoSpheres(){
    }

    public Hittable generateScene(){
        Hittable obj = new Hittable();
        Texture checker = new CheckerBoard(new Vector3D(0.2f, 0.3f, 0.1f), new Vector3D(0.9f, 0.9f, 0.9f));

        obj.worldObjects.add(new Sphere(10, new Vector3D(0.0f, -10.0f, 0.0f), checker));
        obj.worldObjects.add(0, new Sphere(10, new Vector3D(0.0f, 10.0f, 0.0f), checker));

        return obj;
    }
}
