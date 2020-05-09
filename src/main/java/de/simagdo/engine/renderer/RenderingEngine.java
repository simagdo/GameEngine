package de.simagdo.engine.renderer;

import de.simagdo.engine.camera.Camera;
import de.simagdo.engine.configs.DefaultConfig;
import de.simagdo.engine.window.Window;
import de.simagdo.modules.Skydome;

/**
 * @author Simagdo
 * The RenderingEngine manages the render calls of all 3D entities
 * with shadow rendering and post processing effects
 */
public class RenderingEngine {

    private Window window;
    private Skydome skydome;

    public RenderingEngine() {
        window = Window.getInstance();
        skydome = new Skydome();
    }

    public void init() {
        window.init();
    }

    public void render() {
        Camera.getInstance().update();

        DefaultConfig.clearScreen();

        skydome.render();

        // draw into OpenGL window
        window.render();
    }

    public void update() {
    }

    public void shutdown() {
    }
}