#version 120

varying vec3 position;

vec3 hsv2rgb(vec3 c){
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

void main() {
    float posZ = sqrt(max(0.0, -position.z * 2.0));

    gl_FragColor = vec4(hsv2rgb(vec3(mod(posZ, 1.0), 1.0, 1.0)), 1.0);
}