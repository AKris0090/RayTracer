package Vector;

import ImageCreators.Ray;

public class Camera {

    private final Vector3D origin;
    private final double viewPortWidth;
    private final double viewPortHeight;
    private final double focalLength;
    private final Vector3D posX;
    private final Vector3D posY;
    private final Vector3D botLeftCorner = new Vector3D();
    private final VectorMath vm = new VectorMath();

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

    public Ray getRay(double u, double v) {
        Vector3D dir = new Vector3D(botLeftCorner.x, botLeftCorner.y, botLeftCorner.z);
        dir.x = botLeftCorner.x + vm.multiply(posX, (float) u).getX() - origin.getX();
        dir.y = botLeftCorner.y + vm.multiply(posY, (float) v).getY() - origin.getY();
        dir.z = botLeftCorner.z - origin.getZ();

        return new Ray(origin, dir);
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
