package de.simagdo.engine.buffers;

import de.simagdo.engine.model.Mesh;

public interface VBO {

    public void allocate(Mesh mesh);

    public void draw();

    public void delete();

}
