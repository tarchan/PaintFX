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

    private final Class _base;
    private final FXMLLoader _fxml;
    private Stage _stage;

    private FX(Class base, String name) {
        this._base = base;
        this._fxml = loadFXML(name);
    }

    public static FX build(Class base, String name) {
        return new FX(base, name);
    }

    private FXMLLoader loadFXML(String name) {
        try {
            URL rsrc = _base.getResource(name + ".fxml");
            FXMLLoader fxml = new FXMLLoader(rsrc);
            fxml.load();
//            fxml.getRoot();
//            fxml.getController();
            return fxml;
        } catch (IOException | RuntimeException ex) {
            throw new RuntimeException("FXMLをロードできません。: " + name + ".fxml", ex);
        }
    }

    public <V> V getRoot() {
        return _fxml.getRoot();
    }

    public <C> C getController() {
        return _fxml.getController();
    }

    public FX dialog(String title, Parent owner) {
            Parent root = _fxml.getRoot();
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            if (owner != null) {
                stage.initOwner(owner.getScene().getWindow());
            }
            stage.initStyle(StageStyle.UTILITY);
            stage.setScene(new Scene(root));
            stage.setTitle(title);
//            stage.show();
            _stage = stage;
            return this;
    }

    public <U> U show() {
        _stage.show();
        return getUserData(_stage.getScene().getRoot());
    }

    public <U> U showDialog() {
        _stage.showAndWait();
        return getUserData(_stage.getScene().getRoot());
    }

    public static void hide(Parent root) {
        Stage stage = (Stage)root.getScene().getWindow();
        if (stage != null) {
            stage.hide();
        }
    }

    public static void setUserData(Parent root, Object value) {
        root.setUserData(value);
    }

    public static <U> U getUserData(Parent root) {
        return (U)root.getUserData();
    }
}
