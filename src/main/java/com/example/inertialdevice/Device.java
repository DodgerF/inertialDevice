package com.example.inertialdevice;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.Date;

public class Device extends Pane implements Runnable {
    private Line _line;
    private final SimpleDoubleProperty _currentAngle = new SimpleDoubleProperty();
    private double _angle;
    private double _acceleration;
    private double _speed;
    private final Thread _thread;
    private long _timeStart;
    private boolean _isActive;
    private double _redAngle;

    public Device(double w, double h) {
        setWidth(w);
        setHeight(h);

        addLine();
        addZoneIndicator();

        _currentAngle.set(0);
        _angle = 0;
        _speed = 0;
        _thread = new Thread(this);
    }
    public void setAngle(Double angle) {
        _angle = angle * Math.PI/180;

        _timeStart = new Date().getTime() - 1000;

        _isActive = true;
        _thread.start();
    }


    private void addLine(){
        _line = new Line();
        _line.setStartY(getHeight());
        _line.setStartX(getWidth() / 2);
        _line.setEndY(getHeight());
        _line.setEndX(getWidth() * 0.1);
        getChildren().add(_line);
    }

    private void addZoneIndicator() {
        Line redLine = new Line();
        redLine.setStroke(Color.RED);
        redLine.setStartY(getHeight());
        redLine.setStartX(getWidth() / 2);
        redLine.setEndY(getHeight() * 0.9);
        redLine.setEndX(getWidth() * 0.9);
        getChildren().add(redLine);

        _redAngle = Math.atan2(redLine.getStartY() - redLine.getEndY(), redLine.getStartX() - redLine.getEndX());
    }
    private void turnToAngle(int time){
        var radius = Math.sqrt(Math.pow(_line.getEndX() - _line.getStartX(), 2) +
                Math.pow(_line.getEndY() - _line.getStartY(), 2));

        if (Math.abs(_currentAngle.doubleValue() - _angle) <= 0.1) {
            var AMPLITUDE_OF_CHANGE = 2;
            var ATTENUATION_COEFFICIENT = -0.73;
            _acceleration = AMPLITUDE_OF_CHANGE * Math.sin(_acceleration * time) * Math.exp(ATTENUATION_COEFFICIENT * time);
        }
        else{
            _acceleration = 0.04;
        }

        if (_currentAngle.doubleValue() < _angle){
            _speed += _acceleration * time;
        }
        else {
            _speed -= _acceleration * time;
        }
        _currentAngle.set(_speed);
        _line.setEndX(_line.getStartX() - (radius * Math.cos(_currentAngle.getValue())));
        _line.setEndY(_line.getStartY() - (radius * Math.sin(_currentAngle.getValue())));
    }
    public Double getRedAngle() {
        return _redAngle;
    }
    public SimpleDoubleProperty getAngle() {
        return _currentAngle;
    }
    public void disable() {
        _isActive = false;
    }
    @Override
    public void run() {

        long minUpdateInterval = 10;
        long lastUpdate = 0;

        while (_isActive) {
            long now = new Date().getTime();
            if (now - lastUpdate > minUpdateInterval) {
                turnToAngle(((int)(now - _timeStart)/1000));
                lastUpdate = now;
            }
            if (_speed == 0)
                disable();
        }
    }
}
