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

public class GradientAndRedCircle {

    private final int height;
    private final int width;
    private PPMFileMaker ppm;
    private final VectorMath vm = new VectorMath();
    private final Camera camera;

    public GradientAndRedCircle(int height, int width, PPMFileMaker ppm, Camera camera) {
        this.height = height;
        this.width = width;
        this.ppm = ppm;
        this.camera = camera;
    }

    public Vector3D gradColor(Ray r) {
        Vector3D dir = r.getDirection();
        Vector3D direction = vm.normalize(dir);

        double t = (0.5) * (direction.y + 1.0);

        Vector3D white = new Vector3D(1.0f, 1.0f, 1.0f);
        Vector3D desired = new Vector3D(0.5f, 0.7f, 1.0f);

        Sphere c = new Sphere(0.5f, (new Vector3D(0, 0, -1)));

        //Check red circle
        Hittable h = new Hittable();
        if (c.checkHit(r, -Double.MIN_VALUE, -Double.MAX_VALUE, h.hRec)) {
            return new Vector3D(1, 0, 0); //returns red if ray is in circle
        }

        return vm.add((vm.multiply(white, (float) (1.0 - t))), (vm.multiply(desired, (float) t)));
    }

    public void initGradientImage() throws IOException {
        float[][] redChannel = new float[height + 1][width];
        float[][] greenChannel = new float[height + 1][width];
        float[][] blueChannel = new float[height + 1][width];

        int count = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                float x = j / (float) (width);
                float y = i / (float) (height);

                Ray r = camera.getRay(x, y);
                Vector3D color = gradColor(r);

                redChannel[i][j] = color.getX() * 255;
                greenChannel[i][j] = color.getY() * 255;
                blueChannel[i][j] = color.getZ() * 255;
            }
            updateProgressBar(count, height);
            count++;
        }
        ppm = new PPMFileMaker(width, height);
        ppm.setRedChannel(redChannel);
        ppm.setGreenChannel(greenChannel);
        ppm.setBlueChannel(blueChannel);

        ppm.createImage("2gradientBlue.ppm", 1);
        System.out.println("\n Gradient Image Printed \n");
    }

    public void updateProgressBar(int count, int height) {
        System.out.print("\r|| " + (((int) ((count / (double) height) * 100)) + 1) + "%");
    }
}
