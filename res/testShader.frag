#version 330 core

flat in int frag_tid;

in vec2 frag_texCoords;
in vec4 frag_color;

out vec4 color;

uniform sampler2D samplers[32];
uniform float time;

float mapFloat(float val, float min1, float max1, float min2, float max2) {
	float percent = (val - min1) / (max1 - min1);
	return percent * (max2 - min2) + min2;
}

void main() {
	if(frag_color.a < 0.01) discard;
	vec4 texColor = texture(samplers[frag_tid], frag_texCoords);
	color =   texColor * frag_color;
}
