package de.simagdo.engine.renderer;

import de.simagdo.engine.camera.Camera;
import de.simagdo.engine.configs.DefaultConfig;
import de.simagdo.engine.window.Window;
import de.simagdo.modules.skydome.Skydome;
import de.simagdo.modules.terrain.Terrain;

/**
 * @author Simagdo
 * The RenderingEngine manages the render calls of all 3D entities
 * with shadow rendering and post processing effects
 */
public class RenderingEngine {

    private Window window;
    private Skydome skydome;
    private Terrain terrain;

    public RenderingEngine() {
        window = Window.getInstance();
        skydome = new Skydome();
        this.terrain = new Terrain();
    }

    public void init() {
        window.init();
        this.terrain.init("settings/terrain_settings.txt");
    }

    public void render() {
        Camera.getInstance().update();

        DefaultConfig.clearScreen();

        skydome.render();

        this.terrain.updateQuadtree();
        this.terrain.render();

        // draw into OpenGL window
        window.render();
    }

    public void update() {
    }

    public void shutdown() {
    }
}
