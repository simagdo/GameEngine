package de.simagdo.modules.gpgpu;

import de.simagdo.engine.texturing.Texture2D;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glFinish;
import static org.lwjgl.opengl.GL15.GL_WRITE_ONLY;
import static org.lwjgl.opengl.GL30.GL_RGBA16F;
import static org.lwjgl.opengl.GL42.glBindImageTexture;
import static org.lwjgl.opengl.GL42.glTexStorage2D;
import static org.lwjgl.opengl.GL43.glDispatchCompute;

public class SplatMapRenderer {

    private Texture2D splatMap;
    private SplatMapShader shader;
    private int n;

    public SplatMapRenderer(int n) {
        this.n = n;
        this.shader = SplatMapShader.getInstance();
        this.splatMap = new Texture2D();
        this.splatMap.generate();
        this.splatMap.bind();
        this.splatMap.bilinearFilter();
        glTexStorage2D(GL_TEXTURE_2D, (int) (Math.log(this.n) / Math.log(2)), GL_RGBA16F, this.n, this.n);
    }

    public void render(Texture2D normapMap) {
        this.shader.bind();
        this.shader.updateUniforms(normapMap, this.n);
        glBindImageTexture(0, this.splatMap.getId(), 0, false, 0, GL_WRITE_ONLY, GL_RGBA16F);
        glDispatchCompute(this.n / 16, this.n / 16, 1);
        glFinish();
        this.splatMap.bind();
        this.splatMap.bilinearFilter();
    }

    public Texture2D getSplatMap() {
        return splatMap;
    }

    public void setSplatMap(Texture2D splatMap) {
        this.splatMap = splatMap;
    }

    public SplatMapShader getShader() {
        return shader;
    }

    public void setShader(SplatMapShader shader) {
        this.shader = shader;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
