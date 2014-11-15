package com.puzzleall.glesbook2.util;

import android.util.Log;

import static android.opengl.GLES20.*;

public class ShaderHelper {
    private static final String TAG = "ShaderHelper";

    public static int compileVertexShader(String shaderCode){
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode){
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    private static int compileShader(int type, String shaderCode){
        final int shaderObjectId = glCreateShader(type);
        if(shaderObjectId == 0){
            if(LoggerConfig.ON){
                Log.w(TAG, "Could not create new shader.");
            }
            return 0;
        }
        // Upload the source code
        glShaderSource(shaderObjectId, shaderCode);
        // Compile the source code
        glCompileShader(shaderObjectId);
        final int[] compileStatus = new int[1];
        // Read compile status and write it to 0th element of compileStatus array
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);

        if(LoggerConfig.ON){
            // Print the shader info log to the Android log output
            Log.v(TAG, "Result of compiling source: " + "\n" + shaderCode + "\n:" + glGetShaderInfoLog(shaderObjectId));
        }

        if(compileStatus[0] == 0){
            // If it failed, delete the shader object
            glDeleteShader(shaderObjectId);
            if(LoggerConfig.ON){
                Log.w(TAG, "COmpilation of shader failed");
            }
            return 0;
        }

        return shaderObjectId;
    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId){
        final int programObjectId = glCreateProgram();
        if(programObjectId == 0){
            if(LoggerConfig.ON){
                Log.w(TAG, "Could not create new program");
            }
            return 0;
        }
        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragmentShaderId);

        glLinkProgram(programObjectId);

        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);

        if(LoggerConfig.ON){
            // Print the program info log to the Android Log
            Log.v(TAG, "Results of linking program:\n" + glGetProgramInfoLog(programObjectId));
        }

        if(linkStatus[0] == 0){
            // If it failed, delete the program object
            glDeleteProgram(programObjectId);
            if(LoggerConfig.ON){
                Log.w(TAG, "Linking of program failed.");
            }
            return 0;
        }

        return programObjectId;
    }


}