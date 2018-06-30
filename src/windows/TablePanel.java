package windows;

import models.Table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TablePanel extends JPanel {

    JTable table;
    JScrollPane scrollPane;

    public TablePanel() {
        table = new JTable();
        scrollPane = new JScrollPane(table);
        scrollPane.setViewportView(table);
        add(scrollPane);
    }

    public void setTable(Table table) {
        DefaultTableModel tableModel = new DefaultTableModel(table.getData(), table.getColumnsNames());
        this.table.setModel(tableModel);
    }

}
