package utils.objLoader;

import java.util.Arrays;

public class Face {

    private int[] indices = new int[3];
    private String material;

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "Face{" +
                "indices=" + Arrays.toString(indices) +
                ", material='" + material + '\'' +
                '}';
    }
}
