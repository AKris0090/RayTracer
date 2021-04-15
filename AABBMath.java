package AABB;

import Vector.Vector3D;

public class AABBMath {

    public AABBMath(){
    }

    public AABB surroundingBox(AABB box0, AABB box1){
        Vector3D small = new Vector3D(Math.min(box0.getMinimum().getX(), box1.getMinimum().getX()), Math.min(box0.getMinimum().getY(), box1.getMinimum().getY()), Math.min(box0.getMinimum().getZ(), box1.getMinimum().getZ()));
        Vector3D big = new Vector3D(Math.min(box0.getMaximum().getX(), box1.getMaximum().getX()), Math.min(box0.getMaximum().getY(), box1.getMaximum().getY()), Math.min(box0.getMaximum().getZ(), box1.getMaximum().getZ()));
        return new AABB(small, big);
    }
}
