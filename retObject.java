package AABB;

public class retObject {

    public boolean bounded;
    public AABB outputBox;

    public retObject(boolean b, AABB a){
        this.bounded = b;
        this.outputBox = a;
    }

    public retObject() {
    }

    public boolean isBounded() {
        return bounded;
    }

    public void setBounded(boolean bounded) {
        this.bounded = bounded;
    }

    public AABB getOutputBox() {
        return outputBox;
    }

    public void setOutputBox(AABB outputBox) {
        this.outputBox = outputBox;
    }
}
