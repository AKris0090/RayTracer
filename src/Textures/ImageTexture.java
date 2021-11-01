/*
 * Copyright (c) 2021.
 *
 * Arjun Krishnan 10/31/2021
 * See my other coding projects at: akrishnan.netlify.app
 * Questions, email me at: artk0090@gmail.com
 */

package Textures;

import ImageCreators.DImage;
import Vector.Vector3D;
import Vector.VectorMath;
import jogamp.opengl.glu.mipmap.Image;
import processing.core.PImage;
import processing.core.PApplet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;


public class ImageTexture extends PApplet implements Texture {

    public String imageName;
    public short[][] redImg;
    public short[][] greenImg;
    public short[][] blueImg;
    int width;
    int height;
    private final VectorMath vm = new VectorMath();
    private int componentsPerPixel;
    private int bytesPerScanLine;

    public ImageTexture(String imageNaM) {
        this.imageName = imageNaM;
        BufferedImage image = null;
        try {
            File pathToFile = new File(imageName);
            image = ImageIO.read(pathToFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        PImage img = new PImage(image);
        this.width = img.width;
        this.height = img.height;
        DImage dimg = new DImage(img);
        redImg = dimg.getRedChannel();
        greenImg = dimg.getGreenChannel();
        blueImg = dimg.getBlueChannel();
        int bytesPerPixel = 3;
        this.componentsPerPixel = bytesPerPixel;
        this.bytesPerScanLine = bytesPerPixel * this.width;
    }

    @Override
    public Vector3D value(double u, double v, Vector3D p) {
        if (redImg == null){
            return new Vector3D(0.0f, 0.1f, 0.1f);
        }

        int i;
        int j;
        if (u > 0) {
            double uu = vm.clamp(u, 0.0, 1.0);
            double vv = 1.0 - vm.clamp(v, 0.0, 1.0);
//        System.out.println(uu + " , " + vv);
            i = (int) (uu * width);
            j = (int) (vv * height);
            if (i >= width) {
                i = width - 1;
            }
            if (j >= height) {
                j = height - 1;
            }
        } else {
            double vv = 1.0 - vm.clamp(v, 0.0, 1.0);
//        System.out.println(uu + " , " + vv);
            i = (int) ((width) + (u * width));
            j = (int) (vv * height);
            if (i >= width) {
                i = width - 1;
            }
            if (j >= height) {
                j = height - 1;
            }
        }

        double colorScale = 1.0 / 255.0;
        int redPixColor = redImg[j][i];
        int greenPixColor = greenImg[j][i];
        int bluePixColor = blueImg[j][i];

        Vector3D retVec = new Vector3D((float) colorScale * redPixColor, (float) colorScale * greenPixColor, (float) colorScale * bluePixColor);

        return retVec;
    }
}
