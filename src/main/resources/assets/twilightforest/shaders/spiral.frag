//precision highp float;

#version 120

#define PI 3.14159265359
#define PI2 6.28318530718

uniform sampler2D zero;
uniform int time;

uniform float yaw;
uniform float pitch;

varying vec2 texCoord0;
varying vec3 position;

varying vec3 normals;
varying vec3 camNorms;

// https://gist.github.com/antoineMoPa/dea8e2f8495f6e5edcf724569ba5feae

vec4 spiral(vec2 pos){
	vec4 col = vec4(0.0);

    col.b += 0.3 * floor(
      3.0 *
      sin(pos.x * 0.4 +floor(pos.y * 20.0) - time * PI2)
    );

    col.g += 0.3 * col.b;

    col.a = 1.0;

    return col;
}

void main() {
    vec4 tex = texture2D(zero, vec2(texCoord0));

    gl_FragColor = vec4(abs(camNorms), 1) * tex;
}