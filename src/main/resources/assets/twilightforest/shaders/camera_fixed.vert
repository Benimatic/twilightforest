#version 120

varying vec4 center;
varying vec4 position;
varying vec4 worldPos;
varying vec2 texCoord0;

varying vec3 centerCoord;
varying float scale;

void main() {
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;

	worldPos = gl_Vertex;// * gl_NormalScale * 10;
	scale = gl_NormalScale;

	//position = gl_Position.xyz;//(gl_ModelViewMatrix * gl_Vertex).xyz;
	//position = gl_NormalMatrix * worldPos * gl_NormalScale;
	center = (gl_ModelViewMatrix * vec4(vec3(0.0), 1.0));
	position = gl_ModelViewMatrix * gl_Vertex;

	//center = center/center.w;
    //center.w = 1 / center.w;
    //position = position/position.w;
    //position.w = 1 / position.w;

	centerCoord = position.xyz - center.xyz;

	texCoord0 = vec2(gl_MultiTexCoord0);
}