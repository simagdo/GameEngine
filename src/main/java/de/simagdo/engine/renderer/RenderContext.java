package de.simagdo.engine.renderer;

public class RenderContext {

    private static RenderContext instance = null;
    private boolean wireFrame;

    public static RenderContext getInstance() {
        if (instance == null) instance = new RenderContext();
        return instance;
    }

    public boolean isWireFrame() {
        return wireFrame;
    }

    public void setWireFrame(boolean wireFrame) {
        this.wireFrame = wireFrame;
    }
}
