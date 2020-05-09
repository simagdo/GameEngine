package de.simagdo.modules;

import de.simagdo.engine.scene.GameObject;
import de.simagdo.engine.shaders.Shader;
import utils.Utils;

public class AtmosphereShader extends Shader {

    private static AtmosphereShader instance;

    protected AtmosphereShader() {
        super();
        this.addVertexShader(Utils.getDataAsString("shaders/sky/atmosphere_VS.glsl"));
        this.addFragmentShader(Utils.getDataAsString("shaders/sky/atmosphere_FS.glsl"));
        this.compileShader();

        this.addUniform("modelViewProjectionMatrix");
        this.addUniform("worldMatrix");

    }

    public static AtmosphereShader getInstance() {
        return instance == null ? new AtmosphereShader() : instance;
    }

    public void updateUniforms(GameObject gameObject){
        this.setUniform("modelViewProjectionMatrix", gameObject.getTransform().getModelViewProjectionMatrix());
        this.setUniform("worldMatrix", gameObject.getTransform().getWorldMatrix());
    }

}
