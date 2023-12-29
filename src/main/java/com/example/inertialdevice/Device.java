package com.example.inertialdevice;

import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Device extends Pane {
    private Line line;



    public Device(double w, double h) {
        setWidth(w);
        setHeight(h);
        addLine();
    }

    private void addLine(){
        line = new Line();
        line.setStartY(getHeight());
        line.setStartX(getWidth()/2);
        line.setEndY(getHeight()*0.9);
        line.setEndX(getWidth()*0.1);
        getChildren().add(line);
    }
    public void setCord(Double x, Double y){
        line.setEndX(x);
        line.setEndY(y);
    }

}
