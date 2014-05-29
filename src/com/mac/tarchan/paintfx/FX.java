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
    private FXMLLoader fxml;
    private Stage stage;

    private FX(Class base, String name) {
        this.base = base;
        this.fxml = loadFXML(name);
    }

    public static FX build(Class base, String name) {
        return new FX(base, name);
    }

    private FXMLLoader loadFXML(String name) {
        try {
            URL rsrc = base.getResource(name + ".fxml");
            FXMLLoader fxml = new FXMLLoader(rsrc);
            fxml.load();
//            fxml.getRoot();
//            fxml.getController();
            return fxml;
        } catch (IOException | RuntimeException ex) {
            throw new RuntimeException("FXMLをロードできません。: " + name + ".fxml", ex);
        }
    }

    public Parent getRoot() {
        return fxml.getRoot();
    }

    public <T> T getController() {
        return fxml.getController();
    }

    public FX dialog(String title, Parent owner) {
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
            this.stage = stage;
            return this;
    }

    public FX show() {
//        stage.show();
        stage.showAndWait();
        return this;
    }

    public static void hide(Parent root) {
        Stage stage = (Stage)root.getScene().getWindow();
        if (stage != null) {
            stage.hide();
//            stage.getStyle().UTILITY;
        }
    }
}
