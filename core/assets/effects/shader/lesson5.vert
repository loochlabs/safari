//combined projection and view matrix
//uniform mat4 u_projView;
uniform mat4 u_projTrans;

//"in" attributes from our SpriteBatch
attribute vec3 a_position;//Position
attribute vec2 a_texCoord0;//TexCoord
attribute vec4 a_color;//Color

//"out" varyings to our fragment shader
varying vec4 vColor;
varying vec2 vTexCoord;
 
void main() {
	vColor = a_color;
	vTexCoord = a_texCoord0;
	gl_Position = u_projTrans * vec4(a_position, 1.0);
}