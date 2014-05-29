/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mac.tarchan.paintfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * PaintFX
 * 
 * @author tarchan
 */
public class PaintFX extends Application {

    static {
//        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tT %2$s %4$s: %5$s%6$s%n");
        // LTSVフォーマットに変更
        System.setProperty("java.util.logging.SimpleFormatter.format", "time:%1$tT\tmethod:%2$s\tlevel:%4$s\tmessage:%5$s\tthrown:%6$s%n");
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("PaintFX.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("PaintFX");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
