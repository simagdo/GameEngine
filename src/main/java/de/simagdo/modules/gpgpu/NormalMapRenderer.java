package de.simagdo.modules.gpgpu;

import de.simagdo.engine.texturing.Texture2D;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glFinish;
import static org.lwjgl.opengl.GL15.GL_WRITE_ONLY;
import static org.lwjgl.opengl.GL30.GL_RGBA32F;
import static org.lwjgl.opengl.GL42.glBindImageTexture;
import static org.lwjgl.opengl.GL42.glTexStorage2D;
import static org.lwjgl.opengl.GL43.glDispatchCompute;

public class NormalMapRenderer {

    private float strength;
    private Texture2D normalMap;
    private NormalMapShader shader;
    private int n;

    public NormalMapRenderer(int n) {
        this.n = n;
        this.shader = NormalMapShader.getInstance();
        this.normalMap = new Texture2D();
        this.normalMap.generate();
        this.normalMap.bind();
        this.normalMap.bilinearFilter();

        glTexStorage2D(GL_TEXTURE_2D, (int) (Math.log(this.n) / Math.log(2)), GL_RGBA32F, this.n, this.n);
    }

    public void render(Texture2D heightMap) {
        this.shader.bind();
        this.shader.updateUniforms(heightMap, this.n, this.strength);
        glBindImageTexture(0, this.normalMap.getId(), 0, false, 0, GL_WRITE_ONLY, GL_RGBA32F);
        glDispatchCompute(this.n / 16, this.n / 16, 1);
        glFinish();
        this.normalMap.bind();
        this.normalMap.bilinearFilter();
    }

    public float getStrength() {
        return strength;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    public Texture2D getNormalMap() {
        return normalMap;
    }

    public void setNormalMap(Texture2D normalMap) {
        this.normalMap = normalMap;
    }

    public NormalMapShader getShader() {
        return shader;
    }

    public void setShader(NormalMapShader shader) {
        this.shader = shader;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
