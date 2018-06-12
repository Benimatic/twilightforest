//precision highp float;

#version 120

uniform sampler2D zero;
uniform float time;

uniform float yaw;
uniform float pitch;

varying vec2 texCoord0;
varying vec3 position;

// Thanks Amadornes! :3

// Utils
vec2 convolve(vec2 pos, float off, float strength) {
	float amt = length(pos) * strength + off;
	float arc = atan(pos.x, pos.y);
	return vec2(cos(arc + amt), sin(arc + amt)) * length(pos);
}

void main() {
    vec4 tex = texture2D(zero, vec2(texCoord0));

    tex.a = 1;

    vec2 pos = position.xy * 2.0 - 1.0;
    vec2 realPos = convolve(pos, 0.0, 50.0);

    if(abs(realPos.x) < abs(realPos.y) && realPos.y <= 0.0 && realPos.y >= -0.2) {
    	gl_FragColor = vec4(vec3(1.0), tex.a);
    } else {
    	gl_FragColor = vec4(vec3(0.0), tex.a);
    }
}