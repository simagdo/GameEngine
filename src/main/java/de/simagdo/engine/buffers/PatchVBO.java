package de.simagdo.engine.buffers;

import de.simagdo.math.Vec2f;
import utils.BufferUtil;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL40.*;

public class PatchVBO implements VBO {

    private int vbo;
    private int vaoId;
    private int size;

    public PatchVBO() {
        vbo = glGenBuffers();
        vaoId = glGenVertexArrays();
        size = 0;
    }

    public void allocate(Vec2f[] vertices, int patchsize) {
        size = vertices.length;

        glBindVertexArray(vaoId);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, BufferUtil.createFlippedBuffer(vertices), GL_STATIC_DRAW);

        glVertexAttribPointer(0, 2, GL_FLOAT, false, Float.BYTES * 2, 0);
        glPatchParameteri(GL_PATCH_VERTICES, patchsize);

        glBindVertexArray(0);
    }

    public void draw() {
        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);

        glDrawArrays(GL_PATCHES, 0, size);

        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }

    public void delete() {
        glBindVertexArray(vaoId);
        glDeleteBuffers(vbo);
        glDeleteVertexArrays(vaoId);
        glBindVertexArray(0);
    }

}