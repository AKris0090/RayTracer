/*
 * Copyright (c) 2021.
 *
 * Arjun Krishnan 10/31/2021
 * See my other coding projects at: akrishnan.netlify.app
 * Questions, email me at: artk0090@gmail.com
 */

package Vector;

import ImageCreators.Ray;

public class Camera {

    private final Vector3D origin;
    private final double viewPortWidth;
    private final double viewPortHeight;
    private double focalLength;
    private final Vector3D posX;
    private final Vector3D posY;
    private Vector3D botLeftCorner = new Vector3D();
    private final VectorMath vm = new VectorMath();
    private double theta;
    private double h;
    private int outL = -1;
    private Vector3D v;
    private Vector3D u;
    private Vector3D w;
    private double lensRad;
    private double time0 = 0.0; //SHUTTER OPEN TIME
    private double time1 = 0.0; //SHUTTER CLOSE TIME

    public Camera(Vector3D origin, double viewPortWidth, double viewPortHeight, double focalLength) {
        this.origin = origin;
        this.viewPortWidth = viewPortWidth;
        this.viewPortHeight = viewPortHeight;
        this.focalLength = focalLength;
        this.posX = new Vector3D((float) (viewPortWidth), 0, 0);
        this.posY = new Vector3D(0, (float) (viewPortHeight), 0);
        this.botLeftCorner.setX((float) (origin.x - (viewPortWidth / 2.0)));
        this.botLeftCorner.setY((float) (origin.y - (viewPortHeight / 2.0)));
        this.botLeftCorner.setZ((float) (origin.z - focalLength));
    }

    public Camera(Vector3D origin, double focalLength, double vFOV, double aspectRatio, int outL) {
        this.origin = origin;
        this.theta = Math.toRadians(vFOV);
        this.h = Math.tan(this.theta / 2.0);
        this.viewPortHeight = 2.0 * this.h;
        this.viewPortWidth = aspectRatio * viewPortHeight;
        this.focalLength = focalLength;
        this.posX = new Vector3D((float) (viewPortWidth), 0.0f, 0.0f);
        this.posY = new Vector3D(0.0f, (float) (viewPortHeight), 0.0f);
        this.botLeftCorner.setX((float) (origin.x - (viewPortWidth / 2.0)));
        this.botLeftCorner.setY((float) (origin.y - (viewPortHeight / 2.0)));
        this.botLeftCorner.setZ((float) (origin.z - focalLength));
        this.outL = outL;
    }

    public Camera(Vector3D lookFrom, Vector3D lookAt, Vector3D vUp, double vFOV, double aspectRatio, int outL) {
        this.theta = vFOV / 2.0f;
        this.h = Math.tan(Math.toRadians(theta));
        this.viewPortHeight = 2.0 * h;
        this.viewPortWidth = aspectRatio * viewPortHeight;

        Vector3D w = vm.normalize(vm.sub(lookFrom, lookAt));
        Vector3D u = vm.normalize(vm.crossProduct(vUp, w));
        Vector3D v = vm.crossProduct(w, u);

        this.origin = new Vector3D(lookFrom.x, lookFrom.y, lookFrom.z);
        this.posX = vm.multiply(u, (float) viewPortWidth);
        this.posY = vm.multiply(v, (float) viewPortHeight);
        Vector3D dividedH = vm.divide(posX, 2.0f);
        Vector3D dividedV = vm.multiply(vm.divide(posY, 2.0f), -1.0f);
        this.botLeftCorner = vm.sub(origin, vm.sub(dividedH, vm.sub(dividedV, w)));
        this.outL = outL;
    }

    public Camera(Vector3D lookFrom, Vector3D lookAt, Vector3D vUp, double vFOV, double aspectRatio, double aperture, double focusDist, int outL) {
        this.theta = vFOV / 2.0f;
        this.h = Math.tan(Math.toRadians(theta));
        this.viewPortHeight = 2.0 * h;
        this.viewPortWidth = aspectRatio * viewPortHeight;

        this.w = vm.normalize(vm.sub(lookFrom, lookAt));
        this.u = vm.normalize(vm.crossProduct(vUp, w));
        this.v = vm.crossProduct(w, u);

        this.origin = new Vector3D(lookFrom.x, lookFrom.y, lookFrom.z);
        this.posX = vm.multiply(vm.multiply(u, (float) viewPortWidth), (float) focusDist);
        this.posY = vm.multiply(vm.multiply(v, (float) viewPortHeight), (float) focusDist);
        Vector3D dividedH = vm.divide(posX, 2.0f);
        Vector3D dividedV = vm.multiply(vm.divide(posY, 2.0f), -1.0f);
        this.botLeftCorner = vm.sub(origin, vm.sub(dividedH, vm.sub(dividedV, (vm.multiply(w, (float) focusDist)))));
        this.outL = outL;

        this.lensRad = aperture / 2.0;
    }

    public Camera(Vector3D lookFrom, Vector3D lookAt, Vector3D vUp, double vFOV, double aspectRatio, double aperture, double focusDist, double tm1, double tm2, int outL) {
        this.theta = vFOV / 2.0f;
        this.h = Math.tan(Math.toRadians(theta));
        this.viewPortHeight = 2.0 * h;
        this.viewPortWidth = aspectRatio * viewPortHeight;

        this.w = vm.normalize(vm.sub(lookFrom, lookAt));
        this.u = vm.normalize(vm.crossProduct(vUp, w));
        this.v = vm.crossProduct(w, u);

        this.origin = new Vector3D(lookFrom.x, lookFrom.y, lookFrom.z);
        this.posX = vm.multiply(vm.multiply(u, (float) viewPortWidth), (float) focusDist);
        this.posY = vm.multiply(vm.multiply(v, (float) viewPortHeight), (float) focusDist);
        Vector3D dividedH = vm.divide(posX, 2.0f);
        Vector3D dividedV = vm.multiply(vm.divide(posY, 2.0f), -1.0f);
        this.botLeftCorner = vm.sub(origin, vm.sub(dividedH, vm.sub(dividedV, (vm.multiply(w, (float) focusDist)))));
        this.outL = outL;

        this.lensRad = aperture / 2.0;
        this.time0 = tm1;
        this.time1 = tm2;
    }

    public Ray getRay(double u, double v) {
        if (outL <= 1){
            Vector3D dir = new Vector3D(botLeftCorner.x, botLeftCorner.y, botLeftCorner.z);
            dir.x = botLeftCorner.x + vm.multiply(posX, (float) u).getX() - origin.getX();
            dir.y = botLeftCorner.y + vm.multiply(posY, (float) v).getY() - origin.getY();
            dir.z = botLeftCorner.z - origin.getZ();

            return new Ray(origin, dir);
        } else if (outL == 2){
            Vector3D scaleH = vm.multiply(posX, (float) u);
            Vector3D scaleV = vm.multiply(posY, (float) v);
            Vector3D dir = vm.add(botLeftCorner, scaleH);
            dir = vm.add(dir, scaleV);
            dir = vm.sub(dir, origin);
            return new Ray(origin, dir);
        } else if (outL == 3){
            Vector3D rd = vm.multiply(vm.randomVectorInUnitDisk(), (float) lensRad);
            Vector3D offsetX = vm.multiply(this.u, rd.x);
            Vector3D offsetY = vm.multiply(this.v, rd.y);

            Vector3D scaleH = vm.multiply(posX, (float) u);
            Vector3D scaleV = vm.multiply(posY, (float) v);
            Vector3D dir = vm.add(botLeftCorner, scaleH);
            dir = vm.add(dir, scaleV);
            dir = vm.sub(dir, origin);
            dir = vm.sub(dir, vm.add(offsetX, offsetY));

            return new Ray(vm.add(origin, vm.add(offsetX, offsetY)), dir);
        } else if (outL == 4){
            Vector3D rd = vm.multiply(vm.randomVectorInUnitDisk(), (float) lensRad);
            Vector3D offsetX = vm.multiply(this.u, rd.x);
            Vector3D offsetY = vm.multiply(this.v, rd.y);

            Vector3D scaleH = vm.multiply(posX, (float) u);
            Vector3D scaleV = vm.multiply(posY, (float) v);
            Vector3D dir = vm.add(botLeftCorner, scaleH);
            dir = vm.add(dir, scaleV);
            dir = vm.sub(dir, origin);
            dir = vm.sub(dir, vm.add(offsetX, offsetY));

            return new Ray(vm.add(origin, vm.add(offsetX, offsetY)), dir, vm.randomDouble(time0, time1));
        } else {
            return null;
        }
    }

    public Vector3D getOrigin() {
        return origin;
    }

    public double getViewPortWidth() {
        return viewPortWidth;
    }

    public double getViewPortHeight() {
        return viewPortHeight;
    }

    public double getFocalLength() {
        return focalLength;
    }

    public Vector3D getPosX() {
        return posX;
    }

    public Vector3D getPosY() {
        return posY;
    }

    public Vector3D getBotLeftCorner() {
        return botLeftCorner;
    }
}
