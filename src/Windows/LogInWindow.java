package Windows;

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
    JTextField loginField;
    JRadioButton echoRadioButton;
    JLabel feedback, loginLabel, passwordLabel;

    public LogInWindow() {
        setSize(windowWidth, windowHeight);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Sign in");
        setLayout(null);
        logInButton = new JButton("Sign In");
        logInButton.setBounds(135, 130, 80, 20);
        logInButton.addActionListener(this);
        add(logInButton);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 100, 65, 20);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 100, 110, 20);
        passwordField.setEchoChar('*');
        add(passwordField);

        loginLabel = new JLabel("Login:");
        loginLabel.setBounds(75, 70, 40, 20);
        add(loginLabel);

        loginField = new JTextField();
        loginField.setBounds(120, 70, 110, 20);
        add(loginField);

        echoRadioButton = new JRadioButton();
        echoRadioButton.setBounds(240, 100, 80, 20);
        echoRadioButton.setText("Show");
        echoRadioButton.addActionListener(this);
        add(echoRadioButton);

        feedback = new JLabel();
        feedback.setBounds(100, 160, 150, 20);
        add(feedback);
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
            String login = loginField.getText();
            String password = new String(passwordField.getPassword());
            if (dataBaseFactory.checkIfUserExists(login)) {
                if (dataBaseFactory.checkPassword(login, password)) {
                    MainWindow mainWindow = new MainWindow();
                    mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    mainWindow.setVisible(true);
                    this.dispose();
                } else {
                    feedback.setText("Wrong password");
                    feedback.setForeground(Color.RED);
                }
            } else {
                feedback.setText("Wrong login");
                feedback.setForeground((Color.RED));
            }
        }
    }

}

