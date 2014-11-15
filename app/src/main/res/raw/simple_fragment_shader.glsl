precision mediump float;
// a uniform keeps the same value for all vertices until we change it again
uniform vec4 u_Color;
void main() {
    gl_FragColor = u_Color;
}