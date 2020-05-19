package de.simagdo.modules.terrain;

import de.simagdo.engine.buffers.PatchVBO;
import de.simagdo.engine.scene.Node;
import de.simagdo.math.Vec2f;
import de.simagdo.math.Vec3f;

public class TerrainQuadtree extends Node {

    public static final int ROOT_NODES = 8;

    public TerrainQuadtree(TerrainConfig config) {

        PatchVBO buffer = new PatchVBO();
        buffer.allocate(this.generatePatch());

        for (int i = 0; i < ROOT_NODES; i++) {
            for (int j = 0; j < ROOT_NODES; j++) {
                this.addChild(new TerrainNode(buffer, config, new Vec2f(i / (float) ROOT_NODES, j / (float) ROOT_NODES), 0, new Vec2f(i, j)));
            }
        }

        this.getWorldTransform().setScaling(new Vec3f(config.getScaleXZ(), config.getScaleY(), config.getScaleXZ()));
        this.getWorldTransform().setTranslation(new Vec3f(config.getScaleXZ() / 2f, 0, config.getScaleXZ() / 2f));
    }

    public void updateQuadTree() {
        this.getChildren().forEach(child -> ((TerrainNode) child).updateQuadTree());
    }

    public Vec2f[] generatePatch() {
        Vec2f[] vertices = new Vec2f[16];
        int index = 0;

        vertices[index++] = new Vec2f(0, 0);
        vertices[index++] = new Vec2f(0.333f, 0);
        vertices[index++] = new Vec2f(0.666f, 0);
        vertices[index++] = new Vec2f(1, 0);

        vertices[index++] = new Vec2f(0, 0.333f);
        vertices[index++] = new Vec2f(0.333f, 0.333f);
        vertices[index++] = new Vec2f(0.666f, 0.333f);
        vertices[index++] = new Vec2f(1, 0.333f);

        vertices[index++] = new Vec2f(0, 0);
        vertices[index++] = new Vec2f(0.333f, 0.666f);
        vertices[index++] = new Vec2f(0.666f, 0.666f);
        vertices[index++] = new Vec2f(1, 0.666f);

        vertices[index++] = new Vec2f(0, 1);
        vertices[index++] = new Vec2f(0.333f, 1);
        vertices[index++] = new Vec2f(0.666f, 1);
        vertices[index++] = new Vec2f(1, 1);

        return vertices;
    }

}
