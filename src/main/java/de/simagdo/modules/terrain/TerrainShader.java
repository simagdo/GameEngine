package de.simagdo.modules.terrain;

import de.simagdo.engine.camera.Camera;
import de.simagdo.engine.scene.GameObject;
import de.simagdo.engine.shaders.Shader;
import org.lwjgl.opengl.GL13C;
import utils.Utils;

import static org.lwjgl.opengl.GL13C.*;

public class TerrainShader extends Shader {

    private static TerrainShader instance = null;

    public static TerrainShader getInstance() {
        if (instance == null) instance = new TerrainShader();
        return instance;
    }

    protected TerrainShader() {
        super();

        this.addVertexShader(Utils.getDataAsString("shaders/terrain/Terrain_VS.glsl"));
        this.addTessellationControlShader(Utils.getDataAsString("shaders/terrain/Terrain_TC.glsl"));
        this.addTessellationEvaluationShader(Utils.getDataAsString("shaders/terrain/Terrain_TE.glsl"));
        this.addGeometryShader(Utils.getDataAsString("shaders/terrain/Terrain_GS.glsl"));
        this.addFragmentShader(Utils.getDataAsString("shaders/terrain/Terrain_FS.glsl"));

        this.compileShader();

        this.addUniform("localMatrix");
        this.addUniform("worldMatrix");
        this.addUniform("m_ViewProjection");

        this.addUniform("index");
        this.addUniform("gap");
        this.addUniform("lod");
        this.addUniform("scaleY");
        this.addUniform("location");
        this.addUniform("cameraPosition");

        this.addUniform("tessellationFactor");
        this.addUniform("tessellationSlope");
        this.addUniform("tessellationShift");

        this.addUniform("heightmap");
        this.addUniform("normalmap");

        for (int i = 0; i < 8; i++) {
            this.addUniform("lodMorphArea[" + i + "]");
        }

    }

    public void updateUniforms(GameObject gameObject) {

        TerrainNode terrainNode = (TerrainNode) gameObject;

        this.setUniformf("scaleY", terrainNode.getConfig().getScaleY());
        this.setUniformf("lod", terrainNode.getLod());
        this.setUniform("index", terrainNode.getIndex());
        this.setUniform("location", terrainNode.getLocation());
        this.setUniformf("gap", terrainNode.getGap());

        for (int i = 0; i < 8; i++) {
            this.setUniformi("lodMorphArea[" + i + "]", terrainNode.getConfig().getLodMorphingArea()[i]);
        }

        glActiveTexture(GL_TEXTURE0);
        terrainNode.getConfig().getHeightmap().bind();
        this.setUniformi("heightmap", 0);

        glActiveTexture(GL_TEXTURE1);
        terrainNode.getConfig().getNormalmap().bind();
        this.setUniformi("normalmap", 1);

        this.setUniformi("tessellationFactor", terrainNode.getConfig().getTessellationFactor());
        this.setUniformf("tessellationSlope", terrainNode.getConfig().getTessellationSlope());
        this.setUniformf("tessellationShift", terrainNode.getConfig().getTessellationShift());

        this.setUniform("cameraPosition", Camera.getInstance().getPosition());
        this.setUniform("m_ViewProjection", Camera.getInstance().getViewProjectionMatrix());
        this.setUniform("localMatrix", gameObject.getLocalTransform().getWorldMatrix());
        this.setUniform("worldMatrix", gameObject.getWorldTransform().getWorldMatrix());

    }

}
