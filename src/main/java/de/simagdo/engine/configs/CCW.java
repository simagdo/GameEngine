package de.simagdo.engine.configs;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class CCW implements RenderConfig {

    @Override
    public void enable() {
        glFrontFace(GL_CCW);
    }

    @Override
    public void disable() {
        glFrontFace(GL_CW);
    }
}
