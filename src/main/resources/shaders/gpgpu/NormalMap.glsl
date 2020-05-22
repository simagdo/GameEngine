#version 430 core

layout (local_size_x = 16, local_size_y = 16) in;

layout (binding = 0, rgba32f) uniform writeonly image2D normalmap;

uniform sampler2D heightmap;
uniform int n;
uniform float strength;

void main(void) {

    // x0 -- z1 -- z2
    // |     |      |
    // z3 -- h -- z4
    // |     |      |
    // z5 -- z6 -- z7

    ivec2 x = ivec2(gl_GlobalInvocationID.xy);
    vec2 texCoord = gl_GlobalInvocationID.xy / float(n);

}