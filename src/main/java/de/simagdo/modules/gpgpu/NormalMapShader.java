package de.simagdo.modules.gpgpu;

import de.simagdo.engine.shaders.Shader;
import de.simagdo.engine.texturing.Texture2D;
import org.lwjgl.opengl.GL13C;
import utils.Utils;

import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13C.glActiveTexture;

public class NormalMapShader extends Shader {

    private static NormalMapShader instance = null;

    public static NormalMapShader getInstance() {
        if (instance == null) instance = new NormalMapShader();
        return instance;
    }

    protected NormalMapShader() {
        super();

        this.addComputeShader(Utils.getDataAsString("shaders/gpgpu/NormalMap.glsl"));

        this.compileShader();

        this.addUniform("heightmap");
        this.addUniform("n");
        this.addUniform("strength");

    }

    public void updateUniforms(Texture2D heightmap, int n, float strength) {
        glActiveTexture(GL_TEXTURE0);
        heightmap.bind();
        this.setUniformi("heightmap", 0);
        this.setUniformi("n", n);
        this.setUniformf("strength", strength);
    }

}
