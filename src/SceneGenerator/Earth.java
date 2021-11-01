/*
 * Copyright (c) 2021.
 *
 * Arjun Krishnan 10/31/2021
 * See my other coding projects at: akrishnan.netlify.app
 * Questions, email me at: artk0090@gmail.com
 */

package SceneGenerator;

import ImageCreators.Hittable;
import ImageCreators.Materials.Lambertian;
import ImageCreators.Materials.Material;
import ImageCreators.Sphere;
import Textures.ImageTexture;
import Textures.Texture;
import Vector.Vector3D;

public class Earth {

    public String fileName;

    public Earth(String fileName){
        this.fileName = fileName;
    }

    public Hittable generateScene(){
        Hittable obj = new Hittable();
        Texture img = new ImageTexture(fileName);

        Material ground = new Lambertian(new Vector3D(0.5f, 0.5f, 0.5f));
        obj.worldObjects.add(new Sphere(2, new Vector3D(0.0f, 0.0f, 0.0f), img));

        return obj;
    }
}
