/*
 * Copyright (c) 2021.
 *
 * Arjun Krishnan 10/31/2021
 * See my other coding projects at: akrishnan.netlify.app
 * Questions, email me at: artk0090@gmail.com
 */

package SceneGenerator;

import ImageCreators.*;
import ImageCreators.Materials.DiffuseLight;
import ImageCreators.Materials.Lambertian;
import ImageCreators.Materials.Material;
import Vector.Vector3D;

public class CornellBox {

    public CornellBox(){
    }

    public Hittable generateScene(){
        Hittable obj = new Hittable();

        Material red = new Lambertian(new Vector3D(0.65f, 0.05f, 0.05f));
        Material white = new Lambertian(new Vector3D(0.73f, 0.73f, 0.73f));
        Material green = new Lambertian(new Vector3D(0.12f, 0.45f, 0.15f));
        Material light = new DiffuseLight(new Vector3D(15, 15, 15));

        obj.worldObjects.add(0, new Box(new Vector3D(130, 0, 65), new Vector3D(295, 165, 230), white));
        obj.worldObjects.add(0, new Box(new Vector3D(265, 0, 295), new Vector3D(430, 330, 460), white));
        obj.worldObjects.add(0, new YZRect(0, 555, 0, 555, 555, green));
        obj.worldObjects.add(0, new YZRect(0, 555, 0, 555, 0, red));
        obj.worldObjects.add(0, new XZRect(213, 343, 227, 332, 554, light));

        
        obj.worldObjects.add(0, new XZRect(0, 555, 0, 555, 0, white));
        obj.worldObjects.add(0, new XZRect(0, 555, 0, 555, 555, white));
        obj.worldObjects.add(0, new XYRect(0, 555, 0, 555, 555, white));

        obj.worldObjects.add(0, new Box(new Vector3D(130, 0, 65), new Vector3D(295, 165, 230), white));
        obj.worldObjects.add(0, new Box(new Vector3D(265, 0, 295), new Vector3D(430, 330, 460), white));

        return obj;
    }
}
