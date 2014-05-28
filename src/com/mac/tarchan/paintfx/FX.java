/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mac.tarchan.paintfx;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FX
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

    public FXMLLoader loadFXML(String name) throws IOException {
        URL rsrc = null;
        try {
            rsrc = base.getResource(name + ".fxml");
            FXMLLoader fxml = new FXMLLoader(rsrc);
            fxml.load();
//            fxml.getRoot();
//            fxml.getController();
            return fxml;
        } catch (IOException | RuntimeException ex) {
            throw new IOException("FXMLをロードできません。: " + name, ex);
        }
    }

    public Stage dialog(String name, String title, Parent owner) {
        try {
            FXMLLoader fxml = loadFXML(name);
            Parent root = fxml.getRoot();
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            if (owner != null) {
                stage.initOwner(owner.getScene().getWindow());
            }
            stage.setScene(new Scene(root));
            stage.setTitle(title);
//            stage.show();
            return stage;
        } catch (IOException ex) {
            throw new RuntimeException("ダイアログを表示できません。: " + name, ex);
        }
    }
}
