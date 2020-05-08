package de.simagdo.modules.gpgpu;

import de.simagdo.engine.shaders.Shader;
import de.simagdo.engine.texturing.Texture2D;
import utils.ResourceLoader;
import utils.Utils;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class NormalMapShader extends Shader {

    private static NormalMapShader instance = null;

    public static NormalMapShader getInstance() {
        if (instance == null) {
            instance = new NormalMapShader();
        }
        return instance;
    }

    protected NormalMapShader() {
        super();

        addComputeShader(Utils.getDataAsString("shaders/gpgpu/NormalMap.glsl"));
        compileShader();

        addUniform("displacementmap");
        addUniform("N");
        addUniform("normalStrength");
    }

    public void updateUniforms(Texture2D heightmap, int N, float strength) {
        glActiveTexture(GL_TEXTURE0);
        heightmap.bind();
        setUniformi("displacementmap", 0);
        setUniformi("N", N);
        setUniformf("normalStrength", strength);
    }

}
