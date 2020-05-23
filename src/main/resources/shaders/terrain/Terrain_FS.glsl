#version 330

layout(location = 0) out vec4 outputColor;

in vec2 mapCoordFS;
in vec3 positionFS;
in vec3 tangentFS;

struct Material {
    sampler2D diffuseMap;
    sampler2D normalMap;
    sampler2D heightMap;
    float heightScaling;
    float horizontalScaling;
};

uniform sampler2D normalmap;
uniform Material[2] materials;
uniform int tbnRange;
uniform vec3 cameraPosition;

const vec3 direction = vec3(0.1, -1.0, 0.1);
const float intensity = 1.2;

float diffuse(vec3 direction, vec3 normal, float intensity) {
    return max(0.04, dot(normal, -direction) * intensity);
}

void main() {

    float dist = length(cameraPosition - positionFS);
    float height = positionFS.y;

    vec3 normal = normalize(texture(normalmap, mapCoordFS).rgb);

    vec3 materialColor0 = texture(materials[0].diffuseMap, mapCoordFS * materials[0].horizontalScaling).rgb;
    vec3 materialColor1 = texture(materials[1].diffuseMap, mapCoordFS * materials[1].horizontalScaling).rgb;

    float[2] materialAlpha = float[](0, 0);

    if (normal.y > 0.5) {
        materialAlpha[1] = 1;
    } else {
        materialAlpha[0] = 1;
    }

    if (dist < tbnRange - 50) {
        float attenuation = clamp(-dist / (tbnRange - 50) + 1, 0.0, 1.0);

        vec3 bitangent = normalize(cross(tangentFS, normal));
        mat3 TBN = mat3(bitangent, normal, tangentFS);

        vec3 bumpNormal;

        for (int i = 0; i < 2; i++) {
            bumpNormal += (2 * (texture(materials[i].normalMap, mapCoordFS * materials[i].horizontalScaling).rbg) - 1) * materialAlpha[i];
        }

        bumpNormal = normalize(bumpNormal);

        bumpNormal.xz *= attenuation;

        normal = normalize(TBN * bumpNormal);

    }

    vec3 fragColor = materialColor0 * materialAlpha[0] + materialColor1 * materialAlpha[1];

    float diffuse = diffuse(direction, normal, intensity);

    fragColor *= diffuse;

    outputColor = vec4(fragColor, 1.0);
}