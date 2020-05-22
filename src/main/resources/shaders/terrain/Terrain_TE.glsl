#version 430

layout(quads, fractional_odd_spacing, cw) in;

in vec2[] mapCoordTE;

out vec2 mapCoordGS;

uniform sampler2D heightmap;
uniform float scaleY;

void main() {
    float u = gl_TessCoord.x;
    float v = gl_TessCoord.y;

    vec4 position =
    ((1 - u) * (1 - v)* gl_in[12].gl_Position +
    u * (1 - v) * gl_in[0].gl_Position +
    u * v * gl_in[3].gl_Position +
    (1 - u) * v * gl_in[15].gl_Position);

    vec2 mapCoord =
    ((1 - u) * (1 - v) * mapCoordTE[12] +
    u * (1 - v) * mapCoordTE[0] +
    u * v * mapCoordTE[3] +
    (1 - u) * v * mapCoordTE[15]);

    float height = texture(heightmap, mapCoord).r;
    height *= scaleY;

    position.y = height;

    //mapCoordGS = mapCoord;

    gl_Position = position;
}