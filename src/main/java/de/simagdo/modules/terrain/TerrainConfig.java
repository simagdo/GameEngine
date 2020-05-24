package de.simagdo.modules.terrain;

import de.simagdo.engine.model.Material;
import de.simagdo.engine.texturing.Texture2D;
import de.simagdo.modules.gpgpu.NormalMapRenderer;
import de.simagdo.modules.gpgpu.SplatMapRenderer;
import org.lwjgl.opengl.GL11;
import utils.BufferUtil;
import utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class TerrainConfig {

    private float scaleY;
    private float scaleXZ;
    private int[] lodRange = new int[8];
    private int[] lodMorphingArea = new int[8];
    private int tessellationFactor;
    private float tessellationSlope;
    private float tessellationShift;
    private Texture2D heightMap;
    private Texture2D normalMap;
    private Texture2D splatMap;
    private FloatBuffer heightMapDataBuffer;
    private int tbnRange;
    private List<Material> materials = new ArrayList<>();

    public void loadFile(String file) {
        try {
            BufferedReader reader = Utils.getReader(file);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                tokens = Utils.removeEmptyStrings(tokens);

                if (tokens.length == 0) continue;

                switch (tokens[0]) {
                    case "scaleY":
                        this.setScaleY(Float.parseFloat(tokens[1]));
                        break;
                    case "scaleXZ":
                        this.setScaleXZ(Float.parseFloat(tokens[1]));
                        break;
                    case "tessellationFactor":
                        this.setTessellationFactor(Integer.parseInt(tokens[1]));
                        break;
                    case "tessellationSlope":
                        this.setTessellationSlope(Float.parseFloat(tokens[1]));
                        break;
                    case "tessellationShift":
                        this.setTessellationShift(Float.parseFloat(tokens[1]));
                        break;
                    case "#lod_ranges":
                        for (int i = 0; i < 8; i++) {
                            line = reader.readLine();
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
                    case "heightmap":
                        this.setHeightMap(new Texture2D(tokens[1]));
                        this.getHeightMap().bind();
                        this.getHeightMap().bilinearFilter();

                        this.createHeightMapDataBuffer();

                        NormalMapRenderer renderer = new NormalMapRenderer(this.getHeightMap().getWidth());
                        renderer.setStrength(60);
                        renderer.render(this.getHeightMap());
                        this.setNormalMap(renderer.getNormalmap());

                        SplatMapRenderer splatMapRenderer = new SplatMapRenderer(this.getHeightMap().getWidth());
                        splatMapRenderer.render(this.getNormalMap());
                        this.setSplatMap(splatMapRenderer.getSplatMap());

                        break;
                    case "tbnRange":
                        this.setTbnRange(Integer.parseInt(tokens[1]));
                        break;
                    case "material":
                        this.getMaterials().add(new Material());
                        break;
                    case "materialDIF":
                        Texture2D diffuseMap = new Texture2D(tokens[1]);
                        diffuseMap.bind();
                        diffuseMap.trilinearFilter();
                        this.getMaterials().get(this.materials.size() - 1).setDiffuseMap(diffuseMap);
                        break;
                    case "materialNRM":
                        Texture2D normalMap = new Texture2D(tokens[1]);
                        normalMap.bind();
                        normalMap.trilinearFilter();
                        this.getMaterials().get(this.materials.size() - 1).setNormalMap(normalMap);
                        break;
                    case "materialDISP":
                        Texture2D displaceMap = new Texture2D(tokens[1]);
                        displaceMap.bind();
                        displaceMap.trilinearFilter();
                        this.getMaterials().get(this.materials.size() - 1).setDisplacMmap(displaceMap);
                        break;
                    case "materialHeightScaling":
                        this.getMaterials().get(this.materials.size() - 1).setDisplaceScale(Float.parseFloat(tokens[1]));
                        break;
                    case "materialHorizontalScaling":
                        this.getMaterials().get(this.materials.size() - 1).setHorizontalScale(Float.parseFloat(tokens[1]));
                        break;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int updateMorphingArea(int lod) {
        return (int) ((this.scaleXZ / TerrainQuadtree.ROOT_NODES) / (Math.pow(2, lod)));
    }

    private void setLodRange(int index, int lodRange) {
        this.lodRange[index] = lodRange;
        this.lodMorphingArea[index] = lodRange - this.updateMorphingArea(index + 1);
    }

    public void createHeightMapDataBuffer() {
        this.heightMapDataBuffer = BufferUtil.createFloatBuffer(this.getHeightMap().getWidth() * this.getHeightMap().getHeight());
        this.getHeightMap().bind();
        glGetTexImage(GL_TEXTURE_2D, 0, GL_RED, GL_FLOAT, this.getHeightMapDataBuffer());
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

    public Texture2D getHeightMap() {
        return heightMap;
    }

    public void setHeightMap(Texture2D heightMap) {
        this.heightMap = heightMap;
    }

    public Texture2D getNormalMap() {
        return normalMap;
    }

    public void setNormalMap(Texture2D normalMap) {
        this.normalMap = normalMap;
    }

    public int getTbnRange() {
        return tbnRange;
    }

    public void setTbnRange(int tbnRange) {
        this.tbnRange = tbnRange;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public Texture2D getSplatMap() {
        return splatMap;
    }

    public void setSplatMap(Texture2D splatMap) {
        this.splatMap = splatMap;
    }

    public FloatBuffer getHeightMapDataBuffer() {
        return heightMapDataBuffer;
    }

    public void setHeightMapDataBuffer(FloatBuffer heightMapDataBuffer) {
        this.heightMapDataBuffer = heightMapDataBuffer;
    }
}
