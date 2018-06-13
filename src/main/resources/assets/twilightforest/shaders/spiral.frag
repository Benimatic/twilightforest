#version 120

uniform sampler2D zero;
uniform float time;

uniform float yaw;
uniform float pitch;
uniform ivec2 resolution;

varying vec2 texCoord0;
varying vec4 worldPos;
varying vec4 position;
varying vec4 center;
varying float scale;

// Thanks Amadornes! :3

// Utils
vec2 convolve(vec2 pos, float off, float strength) {
	float amt = length(pos) * strength + off;
	float arc = atan(pos.x, pos.y);
	return vec2(cos(arc + amt), sin(arc + amt)) * length(pos);
}

vec3 hsv2rgb(vec3 c) {
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

void main() {
    vec4 tex = texture2D(zero, vec2(texCoord0));

    //tex.a = 1;

    float posZ = sqrt(abs((center - position).z)) * 10;

    vec2 pos = (center - position).xy / 2;
    vec2 realPos = convolve(pos, -time/10.0, 75.0);

    if(abs(realPos.x) < abs(realPos.y) && realPos.y <= 0.0 && realPos.y >= -0.2) {
    	gl_FragColor = vec4(vec3(1.0), tex.a);
    } else {
    	gl_FragColor = vec4(vec3(0.0), tex.a);
    }

    // fixme debug
    //gl_FragColor = tex;

    //float posZ = 1;//sqrt(abs(center.z));

    //vec2 center_Coord = center.xy + position.xy;

    //float len = abs(position.x);

    //gl_FragColor = vec4(hsv2rgb(vec3(len, 1, 1)), 1.0);
    //if (len < 0.01 * posZ && len > 0.005 * posZ) { gl_FragColor = vec4(1.0); }
    //if (len < 0.1 * posZ && len > 0.105 * posZ) { gl_FragColor = vec4(1.0); }

    //gl_FragColor = vec4(vec3(len), 1.0);
}