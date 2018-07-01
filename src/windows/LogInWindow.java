package windows;

import factories.DatabaseFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInWindow extends JFrame implements ActionListener {

    private final int windowWidth = 350;
    private final int windowHeight = 250;

    JButton logInButton;
    JPasswordField passwordField;
    JTextField loginTextField;
    JRadioButton echoRadioButton;
    JLabel feedback, loginLabel, passwordLabel;
    JPanel centerPanel, eastPanel;

    Dimension verticalMargin;
    Dimension horizontalMargin;
    Dimension spacinigBetweenComponents;
    Dimension textFieldSize;

    public LogInWindow() {
        setSize(windowWidth, windowHeight);
        setResizable(false);
        setTitle("Sign in");

        verticalMargin = new Dimension(0, 20);
        horizontalMargin = new Dimension(90, 0);
        spacinigBetweenComponents = new Dimension(15, 15);
        textFieldSize = new Dimension(150, 20);

        setLayout(new BorderLayout());

        add(Box.createRigidArea(horizontalMargin), BorderLayout.WEST);

        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        centerPanel.add(Box.createRigidArea(verticalMargin));

        loginLabel = new JLabel("Login:");
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(loginLabel);

        loginTextField = new JTextField();
        loginTextField.setMaximumSize(textFieldSize);
        loginTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(loginTextField);

        centerPanel.add(Box.createRigidArea(spacinigBetweenComponents));

        passwordLabel = new JLabel("Password:");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setMaximumSize(textFieldSize);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setEchoChar('*');
        centerPanel.add(passwordField);

        centerPanel.add(Box.createRigidArea(spacinigBetweenComponents));

        logInButton = new JButton("Sign In");
        logInButton.addActionListener(this);
        logInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(logInButton);

        centerPanel.add(Box.createRigidArea(spacinigBetweenComponents));

        feedback = new JLabel();
        feedback.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(feedback);

        centerPanel.add(Box.createRigidArea(verticalMargin));

        add(centerPanel, BorderLayout.CENTER);

        eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.setPreferredSize(horizontalMargin);
        eastPanel.add(Box.createRigidArea(new Dimension(0, verticalMargin.height + spacinigBetweenComponents.height + textFieldSize.height + loginLabel.getMaximumSize().height + passwordLabel.getMaximumSize().height - 2)));

        echoRadioButton = new JRadioButton();
        echoRadioButton.setText("Show");
        echoRadioButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        echoRadioButton.addActionListener(this);
        eastPanel.add(echoRadioButton);
        add(eastPanel, BorderLayout.EAST);

        getRootPane().setDefaultButton(logInButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == echoRadioButton) {
            String tmp = new String(passwordField.getPassword());
            if (echoRadioButton.isSelected()) {
                passwordField.setEchoChar((char) 0);
                echoRadioButton.setText("Hide");
            } else {
                passwordField.setEchoChar('*');
                echoRadioButton.setText("Show");
            }
            passwordField.setText(tmp);
        } else {
            DatabaseFactory databaseFactory = new DatabaseFactory();
            String login = loginTextField.getText();
            String password = new String(passwordField.getPassword());
            if (databaseFactory.connect(login, password)) {
                MainWindow mainWindow = new MainWindow(databaseFactory);
                mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainWindow.setVisible(true);
                mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
                this.dispose();
            } else {
                feedback.setText("Wrong login or password");
                feedback.setForeground(Color.RED);
            }
        }
    }

}

