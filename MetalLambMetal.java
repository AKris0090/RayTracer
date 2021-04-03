package ImageCreators;

import Vector.Camera;
import Vector.Vector3D;
import Vector.VectorMath;

import java.io.IOException;

public class MetalLambMetal {

    private final int height;
    private final int width;
    private PPMFileMaker ppm;
    private final VectorMath vm = new VectorMath();
    private final Hittable h;
    private final Camera camera;
    private final int samplesPerPixel;
    private final int maxNumBounces;

    public MetalLambMetal(int height, int width, PPMFileMaker ppm, Camera camera, Hittable h, int samplesPerPixel, int numBounces) {
        this.height = height;
        this.width = width;
        this.ppm = ppm;
        this.camera = camera;
        this.h = h;
        this.samplesPerPixel = samplesPerPixel;
        this.maxNumBounces = numBounces;
    }

    public Vector3D randomHemisphericalScatter(double min, double max, Vector3D normal) {
        Vector3D rand = vm.randomVectorInUnitCircle(min, max);
        if (vm.dotProduct(rand, normal) > 0.0) {
            return rand;
        } else {
            return vm.multiply(rand, -1.0f);
        }
    }

    public Vector3D gradColor(Ray r, int numBounces) {
        Vector3D dir = r.getDirection();
        Vector3D direction = vm.normalize(dir);

        double t = (0.5) * (direction.y + 1.0);

        Vector3D white = new Vector3D(1.0f, 1.0f, 1.0f);
        Vector3D desired = new Vector3D(0.5f, 0.7f, 1.0f);

        if (numBounces <= 0) {
            return new Vector3D(0, 0, 0);
        }

        if (h.hitAnything(r, 0.001, Double.POSITIVE_INFINITY, h.hRec)) {
            if (h.hRec.m.scatter(r, h)) {
                return vm.basicMultiply(h.hRec.m.attenuation, gradColor(h.hRec.m.scattered, numBounces - 1));
            }
            return new Vector3D(0, 0, 0);
        }

        return vm.add((vm.multiply(white, (float) (1.0 - t))), (vm.multiply(desired, (float) t)));
    }

    public void initMetalSpheresImage() throws IOException {
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

        ppm.createImage("metalSpheres.ppm", samplesPerPixel);
        System.out.println("\n Metal Spheres Image Printed \n");
    }

    public void updateProgressBar(int count, int height) {
        System.out.print("\r|| " + (((int) ((count / (double) height) * 100)) + 1) + "%");
    }
}
