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

public class GammaCorrectedDiffuseSphere {

    private final int height;
    private final int width;
    private PPMFileMaker ppm;
    private final VectorMath vm = new VectorMath();
    private final Hittable h;
    private final Camera camera;
    private final int samplesPerPixel;
    private final int maxNumBounces;

    public GammaCorrectedDiffuseSphere(int height, int width, PPMFileMaker ppm, Camera camera, Hittable h, int samplesPerPixel, int numBounces) {
        this.height = height;
        this.width = width;
        this.ppm = ppm;
        this.camera = camera;
        this.h = h;
        this.samplesPerPixel = samplesPerPixel;
        this.maxNumBounces = numBounces;
    }

    public Vector3D gradColor(Ray r, int numBounces) {
        Hittable h1 = new Hittable();
        h1.worldObjects = h.worldObjects;
        Vector3D dir = r.getDirection();
        Vector3D direction = vm.normalize(dir);

        double t = (0.5) * (direction.y + 1.0);

        Vector3D white = new Vector3D(1.0f, 1.0f, 1.0f);
        Vector3D desired = new Vector3D(0.5f, 0.7f, 1.0f);

        if (numBounces <= 0) {
            return new Vector3D(0, 0, 0);
        }

        if (h1.hitAnything(r, 0.001, Double.POSITIVE_INFINITY, h1.hRec)) {
            Vector3D rand = vm.randomVectorInUnitCircle(-1, 1);
            Vector3D target = vm.add(vm.add(h1.hRec.point, h1.hRec.normal), rand);
            return vm.multiply(gradColor((new Ray(h1.hRec.point, vm.sub(target, h1.hRec.point))), numBounces - 1), 0.5f);
        }

        return vm.add((vm.multiply(white, (float) (1.0 - t))), (vm.multiply(desired, (float) t)));
    }

    public void initGammaCorrectedDiffuseImage() throws IOException {
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
                    color = vm.add(color, gradColor(r, maxNumBounces));
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

        ppm.createImage("6gammaCorrectedDiffuseSphere.ppm", samplesPerPixel);
        System.out.println("\n Gamma Corrected Diffuse Sphere Image Printed \n");
    }

    public void updateProgressBar(int count, int height) {
        System.out.print("\r|| " + (((int) ((count / (double) height) * 100)) + 1) + "%");
    }
}
