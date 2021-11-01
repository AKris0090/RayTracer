/*
 * Copyright (c) 2021.
 *
 * Arjun Krishnan 10/31/2021
 * See my other coding projects at: akrishnan.netlify.app
 * Questions, email me at: artk0090@gmail.com
 */

import ImageCreators.*;
import ImageCreators.Materials.Dielectric;
import ImageCreators.Materials.Lambertian;
import ImageCreators.Materials.Material;
import ImageCreators.Materials.Metal;
import SceneGenerator.*;
import Vector.Camera;
import Vector.Vector3D;
import Vector.VectorMath;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        Camera camera = new Camera(new Vector3D(0, 0, 0), 4.0, 2.0, 1.0);
        VectorMath vm = new VectorMath();

        //IMAGE SET UP
        int width = 400; //OR CHOOSE 1280 FOR MORE DETAIL
        double aspectRatio = 16.0f / 8.0f;
        double invAspectRatio = 8.0f / 16.0f;
        int height = (int) (width * invAspectRatio);
        PPMFileMaker ppm = new PPMFileMaker(width, height);

        //WORLD SET UP
        Hittable h = new Hittable();
        h.worldObjects.add(new Sphere(0.5f, new Vector3D(0, 0, -1)));
        h.worldObjects.add(new Sphere(100, new Vector3D(0, (float) -100.5, -1)));
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
//
//        //DIFFUSED SPHERE WITH GROUND
//        DiffuseImage diffuseImage = new DiffuseImage(height, width, ppm, camera, h, samplesPerPixel, numBounces);
//        diffuseImage.initDiffuseImage();
//
//        //GAMMA CORRECTED DIFFUSE SPHERE WITH GROUND + SHADOW ACNE FIX
//        GammaCorrectedDiffuseSphere gammaCorrectedDiffuseSphere = new GammaCorrectedDiffuseSphere(height, width, ppm, camera, h, samplesPerPixel, numBounces);
//        gammaCorrectedDiffuseSphere.initGammaCorrectedDiffuseImage();
//
//        //LAMBERTIAN CORRECTED DIFFUSE SPHERE + HEMISPHERICAL SCATTERING
//        TrueLambReflection trueLambReflection = new TrueLambReflection(height, width, ppm, camera, h, samplesPerPixel, numBounces);
//        trueLambReflection.initLambertianGammaCorrectedDiffuseImage();

        //METAL SPHERES WITH LAMB SPHERE IN CENTER
        Material ground = new Lambertian(new Vector3D(0.8f, 0.8f, 0.0f));
        Material centerSphere = new Lambertian(new Vector3D(0.7f, 0.3f, 0.3f));
        Material leftSphere = new Metal(new Vector3D(0.8f, 0.8f, 0.8f));
        Material rightSphere = new Metal(new Vector3D(0.8f, 0.6f, 0.2f));

        h.worldObjects.clear();
        h.worldObjects.add(new Sphere(0.5f, new Vector3D(0.0f, 0.0f, -1.0f), centerSphere));
        h.worldObjects.add(new Sphere(0.5f, new Vector3D(-1.0f, 0.0f, -1.0f), leftSphere));
        h.worldObjects.add(new Sphere(0.5f, new Vector3D(1.0f, 0.0f, -1.0f), rightSphere));
        h.worldObjects.add(new Sphere(100.0f, new Vector3D(0.0f, -100.5f, -1.0f), ground));
        MetalLambMetal metalLambMetal = new MetalLambMetal(height, width, ppm, camera, h, samplesPerPixel, numBounces);
        metalLambMetal.initMetalSpheresImage();

//        //FUZZY REFLECTIONS (METAL SPHERE)
//        ground = new Lambertian(new Vector3D(0.8f, 0.8f, 0.0f));
//        centerSphere = new Lambertian(new Vector3D(0.7f, 0.3f, 0.3f));
//        leftSphere = new Metal(new Vector3D(0.8f, 0.8f, 0.8f), 0.3);
//        rightSphere = new Metal(new Vector3D(0.8f, 0.6f, 0.2f), 0.3);
//
//        h.worldObjects.clear();
//        h.worldObjects.add(new Sphere(0.5f, new Vector3D(0.0f, 0.0f, -1.0f), centerSphere));
//        h.worldObjects.add(new Sphere(0.5f, new Vector3D(-1.0f, 0.0f, -1.0f), leftSphere));
//        h.worldObjects.add(new Sphere(0.5f, new Vector3D(1.0f, 0.0f, -1.0f), rightSphere));
//        h.worldObjects.add(new Sphere(100.0f, new Vector3D(0.0f, -100.5f, -1.0f), ground));
//        FuzzyReflections fuzzyReflections = new FuzzyReflections(height, width, ppm, camera, h, samplesPerPixel, numBounces);
//        fuzzyReflections.initFuzzyReflectionsImage();
//
//        //GLASS SPHERES
//        ground = new Lambertian(new Vector3D(0.8f, 0.8f, 0.0f));
//        centerSphere = new Lambertian(new Vector3D(0.1f, 0.2f, 0.5f));
//        leftSphere = new Dielectric(1.5);
//        rightSphere = new Metal(new Vector3D(0.8f, 0.6f, 0.2f), 0.0);
//
//        h.worldObjects.clear();
//        h.worldObjects.add(new Sphere(0.5f, new Vector3D(0.0f, 0.0f, -1.0f), centerSphere));
//        h.worldObjects.add(new Sphere(0.5f, new Vector3D(-1.0f, 0.0f, -1.0f), leftSphere));
//        h.worldObjects.add(new Sphere(0.5f, new Vector3D(1.0f, 0.0f, -1.0f), rightSphere));
//        h.worldObjects.add(new Sphere(100.0f, new Vector3D(0.0f, -100.5f, -1.0f), ground));
//        FullGlassSpheres glassSpheres = new FullGlassSpheres(height, width, ppm, camera, h, samplesPerPixel, numBounces, 1);
//        glassSpheres.initGlassSpheresImage();
//
//        //DIFF PERSPECTIVE
//        camera = new Camera(new Vector3D(0, 0, 0), 1.0, 90.0, aspectRatio, 1);
//        double r = Math.cos(Math.PI / 4.0);
//        Material leftOne = new Lambertian(new Vector3D(0.0f, 0.0f, 1.0f));
//        Material rightOne = new Lambertian(new Vector3D(1.0f, 0.0f, 0.0f));
//
//        h.worldObjects.clear();
//        h.worldObjects.add(new Sphere((float) r, new Vector3D((float)(-r), 0.0f, -1.0f), leftOne));
//        h.worldObjects.add(new Sphere((float) r, new Vector3D((float) (r), 0.0f, -1.0f), rightOne));
//        FullGlassSpheres glassSpheres1 = new FullGlassSpheres(height, width, ppm, camera, h, samplesPerPixel, numBounces, 2);
//        glassSpheres1.initGlassSpheresImage();
//
//        //DIFF PERSPECTIVE SCENE NUMBER 2
//        Camera camera2 = new Camera(new Vector3D(-2.0f, 2.0f, 1.0f), new Vector3D(0.0f, 0.0f, -1.0f), new Vector3D(0.0f, 1.0f, 0.0f), 40, aspectRatio, 2);
//
//        ground = new Lambertian(new Vector3D(0.8f, 0.8f, 0.0f));
//        centerSphere = new Lambertian(new Vector3D(0.1f, 0.2f, 0.5f));
//        leftSphere = new Dielectric(1.5);
//        rightSphere = new Metal(new Vector3D(0.8f, 0.6f, 0.2f), 0.0);
//
//        h.worldObjects.clear();
//        h.worldObjects.add(new Sphere(0.5f, new Vector3D(0.0f, 0.0f, -1.0f), centerSphere));
//        h.worldObjects.add(new Sphere(0.5f, new Vector3D(-1.0f, 0.0f, -1.0f), leftSphere));
//        h.worldObjects.add(new Sphere(0.5f, new Vector3D(1.0f, 0.0f, -1.0f), rightSphere));
//        h.worldObjects.add(new Sphere(100.0f, new Vector3D(0.0f, -100.5f, -1.0f), ground));
//
//        FullGlassSpheres glassSpheres2 = new FullGlassSpheres(height, width, ppm, camera2, h, samplesPerPixel, numBounces, 3);
//        glassSpheres2.initGlassSpheresImage();

        //DEFOCUS BLUR
        Vector3D lookFrom = new Vector3D(3.0f, 3.0f, 2.0f);
        Vector3D lookAt = new Vector3D(0.0f, 0.0f, -1.0f);
        Vector3D vUp = new Vector3D(0.0f, 1.0f, 0.0f);
        double distToFocus = vm.sub(lookFrom, lookAt).getNormalLength();
        double aperture = 2.0;
        Camera camera3 = new Camera(lookFrom, lookAt, vUp, 20, aspectRatio, aperture, distToFocus, 3);

        ground = new Lambertian(new Vector3D(0.8f, 0.8f, 0.0f));
        centerSphere = new Lambertian(new Vector3D(0.1f, 0.2f, 0.5f));
        leftSphere = new Dielectric(1.5);
        rightSphere = new Metal(new Vector3D(0.8f, 0.6f, 0.2f), 0.0);

        h.worldObjects.clear();
        h.worldObjects.add(new Sphere(0.5f, new Vector3D(0.0f, 0.0f, -1.0f), centerSphere));
        h.worldObjects.add(new Sphere(0.5f, new Vector3D(-1.0f, 0.0f, -1.0f), leftSphere));
        h.worldObjects.add(new Sphere(0.5f, new Vector3D(1.0f, 0.0f, -1.0f), rightSphere));
        h.worldObjects.add(new Sphere(100.0f, new Vector3D(0.0f, -100.5f, -1.0f), ground));

        FullGlassSpheres glassSpheres3 = new FullGlassSpheres(height, width, ppm, camera3, h, samplesPerPixel, numBounces, 4);
        glassSpheres3.initGlassSpheresImage();

//        //FINAL SCENE
//        width = 400; //CHANGE TO 1200
//        height = (int) (width * invAspectRatio);
//        samplesPerPixel = 100; //CHANGE TO 500
//
//        lookFrom = new Vector3D(13.0f, 2.0f, 3.0f);
//        lookAt = new Vector3D(0.0f, 0.0f, 0.0f);
//        vUp = new Vector3D(0.0f, 1.0f, 0.0f);
//        distToFocus = 10.0;
//        aperture = 0.1;
//        Camera camera4 = new Camera(lookFrom, lookAt, vUp, 20, aspectRatio, aperture, distToFocus, 3);
//
//        RandomSpheres randomSpheres = new RandomSpheres();
//        Hittable h1 = randomSpheres.randomScene();
//
//        Final f = new Final(height, width, ppm, camera4, h1, samplesPerPixel, numBounces, 1);
//        f.initFinalImage(new Vector3D(0.7f, 0.8f, 1.0f));
//
//        //FINAL SCENE WITH MOVING SPHERES
//        Camera camera5 = new Camera(lookFrom, lookAt, vUp, 20, aspectRatio, aperture, distToFocus, 0.0, 1.0, 4);
//
//        RandomMovingSpheres randomMovingSpheres = new RandomMovingSpheres(1);
//        Hittable h2 = randomMovingSpheres.randomScene();
//
//        Final f1 = new Final(height, width, ppm, camera5, h2, samplesPerPixel, numBounces, 2);
//        f1.initFinalImage(new Vector3D(0.7f, 0.8f, 1.0f));
//
//        //FINAL SCENE WITH MOVING SPHERES AND CHECKER BOARD
//        Camera camera6 = new Camera(lookFrom, lookAt, vUp, 20, aspectRatio, aperture, distToFocus, 0.0, 1.0, 4);
//
//        RandomMovingSpheres randomMovingSpheres2 = new RandomMovingSpheres(2);
//        Hittable h3 = randomMovingSpheres2.randomScene();
//
//        Final f2 = new Final(height, width, ppm, camera6, h3, samplesPerPixel, numBounces, 3);
//        f2.initFinalImage(new Vector3D(0.7f, 0.8f, 1.0f));
//
//        //TWO SPHERE CHECKER SCENE
//        Camera camera7 = new Camera(lookFrom, lookAt, vUp, 20, aspectRatio, aperture, distToFocus, 0.0, 1.0, 4);
//
//        TwoSpheres twoSpheres = new TwoSpheres();
//        Hittable h4 = twoSpheres.generateScene();
//
//        Final f3 = new Final(height, width, ppm, camera7, h4, samplesPerPixel, numBounces, 4);
//        f3.initFinalImage(new Vector3D(0.7f, 0.8f, 1.0f));
//
//        //TWO SPHERE PERLIN NOISE SCENE
//        width = 400;
//        height = (int) (width * invAspectRatio);
//        samplesPerPixel = 100;
//
//        lookFrom = new Vector3D(13.0f, 2.0f, 3.0f);
//        lookAt = new Vector3D(0.0f, 0.0f, 0.0f);
//        vUp = new Vector3D(0.0f, 1.0f, 0.0f);
//        distToFocus = 10.0;
//        aperture = 0.1;
//        Camera camera8 = new Camera(lookFrom, lookAt, vUp, 20, aspectRatio, aperture, distToFocus, 0.0, 1.0, 4);
//
//        NoiseTwoSpheres noiseTwoSpheres = new NoiseTwoSpheres(1, 1.0);
//        Hittable h5 = noiseTwoSpheres.generateScene();
//
//        Final f4 = new Final(height, width, ppm, camera8, h5, samplesPerPixel, numBounces, 5);
//        f4.initFinalImage(new Vector3D(0.7f, 0.8f, 1.0f));
//
//        //SMOOTHENED TWO SPHERE PERLIN NOISE SCENE + HERMITIAN SMOOTHING + SCALING
//        width = 400;
//        height = (int) (width * invAspectRatio);
//        samplesPerPixel = 100;
//
//        lookFrom = new Vector3D(13.0f, 2.0f, 3.0f);
//        lookAt = new Vector3D(0.0f, 0.0f, 0.0f);
//        vUp = new Vector3D(0.0f, 1.0f, 0.0f);
//        distToFocus = 10.0;
//        aperture = 0.1;
//        Camera camera9 = new Camera(lookFrom, lookAt, vUp, 20, aspectRatio, aperture, distToFocus, 0.0, 1.0, 4);
//
//        NoiseTwoSpheres noiseTwoSpheres2 = new NoiseTwoSpheres(2, 4.0);
//        Hittable h6 = noiseTwoSpheres2.generateScene();
//
//        Final f5 = new Final(height, width, ppm, camera9, h6, samplesPerPixel, numBounces, 6);
//        f5.initFinalImage(new Vector3D(0.7f, 0.8f, 1.0f));
//
//        //SHIFTED INTEGER VALUE PERLIN NOISE
//        width = 400;
//        height = (int) (width * invAspectRatio);
//        samplesPerPixel = 100;
//
//        lookFrom = new Vector3D(13.0f, 2.0f, 3.0f);
//        lookAt = new Vector3D(0.0f, 0.0f, 0.0f);
//        vUp = new Vector3D(0.0f, 1.0f, 0.0f);
//        distToFocus = 10.0;
//        aperture = 0.1;
//        Camera camera10 = new Camera(lookFrom, lookAt, vUp, 20, aspectRatio, aperture, distToFocus, 0.0, 1.0, 4);
//
//        NoiseTwoSpheres noiseTwoSpheres1 = new NoiseTwoSpheres(3, 4.0);
//        Hittable h7 = noiseTwoSpheres1.generateScene();
//
//        Final f6 = new Final(height, width, ppm, camera10, h7, samplesPerPixel, numBounces, 7);
//        f6.initFinalImage(new Vector3D(0.7f, 0.8f, 1.0f));
//
//        //TURBULENCE PERLIN NOISE
//        width = 400;
//        height = (int) (width * invAspectRatio);
//        samplesPerPixel = 100;
//
//        lookFrom = new Vector3D(13.0f, 2.0f, 3.0f);
//        lookAt = new Vector3D(0.0f, 0.0f, 0.0f);
//        vUp = new Vector3D(0.0f, 1.0f, 0.0f);
//        distToFocus = 10.0;
//        aperture = 0.1;
//        Camera camera11 = new Camera(lookFrom, lookAt, vUp, 20, aspectRatio, aperture, distToFocus, 0.0, 1.0, 4);
//
//        NoiseTwoSpheres noiseTwoSpheres3 = new NoiseTwoSpheres(4, 4.0);
//        Hittable h8 = noiseTwoSpheres3.generateScene();
//
//        Final f7 = new Final(height, width, ppm, camera11, h8, samplesPerPixel, numBounces, 8);
//        f7.initFinalImage(new Vector3D(0.7f, 0.8f, 1.0f));
//
//        //MARBLE SPHERES
//        width = 400;
//        height = (int) (width * invAspectRatio);
//        samplesPerPixel = 100;
//
//        lookFrom = new Vector3D(13.0f, 2.0f, 3.0f);
//        lookAt = new Vector3D(0.0f, 0.0f, 0.0f);
//        vUp = new Vector3D(0.0f, 1.0f, 0.0f);
//        distToFocus = 10.0;
//        aperture = 0.1;
//        Camera camera12 = new Camera(lookFrom, lookAt, vUp, 20, aspectRatio, aperture, distToFocus, 0.0, 1.0, 4);
//
//        NoiseTwoSpheres noiseTwoSpheres4 = new NoiseTwoSpheres(5, 4.0);
//        Hittable h9 = noiseTwoSpheres4.generateScene();
//
//        Final f8 = new Final(height, width, ppm, camera12, h9, samplesPerPixel, numBounces, 9);
//        f8.initFinalImage(new Vector3D(0.7f, 0.8f, 1.0f));
//
        //EARTH SPHERE - WORK ON THIS LATER WHEN YOU GIVE A DAMN
        width = 400;
        height = (int) (width * invAspectRatio);
        samplesPerPixel = 100;

        lookFrom = new Vector3D(13.0f, 3.0f, -2.0f);
//        lookFrom = new Vector3D(13.0f, 13.0f, 0.0f);
        lookAt = new Vector3D(0.0f, 0.0f, 0.0f);
        vUp = new Vector3D(0.0f, 1.0f, 0.0f);
        distToFocus = 10.0;
        aperture = 0.1;
        Camera camera13 = new Camera(lookFrom, lookAt, vUp, 20, aspectRatio, aperture, distToFocus, 0.0, 1.0, 4);

        Earth earth = new Earth("refImage\\secondtex.jpg");
        Hittable h10 = earth.generateScene();

        Final f9 = new Final(height, width, ppm, camera13, h10, samplesPerPixel, numBounces, 10);
        f9.initFinalImage(new Vector3D(0.7f, 0.8f, 1.0f));
//
//        //EMISSION IMAGE
//        width = 400;
//        height = (int) (width * invAspectRatio);
//        samplesPerPixel = 600;
//
//        lookFrom = new Vector3D(26.0f, 3.0f, 6.0f);
//        lookAt = new Vector3D(0.0f, 2.0f, 0.0f);
//        vUp = new Vector3D(0.0f, 1.0f, 0.0f);
//        distToFocus = 20.0;
//        aperture = 0.1;
//        Camera camera14 = new Camera(lookFrom, lookAt, vUp, 20, aspectRatio, aperture, distToFocus, 0.0, 1.0, 4);
//
//        SimpleLight simpleLight = new SimpleLight(4.0);
//        Hittable h11 = simpleLight.generateScene();
//
//        Final f10 = new Final(height, width, ppm, camera14, h11, samplesPerPixel, numBounces, 11);
//        f10.initFinalImage(new Vector3D(0.0f, 0.0f, 0.0f));
//
//        //BASIC CORNELL BOX IMAGE
//        width = 800;
//        invAspectRatio = 1.0;
//        height = (int) (width * invAspectRatio);
//        samplesPerPixel = 200;
//
//        lookFrom = new Vector3D(278.0f, 278.0f, -800.0f);
//        lookAt = new Vector3D(278.0f, 278.0f, 0.0f);
//        vUp = new Vector3D(0.0f, 1.0f, 0.0f);
//        distToFocus = 10.0;
//        aperture = 0.1;
//        Camera camera15 = new Camera(lookFrom, lookAt, vUp, 40, aspectRatio, aperture, distToFocus, 0.0, 1.0, 4);
//
//        CornellBox cornellBox = new CornellBox();
//        Hittable h12 = cornellBox.generateScene();
//
//        Final f11 = new Final(height, width, ppm, camera15, h12, samplesPerPixel, numBounces, 12);
//        f11.initFinalImage(new Vector3D(0.0f, 0.0f, 0.0f));

    }
}
