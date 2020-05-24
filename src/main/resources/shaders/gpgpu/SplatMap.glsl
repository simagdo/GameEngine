#version 430 core

layout(local_size_x = 16, local_size_y = 16) in;

layout(binding = 0, rgba16f) uniform writeonly image2D splatmap;

uniform sampler2D normalmap;
uniform int n;

void main(void) {

    ivec2 x = ivec2(gl_GlobalInvocationID.xy);
    vec2 x_inv = gl_GlobalInvocationID.xy / float(n);

    vec3 normal = normalize(texture(normalmap, x_inv).rgb);

    float slopeFactor = normal.z;

    vec4 blendValues = vec4(0, 0, 0, 0);

    if (slopeFactor > 0.5) {
        blendValues.x = 1.0;
    } else if (slopeFactor > 0.35){
        blendValues.y = 1.0;
    } else {
        blendValues.z = 1.0;
    }

    imageStore(splatmap, x, blendValues);
}