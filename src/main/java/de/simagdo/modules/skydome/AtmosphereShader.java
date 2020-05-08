package de.simagdo.modules.skydome;

import de.simagdo.engine.scene.GameObject;
import de.simagdo.engine.shaders.Shader;
import utils.ResourceLoader;
import utils.Utils;

public class AtmosphereShader extends Shader {

    private static AtmosphereShader instance = null;

    public static AtmosphereShader getInstance() {
        if (instance == null) {
            instance = new AtmosphereShader();
        }
        return instance;
    }

    protected AtmosphereShader() {
        super();

        addVertexShader(Utils.getDataAsString("shaders/sky/atmosphere_VS.glsl"));
        addFragmentShader(Utils.getDataAsString("shaders/sky/atmosphere_FS.glsl"));
        compileShader();

        addUniform("modelViewProjectionMatrix");
        addUniform("worldMatrix");
    }

    public void updateUniforms(GameObject object) {
        setUniform("modelViewProjectionMatrix", object.getWorldTransformation().getModelViewProjectionMatrix());
        setUniform("worldMatrix", object.getWorldTransformation().getWorldMatrix());
    }

}
