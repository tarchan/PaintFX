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
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author v-togura
 */
public class PaintFXController implements Initializable {
    
    private WritableImage canvas;
    @FXML
    private ImageView view;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        canvas = new WritableImage(1024, 1024);
        view.setImage(canvas);
    }    

    @FXML
    private void onDragged(MouseEvent event) {
        PixelWriter px = canvas.getPixelWriter();
        px.setColor((int)event.getX(), (int)event.getY(), Color.BLACK);
    }

    @FXML
    private void onClose(ActionEvent event) {
    }

    @FXML
    private void onSave(ActionEvent event) {
    }

    @FXML
    private void onAbout(ActionEvent event) {
    }
}
