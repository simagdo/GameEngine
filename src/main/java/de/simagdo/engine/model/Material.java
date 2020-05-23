package de.simagdo.engine.model;

import de.simagdo.engine.texturing.Texture2D;
import de.simagdo.math.Vec3f;

public class Material {

    private String name;
    private Texture2D diffuseMap;
    private Texture2D normalMap;
    private Texture2D displaceMap;
    private Texture2D ambientMap;
    private Texture2D specularMap;
    private Texture2D alphaMap;
    private Vec3f color;
    private float alpha;
    private float displaceScale;
    private float emission;
    private float shininess;
    private float horizontalScale;

    public Texture2D getDiffuseMap() {
        return diffuseMap;
    }

    public void setDiffuseMap(Texture2D diffuseMap) {
        this.diffuseMap = diffuseMap;
    }

    public Texture2D getNormalMap() {
        return normalMap;
    }

    public void setNormalMap(Texture2D normalMap) {
        this.normalMap = normalMap;
    }

    public Texture2D getDisplaceMap() {
        return displaceMap;
    }

    public void setDisplacMmap(Texture2D displaceMap) {
        this.displaceMap = displaceMap;
    }

    public Texture2D getAmbientMap() {
        return ambientMap;
    }

    public void setAmbientMap(Texture2D ambientMap) {
        this.ambientMap = ambientMap;
    }

    public Texture2D getSpecularMap() {
        return specularMap;
    }

    public void setSpecularMap(Texture2D specularMap) {
        this.specularMap = specularMap;
    }

    public Texture2D getAlphaMap() {
        return alphaMap;
    }

    public void setAlphaMap(Texture2D alphaMap) {
        this.alphaMap = alphaMap;
    }

    public Vec3f getColor() {
        return color;
    }

    public void setColor(Vec3f color) {
        this.color = color;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getDisplaceScale() {
        return displaceScale;
    }

    public void setDisplaceScale(float displaceScale) {
        this.displaceScale = displaceScale;
    }

    public float getEmission() {
        return emission;
    }

    public void setEmission(float emission) {
        this.emission = emission;
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getHorizontalScale() {
        return horizontalScale;
    }

    public void setHorizontalScale(float horizontalScale) {
        this.horizontalScale = horizontalScale;
    }

}
