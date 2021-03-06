package de.simagdo.modules.terrain;

import de.simagdo.engine.camera.Camera;
import de.simagdo.engine.scene.GameObject;
import de.simagdo.engine.shaders.Shader;
import utils.Utils;

import static org.lwjgl.opengl.GL13C.*;

public class TerrainWireframeShader extends Shader {

    private static TerrainWireframeShader instance = null;

    public static TerrainWireframeShader getInstance() {
        if (instance == null) instance = new TerrainWireframeShader();
        return instance;
    }

    protected TerrainWireframeShader() {
        super();

        this.addVertexShader(Utils.getDataAsString("shaders/terrain/Terrain_VS.glsl"));
        this.addTessellationControlShader(Utils.getDataAsString("shaders/terrain/Terrain_TC.glsl"));
        this.addTessellationEvaluationShader(Utils.getDataAsString("shaders/terrain/Terrain_TE.glsl"));
        this.addGeometryShader(Utils.getDataAsString("shaders/terrain/wireframe_GS.glsl"));
        this.addFragmentShader(Utils.getDataAsString("shaders/terrain/wireframe_FS.glsl"));

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
        this.addUniform("splatmap");

        for (int i = 0; i < 8; i++) {
            this.addUniform("lodMorphArea[" + i + "]");
        }

        this.addUniform("tbnRange");

        for (int i = 0; i < 3; i++) {
            this.addUniform("materials[" + i + "].heightMap");
            this.addUniform("materials[" + i + "].heightScaling");
            this.addUniform("materials[" + i + "].horizontalScaling");
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
        terrainNode.getConfig().getHeightMap().bind();
        this.setUniformi("heightmap", 0);

        glActiveTexture(GL_TEXTURE2);
        terrainNode.getConfig().getSplatMap().bind();
        this.setUniformi("splatmap", 2);

        this.setUniformi("tessellationFactor", terrainNode.getConfig().getTessellationFactor());
        this.setUniformf("tessellationSlope", terrainNode.getConfig().getTessellationSlope());
        this.setUniformf("tessellationShift", terrainNode.getConfig().getTessellationShift());

        this.setUniform("cameraPosition", Camera.getInstance().getPosition());
        this.setUniform("m_ViewProjection", Camera.getInstance().getViewProjectionMatrix());
        this.setUniform("localMatrix", gameObject.getLocalTransform().getWorldMatrix());
        this.setUniform("worldMatrix", gameObject.getWorldTransform().getWorldMatrix());

        this.setUniformi("tbnRange", terrainNode.getConfig().getTbnRange());

        int texUnit = 3;
        for (int i = 0; i < 3; i++) {

            //Displace Map
            glActiveTexture(GL_TEXTURE0 + texUnit);
            terrainNode.getConfig().getMaterials().get(i).getDisplaceMap().bind();
            this.setUniformi("materials[" + i + "].heightMap", texUnit);
            texUnit++;

            this.setUniformf("materials[" + i + "].heightScaling", terrainNode.getConfig().getMaterials().get(i).getDisplaceScale());
            this.setUniformf("materials[" + i + "].horizontalScaling", terrainNode.getConfig().getMaterials().get(i).getHorizontalScale());
        }

    }

}
