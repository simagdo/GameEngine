package de.simagdo.engine.renderer;

public class RenderContext {

    private static RenderContext instance = null;
private boolean wireframe;

    public static RenderContext getInstance() {
        return instance == null ? new RenderContext() : instance;
    }

    public boolean isWireframe() {
        return wireframe;
    }

    public void setWireframe(boolean wireframe) {
        this.wireframe = wireframe;
    }
}
