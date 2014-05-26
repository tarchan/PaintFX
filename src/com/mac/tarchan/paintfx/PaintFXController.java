/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mac.tarchan.paintfx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 * PaintFXController
 * 
 * @author tarchan
 */
public class PaintFXController implements Initializable {
    
    private static final Logger logger = Logger.getLogger(PaintFXController.class.getName());
    private Canvas canvas;
    private final FileChooser fileChooser = new FileChooser();
    private File savedFile;
    @FXML
    private Group group;
    @FXML
    private Label status;

    private void saveImage(Image image, File file) throws IOException {
        BufferedImage saveImage = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(saveImage, "png", file);
    }

    private void saveFile(File file) {
        if (file == null) {
            file = fileChooser.showSaveDialog(group.getScene().getWindow());
            if (file == null) {
                // キャンセル
                return;
            }
        }
        try {
            SnapshotParameters params = new SnapshotParameters();
            WritableImage image = canvas.snapshot(params, null);
            saveImage(image, file);
            savedFile = file;
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "イメージを保存できません。: " + file, ex);
        }
    }

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
        saveFile(null);
    }

    @FXML
    private void onAbout(ActionEvent event) {
        // アプリケーション情報ダイアログ
    }
}
