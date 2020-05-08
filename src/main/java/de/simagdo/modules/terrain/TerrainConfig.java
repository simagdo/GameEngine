package de.simagdo.modules.terrain;

import de.simagdo.engine.model.Material;
import de.simagdo.engine.texturing.Texture2D;
import de.simagdo.modules.gpgpu.NormalMapRenderer;
import de.simagdo.modules.gpgpu.SplatMapRenderer;
import utils.BufferUtil;
import utils.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_FLOAT;

public class TerrainConfig {

    private float scaleY;
    private float scaleXZ;
    private Texture2D heightmap;
    private Texture2D normalmap;
    private Texture2D splatmap;
    private FloatBuffer heightmapDataBuffer;
    private int tessellationFactor;
    private float tessellationSlope;
    private float tessellationShift;
    private int tbn_Range;
    private int[] lod_range = new int[8];
    private int[] lod_morphing_area = new int[8];

    private List<Material> materials = new ArrayList<>();

    public void loadFile(String file) {
        try {
            String[] lines = Utils.getDataAsString(file).split("\n");
            for (String line : lines) {

                String[] tokens = line.split(" ");
                tokens = Utils.removeEmptyStrings(tokens);

                if (tokens.length == 0)
                    continue;

                if (tokens[0].equals("scaleY")) {
                    setScaleY(Float.valueOf(tokens[1]));
                }
                if (tokens[0].equals("scaleXZ")) {
                    setScaleXZ(Float.valueOf(tokens[1]));
                }
                if (tokens[0].equals("heightmap")) {
                    setHeightmap(new Texture2D(tokens[1]));
                    getHeightmap().bind();
                    getHeightmap().bilinearFilter();

                    createHeightmapDataBuffer();

                    NormalMapRenderer normalRenderer = new NormalMapRenderer(getHeightmap().getWidth());
                    normalRenderer.setStrength(60);
                    normalRenderer.render(getHeightmap());
                    setNormalmap(normalRenderer.getNormalmap());

                    SplatMapRenderer splatmapRenderer = new SplatMapRenderer(getHeightmap().getWidth());
                    splatmapRenderer.render(getNormalmap());
                    setSplatmap(splatmapRenderer.getSplatmap());
                }
                if (tokens[0].equals("tessellationFactor")) {
                    setTessellationFactor(Integer.valueOf(tokens[1]));
                }
                if (tokens[0].equals("tessellationSlope")) {
                    setTessellationSlope(Float.valueOf(tokens[1]));
                }
                if (tokens[0].equals("tessellationShift")) {
                    setTessellationShift(Float.valueOf(tokens[1]));
                }
                if (tokens[0].equals("#lod_ranges")) {
                    for (int i = 0; i < 8; i++) {
                        tokens = line.split(" ");
                        tokens = Utils.removeEmptyStrings(tokens);
                        if (tokens[0].equals("lod" + (i + 1) + "_range")) {
                            if (Integer.valueOf(tokens[1]) == 0) {
                                lod_range[i] = 0;
                                lod_morphing_area[i] = 0;
                            } else {
                                setLodRange(i, Integer.valueOf(tokens[1]));
                            }
                        }
                    }
                }
                if (tokens[0].equals("material")) {
                    getMaterials().add(new Material());
                }
                if (tokens[0].equals("material_DIF")) {
                    Texture2D diffusemap = new Texture2D(tokens[1]);
                    diffusemap.bind();
                    diffusemap.trilinearFilter();
                    getMaterials().get(materials.size() - 1).setDiffusemap(diffusemap);
                }
                if (tokens[0].equals("material_NRM")) {
                    Texture2D normalmap = new Texture2D(tokens[1]);
                    normalmap.bind();
                    normalmap.trilinearFilter();
                    getMaterials().get(materials.size() - 1).setNormalmap(normalmap);
                }
                if (tokens[0].equals("material_DISP")) {
                    Texture2D heightmap = new Texture2D(tokens[1]);
                    heightmap.bind();
                    heightmap.trilinearFilter();
                    getMaterials().get(materials.size() - 1).setDisplacemap(heightmap);
                }
                if (tokens[0].equals("material_heightScaling")) {
                    getMaterials().get(materials.size() - 1).setDisplaceScale(Float.valueOf(tokens[1]));
                }
                if (tokens[0].equals("material_horizontalScaling")) {
                    getMaterials().get(materials.size() - 1).setHorizontalScale(Float.valueOf(tokens[1]));
                }
                if (tokens[0].equals("tbn_Range")) {
                    setTbn_Range(Integer.valueOf(tokens[1]));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private int updateMorphingArea(int lod) {
        return (int) ((scaleXZ / TerrainQuadtree.getRootNodes()) / (Math.pow(2, lod)));
    }

    public void createHeightmapDataBuffer() {

        heightmapDataBuffer = BufferUtil.createFloatBuffer(getHeightmap().getWidth() * getHeightmap().getHeight());
        heightmap.bind();
        glGetTexImage(GL_TEXTURE_2D, 0, GL_RED, GL_FLOAT, heightmapDataBuffer);
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

    public void setLodRange(int index, int lod_range) {
        this.lod_range[index] = lod_range;
        lod_morphing_area[index] = lod_range - updateMorphingArea(index + 1);
    }

    public int[] getLod_morphing_area() {
        return lod_morphing_area;
    }

    public int[] getLod_range() {
        return lod_range;
    }

    public int getTessellationFactor() {
        return tessellationFactor;
    }

    public void setTessellationFactor(int tessellationFactor) {
        this.tessellationFactor = tessellationFactor;
    }

    public float getTessellationSlope() {
        return tessellationSlope;
    }

    public void setTessellationSlope(float tessellationSlope) {
        this.tessellationSlope = tessellationSlope;
    }

    public float getTessellationShift() {
        return tessellationShift;
    }

    public void setTessellationShift(float tessellationShift) {
        this.tessellationShift = tessellationShift;
    }

    public Texture2D getHeightmap() {
        return heightmap;
    }

    public void setHeightmap(Texture2D heightmap) {
        this.heightmap = heightmap;
    }

    public Texture2D getNormalmap() {
        return normalmap;
    }

    public void setNormalmap(Texture2D normalmap) {
        this.normalmap = normalmap;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public int getTbn_Range() {
        return tbn_Range;
    }

    public void setTbn_Range(int tbn_Range) {
        this.tbn_Range = tbn_Range;
    }

    public Texture2D getSplatmap() {
        return splatmap;
    }

    public void setSplatmap(Texture2D splatmap) {
        this.splatmap = splatmap;
    }

    public FloatBuffer getHeightmapDataBuffer() {
        return heightmapDataBuffer;
    }

    public void setHeightmapDataBuffer(FloatBuffer heightmapDataBuffer) {
        this.heightmapDataBuffer = heightmapDataBuffer;
    }

}
