#version 120

uniform sampler2D texture;
uniform int time;

uniform float yaw;
uniform float pitch;
uniform ivec2 resolution;

varying vec3 position;

float interpolate(float v1, float v2, float placement) {
    placement = clamp(placement, 0.0, 1.0);
    return sqrt(((v1 * v1) * (1.0 - placement)) + ((v2 * v2) * placement));
}

vec4 interpolate(vec4 v1, vec4 v2, float placement) {
    placement = clamp(placement, 0.0, 1.0);
    return sqrt(((v1 * v1) * (1.0 - placement)) + ((v2 * v2) * placement));
}

vec3 hsv2rgb(vec3 c)
{
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

void main() {
    float fixedPitch = (pitch/3.2) + 0.5;
    float fixedY = gl_FragCoord.y / (resolution.y / 2.0);

    // background colour
    vec4 sky = vec4( 40.0 / 255.0, 37.0 / 255.0, 63.0 / 255.0, 1.0 );
    // fog color
    vec4 fog = vec4( 0.39905882, 0.53, 0.46164703, 1.0 );

    float posZ = sqrt(clamp(-position.z * 2.0, 0.0, 10000.0));

    float depth = mod(posZ, 1.0);

    vec3 colorOut = hsv2rgb(vec3(depth, 1.0, 1.0));

    //vec3 colorOut = hsv2rgb(vec3(depth, 1.0, 1.0));

    gl_FragColor = vec4(colorOut, 1.0);
}