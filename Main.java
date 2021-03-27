import Vector.Vector3D;
import Vector.VectorMath;

import java.io.IOException;

public class Main{

    private static final VectorMath vm = new VectorMath();
    private static final double invAspectRatio = 9.0 / 16.0;
    private static int width;
    private static int height;
    private static PPMFileMaker ppm;

    private static final double viewportHeight = 2.0;
    private static final double viewPortWidth = 4.0;

    private static final Vector3D camera = new Vector3D(0, 0, 0);

    private static final Vector3D posX = new Vector3D((float)(viewPortWidth), 0, 0);
    private static final Vector3D posY = new Vector3D(0, (float)(viewportHeight), 0);
    private static final Vector3D botLeftCorner = new Vector3D();

    public static void main(String[] args) throws IOException {
        width = 400;
        height = (int) (width * invAspectRatio);

        botLeftCorner.setX((float) (camera.x - (viewPortWidth / 2.0)));
        botLeftCorner.setY((float) (camera.y - (viewportHeight / 2.0)));
        double focalLength = 1.0;
        botLeftCorner.setZ((float) (camera.z - focalLength));

        initFirstImage();
        initGradientImage(); // + red circle
    }

    private static boolean checkInCircle(Vector3D circleCenter, float radius, Ray r) {
        Vector3D originToPoint = vm.sub(r.getOrigin(), circleCenter);
        float aVal = vm.dotProduct(r.getDirection(), r.getDirection());
        float bVal = (2.0f * vm.dotProduct(originToPoint, r.getDirection()));
        float cVal = vm.dotProduct(originToPoint, originToPoint) - (radius * radius);
        float discriminant = (bVal * bVal) - (4 * aVal * cVal);
        return (discriminant >= 0);
    }

    private static Vector3D gradColor(Ray r) {
        Vector3D dir = r.getDirection();
        Vector3D direction = vm.normalize(dir);

        double t = (0.5) * (direction.y + 1.0);

        Vector3D white = new Vector3D(1.0f, 1.0f, 1.0f);
        Vector3D desired = new Vector3D(0.5f, 0.7f, 1.0f);

        //Check red circle, radius 0.5
        if (checkInCircle(new Vector3D(0, 0, -1), 0.5f, r)){
            return new Vector3D(1, 0, 0); //returns red if ray is in circle
        }

        return vm.add((vm.multiply(white, (float) (1.0 - t))), (vm.multiply(desired, (float) t)));
    }

    private static void initGradientImage() throws IOException {
        float[][] redChannel = new float[height][width];
        float[][] greenChannel = new float[height][width];
        float[][] blueChannel = new float[height][width];

        int count = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                float x = j / (float) (width);
                float y = i / (float) (height);

                Vector3D dir = new Vector3D(botLeftCorner.x, botLeftCorner.y, botLeftCorner.z);
                dir.x = botLeftCorner.x + vm.multiply(posX, x).getX() - camera.getX();
                dir.y = botLeftCorner.y + vm.multiply(posY, y).getY() - camera.getY();
                dir.z = botLeftCorner.z - camera.getZ();

                Ray r = new Ray(camera, dir);
                Vector3D color = gradColor(r);

                redChannel[i][j] = color.getX() * 255;
                greenChannel[i][j] = color.getY() * 255;
                blueChannel[i][j] = color.getZ() * 255;
            }
            count++;
            updateProgressBar(count, height);
        }
        ppm = new PPMFileMaker(width, height);
        ppm.setRedChannel(redChannel);
        ppm.setGreenChannel(greenChannel);
        ppm.setBlueChannel(blueChannel);

        ppm.createImage("gradientBlue.ppm");
        System.out.println("\n Gradient Image Printed \n");
    }

    private static void initFirstImage() throws IOException {
        float[][] redChannel = new float[height][width];
        float[][] greenChannel = new float[height][width];
        float[][] blueChannel = new float[height][width];
        int count = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                double red = j / (double) (width - 1);
                double green = i / (double) (height - 1);
                double blue = 0.25;

                double ir = (((double)(width))) * red;
                double ig = (((double)(height))) * green;
                double ib = (((double)(width))) * blue;

                redChannel[(height-1) - i][j] = (float) (((float)(ir)) * red);
                greenChannel[(height-1) - i][j] = (float) (((float)(ig)) * green);
                blueChannel[(height-1) - i][j] = (float) (((float)(ib)) * blue);
            }
            count++;
            updateProgressBar(count, height);
        }
        ppm = new PPMFileMaker(width, height);
        ppm.setRedChannel(redChannel);
        ppm.setGreenChannel(greenChannel);
        ppm.setBlueChannel(blueChannel);

        ppm.createImage("base.ppm");
        System.out.println("\n Base Image Printed \n");
    }

    private static void updateProgressBar(int count, double height) {
        System.out.print("\r|| " + ((count / height) * 100) + "%");
    }
}
