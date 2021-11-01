/*
 * Copyright (c) 2021.
 *
 * Arjun Krishnan 10/31/2021
 * See my other coding projects at: akrishnan.netlify.app
 * Questions, email me at: artk0090@gmail.com
 */

package ImageCreators;

import Vector.Camera;
import Vector.Vector3D;
import Vector.VectorMath;

import java.io.IOException;

public class AntiAliasedColoredNormals {

    private final int height;
    private final int width;
    private PPMFileMaker ppm;
    private final VectorMath vm = new VectorMath();
    private final Hittable h;
    private final Camera camera;
    private final int samplesPerPixel;

    public AntiAliasedColoredNormals(int height, int width, PPMFileMaker ppm, Camera camera, Hittable h, int samplesPerPixel) {
        this.height = height;
        this.width = width;
        this.ppm = ppm;
        this.camera = camera;
        this.h = h;
        this.samplesPerPixel = samplesPerPixel;
    }

    public Vector3D gradColor(Ray r) {
        Vector3D dir = r.getDirection();
        Vector3D direction = vm.normalize(dir);

        double t = (0.5) * (direction.y + 1.0);

        Vector3D white = new Vector3D(1.0f, 1.0f, 1.0f);
        Vector3D desired = new Vector3D(0.5f, 0.7f, 1.0f);

        if (h.hitAnything(r, 0.0, Double.MAX_VALUE, h.hRec)) {
            return (vm.multiply(vm.add(h.hRec.normal, white), 0.5f));
        }

        return vm.add((vm.multiply(white, (float) (1.0 - t))), (vm.multiply(desired, (float) t)));
    }

    public void initAntiAliasedColoredNormalsImage() throws IOException {
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
                    color = vm.add(color, gradColor(r));
                }
                redChannel[i][j] = color.getX() / samplesPerPixel;
                greenChannel[i][j] = color.getY() / samplesPerPixel;
                blueChannel[i][j] = color.getZ() / samplesPerPixel;
            }
            updateProgressBar(count, height);
            count++;
        }
        ppm = new PPMFileMaker(width, height);
        ppm.setRedChannel(redChannel);
        ppm.setGreenChannel(greenChannel);
        ppm.setBlueChannel(blueChannel);

        ppm.createImage("4antiAliasedColoredNormalsAndGround.ppm", samplesPerPixel);
        System.out.println("\n AntiAliased Colored Normals Image Printed \n");
    }

    public void updateProgressBar(int count, int height) {
        System.out.print("\r|| " + (((int) ((count / (double) height) * 100)) + 1) + "%");
    }
}
