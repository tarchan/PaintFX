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
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * PaintFXController
 * 
 * @author tarchan
 */
public class PaintFXController implements Initializable {
    
    private Canvas canvas;
    @FXML
    private Group group;
    @FXML
    private Label status;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        canvas = new Canvas(1024, 1024);
        group.getChildren().add(canvas);
    }    

    @FXML
    private void onDragged(MouseEvent event) {
//        PixelWriter px = canvas.getPixelWriter();
//        px.setColor((int)event.getSceneX(), (int)event.getSceneY(), Color.BLACK);
        GraphicsContext g = canvas.getGraphicsContext2D();
//        Point2D p = group.getLocalToParentTransform().transform(event.getSceneX(), event.getSceneY());
//        Point2D p2 = group.getLocalToSceneTransform().transform(event.getSceneX(), event.getSceneY());
        Point2D p = canvas.sceneToLocal(event.getSceneX(), event.getSceneY());
        g.fillRect(p.getX(), p.getY(), 1, 1);
        status.setText(String.format("(%s,%s)", p.getX(), p.getY()));
//        group.getParent().getLocalToSceneTransform();
    }

    @FXML
    private void onNew(ActionEvent event) {
        // TODO キャンバスサイズを設定するダイアログ
    }

    @FXML
    private void onClose(ActionEvent event) {
    }

    @FXML
    private void onSave(ActionEvent event) {
    }

    @FXML
    private void onAbout(ActionEvent event) {
        // アプリケーション情報ダイアログ
    }
}
