package de.simagdo.modules.skydome;

import de.simagdo.engine.buffers.MeshVBO;
import de.simagdo.engine.configs.CCW;
import de.simagdo.engine.model.Mesh;
import de.simagdo.engine.renderer.RenderInfo;
import de.simagdo.engine.renderer.Renderer;
import de.simagdo.engine.scene.GameObject;
import utils.Constants;
import utils.objLoader.OBJLoader;

public class Skydome extends GameObject {

    public Skydome() {
        this.getWorldTransformation().setScaling(Constants.ZFAR * 0.5f, Constants.ZFAR * 0.5f, Constants.ZFAR * 0.5f);
        Mesh mesh = new OBJLoader().load("models/dome", "dome.obj", null)[0].getMesh();
        MeshVBO meshBuffer = new MeshVBO();
        meshBuffer.allocate(mesh);
        Renderer renderer = new Renderer();
        renderer.setVbo(meshBuffer);
        renderer.setRenderInfo(new RenderInfo(new CCW(), AtmosphereShader.getInstance()));
        addComponent(Constants.RENDERER_COMPONENT, renderer);
    }

}
