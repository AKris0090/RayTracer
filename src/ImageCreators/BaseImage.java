package ImageCreators;

import java.io.IOException;

public class BaseImage {

    private final int height;
    private final int width;
    private PPMFileMaker ppm;

    public BaseImage(int height, int width, PPMFileMaker ppm) {
        this.height = height;
        this.width = width;
        this.ppm = ppm;
    }

    public void initFirstImage() throws IOException {
        float[][] redChannel = new float[height + 1][width];
        float[][] greenChannel = new float[height + 1][width];
        float[][] blueChannel = new float[height + 1][width];
        int count = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                double red = j / (double) (width - 1);
                double green = i / (double) (height - 1);
                double blue = 0.25;

                double ir = (((double) (width))) * red;
                double ig = (((double) (height))) * green;
                double ib = (((double) (width))) * blue;

                redChannel[(height) - i][j] = (float) (((float) (ir)) * red);
                greenChannel[(height) - i][j] = (float) (((float) (ig)) * green);
                blueChannel[(height) - i][j] = (float) (((float) (ib)) * blue);
            }
            updateProgressBar(count, height);
            count++;
        }
        ppm = new PPMFileMaker(width, height);
        ppm.setRedChannel(redChannel);
        ppm.setGreenChannel(greenChannel);
        ppm.setBlueChannel(blueChannel);

        ppm.createImage("1base.ppm", 1);
        System.out.println("\n Base Image Printed \n");
    }

    public void updateProgressBar(int count, int height) {
        System.out.print("\r|| " + (((int) ((count / (double) height) * 100)) + 1) + "%");
    }
}
