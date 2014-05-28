/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mac.tarchan.paintfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author v-togura
 */
public class NewController implements Initializable {
    
    private SimpleIntegerProperty width = new SimpleIntegerProperty();
    private SimpleIntegerProperty height = new SimpleIntegerProperty();
    private SimpleIntegerProperty dpi = new SimpleIntegerProperty();
    @FXML
    private TextField widthBox;
    @FXML
    private TextField heightBox;
    @FXML
    private TextField dpiBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        widthBox.setText("1024");
        heightBox.setText("1024");
        dpiBox.setText("72");
    }    

    public IntegerProperty widthProperty() {
        return width;
    }

    public IntegerProperty heightProperty() {
        return height;
    }

    public IntegerProperty dpiProperty() {
        return dpi;
    }

    @FXML
    private void onCancel(ActionEvent event) {
        FX.hide(widthBox.getScene().getRoot());
    }

    @FXML
    private void onOK(ActionEvent event) {
        width.set(Integer.valueOf(widthBox.getText()));
        height.set(Integer.valueOf(heightBox.getText()));
        dpi.set(Integer.valueOf(dpiBox.getText()));
        FX.hide(widthBox.getScene().getRoot());
    }
}
