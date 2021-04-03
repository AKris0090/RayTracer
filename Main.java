import ImageCreators.*;
import ImageCreators.Materials.Material;
import Vector.Camera;
import Vector.Vector3D;

import java.io.IOException;


public class Main {

    //10.0

    public static void main(String[] args) throws IOException {
        Camera camera = new Camera(new Vector3D(0, 0, 0), 4.0, 2.0, 1.0);

        //IMAGE SET UP
        int width = 1280; //OR CHOOSE 1280 FOR MORE DETAIL
        double invAspectRatio = 8.0f / 16.0f;
        int height = (int) (width * invAspectRatio);
        PPMFileMaker ppm = new PPMFileMaker(width, height);

        //WORLD SET UP
        Hittable h = new Hittable();
        h.worldObjects.add(new Sphere(0.5f, new Vector3D(0, 0, -1)));
        h.worldObjects.add(new Sphere(100, new Vector3D(0, (float) -100.5, -1)));
        int samplesPerPixel = 100;
        int numBounces = 50;

        //BASE IMAGE
        BaseImage baseImage = new BaseImage(height, width, ppm);
        baseImage.initFirstImage();

        //GRADIENT AND RED CIRCLE (RADIUS 0.5)
        GradientAndRedCircle gradientCircleImage = new GradientAndRedCircle(height, width, ppm, camera);
        gradientCircleImage.initGradientImage();

        //COLORED NORMALS AND GROUND
        ColoredNormals coloredNormals = new ColoredNormals(height, width, ppm, camera, h);
        coloredNormals.initColoredNormalsImage();

        //ANTIALIASED COLORED NORMALS AND GROUND
        AntiAliasedColoredNormals antiAliasedColoredNormals = new AntiAliasedColoredNormals(height, width, ppm, camera, h, samplesPerPixel);
        antiAliasedColoredNormals.initAntiAliasedColoredNormalsImage();

        //DIFFUSED SPHERE WITH GROUND
        DiffuseImage diffuseImage = new DiffuseImage(height, width, ppm, camera, h, samplesPerPixel, numBounces);
        diffuseImage.initDiffuseImage();

        //GAMMA CORRECTED DIFFUSE SPHERE WITH GROUND + SHADOW ACNE FIX
        GammaCorrectedDiffuseSphere gammaCorrectedDiffuseSphere = new GammaCorrectedDiffuseSphere(height, width, ppm, camera, h, samplesPerPixel, numBounces);
        gammaCorrectedDiffuseSphere.initGammaCorrectedDiffuseImage();

        //LAMBERTIAN CORRECTED DIFFUSE SPHERE + HEMISPHERICAL SCATTERING
        TrueLambReflection trueLambReflection = new TrueLambReflection(height, width, ppm, camera, h, samplesPerPixel, numBounces);
        trueLambReflection.initLambertianGammaCorrectedDiffuseImage();

        //METAL SPHERES WITH LAMB SPHERE IN CENTER
        Material ground = new Material(new Vector3D(0.8f, 0.8f, 0.0f), "lambertian");
        Material centerSphere = new Material(new Vector3D(0.7f, 0.3f, 0.3f), "lambertian");
        Material leftSphere = new Material(new Vector3D(0.8f, 0.8f, 0.8f), "metal");
        Material rightSphere = new Material(new Vector3D(0.8f, 0.6f, 0.2f), "metal");

        h.worldObjects.clear();
        h.worldObjects.add(new Sphere(0.5f, new Vector3D(0.0f, 0.0f, -1.0f), centerSphere));
        h.worldObjects.add(new Sphere(0.5f, new Vector3D(-1.0f, 0.0f, -1.0f), leftSphere));
        h.worldObjects.add(new Sphere(0.5f, new Vector3D(1.0f, 0.0f, -1.0f), rightSphere));
        h.worldObjects.add(new Sphere(100.0f, new Vector3D(0.0f, -100.5f, -1.0f), ground));
        MetalLambMetal metalLambMetal = new MetalLambMetal(height, width, ppm, camera, h, samplesPerPixel, numBounces);
        metalLambMetal.initMetalSpheresImage();

        //FUZZY REFLECTIONS (METAL SPHERE)
        ground = new Material(new Vector3D(0.8f, 0.8f, 0.0f), "lambertian");
        centerSphere = new Material(new Vector3D(0.7f, 0.3f, 0.3f), "lambertian");
        leftSphere = new Material(new Vector3D(0.8f, 0.8f, 0.8f), 0.3, "metal");
        rightSphere = new Material(new Vector3D(0.8f, 0.6f, 0.2f), 0.3, "metal");

        h.worldObjects.clear();
        h.worldObjects.add(new Sphere(0.5f, new Vector3D(0.0f, 0.0f, -1.0f), centerSphere));
        h.worldObjects.add(new Sphere(0.5f, new Vector3D(-1.0f, 0.0f, -1.0f), leftSphere));
        h.worldObjects.add(new Sphere(0.5f, new Vector3D(1.0f, 0.0f, -1.0f), rightSphere));
        h.worldObjects.add(new Sphere(100.0f, new Vector3D(0.0f, -100.5f, -1.0f), ground));
        FuzzyReflections fuzzyReflections = new FuzzyReflections(height, width, ppm, camera, h, samplesPerPixel, numBounces);
        fuzzyReflections.initFuzzyReflectionsImage();
    }
}
