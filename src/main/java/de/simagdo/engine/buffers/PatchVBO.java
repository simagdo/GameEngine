package de.simagdo.engine.buffers;

import de.simagdo.math.Vec2f;
import utils.BufferUtil;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL40.*;

public class PatchVBO implements VBO {

    private int vboId;
    private int vaoId;
    private int size;

    public PatchVBO() {
        this.vboId = glGenBuffers();
        this.vaoId = glGenVertexArrays();
    }

    public void allocate(Vec2f[] vertices) {
        this.size = vertices.length;

        glBindVertexArray(this.vaoId);
        glBindBuffer(GL_ARRAY_BUFFER, this.vboId);
        glBufferData(GL_ARRAY_BUFFER, BufferUtil.createFlippedBuffer(vertices), GL_STATIC_DRAW);

        glVertexAttribPointer(0, 2, GL_FLOAT, false, Float.BYTES * 2, 0);
        glPatchParameteri(GL_PATCH_VERTICES, this.size);

        glBindVertexArray(0);
    }

    @Override
    public void draw() {
        glBindVertexArray(this.vaoId);
        glEnableVertexAttribArray(0);

        glDrawArrays(GL_PATCHES, 0, this.size);

        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }

    @Override
    public void delete() {
        glBindVertexArray(this.vaoId);
        glDeleteBuffers(this.vboId);
        glDeleteVertexArrays(this.vaoId);
        glBindVertexArray(0);
    }
}
