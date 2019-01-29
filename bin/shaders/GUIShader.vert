#version 330 core

in layout(location = 0) vec4 vert_pos;
in layout(location = 1) vec2 vert_texCoords;
in layout(location = 2) int vert_tid;
in layout(location = 3) vec4 vert_color;

flat out int frag_tid;
out vec2 frag_texCoords;
out vec4 frag_color;

uniform mat4 projection_matrix;

void processFragmentOutput() {
 	frag_tid = vert_tid;
	frag_texCoords = vert_texCoords;
	frag_color = vert_color;
}

void main() {
	processFragmentOutput();
	gl_Position = projection_matrix * vert_pos;
}
