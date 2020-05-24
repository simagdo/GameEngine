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
uniform sampler2D splatmap;
uniform Material[3] materials;
uniform int tbnRange;
uniform vec3 cameraPosition;

const vec3 direction = vec3(0.1, -1.0, 0.1);
const float intensity = 0.8;
const float zFar = 10000;
const float zNear = 0.1;
const vec3 fogColor = vec3(0.65, 0.85, 0.9);
const float sightRange = 0.6;

float diffuse(vec3 direction, vec3 normal, float intensity) {
    return max(0.04, dot(normal, -direction) * intensity);
}

float getFogFactor(float dist) {
    return -0.0002 / sightRange * (dist - zFar / 10 * sightRange) + 1;
}

void main() {

    float dist = length(cameraPosition - positionFS);
    float height = positionFS.y;

    vec3 normal = normalize(texture(normalmap, mapCoordFS).rbg);

    vec4 blendValues = texture(splatmap, mapCoordFS).rgba;

    float[4] blendValuesArray = float[](blendValues.r, blendValues.g, blendValues.b, blendValues.a);

    if (dist < tbnRange - 50) {
        float attenuation = clamp(-dist / (tbnRange - 50) + 1, 0.0, 1.0);

        vec3 bitangent = normalize(cross(tangentFS, normal));
        mat3 TBN = mat3(bitangent, normal, tangentFS);

        vec3 bumpNormal;

        for (int i = 0; i < 3; i++) {
            bumpNormal += (2 * (texture(materials[i].normalMap, mapCoordFS * materials[i].horizontalScaling).rbg) - 1) * blendValuesArray[i];
        }

        bumpNormal = normalize(bumpNormal);

        bumpNormal.xz *= attenuation;

        normal = normalize(TBN * bumpNormal);

    }

    vec3 fragColor = vec3(0, 0, 0);
    for (int i = 0; i< 3;i++){
        fragColor += texture(materials[i].diffuseMap, mapCoordFS * materials[i].horizontalScaling).rgb * blendValuesArray[i];
    }

    float diffuse = diffuse(direction, normal, intensity);

    fragColor *= diffuse;

    float fogFactor = getFogFactor(dist);
    fragColor = mix(fogColor, fragColor, clamp(fogFactor, 0, 1));

    outputColor = vec4(fragColor, 1.0);
}