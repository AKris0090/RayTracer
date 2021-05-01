package SceneGenerator;

import ImageCreators.Hittable;
import ImageCreators.Materials.Lambertian;
import ImageCreators.Materials.Material;
import ImageCreators.Sphere;
import Textures.PerlinNoise;
import Textures.Texture;
import Vector.Vector3D;

public class NoiseTwoSpheres {

    private final int version;
    private final double scale;

    public NoiseTwoSpheres(int version, double scale){
        this.version = version;
        this.scale = scale;
    }

    public Hittable generateScene(){
        if (this.version == 1) {
            Hittable obj = new Hittable();
            Texture perlNoise = new PerlinNoise(1, scale);

            Material ground = new Lambertian(new Vector3D(0.5f, 0.5f, 0.5f));
            obj.worldObjects.add(new Sphere(1000, new Vector3D(0.0f, -1000.0f, 0.0f), perlNoise));
            obj.worldObjects.add(0, new Sphere(2, new Vector3D(0.0f, 2.0f, 0.0f), perlNoise));

            return obj;
        } else if (version == 2){
            Hittable obj = new Hittable();
            Texture perlNoise = new PerlinNoise(2, scale);

            Material ground = new Lambertian(new Vector3D(0.5f, 0.5f, 0.5f));
            obj.worldObjects.add(new Sphere(1000, new Vector3D(0.0f, -1000.0f, 0.0f), perlNoise));
            obj.worldObjects.add(0, new Sphere(2, new Vector3D(0.0f, 2.0f, 0.0f), perlNoise));

            return obj;
        } else if (version == 3){
            Hittable obj = new Hittable();
            Texture perlNoise = new PerlinNoise(3, scale);

            Material ground = new Lambertian(new Vector3D(0.5f, 0.5f, 0.5f));
            obj.worldObjects.add(new Sphere(1000, new Vector3D(0.0f, -1000.0f, 0.0f), perlNoise));
            obj.worldObjects.add(0, new Sphere(2, new Vector3D(0.0f, 2.0f, 0.0f), perlNoise));

            return obj;
        } else if (version == 4){
            Hittable obj = new Hittable();
            Texture perlNoise = new PerlinNoise(4, scale);

            Material ground = new Lambertian(new Vector3D(0.5f, 0.5f, 0.5f));
            obj.worldObjects.add(new Sphere(1000, new Vector3D(0.0f, -1000.0f, 0.0f), perlNoise));
            obj.worldObjects.add(0, new Sphere(2, new Vector3D(0.0f, 2.0f, 0.0f), perlNoise));

            return obj;
        } else {
            Hittable obj = new Hittable();
            Texture perlNoise = new PerlinNoise(5, scale);

            Material ground = new Lambertian(new Vector3D(0.5f, 0.5f, 0.5f));
            obj.worldObjects.add(new Sphere(1000, new Vector3D(0.0f, -1000.0f, 0.0f), perlNoise));
            obj.worldObjects.add(0, new Sphere(2, new Vector3D(0.0f, 2.0f, 0.0f), perlNoise));

            return obj;
        }
    }
}
