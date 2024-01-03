package com.example.inertialdevice;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements Initializable {
    private Device _device;
    @FXML
    private TextField _textField;

    public void setDevice(Device device){
        _device = device;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        _textField.setOnAction(actionEvent -> {
            try {
                var value  = Double.parseDouble(_textField.getText());
                if (value >= 0 && value <= 180)
                    _device.setAngle(value);
            }
            catch (Exception ignored){
            }
        });
    }
}
