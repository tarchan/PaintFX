/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mac.tarchan.paintfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

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
        StringConverter<Number> converter =  new NumberStringConverter("#0");
        Bindings.bindBidirectional(widthBox.textProperty(), width, converter);
        Bindings.bindBidirectional(heightBox.textProperty(), height, converter);
        Bindings.bindBidirectional(dpiBox.textProperty(), dpi, converter);
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
        width.set(0);
        height.set(0);
        dpi.set(0);
        FX.hide(widthBox.getScene().getRoot());
    }

    @FXML
    private void onOK(ActionEvent event) {
        FX.hide(widthBox.getScene().getRoot());
    }
}
