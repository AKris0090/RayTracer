package ImageCreators.Materials;

import ImageCreators.Hittable;
import ImageCreators.Ray;
import Vector.Vector3D;

public class MaterialInfo {

    public Ray scattered;
    public Vector3D attenuation;
    public Hittable h;
    public boolean isScattered;

    public MaterialInfo(){
    }

    public MaterialInfo(Ray scattered, Vector3D attenuation, Hittable h, boolean isScattered){
        this.scattered = scattered;
        this.attenuation = attenuation;
        this.h = h;
        this.isScattered = isScattered;
    }

    public Ray getScattered() {
        return scattered;
    }

    public void setScattered(Ray scattered) {
        this.scattered = scattered;
    }

    public Vector3D getAttenuation() {
        return attenuation;
    }

    public void setAttenuation(Vector3D attenuation) {
        this.attenuation = attenuation;
    }

    public Hittable getH() {
        return h;
    }

    public void setH(Hittable h) {
        this.h = h;
    }

    public boolean isScattered() {
        return isScattered;
    }

    public void setScattered(boolean scattered) {
        isScattered = scattered;
    }
}
