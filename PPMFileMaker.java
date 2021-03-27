import Vector.Vector3D;

import java.io.FileOutputStream;
import java.io.IOException;

public class PPMFileMaker {

    private float[][] red;
    private float[][] green;
    private float[][] blue;
    private float[][] alpha;
    private final int width;
    private final int height;

    public PPMFileMaker(int width, int height){
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

    public void createImage(String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("P3\n").append(width).append("\n").append(height).append("\n255\n");
        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
                Vector3D v = new Vector3D(red[i][j], green[i][j], blue[i][j]);
                sb.append(v.writeColor());
            }
        }
        FileOutputStream FOS = new FileOutputStream("OutputImage/" + fileName);
        FOS.write(sb.toString().getBytes());
        FOS.close();
    }
}
