package com.event;

import javax.swing.*;
import java.awt.*;

public class TableUtils {

    public static void setRowSelection(JTable table, int[] rows, Color color) {
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.clearSelection();
        for (int i = 0; i < rows.length; i++) {
            int row = rows[i];
            selectionModel.addSelectionInterval(row, row);
            table.setSelectionBackground(color);
        }
    }
}
