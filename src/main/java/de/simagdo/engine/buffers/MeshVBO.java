package de.simagdo.engine.buffers;

import de.simagdo.engine.model.Mesh;
import utils.BufferUtil;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class MeshVBO implements VBO {

    private int vboId;
    private int inticesId;
    private int vaoId;
    private int size;

    public MeshVBO() {
        this.vboId = glGenBuffers();
        this.inticesId = glGenBuffers();
        this.vaoId = glGenVertexArrays();
    }

    public void allocate(Mesh mesh) {
        this.size = mesh.getIndices().length;
        glBindVertexArray(this.vaoId);
        glBindBuffer(GL_ARRAY_BUFFER, this.vboId);
        glBufferData(GL_ARRAY_BUFFER, BufferUtil.createFlippedBufferAOS(mesh.getVertices()), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.inticesId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtil.createFlippedBuffer(mesh.getIndices()), GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, Float.BYTES * 8, 0);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, Float.BYTES * 8, Float.BYTES * 3);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, Float.BYTES * 8, Float.BYTES * 6);

        glBindVertexArray(0);
    }

    @Override
    public void draw() {
        glBindVertexArray(this.vaoId);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glDrawElements(GL_TRIANGLES, this.size, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);

        glBindVertexArray(0);
    }

    @Override
    public void delete() {
        glBindVertexArray(this.vaoId);
        glDeleteBuffers(this.vboId);
        glDeleteBuffers(this.inticesId);
        glDeleteVertexArrays(this.vaoId);
        glBindVertexArray(0);
    }
}
