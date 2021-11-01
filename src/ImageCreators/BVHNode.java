/*
 * Copyright (c) 2021.
 *
 * Arjun Krishnan 10/31/2021
 * See my other coding projects at: akrishnan.netlify.app
 * Questions, email me at: artk0090@gmail.com
 */

package ImageCreators;

import AABB.AABB;
import AABB.retObject;
import AABB.AABBMath;
import Vector.VectorMath;

import java.util.ArrayList;
import java.util.Comparator;

public class BVHNode implements HittableObject, Comparator<HittableObject> {

    public HittableObject left;
    public HittableObject right;
    AABB box;
    public BVHNode bvhNode;
    private int axis;

    public BVHNode(){
    }

    public BVHNode(Hittable h, double time0, double time1) {
        bvhNode = new BVHNode(h.worldObjects, 0, h.worldObjects.size(), time0, time1);
    }

    public Comparator<HittableObject> axisComparator = new Comparator<>() {
        @Override
        public int compare(HittableObject o1, HittableObject o2) {
            return (axis == 0) ? boxXcompare(o1, o2) : (axis == 1) ? boxYCompare(o1, o2) : boxZCompare(o1, o2);
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    };

    public BVHNode(ArrayList<HittableObject> worldObjects, int start, int end, double time0, double time1){
        ArrayList<HittableObject> copyObjects = copy(worldObjects);

        VectorMath vm = new VectorMath();
        axis = vm.randomInt(0, 2);
        int objSpan = end - start;

        if (objSpan == 1){
            left = right = copyObjects.get(start);
        } else if (objSpan == 2){
            if (comparator(copyObjects.get(start), copyObjects.get(start + 1), axis)){
                left = copyObjects.get(start);
                right = copyObjects.get(start + 1);
            } else {
                left = copyObjects.get(start + 1);
                right = copyObjects.get(start);
            }
        } else {
            ArrayList<HittableObject> doubleTemp = new ArrayList<>();
            for (int i = start; i < end; i++) {
                doubleTemp.add(copyObjects.get(i));
            }
            doubleTemp.sort(axisComparator);

            double mid = start + (objSpan / 2.0);
            left = new BVHNode(doubleTemp, start, (int) mid, time0, time1);
            right = new BVHNode(doubleTemp, (int) mid, end, time0, time1);
        }
        
        AABB boxLeft = new AABB();
        AABB boxRight = new AABB();
        retObject r1 = left.boundingBox(time0, time1, boxLeft);
        retObject r2 = right.boundingBox(time0, time1, boxRight);
        if (!(r1.isBounded()) || !(r2.isBounded())){
            System.out.println("no bounding box in bvh node constructor");
        }

        AABBMath aabbMath = new AABBMath();
        box = aabbMath.surroundingBox(r1.getOutputBox(), r2.getOutputBox());
    }

    private ArrayList<HittableObject> copy(ArrayList<HittableObject> worldObjects) {
        return new ArrayList<>(worldObjects);
    }

    @Override
    public boolean checkHit(Ray r, double tMin, double tMax, Hittable.hitRecord hRec) {
        if (!box.hit(r, tMin, tMax)){
            return false;
        }

        Hittable leftH = (Hittable) left;
        Hittable rightH = (Hittable) right;

        boolean hitLeft = leftH.hitAnything(r, tMin, tMax, hRec);
        boolean hitRight = rightH.hitAnything(r, tMin, hitLeft ? hRec.t : tMax, hRec);

        return hitLeft || hitRight;
    }

    @Override
    public retObject boundingBox(double tim0, double tim1, AABB outputBox) {
        return new retObject(true, box);
    }

    public boolean comparator(HittableObject o1, HittableObject o2, int axis) {
        AABB box1 = new AABB();
        AABB box2 = new AABB();

        Hittable h1 = (Hittable) o1;
        Hittable h2 = (Hittable) o2;

        if (!(h1.boundingListBox(0, 0, box1) || (!h2.boundingListBox(0, 0, box2)))) {
            System.out.println("No bounding box in bvh node constructor");
        }

        if (axis == 0) {
            return box1.minimum.getX() < box2.minimum.getX();
        } else if (axis == 1) {
            return box1.minimum.getY() < box2.minimum.getY();
        } else {
            return box1.minimum.getZ() < box2.minimum.getZ();
        }
    }

    private int boxZCompare(HittableObject o1, HittableObject o2) {
        if (comparator(o1, o2, 2)){
            return 1;
        } else {
            return -1;
        }
    }

    private int boxYCompare(HittableObject o1, HittableObject o2) {
        if (comparator(o1, o2, 1)){
            return 1;
        } else {
            return -1;
        }
    }

    private int boxXcompare(HittableObject o1, HittableObject o2) {
        if (comparator(o1, o2, 0)){
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public int compare(HittableObject o1, HittableObject o2) {
        return 0;
    }
}
