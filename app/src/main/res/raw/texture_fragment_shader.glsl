precision mediump float;
// texture data for each fragment
// sampler2D refers to an array of two-dimensional texture data
uniform sampler2D u_TextureUnit;
varying vec2 v_TextureCoordinates;

void main(){
    gl_FragColor = texture2D(u_TextureUnit, v_TextureCoordinates);
}