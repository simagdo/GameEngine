#version 430

layout(location = 0) out vec4 outputColor;

in vec2 mapCoordFS;

uniform sampler2D normalmap;

const vec3 lightDirection = vec3(0.1, -1.0, 0.1);
const float intensity = 1.2;

float diffuse(vec3 direction, vec3 normal, float intensity) {
    return max(0.01, dot(normal, - direction) * intensity);
}

void main() {

    vec3 normal = texture(normalmap, mapCoordFS).rgb;

    float diff = diffuse(lightDirection, normal, intensity);

    vec3 rgb = vec3(0.1, 1.0, 0.1) * diff;

    outputColor = vec4(rgb, 1.0);
}