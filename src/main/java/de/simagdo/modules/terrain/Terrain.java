package de.simagdo.modules.terrain;

import de.simagdo.engine.camera.Camera;
import de.simagdo.engine.scene.Node;

public class Terrain extends Node {

    private TerrainConfig config;

    public void init(String config) {
        this.config = new TerrainConfig();
        this.config.loadFile(config);

        this.addChild(new TerrainQuadtree(this.config));
    }

    public void updateQuadtree() {
        if (Camera.getInstance().isCameraMoved()) ((TerrainQuadtree) this.getChildren().get(0)).updateQuadtree();
    }

    public TerrainConfig getConfig() {
        return config;
    }

    public void setConfig(TerrainConfig config) {
        this.config = config;
    }
}
