import ImageCreators.*;
import Vector.Camera;
import Vector.Vector3D;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        Camera camera = new Camera(new Vector3D(0, 0, 0), 4.0, 2.0, 1.0);

        //IMAGE SET UP
        int width = 1280;
        double invAspectRatio = 8.0f / 16.0f;
        int height = (int) (width * invAspectRatio);
        PPMFileMaker ppm = new PPMFileMaker(width, height);

        //WORLD SET UP
        Hittable h = new Hittable();
        h.worldObjects.add(new Sphere(0.5f, new Vector3D(0, 0, -1)));
        h.worldObjects.add(new Sphere(100, new Vector3D(0, -101, -1)));
        int samplesPerPixel = 100;
        int numBounces = 50;

//        //BASE IMAGE
//        BaseImage baseImage = new BaseImage(height, width, ppm);
//        baseImage.initFirstImage();
//
//        //GRADIENT AND RED CIRCLE (RADIUS 0.5)
//        GradientAndRedCircle gradientCircleImage = new GradientAndRedCircle(height, width, ppm, camera);
//        gradientCircleImage.initGradientImage();
//
//        //COLORED NORMALS AND GROUND
//        ColoredNormals coloredNormals = new ColoredNormals(height, width, ppm, camera, h);
//        coloredNormals.initColoredNormalsImage();
//
//        //ANTIALIASED COLORED NORMALS AND GROUND
//        AntiAliasedColoredNormals antiAliasedColoredNormals = new AntiAliasedColoredNormals(height, width, ppm, camera, h, samplesPerPixel);
//        antiAliasedColoredNormals.initAntiAliasedColoredNormalsImage();

        DiffuseImage diffuseImage = new DiffuseImage(height, width, ppm, camera, h, samplesPerPixel, numBounces);
        diffuseImage.initDiffuseImage();
    }
}
