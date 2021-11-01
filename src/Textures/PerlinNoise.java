/*
 * Copyright (c) 2021.
 *
 * Arjun Krishnan 10/31/2021
 * See my other coding projects at: akrishnan.netlify.app
 * Questions, email me at: artk0090@gmail.com
 */

package Textures;

import Vector.Vector3D;
import Vector.VectorMath;

public class PerlinNoise implements Texture {

    private final int pointCount = 256;
    double[] ranfloat;
    Vector3D[] otherRanFloat;
    int[] permX;
    int[] permY;
    int[] permZ;
    private final VectorMath vm = new VectorMath();
    private final int version;
    private final double scale;

    public PerlinNoise(int version, double scale) {
        this.version = version;
        this.scale = scale;
        if (version <= 2) {
            ranfloat = new double[pointCount];
            for (int i = 0; i < pointCount; i++) {
                ranfloat[i] = Math.random();
            }
        } else {
            otherRanFloat = new Vector3D[pointCount];
            for (int i = 0; i < pointCount; i++) {
                otherRanFloat[i] = new Vector3D((float) vm.randomDouble(-1, 1), (float) vm.randomDouble(-1, 1), (float) vm.randomDouble(-1, 1));
            }
        }

        permX = perlinGenPerm();
        permY = perlinGenPerm();
        permZ = perlinGenPerm();
    }

    public double noise(Vector3D point) {
        if (version == 1) {
            int i = (int) (4 * point.getX()) & 255;
            int j = (int) (4 * point.getY()) & 255;
            int k = (int) (4 * point.getZ()) & 255;

            return ranfloat[permX[i] ^ permY[j] ^ permZ[k]];
        } else if (version == 2) {
            double u = point.getX() - Math.floor(point.getX());
            double v = point.getY() - Math.floor(point.getY());
            double w = point.getZ() - Math.floor(point.getZ());

            u = u * u * (3 - 2 * u);
            v = v * v * (3 - 2 * v);
            w = w * w * (3 - 2 * w);

            int i = (int) (Math.floor(point.getX()));
            int j = (int) (Math.floor(point.getY()));
            int k = (int) (Math.floor(point.getZ()));
            double[][][] c = new double[2][2][2];

            for (int l = 0; l < 2; l++) {
                for (int m = 0; m < 2; m++) {
                    for (int n = 0; n < 2; n++) {
                        c[l][m][n] = ranfloat[permX[(i + l) & 255] ^ permY[(j + m) & 255] ^ permZ[(k + n) & 255]];
                    }
                }
            }

            return trilinearInterpolation(c, u, v, w);
        } else {
            double u = point.getX() - Math.floor(point.getX());
            double v = point.getY() - Math.floor(point.getY());
            double w = point.getZ() - Math.floor(point.getZ());

            int i = (int) (Math.floor(point.getX()));
            int j = (int) (Math.floor(point.getY()));
            int k = (int) (Math.floor(point.getZ()));
            Vector3D[][][] c = new Vector3D[2][2][2];

            for (int l = 0; l < 2; l++) {
                for (int m = 0; m < 2; m++) {
                    for (int n = 0; n < 2; n++) {
                        c[l][m][n] = otherRanFloat[permX[(i + l) & 255] ^ permY[(j + m) & 255] ^ permZ[(k + n) & 255]];
                    }
                }
            }

            return perlinInterpolation(c, u, v, w);
        }
    }

    private double perlinInterpolation(Vector3D[][][] c, double u, double v, double w) {
        u = u * u * (3 - 2 * u);
        v = v * v * (3 - 2 * v);
        w = w * w * (3 - 2 * w);
        double accumulation = 0.0;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    Vector3D weight = new Vector3D((float) u - i, (float) v - j, (float) w - k);
                    accumulation += (i * u + (1 - i) * (1 - u)) * (j * v + (1 - j) * (1 - v)) * (k * w + (1 - k) * (1 - w)) * vm.dotProduct(c[i][j][k], weight);
                }
            }
        }
        return accumulation;
    }

    private double trilinearInterpolation(double[][][] c, double u, double v, double w) {
        double accumulation = 0.0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    accumulation += (i * u + (1 - i) * (1 - u)) * (j * v + (1 - j) * (1 - v)) * (k * w + (1 - k) * (1 - w)) * c[i][j][k];
                }
            }
        }
        return accumulation;
    }

    public int[] perlinGenPerm() {
        int[] p = new int[pointCount];

        for (int i = 0; i < pointCount; i++) {
            p[i] = i;
        }

        p = permute(p, pointCount);

        return p;
    }

    private int[] permute(int[] p, int n) {
        int[] permanent = new int[pointCount];
        for (int i = n - 1; i > 0; i--) {
            int target = vm.randomInt(0, i);
            int temp = p[i];
            permanent[i] = p[target];
            permanent[target] = temp;
        }
        return permanent;
    }

    @Override
    public Vector3D value(double u, double v, Vector3D p) {
        if (version <= 2) {
            return vm.multiply(new Vector3D(1.0f, 1.0f, 1.0f), (float) noise(vm.multiply(p, (float) scale)));
        } else if (version == 3) {
            return vm.multiply(new Vector3D(1.0f, 1.0f, 1.0f), (float) (0.5 * (1.0 + noise(vm.multiply(p, (float) scale)))));
        } else if (version == 4) {
            return vm.multiply(new Vector3D(1.0f, 1.0f, 1.0f), (float) turb(vm.multiply(p, (float) scale), 7));
        } else {
            return vm.multiply(new Vector3D(1.0f, 1.0f, 1.0f), (float) (0.5 * (1 + Math.sin(scale * p.getZ() + 10 * turb(p, 7)))));
        }
    }

    private double turb(Vector3D p, int depth) {
        double accum = 0.0;
        Vector3D temP = p;
        double weight = 1.0;

        for (int i = 0; i < depth; i++) {
            accum += weight * noise(temP);
            weight *= 0.5;
            temP = vm.multiply(temP, 2);
        }

        return Math.abs(accum);
    }
}
