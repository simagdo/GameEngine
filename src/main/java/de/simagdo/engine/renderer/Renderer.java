package de.simagdo.engine.renderer;

import de.simagdo.engine.buffers.VBO;
import de.simagdo.engine.scene.Component;

public class Renderer extends Component {

    private VBO vbo;
    private RenderInfo renderInfo;

    public void render() {
        this.renderInfo.getConfig().enable();
        this.renderInfo.getShader().bind();

        this.renderInfo.getShader().updateUniforms(this.getParent());

        this.getVbo().draw();
        this.renderInfo.getConfig().disable();
    }

    public VBO getVbo() {
        return vbo;
    }

    public void setVbo(VBO vbo) {
        this.vbo = vbo;
    }

    public RenderInfo getRenderInfo() {
        return renderInfo;
    }

    public void setRenderInfo(RenderInfo renderInfo) {
        this.renderInfo = renderInfo;
    }

    @Override
    public String toString() {
        return "Renderer{" +
                "vbo=" + vbo +
                ", renderInfo=" + renderInfo +
                '}';
    }
}
