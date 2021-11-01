/*
 * Copyright (c) 2021.
 *
 * Arjun Krishnan 10/31/2021
 * See my other coding projects at: akrishnan.netlify.app
 * Questions, email me at: artk0090@gmail.com
 */

package SceneGenerator;

import ImageCreators.Hittable;
import ImageCreators.Materials.Dielectric;
import ImageCreators.Materials.Lambertian;
import ImageCreators.Materials.Material;
import ImageCreators.Materials.Metal;
import ImageCreators.MovingSphere;
import ImageCreators.Sphere;
import Textures.CheckerBoard;
import Vector.Vector3D;
import Vector.VectorMath;

public class RandomMovingSpheres {

    private final VectorMath vm = new VectorMath();
    private final int version;

    public RandomMovingSpheres(int version){
        this.version = version;
    }

    public Hittable randomScene(){
        Hittable world = new Hittable();

        for (int i = -11; i < 11; i++) {
            for (int j = -11; j < 11; j++) {
                double chooseMaterial = Math.random();
                Vector3D center = new Vector3D((float) (i + 0.9 * Math.random()), 0.2f, (float) (j + 0.9 * Math.random()));

                if ((vm.sub(center, new Vector3D(4.0f, 0.2f, 0.0f)).getNormalLength()) > 0.9){
                    if (chooseMaterial < 0.8){
                        //DIFFUSE
                        Vector3D albedo = vm.basicMultiply(new Vector3D((float) Math.random(), (float) Math.random(), (float) Math.random()), new Vector3D((float) Math.random(), (float) Math.random(), (float) Math.random()));
                        Material sphereMat = new Lambertian(albedo);
                        Vector3D center2 = vm.add(center, new Vector3D(0.0f, (float) vm.randomDouble(0, 0.5), 0.0f));
                        world.worldObjects.add(0, new MovingSphere(center, center2, 0.0, 1.0, 0.2, sphereMat));
                    } else if (chooseMaterial < 0.95){
                        //METAL
                        Vector3D albedo = new Vector3D((float) vm.randomDouble(0.5, 1), (float) vm.randomDouble(0.5, 1), (float) vm.randomDouble(0.5, 1));
                        double fuzz = vm.randomDouble(0, 0.5);
                        Material sphereMat = new Metal(albedo, fuzz);
                        world.worldObjects.add(0, new Sphere(0.2f, center, sphereMat));
                    } else {
                        //GLASS
                        Material sphereMat = new Dielectric(1.5);
                        world.worldObjects.add(0, new Sphere(0.2f, center, sphereMat));
                    }
                }
            }
        }

        Material ground = new Lambertian(new Vector3D(0.5f, 0.5f, 0.5f));
        if (this.version > 1){
            CheckerBoard c = new CheckerBoard(new Vector3D(0.2f, 0.3f, 0.1f), new Vector3D(0.9f, 0.9f, 0.9f));
            world.worldObjects.add(new Sphere(1000, new Vector3D(0.0f, -1000.0f, 0.0f), c));
        } else {
            world.worldObjects.add(new Sphere(1000, new Vector3D(0.0f, -1000.0f, 0.0f), ground));
        }

        Material mat2 = new Lambertian(new Vector3D(0.4f, 0.2f, 0.1f));
        world.worldObjects.add(0, new Sphere(1.0f, new Vector3D(-4.0f, 1.0f, 0.0f), mat2));

        Material mat1 = new Dielectric(1.5);
        world.worldObjects.add(0, new Sphere(1.0f, new Vector3D(0.0f, 1.0f, 0.0f), mat1));

        Material mat3 = new Metal(new Vector3D(0.8f, 0.8f, 0.8f), 0.0);
        world.worldObjects.add(0, new Sphere(1.0f, new Vector3D(4.0f, 1.0f, 0.0f), mat3));

        return world;
    }
}
