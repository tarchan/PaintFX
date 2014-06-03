/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mac.tarchan.paintfx;

import javafx.scene.control.ListCell;
import javafx.scene.shape.Line;

/**
 * LineWidthCell
 * 
 * @author tarchan
 */
public class LineWidthCell extends ListCell<Float> {

    @Override
    protected void updateItem(Float item, boolean empty) {
        super.updateItem(item, empty);
        
        if (item == null || empty) {
            return;
        }

        Line line = new Line(0, 0, 50 - item, 0);
        line.setStrokeWidth(item);
        setText(item + " px");
        setGraphic(line);
    }
}
