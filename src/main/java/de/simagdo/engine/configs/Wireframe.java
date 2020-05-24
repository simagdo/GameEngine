package de.simagdo.engine.configs;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class Wireframe implements RenderConfig {

    @Override
    public void enable() {

        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

    }

    @Override
    public void disable() {
        glPolygonMode(GL_FRONT, GL_FILL);
    }
}
