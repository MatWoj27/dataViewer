package windows;

import models.Table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TablePanel extends JPanel {

    JTable table;
    JScrollPane scrollPane;

    public TablePanel() {
        setLayout(new BorderLayout());
        table = new JTable();
        table.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(table);
        scrollPane.setViewportView(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setTable(Table table) {
        DefaultTableModel tableModel = new DefaultTableModel(table.getData(), table.getColumnsNames());
        this.table.setModel(tableModel);
    }

}
