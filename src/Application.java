import windows.LogInWindow;

import javax.swing.*;

public class Application {

    public static void main(String[] args) {
        LogInWindow logInWindow = new LogInWindow();
        logInWindow.setVisible(true);
        logInWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
