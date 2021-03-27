package Vector;

public class Vector3D {

    public float x;
    public float y;
    public float z;
    public float w;

    public Vector3D() {
    }

    public Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getNormalLength() {
        return (float) Math.sqrt((this.getX() * this.getX()) + (this.getY() * this.getY()) + (this.getZ() * this.getZ()));
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public String writeColor(){
        return this.x + " " + this.y + " " + this.z + "\n";
    }
}
