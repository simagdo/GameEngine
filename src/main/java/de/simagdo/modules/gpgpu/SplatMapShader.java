package de.simagdo.modules.gpgpu;

import de.simagdo.engine.shaders.Shader;
import de.simagdo.engine.texturing.Texture2D;
import org.lwjgl.opengl.GL13C;
import utils.Utils;

import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13C.glActiveTexture;

public class SplatMapShader extends Shader {

    private static SplatMapShader instance = null;

    public static SplatMapShader getInstance() {
        if (instance == null) instance = new SplatMapShader();
        return instance;
    }

    protected SplatMapShader() {
        super();

        this.addComputeShader(Utils.getDataAsString("shaders/gpgpu/SplatMap.glsl"));
        this.compileShader();

        this.addUniform("normalmap");
        this.addUniform("n");

    }

    public void updateUniforms(Texture2D normalMap, int n) {
        glActiveTexture(GL_TEXTURE0);
        normalMap.bind();
        this.setUniformi("normalmap", 0);
        this.setUniformi("n", n);
    }

}
