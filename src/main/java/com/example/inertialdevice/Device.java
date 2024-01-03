package com.example.inertialdevice;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.Date;
import java.util.Objects;

public class Device extends Pane implements Runnable {
    private Line _line;
    private double _currentAngle;
    private double _angle;
    private double _acceleration;
    private Thread _thread;
    private long _timeStart;
    private boolean _isActive;

    public Device(double w, double h) {
        setWidth(w);
        setHeight(h);
        addLine();

        _acceleration = 0.03;
        _currentAngle = 0;
        _angle = 0;
        _thread = new Thread(this);
    }
    public void setAngle(Double angle) {
        _angle = angle * Math.PI/180;

        _timeStart = new Date().getTime();
        _isActive = true;
        _thread.start();
    }
    private void addLine(){
        _line = new Line();
        _line.setStartY(getHeight());
        _line.setStartX(getWidth()/2);
        _line.setEndY(getHeight());
        _line.setEndX(0 + getWidth() * 0.1);
        getChildren().add(_line);
    }
    private void turnToAngle(int time){
        var radius = Math.sqrt(Math.pow(_line.getEndX() - _line.getStartX(), 2) +
                Math.pow(_line.getEndY() - _line.getStartY(), 2));

        if (Math.abs(_currentAngle - _angle) <= 0.1) {
            var A = 2; //амплитуда колебаний
            var a = -0.7; //коэффициент затухания
            _acceleration = A * Math.sin(_acceleration * time) * Math.exp(a * time);
        }
        else{
            _acceleration = 0.03;
        }
        if (_currentAngle < _angle){
            _currentAngle += _acceleration * time;
        }
        else {
            _currentAngle -= _acceleration * time;
        }

        _line.setEndX(_line.getStartX() - (radius * Math.cos(_currentAngle)));
        _line.setEndY(_line.getStartY() - (radius * Math.sin(_currentAngle)));

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
        }
    }
}
