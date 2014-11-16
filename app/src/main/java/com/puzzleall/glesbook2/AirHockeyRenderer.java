package com.puzzleall.glesbook2;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.puzzleall.glesbook2.util.LoggerConfig;
import com.puzzleall.glesbook2.util.ShaderHelper;
import com.puzzleall.glesbook2.util.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

public class AirHockeyRenderer implements GLSurfaceView.Renderer {

    private static final String A_COLOR = "a_Color";
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int BYTES_PER_FLOAT = 4;

    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;
    private int aColorLocation;

    // Two components(x,y) per vertex


    private final FloatBuffer vertexData;
    private Context context;
    private int program;


    public AirHockeyRenderer(Context context) {
        this.context = context;
        float[] tableVerticesWithTriangles = {
                // Triangle fan
                0f, 0f, 1f,1f,1f,
                // Bottom
                -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
                -0.25f, -0.5f, 0.7f, 0.7f, 0.7f,
                0f, -0.5f, 0.7f, 0.7f, 0.7f,
                0.25f, -0.5f, 0.7f, 0.7f, 0.7f,
                0.5f, -0.5f, 0.7f, 0.7f, 0.7f,

                // Right edge
                0.5f, -0.25f, 0.7f, 0.7f, 0.7f,
                0.5f, 0f, 0.7f, 0.7f, 0.7f,
                0.5f, 0.25f, 0.7f, 0.7f, 0.7f,
                0.5f, 0.5f, 0.7f, 0.7f, 0.7f,

                // Top
                0.25f, 0.5f, 0.7f, 0.7f, 0.7f,
                0f, 0.5f, 0.7f, 0.7f, 0.7f,
                -0.25f, 0.5f, 0.7f, 0.7f, 0.7f,
                -0.5f, 0.5f, 0.7f, 0.7f, 0.7f,

                // Left edge
                -0.5f, 0.25f, 0.7f, 0.7f, 0.7f,
                -0.5f, 0f, 0.7f, 0.7f, 0.7f,
                -0.5f, -0.25f, 0.7f, 0.7f, 0.7f,
                -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,

                // Line 1
                -0.5f, 0f, 0.8f, 0.3f, 0f,
                -0f, 0f, 1f, 0f, 0f,
                0.5f, 0f, 0.8f, 1f, 0f,
                // Mallets
                0f, -0.25f, 0f, 0f, 1f,
                0f, 0.25f, 1f, 0f, 0f
        };

        // Allocate block of native memory, memory not managed by the garbage collector.
        vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        // Copy vertex data into native memory
        vertexData.put(tableVerticesWithTriangles);
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        String vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader);

        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);

        if (LoggerConfig.ON) {
            ShaderHelper.validateProgram(program);
        }
        glUseProgram(program);

        aColorLocation = glGetAttribLocation(program, A_COLOR);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);

        vertexData.position(POSITION_COMPONENT_COUNT);
        glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexData);
        glEnableVertexAttribArray(aColorLocation);

        vertexData.position(0);
        // Very important function
        glVertexAttribPointer(
                aPositionLocation,  // Attribute location
                POSITION_COMPONENT_COUNT,   // data count per attribute/number of components in each vertex
                GL_FLOAT,// Type of data
                false,// Only applies if we use integer data
                STRIDE,
                vertexData // Where to read the data
        );
        // Enable the attribute
        glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        // Set the OpenGL Viewport to fill the entire surface
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        glClear(GL_COLOR_BUFFER_BIT);

        //glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        // Draw first 6 vertices, i.e. four triangles as fan
        glDrawArrays(GL_TRIANGLE_FAN, 0, 18);


        glDrawArrays(GL_LINES, 18, 2);
        glDrawArrays(GL_LINES, 19, 2);

        // Draw the first mallet blue
        //glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_POINTS, 21, 1);

        // Draw the second mallet red
        //glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_POINTS, 22, 1);

    }
}
