package User;

import Event.GetUserEventList;
import Event.GetUserEventList.EventData;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

// import UserLogin;
import Calendar.CalendarCustom;

public class UserHome extends JPanel {

    private static final long serialVersionUID = 1L;
    private JPanel buttonPanel;
    private JLabel label;
    private CalendarCustom calendarCustom2;

    public UserHome(String UserID, String UserName) {

        setLayout(new BorderLayout());
        
        GetUserEventList userEventList = new GetUserEventList(UserID);
        List<EventData> usereventsList = userEventList.getUserEventList();


        calendarCustom2 = new CalendarCustom(usereventsList, UserID);
        calendarCustom2.setBorder(new EmptyBorder(10, 0, 10, 0));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10, 10));

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setForeground(new Color(0, 0, 0));
        logoutBtn.setBackground(UIManager.getColor("Button.disabledForeground"));
        logoutBtn.setFont(new Font("Tahoma", Font.PLAIN, 39));
        logoutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(logoutBtn, "Are you sure?");
                if (a == JOptionPane.YES_OPTION) {
                    // 현재 창 닫기
                    JFrame frame = (JFrame) getTopLevelAncestor();
                    frame.dispose();

                    // 새로운 UserLogin 창 열기
                    UserLogin obj = new UserLogin();
                    obj.setTitle("학생 로그인");
                    obj.setVisible(true);

                    return;
                }
            }
        });
        

        JButton changeInfoBtn = new JButton("Change-UserInfo\r\n");
        changeInfoBtn.setBackground(UIManager.getColor("Button.disabledForeground"));
        changeInfoBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ChangeUserInfo bo = new ChangeUserInfo(UserID);
                bo.setTitle("Change UserInfo");
                bo.setVisible(true);
            }
        });
        changeInfoBtn.setFont(new Font("Tahoma", Font.PLAIN, 35));


        add(calendarCustom2, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        buttonPanel.add(logoutBtn);
        buttonPanel.add(changeInfoBtn);
    }

    

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JFrame frame = new JFrame();
                    UserHome userHome = new UserHome("jhkim", "jhkim");
    
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setBounds(100, 50, 1700, 1000);
                    frame.getContentPane().setLayout(new BorderLayout());
                    frame.getContentPane().add(userHome, BorderLayout.CENTER);
    
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}