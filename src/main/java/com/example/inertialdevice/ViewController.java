package com.example.inertialdevice;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements Initializable {
    private Device _device;
    @FXML
    private TextField _textField;
    @FXML
    private Text _text;


    public void setDevice(Device device){
        _device = device;
        _device.getRedAngle();
        _device.getAngle().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                _text.setVisible(newValue.doubleValue() > _device.getRedAngle());
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        _text.setVisible(false);
        _textField.setOnAction(actionEvent -> {
            try {
                var value  = Double.parseDouble(_textField.getText());
                if (value >= 0 && value <= 180) {
                    _device.setAngle(value);
                }
            }
            catch (Exception ignored){
            }
        });
    }
}
