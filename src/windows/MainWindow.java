package windows;

import factories.DatabaseFactory;
import models.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame implements ActionListener {

    private final int minimumWindowWidth = 800;
    private final int minimumWindowHeight = 600;

    JTabbedPane tables;
    JPanel verticalPanel, optionPanel, actionPanel, mainPanel;
    JRadioButton showTables, showViews;
    DatabaseFactory databaseFactory;
    String[] tablesNames;

    public MainWindow(DatabaseFactory databaseFactory) {
        setResizable(true);
        setMinimumSize(new Dimension(minimumWindowWidth, minimumWindowHeight));
        setTitle("DataViewer");
        this.databaseFactory = databaseFactory;
        createMainPanel();
    }

    private void createMainPanel() {
        createOptionPanel();
        createTabbedPane();
        createActionPanel();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.add(optionPanel);
        verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        mainPanel.add(verticalPanel);
        verticalPanel.add(tables);
        verticalPanel.add(actionPanel);
        add(mainPanel);
    }

    private void createOptionPanel() {
        optionPanel = new JPanel();
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
        showTables = new JRadioButton("Tables");
        showTables.setSelected(true);
        showTables.addActionListener(this);
        optionPanel.add(showTables);
        showViews = new JRadioButton("Views");
        showViews.addActionListener(this);
        optionPanel.add(showViews);
    }

    private void createTabbedPane() {
        tables = new JTabbedPane();
        tables.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        showTablesInTabbedPane();
    }

    private void createActionPanel() {
        actionPanel = new JPanel();
    }

    private void showViewsInTabbedPane(){
        tablesNames = databaseFactory.getViewsNames();
        refreshTablesInTabbedPane();
    }

    private void showTablesInTabbedPane(){
        tablesNames = databaseFactory.getTablesNames();
        refreshTablesInTabbedPane();
    }

    private void refreshTablesInTabbedPane(){
        tables.removeAll();
        for (int i = 0; i < tablesNames.length; i++) {
            String[] columnsNames = databaseFactory.getTableColumnsNames(tablesNames[i]);
            Table table = new Table();
            table.setColumnsNames(columnsNames);
            table.setData(databaseFactory.getTableData(tablesNames[i], columnsNames));
            TablePanel tablePanel = new TablePanel();
            tablePanel.setTable(table);
            tables.add(tablePanel, tablesNames[i]);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == showTables) {
            if (showViews.isSelected()) {
                showViews.setSelected(false);
            } else {
                showTables.setSelected(true);
            }
            showTablesInTabbedPane();
        }
        else if (source == showViews){
            if (showTables.isSelected()) {
                showTables.setSelected(false);
            } else {
                showViews.setSelected(true);
            }
            showViewsInTabbedPane();
        }
    }
}
