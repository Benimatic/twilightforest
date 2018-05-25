#version 120

#define M_PI 3.1415926535897932384626433832795

// Modifed version of ender.frag

uniform sampler2D texture;
uniform int time;

uniform float yaw;
uniform float pitch;

varying vec3 position;

mat4 rotationMatrix(vec3 axis, float angle)
{

    axis = normalize(axis);
    float s = sin(angle);
    float c = cos(angle);
    float oc = 1.0 - c;

    return mat4(oc * axis.x * axis.x + c,           oc * axis.x * axis.y - axis.z * s,  oc * axis.z * axis.x + axis.y * s,  0.0,
                oc * axis.x * axis.y + axis.z * s,  oc * axis.y * axis.y + c,           oc * axis.y * axis.z - axis.x * s,  0.0,
                oc * axis.z * axis.x - axis.y * s,  oc * axis.y * axis.z + axis.x * s,  oc * axis.z * axis.z + c,           0.0,
                0.0,                                0.0,                                0.0,                                1.0);
}

void main() {
    // background colour
    vec4 col = vec4( 40.0F / 255.0F, 37.0F / 255.0F, 63.0F / 255.0F, 1 );

    // get ray from camera to fragment
    vec4 dir = normalize(vec4( -position, 0));

	// rotate the ray to show the right bit of the sphere for the angle
	float sb = sin(pitch);
	float cb = cos(pitch);
	dir = normalize(vec4(dir.x, dir.y * cb - dir.z * sb, dir.y * sb + dir.z * cb, 0));

	float sa = sin(-yaw);
	float ca = cos(-yaw);
	dir = normalize(vec4(dir.z * sa + dir.x * ca, dir.y, dir.z * ca - dir.x * sa, 0));

	vec4 ray;

	// draw the layers
	for (int i=0; i<4; i++) {
		int mult = 4-i;

		// get semi-random stuff
		int j = i + 7;
		float rand1 = (j * j * 4321 + j * 8) * 2.0F;
		int k = j + 1;
		float rand2 = (k * k * k * 239 + k * 37) * 3.6F;
		float rand3 = rand1 * 347.4 + rand2 * 63.4;

		// random rotation matrix by random rotation around random axis
		vec3 axis = normalize(vec3(sin(rand1), sin(rand2) , cos(rand3)));

		// apply
		ray = dir * rotationMatrix(axis, mod(rand3, 2*M_PI));

		// calcuate the UVs from the final ray
		float u = 0.5 + (atan(ray.z,ray.x)/(2*M_PI));
		float v = 0.5 + (asin(ray.y)/M_PI);

		// get UV scaled for layers and offset by time;
		float scale = mult*0.5 + 2.75;
		vec2 tex = vec2( u * scale, (v + time * 0.00006) * scale * 0.6 );

		// sample the texture
		vec4 tcol = texture2D(texture, tex);

		// set the alpha, blending out at the bunched ends
		float a = tcol.r == 1 ? 1 : 0; // * (0.05 + (1.0/mult) * 0.65) * (1-smoothstep(0.15, 0.48, abs(v-0.5)));

		// mix the colours
		col = col*(1-a) + vec4(1)*a;
	}

    gl_FragColor = col;
}