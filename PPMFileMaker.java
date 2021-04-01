package ImageCreators;

import Vector.Vector3D;
import Vector.VectorMath;

import java.io.FileOutputStream;
import java.io.IOException;

public class PPMFileMaker {

    private float[][] red;
    private float[][] green;
    private float[][] blue;
    private float[][] alpha;
    private final int width;
    private final int height;
    private final VectorMath vm = new VectorMath();

    public PPMFileMaker(int width, int height) {
        red = new float[height][width];
        green = new float[height][width];
        blue = new float[height][width];
        alpha = new float[height][width];
        this.width = width;
        this.height = height;
    }

    public float[][] getRedChannel() {
        return red;
    }

    public void setRedChannel(float[][] red) {
        this.red = red;
    }

    public float[][] getGreenChannel() {
        return green;
    }

    public void setGreenChannel(float[][] green) {
        this.green = green;
    }

    public float[][] getBlueChannel() {
        return blue;
    }

    public void setBlueChannel(float[][] blue) {
        this.blue = blue;
    }

    public float[][] getAlphaChannel() {
        return alpha;
    }

    public void setAlphaChannel(float[][] alpha) {
        this.alpha = alpha;
    }

    public void createImage(String fileName, int samplesPerPixel) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("P3\n").append(width).append("\n").append(height).append("\n255\n");
        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
                float r = red[i][j];
                float g = green[i][j];
                float b = blue[i][j];

                if (samplesPerPixel != 1) {
                    r = (int) (256 * vm.clamp(r, 0.0, 0.999));
                    g = (int) (256 * vm.clamp(g, 0.0, 0.999));
                    b = (int) (256 * vm.clamp(b, 0.0, 0.999));
                }

                Vector3D v = new Vector3D(r, g, b);

                sb.append(v.writeColor());
            }
        }
        FileOutputStream FOS = new FileOutputStream("OutputImage/" + fileName);
        FOS.write(sb.toString().getBytes());
        FOS.close();
    }
}
