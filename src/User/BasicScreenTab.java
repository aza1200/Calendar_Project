package User;

import java.awt.TextArea;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import Event.EventMainScreen;
import Event.CheckAvailability;

public class BasicScreenTab extends JFrame {

    public BasicScreenTab(String UserId, String UserSession) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createTabbedPane(UserId, UserSession);
        setTitle(UserSession + "'s Calendar");
        setSize(1300, 800);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
    }

    void createTabbedPane(String UserId, String UserSession) {
        JTabbedPane tPane = new JTabbedPane();
        add(tPane);

        UserHome mainPanel = new UserHome(UserId, UserSession);
        tPane.addTab("Calendar", mainPanel);

        CheckAvailability checkScreen = new CheckAvailability(UserId, UserSession);
        tPane.addTab("Check Availablility", checkScreen);

        EventMainScreen eventPanel = new EventMainScreen(UserId, UserSession);
        tPane.addTab("Events", eventPanel);

        // JPanel reportPanel = new JPanel();
        // reportPanel.add(reportLabel);
        // tPane.addTab("Check Availability", reportPanel);
    }
    

}