#version 120

uniform sampler2D texture;
uniform int time;

uniform float yaw;
uniform float pitch;
uniform ivec2 resolution;

varying vec3 position;

void main() {
    gl_FragColor = vec4(1.0);
}