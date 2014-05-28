/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mac.tarchan.paintfx;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * JavaFX Utilities
 * 
 * @author tarchan
 */
public class FX {

    private Class base;

    private FX(Class base) {
        this.base = base;
    }

    public static FX build(Class base) {
        return new FX(base);
    }

    public FXMLLoader fxml(String name) throws IOException {
        try {
            URL rsrc = base.getResource(name + ".fxml");
            FXMLLoader fxml = new FXMLLoader(rsrc);
            fxml.load();
//            fxml.getRoot();
//            fxml.getController();
            return fxml;
        } catch (IOException | RuntimeException ex) {
            throw new IOException("FXMLをロードできません。: " + name + ".fxml", ex);
        }
    }

    public Stage dialog(String name, String title, Parent owner) {
        try {
            FXMLLoader fxml = fxml(name);
            Parent root = fxml.getRoot();
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            if (owner != null) {
                stage.initOwner(owner.getScene().getWindow());
            }
            stage.initStyle(StageStyle.UTILITY);
            stage.setScene(new Scene(root));
            stage.setTitle(title);
//            stage.show();
            return stage;
        } catch (IOException ex) {
            throw new RuntimeException("ダイアログを表示できません。: " + name, ex);
        }
    }

    public static void hide(Parent root) {
        Stage stage = (Stage)root.getScene().getWindow();
        if (stage != null) {
            stage.hide();
//            stage.getStyle().UTILITY;
        }
    }
}
