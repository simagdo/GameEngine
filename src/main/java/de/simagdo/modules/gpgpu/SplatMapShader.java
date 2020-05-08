package de.simagdo.modules.gpgpu;

import de.simagdo.engine.shaders.Shader;
import de.simagdo.engine.texturing.Texture2D;
import utils.ResourceLoader;
import utils.Utils;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class SplatMapShader extends Shader {

    private static SplatMapShader instance = null;

    public static SplatMapShader getInstance() {
        if (instance == null) {
            instance = new SplatMapShader();
        }
        return instance;
    }

    protected SplatMapShader() {
        super();

        addComputeShader(Utils.getDataAsString("shaders/gpgpu/SplatMap.glsl"));
        compileShader();

        addUniform("normalmap");
        addUniform("N");
    }

    public void updateUniforms(Texture2D normalmap, int N) {
        glActiveTexture(GL_TEXTURE0);
        normalmap.bind();
        setUniformi("normalmap", 0);
        setUniformi("N", N);
    }

}
