/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mac.tarchan.paintfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author v-togura
 */
public class AboutController implements Initializable {
    @FXML
    private Button close;
    @FXML
    private Label title;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String appName = PaintFX.class.getPackage().getImplementationTitle();
        String appVersion = PaintFX.class.getPackage().getImplementationVersion();
        title.setText(appName + " " + appVersion);
    }

    @FXML
    private void onClose(ActionEvent event) {
        FX.hide(close.getScene().getRoot());
    }
}
