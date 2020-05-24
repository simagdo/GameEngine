package de.simagdo.engine.renderer;

import de.simagdo.engine.camera.Camera;
import de.simagdo.engine.configs.DefaultConfig;
import de.simagdo.engine.input.Input;
import de.simagdo.engine.window.Window;
import de.simagdo.modules.sky.Skydome;
import de.simagdo.modules.terrain.Terrain;
import org.lwjgl.glfw.GLFW;

/**
 * @author Simagdo
 * The RenderingEngine manages the render calls of all 3D entities
 * with shadow rendering and post processing effects
 */
public class RenderingEngine {

    private final Window window;
    private final Skydome skydome;
    private final Terrain terrain;

    public RenderingEngine() {
        this.window = Window.getInstance();
        this.skydome = new Skydome();
        this.terrain = new Terrain();
    }

    public void init() {
        this.window.init();
        this.terrain.init("settings/terrain_settings.txt");
    }

    public void render() {
        Camera.getInstance().update();

        DefaultConfig.clearScreen();

        this.skydome.render();

        this.terrain.updateQuadtree();
        this.terrain.render();

        // draw into OpenGL window
        this.window.render();
    }

    public void update() {
        if (Input.getInstance().isKeyPushed(GLFW.GLFW_KEY_E)) {
            if (RenderContext.getInstance().isWireFrame()) {
                RenderContext.getInstance().setWireFrame(false);
            } else {
                RenderContext.getInstance().setWireFrame(true);
            }
        }
    }

    public void shutdown() {
    }
}