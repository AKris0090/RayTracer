/*
 * Copyright (c) 2021.
 *
 * Arjun Krishnan 10/31/2021
 * See my other coding projects at: akrishnan.netlify.app
 * Questions, email me at: artk0090@gmail.com
 */

package Vector;

public class VectorMath {

    public VectorMath() {
    }

    public Vector3D add(Vector3D o1, Vector3D o2) {
        return new Vector3D((o1.x + o2.x), (o1.y + o2.y), (o1.z + o2.z));
    }

    public Vector3D sub(Vector3D o1, Vector3D o2) {
        return new Vector3D((o1.x - o2.x), (o1.y - o2.y), (o1.z - o2.z));
    }

    public Vector3D multiply(Vector3D v, float scalar) {
        return new Vector3D((v.x * scalar), (v.y * scalar), (v.z * scalar));
    }

    public Vector3D basicMultiply(Vector3D v, Vector3D v1) {
        return new Vector3D((v.x * v1.x), (v.y * v1.y), (v.z * v1.z));
    }

    public Vector3D divide(Vector3D v, float scalar) {
        return new Vector3D((v.x / scalar), (v.y / scalar), (v.z / scalar));
    }

    public Vector3D normalize(Vector3D v) {
        float length = (float) Math.sqrt(this.dotProduct(v, v));

        float nx = v.x / length;
        float ny = v.y / length;
        float nz = v.z / length;

        return new Vector3D(nx, ny, nz);
    }

    public float dotProduct(Vector3D o1, Vector3D o2) {
        return ((o1.getX() * o2.getX()) + (o1.getY() * o2.getY()) + (o1.getZ() * o2.getZ()));
    }

    public Vector3D crossProduct(Vector3D o1, Vector3D o2) {
        float x1, y1, z1, x2, y2, z2;

        x1 = o1.x;
        x2 = o2.x;
        y1 = o1.y;
        y2 = o2.y;
        z1 = o1.z;
        z2 = o2.z;

        float nx, ny, nz;
        nx = (y1 * z2) - (z1 * y2);
        ny = (z1 * x2) - (x1 * z2);
        nz = (x1 * y2) - (y1 * x2);
        return new Vector3D(nx, ny, nz);
    }
    
    public double randomDouble(double min, double max){
        return min + ((max - min) * Math.random());
    }

    public int randomInt(double min, double max){
        return (int) (randomDouble(min, max + 1));
    }

    public double clamp(double x, double min, double max) {
        if (x < min) {
            return min;
        } else if (x > max) {
            return max;
        } else {
            return x;
        }
    }

    public Vector3D randomVectorInUnitCircle(double min, double max) {
        while (true) {
            Vector3D randVector = new Vector3D((float) randomDouble(min, max), (float) randomDouble(min, max), (float) randomDouble(min, max));
            if ((randVector.getNormalLength() * randVector.getNormalLength()) < 1) {
                return normalize(randVector);
            }
        }
    }

    public Vector3D randomVectorInUnitDisk(){
        while (true){
            Vector3D p = new Vector3D((float) randomDouble(-1, 1), (float) randomDouble(-1, 1),  0.0f);
            if (p.getLengthSquared() >= 1){
                continue;
            }
            return p;
        }
    }

    public Vector3D refract (Vector3D uv, Vector3D n, double etaOverEtaPrime){
        float cosTheta = Math.min(dotProduct(sub(new Vector3D(0.0f, 0.0f, 0.0f), uv), n), 1.0f);
        Vector3D rPerpendicular = multiply(add(multiply(n, cosTheta), uv), (float) etaOverEtaPrime);
        Vector3D rParallel = multiply(n, (0.0f - ((float) Math.sqrt(Math.abs(1.0f - rPerpendicular.getLengthSquared())))));
        return add(rPerpendicular, rParallel);
    }

    public Vector3D reflect (Vector3D v, Vector3D n){
        return sub(v, multiply(n, (2 * dotProduct(v, n))));
    }

    public double reflectance(double cosine, double refIdx){
        //SCHLICK'S APPROXIMATION
        double rTheta = (1.0f - refIdx) / (1.0f + refIdx);
        double newRTheta = rTheta * rTheta;
        return newRTheta + ((1.0f - newRTheta) * Math.pow((1.0f - cosine), 5));
    }

    public boolean nearZero(Vector3D v){
        float val = 0.000000001f;
        return ((v.x < val) && (v.y < val) && (v.z < val));
    }
}
