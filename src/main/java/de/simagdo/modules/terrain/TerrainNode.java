package de.simagdo.modules.terrain;

import de.simagdo.engine.buffers.PatchVBO;
import de.simagdo.engine.camera.Camera;
import de.simagdo.engine.configs.DefaultConfig;
import de.simagdo.engine.renderer.RenderInfo;
import de.simagdo.engine.renderer.Renderer;
import de.simagdo.engine.scene.GameObject;
import de.simagdo.engine.scene.Node;
import de.simagdo.math.Vec2f;
import de.simagdo.math.Vec3f;
import utils.Constants;

public class TerrainNode extends GameObject {

    private boolean isLeaf;
    private TerrainConfig config;
    private int lod;
    private Vec2f location;
    private Vec3f worldPosition;
    private Vec2f index;
    private float gap;
    private PatchVBO buffer;

    public TerrainNode(PatchVBO buffer, TerrainConfig config, Vec2f location, int lod, Vec2f index) {
        this.buffer = buffer;
        this.config = config;
        this.location = location;
        this.lod = lod;
        this.index = index;
        this.gap = 1f / (TerrainQuadtree.ROOT_NODES * (float) (Math.pow(2, this.lod)));

        Vec3f localScaling = new Vec3f(this.gap, 0, this.gap);
        Vec3f localTranslation = new Vec3f(this.location.getX(), 0, this.location.getY());

        this.getLocalTransform().setScaling(localScaling);
        this.getLocalTransform().setTranslation(localTranslation);

        this.getWorldTransform().setScaling(new Vec3f(this.config.getScaleXZ(), this.config.getScaleY(), this.config.getScaleXZ()));
        this.getWorldTransform().setTranslation(new Vec3f(-this.config.getScaleXZ() / 2f, 0, -this.config.getScaleXZ() / 2f));

        Renderer renderer = new Renderer();
        renderer.setVbo(buffer);
        renderer.setRenderInfo(new RenderInfo(new DefaultConfig(), TerrainShader.getInstance()));

        this.addComponent(Constants.RENDERER_COMPONENT, renderer);

        this.computeWorldPosition();
        this.updateQuadtree();
    }

    public void render() {
        if (this.isLeaf()) {
            this.getComponents().get(Constants.RENDERER_COMPONENT).render();
        }

        this.getChildren().forEach(Node::render);

    }

    //TODO optimize this code
    public void updateQuadtree() {
        if (Camera.getInstance().getPosition().getY() > this.config.getScaleY())
            this.worldPosition.setY(this.config.getScaleY());
        else this.worldPosition.setY(Camera.getInstance().getPosition().getY());

        this.updateChildNodes();

        this.getChildren().forEach(child -> ((TerrainNode) child).updateQuadtree());
    }

    private void addChildNodes(int lod) {
        if (this.isLeaf()) {
            this.setLeaf(false);
        }

        if (this.getChildren().size() == 0) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    this.addChild(new TerrainNode(this.buffer, this.config, this.location.add(new Vec2f(i * this.gap / 2f, j * gap / 2f)), lod, new Vec2f(i, j)));
                }
            }
        }

    }

    private void removeChildNodes() {
        if (!this.isLeaf()) {
            this.setLeaf(true);
        }

        if (this.getChildren().size() != 0) {
            this.getChildren().clear();
        }
    }

    private void updateChildNodes() {
        float distance = (Camera.getInstance().getPosition().sub(this.worldPosition)).length();

        if (distance < this.config.getLodRange()[this.lod]) this.addChildNodes(this.lod + 1);
        else if (distance >= this.config.getLodRange()[this.lod]) this.removeChildNodes();
    }

    public void computeWorldPosition() {
        Vec2f location = this.location.add(this.gap / 2f).mul(this.config.getScaleXZ()).sub(this.config.getScaleXZ() / 2f);

        this.worldPosition = new Vec3f(location.getX(), 0, location.getY());
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public TerrainConfig getConfig() {
        return config;
    }

    public void setConfig(TerrainConfig config) {
        this.config = config;
    }

    public int getLod() {
        return lod;
    }

    public void setLod(int lod) {
        this.lod = lod;
    }

    public Vec2f getLocation() {
        return location;
    }

    public void setLocation(Vec2f location) {
        this.location = location;
    }

    public Vec3f getWorldPosition() {
        return worldPosition;
    }

    public void setWorldPosition(Vec3f worldPosition) {
        this.worldPosition = worldPosition;
    }

    public Vec2f getIndex() {
        return index;
    }

    public void setIndex(Vec2f index) {
        this.index = index;
    }

    public float getGap() {
        return gap;
    }

    public void setGap(float gap) {
        this.gap = gap;
    }

    public PatchVBO getBuffer() {
        return buffer;
    }

    public void setBuffer(PatchVBO buffer) {
        this.buffer = buffer;
    }

    public TerrainNode getQuadtreeParent() {
        return (TerrainNode) getParent();
    }

}
