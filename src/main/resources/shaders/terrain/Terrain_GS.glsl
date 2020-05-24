#version 430

layout(triangles) in;
layout(triangle_strip, max_vertices = 3) out;

struct Material {
    sampler2D diffuseMap;
    sampler2D normalMap;
    sampler2D heightMap;
    float heightScaling;
    float horizontalScaling;
};

in vec2[] mapCoordGS;

out vec2 mapCoordFS;
out vec3 positionFS;
out vec3 tangentFS;

uniform mat4 m_ViewProjection;
uniform sampler2D normalMap;
uniform sampler2D splatmap;
uniform vec3 cameraPosition;
uniform Material[3] materials;
uniform int tbnRange;

vec3 tangent;
vec3[3] displacement;

void calcTangent() {
    vec3 v0 = gl_in[0].gl_Position.xyz;
    vec3 v1 = gl_in[1].gl_Position.xyz;
    vec3 v2 = gl_in[2].gl_Position.xyz;

    vec3 e1 = v1 - v0;
    vec3 e2 = v2 - v0;

    vec2 uv0 = mapCoordGS[0];
    vec2 uv1 = mapCoordGS[1];
    vec2 uv2 = mapCoordGS[2];

    vec2 deltaUV1 = uv1 - uv0;
    vec2 deltaUV2 = uv2 - uv0;

    float r = 1.0 / (deltaUV1.x * deltaUV2.y - deltaUV1.y * deltaUV1.x);

    tangent = normalize((e1 * deltaUV2.y - e2 * deltaUV1.y) * r);
}

void main() {

    for (int i = 0; i < 3; i++) {
        displacement[i] = vec3(0, 0, 0);
    }

    float dist = (distance(gl_in[0].gl_Position.xyz, cameraPosition)
    + distance(gl_in[1].gl_Position.xyz, cameraPosition)
    + distance(gl_in[2].gl_Position.xyz, cameraPosition)) / 3;

    if (dist < tbnRange) {

        calcTangent();

        for (int k = 0; k < gl_in.length(); k++) {

            displacement[k] = vec3(0, 1, 0);

            float height = gl_in[k].gl_Position.y;

            vec3 normal = normalize(texture(normalMap, mapCoordGS[k]).rbg);

            vec4 blendValues = texture(splatmap, mapCoordGS[k]).rgba;

            float[4] blendValuesArray = float[](blendValues.r, blendValues.g, blendValues.b, blendValues.a);

            float scale = 0;

            for (int i = 0; i < 3;i++){
                scale += texture(materials[i].heightMap, mapCoordGS[k] * materials[i].horizontalScaling).r * materials[i].heightScaling * blendValuesArray[i];
            }

            float attenuation = clamp(-distance(gl_in[k].gl_Position.xyz, cameraPosition) / (tbnRange-50) + 1, 0.0, 1.0);

            scale *= attenuation;

            displacement[k] *= scale;

        }

    }

    for (int i = 0; i < gl_in.length(); i++) {
        vec4 worldPosition = gl_in[i].gl_Position + vec4(displacement[i], 0);
        gl_Position = m_ViewProjection * worldPosition;
        mapCoordFS = mapCoordGS[i];
        positionFS = (worldPosition).xyz;
        tangentFS = tangent;
        EmitVertex();
    }

    EndPrimitive();
}