#version 430

layout(triangles) in;
layout(triangle_strip, max_vertices = 4) out;

in vec2[] mapCoordGS;

out vec2 mapCoordFS;

uniform mat4 m_ViewProjection;

void main() {

    for (int i = 0; i < gl_in.length(); i++) {
        vec4 position = gl_in[i].gl_Position;
        gl_Position = m_ViewProjection * position;
        mapCoordFS = mapCoordGS[i];
        EmitVertex();
    }

    EndPrimitive();
}