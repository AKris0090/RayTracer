package ImageCreators;

public interface hittableObject {

    boolean checkHit(Ray r, double tMin, double tMax, Hittable.hitRecord hRec);
}
