package com.puzzleall.glesbook2.objects;


import com.puzzleall.glesbook2.data.VertexArray;
import com.puzzleall.glesbook2.programs.ColorShaderProgram;

import java.util.List;

import static com.puzzleall.glesbook2.objects.ObjectBuilder.GeneratedData;
import static com.puzzleall.glesbook2.util.Geometry.Cylinder;
import static com.puzzleall.glesbook2.util.Geometry.Point;

public class Puck {
    private static final int POSITION_COMPONENT_COUNT = 3;

    public final float radius,height;

    private final VertexArray vertexArray;

    private final List<ObjectBuilder.DrawCommand> drawList;

    public Puck(float radius, float height, int numPointsAroundPuck){
        GeneratedData generatedData = ObjectBuilder.createPuck(new Cylinder(new Point(0f,0f,0f), radius, height), numPointsAroundPuck);
        this.radius = radius;
        this.height = height;
        vertexArray = new VertexArray(generatedData.vertexData);
        drawList = generatedData.drawList;
    }

    public void bindData(ColorShaderProgram colorProgram){
        vertexArray.setVertexAttribPointer(0, colorProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 0);
    }

    public void draw(){
        for(ObjectBuilder.DrawCommand drawCommand : drawList){
            drawCommand.draw();
        }
    }
}
