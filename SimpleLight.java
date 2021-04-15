package SceneGenerator;

import ImageCreators.Hittable;
import ImageCreators.Materials.DiffuseLight;
import ImageCreators.Materials.Material;
import ImageCreators.Sphere;
import ImageCreators.XYRect;
import Textures.PerlinNoise;
import Textures.Texture;
import Vector.Vector3D;

public class SimpleLight {

    private final double scale;

    public SimpleLight(double scale){
        this.scale = scale;
    }

    public Hittable generateScene(){
        Hittable obj = new Hittable();
        Texture perText = new PerlinNoise(5, scale);

        obj.worldObjects.add(new Sphere(1000, new Vector3D(0.0f, -1000.0f, 0.0f), perText));
        obj.worldObjects.add(0, new Sphere(2, new Vector3D(0.0f, 2.0f, 0.0f), perText));

        Material diffLight = new DiffuseLight(new Vector3D(4.0f, 4.0f, 4.0f));
        obj.worldObjects.add(new XYRect(3, 5, 1, 3, -2, diffLight));
        obj.worldObjects.add(new Sphere(2, new Vector3D(0.0f, 7.0f, 0.0f), diffLight));

        return obj;
    }
}
