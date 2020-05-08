package de.simagdo.math;

import de.simagdo.engine.camera.Camera;

public class Transform {

    private Vec3f translation;
    private Vec3f rotation;
    private Vec3f scaling;

    public Transform() {
        this.setTranslation(new Vec3f(0, 0, 0));
        this.setRotation(new Vec3f(0, 0, 0));
        this.setScaling(new Vec3f(1, 1, 1));
    }

    public Transform(Vec3f translation, Vec3f rotation, Vec3f scaling) {
        this.translation = translation;
        this.rotation = rotation;
        this.scaling = scaling;
    }

    public Matrix4f getWorldMatrix() {
        Matrix4f translationMatrix = new Matrix4f().Translation(this.translation);
        Matrix4f rotationMatrix = new Matrix4f().Rotation(this.rotation);
        Matrix4f scalingMatrix = new Matrix4f().Scaling(this.scaling);

        return translationMatrix.mul(scalingMatrix.mul(rotationMatrix));
    }

    public Matrix4f getModelMatrix() {
        return new Matrix4f().Rotation(this.rotation);
    }

    public Matrix4f getModelViewProjectionMatrix() {
        return Camera.getInstance().getViewProjectionMatrix().mul(this.getWorldMatrix());
    }

    public Vec3f getTranslation() {
        return translation;
    }

    public void setTranslation(Vec3f translation) {
        this.translation = translation;
    }

    public void setTranslation(float x, float y, float z) {
        this.translation = new Vec3f(x, y, z);
    }

    public Vec3f getRotation() {
        return rotation;
    }

    public void setRotation(Vec3f rotation) {
        this.rotation = rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation = new Vec3f(x, y, z);
    }

    public Vec3f getScaling() {
        return scaling;
    }

    public void setScaling(Vec3f scaling) {
        this.scaling = scaling;
    }

    public void setScaling(float x, float y, float z) {
        this.scaling = new Vec3f(x, y, z);
    }

}
