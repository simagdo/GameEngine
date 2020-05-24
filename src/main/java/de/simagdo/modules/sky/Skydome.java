package de.simagdo.modules.sky;

import de.simagdo.engine.buffers.MeshVBO;
import de.simagdo.engine.configs.CCW;
import de.simagdo.engine.model.Mesh;
import de.simagdo.engine.renderer.RenderContext;
import de.simagdo.engine.renderer.RenderInfo;
import de.simagdo.engine.renderer.Renderer;
import de.simagdo.engine.scene.GameObject;
import de.simagdo.math.Vec3f;
import utils.Constants;
import utils.objLoader.OBJLoader;

public class Skydome extends GameObject {

    public Skydome() {
        Mesh mesh = new OBJLoader().load("models/dome", "dome.obj", null)[0].getMesh();
        MeshVBO meshBuffer = new MeshVBO();
        meshBuffer.allocate(mesh);

        Renderer renderer = new Renderer();
        renderer.setVbo(meshBuffer);
        renderer.setRenderInfo(new RenderInfo(new CCW(), AtmosphereShader.getInstance()));
        this.addComponent(Constants.RENDERER_COMPONENT, renderer);

        this.getWorldTransform().setScaling(new Vec3f(Constants.ZFAR * 0.5f, Constants.ZFAR * 0.5f, Constants.ZFAR * 0.5f));
    }

    @Override
    public void render() {
        if (RenderContext.getInstance().isWireFrame()) {
            super.render();
        }
    }
}
