package utils.objLoader;

import de.simagdo.engine.model.Vertex;

import java.util.ArrayList;

public class Polygon {

    private ArrayList<Vertex> vertices;
    private ArrayList<Integer> indices;
    private String material = null;

    public Polygon() {
        this.vertices = new ArrayList<>();
        this.indices = new ArrayList<>();
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices = vertices;
    }

    public ArrayList<Integer> getIndices() {
        return indices;
    }

    public void setIndices(ArrayList<Integer> indices) {
        this.indices = indices;
    }

}
