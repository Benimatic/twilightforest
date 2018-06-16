#version 120

uniform sampler2D zero;
uniform float time;

uniform float yaw;
uniform float pitch;

varying float scale;
varying vec2 texCoord0;
varying vec3 worldPos;

void main() {
    vec4 tex = texture2D(zero, vec2(texCoord0));
    float timing = mod(length(worldPos.xyz) * 4 - time/50, 1);

    if (timing <= 0.25) {
        gl_FragColor = vec4( 0.392, 0, 0, tex.a );
    } else if (timing <= 0.40) {
        gl_FragColor = vec4( 0.615, 0, 0, tex.a );
    } else if (timing <= 0.85) {
        gl_FragColor = vec4( 0.729, 0, 0, tex.a );
    } else {
        gl_FragColor = vec4( 0.615, 0, 0, tex.a );
    }
}