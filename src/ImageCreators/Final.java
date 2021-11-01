/*
 * Copyright (c) 2021.
 *
 * Arjun Krishnan 10/31/2021
 * See my other coding projects at: akrishnan.netlify.app
 * Questions, email me at: artk0090@gmail.com
 */

package ImageCreators;

import ImageCreators.Materials.MaterialInfo;
import Vector.Camera;
import Vector.Vector3D;
import Vector.VectorMath;

import java.io.IOException;

public class Final {

    private final int height;
    private final int width;
    private PPMFileMaker ppm;
    private final VectorMath vm = new VectorMath();
    private final Hittable h;
    private final Camera camera;
    private final int samplesPerPixel;
    private final int maxNumBounces;
    private final int version;

    public Final(int height, int width, PPMFileMaker ppm, Camera camera, Hittable h, int samplesPerPixel, int numBounces, int version) {
        this.height = height;
        this.width = width;
        this.ppm = ppm;
        this.camera = camera;
        this.h = h;
        this.samplesPerPixel = samplesPerPixel;
        this.maxNumBounces = numBounces;
        this.version = version;
    }

    public Vector3D randomHemisphericalScatter(double min, double max, Vector3D normal) {
        Vector3D rand = vm.randomVectorInUnitCircle(min, max);
        if (vm.dotProduct(rand, normal) > 0.0) {
            return rand;
        } else {
            return vm.multiply(rand, -1.0f);
        }
    }

    public Vector3D gradColor(Ray r, Vector3D background, int numBounces) {
        Vector3D dir = r.getDirection();
        Vector3D direction = vm.normalize(dir);

        double t = (0.5) * (direction.y + 1.0);

        Vector3D white = new Vector3D(1.0f, 1.0f, 1.0f);
        Vector3D desired = new Vector3D(0.5f, 0.7f, 1.0f);

        if (numBounces <= 0) {
            return new Vector3D(0, 0, 0);
        }

        if (!h.hitAnything(r, 0.001, Double.POSITIVE_INFINITY, h.hRec)) {
            return background;
        }

        Vector3D attenuation = new Vector3D();
        Ray scattered = new Ray();
        MaterialInfo newMatInfo = h.hRec.m.scatter(r, h, attenuation, scattered);

        Vector3D emitted = h.hRec.m.emitted(newMatInfo.h.hRec.u, newMatInfo.h.hRec.v, newMatInfo.h.hRec.point);


        if (!(newMatInfo.isScattered)) {
            return emitted;
        }

        return vm.add(emitted, vm.basicMultiply(newMatInfo.attenuation, gradColor(newMatInfo.scattered, background, numBounces - 1)));
    }

    public void initFinalImage(Vector3D backColor) throws IOException {
        float[][] redChannel = new float[height + 1][width];
        float[][] greenChannel = new float[height + 1][width];
        float[][] blueChannel = new float[height + 1][width];

        int count = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Vector3D color = new Vector3D(0, 0, 0);
                for (int s = 0; s < samplesPerPixel; s++) {
                    float x = (float) ((j + Math.random()) / (float) (width));
                    float y = (float) ((i + Math.random()) / (float) (height));

                    Ray r = camera.getRay(x, y);
                    color = vm.add(color, gradColor(r, backColor, maxNumBounces));
                }
                redChannel[i][j] = (float) (Math.sqrt((color.getX() / (double) samplesPerPixel)));
                greenChannel[i][j] = (float) (Math.sqrt((color.getY() / (double) samplesPerPixel)));
                blueChannel[i][j] = (float) (Math.sqrt((color.getZ() / (double) samplesPerPixel)));
            }
            updateProgressBar(count, height);
            count++;
        }
        ppm = new PPMFileMaker(width, height);
        ppm.setRedChannel(redChannel);
        ppm.setGreenChannel(greenChannel);
        ppm.setBlueChannel(blueChannel);

        if (version == 1) {
            ppm.createImage("14finalImage.ppm", samplesPerPixel);
            System.out.println("\n Final Image Printed \n");
        } else if (version == 2) {
            ppm.createImage("15blurredImage.ppm", samplesPerPixel);
            System.out.println("\n Blurred Image Printed \n");
        } else if (version == 3) {
            ppm.createImage("16checkerBlurredImage.ppm", samplesPerPixel);
            System.out.println("\n Checker Blurred Image Printed \n");
        } else if (version == 4) {
            ppm.createImage("17twoCheckeredImage.ppm", samplesPerPixel);
            System.out.println("\n Two Spheres Checker Image Printed \n");
        } else if (version == 5) {
            ppm.createImage("18twoPerlinNoiseImage.ppm", samplesPerPixel);
            System.out.println("\n Perlin Noise Image Printed \n");
        } else if (version == 6) {
            ppm.createImage("19twoSmoothenedPerlinNoiseImage.ppm", samplesPerPixel);
            System.out.println("\n Smoothened Perlin Noise Image Printed \n");
        } else if (version == 7) {
            ppm.createImage("20twoSmoothenedShiftedPerlinNoiseImage.ppm", samplesPerPixel);
            System.out.println("\n Smoothened Shifted Perlin Noise Image Printed \n");
        } else if (version == 8) {
            ppm.createImage("21twoTurbulencePerlinNoiseImage.ppm", samplesPerPixel);
            System.out.println("\n Turbulence Perlin Noise Image Printed \n");
        } else if (version == 9) {
            ppm.createImage("22twoMarbleImage.ppm", samplesPerPixel);
            System.out.println("\n Marble Image Printed \n");
        } else if (version == 10) {
            ppm.createImage("23earthImage.ppm", samplesPerPixel);
            System.out.println("\n Earth Image Printed \n");
        } else if (version == 11) {
            ppm.createImage("24lightImage.ppm", samplesPerPixel);
            System.out.println("\n Light Emission Image Printed \n");
        } else if (version == 12) {
            ppm.createImage("25basicCornellBox.ppm", samplesPerPixel);
            System.out.println("\n Basic Cornell Box Image Printed \n");
        }
    }

    public void updateProgressBar(int count, int height) {
        System.out.print("\r|| " + (((int) ((count / (double) height) * 100)) + 1) + "%");
    }
}
