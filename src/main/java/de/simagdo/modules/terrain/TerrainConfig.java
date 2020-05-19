package de.simagdo.modules.terrain;

import utils.Utils;

import java.util.Arrays;

public class TerrainConfig {

    private float scaleY;
    private float scaleXZ;
    private int[] lodRange = new int[8];
    private int[] lodMorphingArea = new int[8];

    public void loadFile(String file) {
        String[] lines = Utils.getDataAsString(file).split("\n");

        for (String line : lines) {
            String[] tokens = Utils.removeEmptyStrings(line.split(" "));

            if (tokens.length == 0) continue;

            switch (tokens[0]) {
                case "scaleY":
                    this.setScaleY(Float.parseFloat(tokens[1]));
                    break;
                case "scaleXZ":
                    this.setScaleXZ(Float.parseFloat(tokens[1]));
                    break;
                case "#lod_ranges":
                    for (int i = 0; i < 8; i++) {
                        tokens = line.split(" ");
                        tokens = Utils.removeEmptyStrings(tokens);
                        if (tokens[0].equals("lod" + (i + 1) + "_range")) {
                            if (Integer.parseInt(tokens[1]) == 0) {
                                this.lodRange[i] = 0;
                                this.lodMorphingArea[i] = 0;
                            } else {
                                this.setLodRange(i, Integer.parseInt(tokens[1]));
                            }
                        }
                    }
                    break;
            }

        }

    }

    private int updateMorphingAreas(int lod) {
        return (int) ((this.scaleXZ / TerrainQuadtree.ROOT_NODES) / Math.pow(2, lod));
    }

    private void setLodRange(int index, int lodRange) {
        this.lodRange[index] = lodRange;
        this.lodMorphingArea[index] = lodRange - this.updateMorphingAreas(index + 1);
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public float getScaleXZ() {
        return scaleXZ;
    }

    public void setScaleXZ(float scaleXZ) {
        this.scaleXZ = scaleXZ;
    }

    public int[] getLodRange() {
        return lodRange;
    }

    public void setLodRange(int[] lodRange) {
        this.lodRange = lodRange;
    }

    public int[] getLodMorphingArea() {
        return lodMorphingArea;
    }

    public void setLodMorphingArea(int[] lodMorphingArea) {
        this.lodMorphingArea = lodMorphingArea;
    }

    @Override
    public String toString() {
        return "TerrainConfig{" +
                "scaleY=" + scaleY +
                ", scaleXZ=" + scaleXZ +
                ", lodRange=" + Arrays.toString(lodRange) +
                ", lodMorphingArea=" + Arrays.toString(lodMorphingArea) +
                '}';
    }
}
