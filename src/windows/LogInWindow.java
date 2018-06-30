package windows;

import factories.DataBaseFactory;

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

    JPanel mainPanel, centerVerticalPanel, rightVerticalPanel;
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
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.add(Box.createRigidArea(horizontalMargin));

        centerVerticalPanel = new JPanel();
        centerVerticalPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerVerticalPanel.setLayout(new BoxLayout(centerVerticalPanel, BoxLayout.Y_AXIS));
        centerVerticalPanel.add(Box.createRigidArea(verticalMargin));
        loginLabel = new JLabel("Login:");
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerVerticalPanel.add(loginLabel);

        loginTextField = new JTextField();
        loginTextField.setMaximumSize(textFieldSize);
        loginTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerVerticalPanel.add(loginTextField);

        centerVerticalPanel.add(Box.createRigidArea(spacinigBetweenComponents));

        passwordLabel = new JLabel("Password:");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerVerticalPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setMaximumSize(textFieldSize);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setEchoChar('*');
        centerVerticalPanel.add(passwordField);

        centerVerticalPanel.add(Box.createRigidArea(spacinigBetweenComponents));

        logInButton = new JButton("Sign In");
        logInButton.addActionListener(this);
        logInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerVerticalPanel.add(logInButton);

        centerVerticalPanel.add(Box.createRigidArea(spacinigBetweenComponents));

        feedback = new JLabel();
        feedback.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerVerticalPanel.add(feedback);

        centerVerticalPanel.add(Box.createRigidArea(verticalMargin));

        mainPanel.add(centerVerticalPanel);

        rightVerticalPanel = new JPanel();
        rightVerticalPanel.setLayout(new BoxLayout(rightVerticalPanel, BoxLayout.Y_AXIS));

        echoRadioButton = new JRadioButton();
        echoRadioButton.setText("Show");
        echoRadioButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        echoRadioButton.setMaximumSize(new Dimension(60, 20));
        echoRadioButton.addActionListener(this);
        rightVerticalPanel.add(echoRadioButton);
        mainPanel.add(rightVerticalPanel);
        this.add(mainPanel);

        this.getRootPane().setDefaultButton(logInButton);
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
            DataBaseFactory dataBaseFactory = new DataBaseFactory();
            String login = loginTextField.getText();
            String password = new String(passwordField.getPassword());
            if (dataBaseFactory.connect(login, password)) {
                MainWindow mainWindow = new MainWindow(dataBaseFactory);
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

