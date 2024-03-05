package Event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.Box;


public class EventMainScreen extends JPanel {
    private DisplayEvents userEventsTable;

    public EventMainScreen(String UserId, String userSes) {
        // 이벤트 뷰
        userEventsTable = new DisplayEvents(UserId, userSes); 
        add(userEventsTable);

        // 이벤트 추가 , 이벤트 삭제 버튼
    }

    public static void main(String[] ar) {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);

        EventMainScreen eventMainScreen = new EventMainScreen("jhkim","jhkim4");
        frame.add(eventMainScreen);

        frame.setVisible(true);   

        new EventMainScreen("jhkim","jhkim4");
    }
}
