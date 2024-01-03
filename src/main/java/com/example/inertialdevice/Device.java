package com.example.inertialdevice;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class Device extends Pane {
    private Line _line;

    public Device(double w, double h) {
        setWidth(w);
        setHeight(h);
        addLine();
    }

    private void addLine(){
        _line = new Line();
        _line.setStartY(getHeight());
        _line.setStartX(getWidth()/2);
        _line.setEndY(getHeight());
        _line.setEndX(0 + getWidth() * 0.1);
        getChildren().add(_line);
    }
    private Double getRadius(){
        return Math.sqrt(Math.pow(_line.getEndX() - _line.getStartX(), 2) +
                         Math.pow(_line.getEndY() - _line.getStartY(), 2));
    }
    public void setAngle(double angle){
        var radius = getRadius();
        _line.setEndX(_line.getStartX() - (radius * Math.cos(angle * Math.PI/180)));
        _line.setEndY(_line.getStartY() - (radius * Math.sin(angle * Math.PI/180)));
    }

}
