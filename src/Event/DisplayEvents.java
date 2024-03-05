package Event;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DisplayEvents extends JPanel{
    private JScrollPane scrolledTable;
    private DefaultTableModel eventTableModel;
    private JTable eventTable;
    private JButton modifyBtn, deleteBtn, createBtn;
    
    public DisplayEvents(String UserId, String userSes) {

        eventTableModel = new DefaultTableModel();
        eventTableModel.addColumn("Event Name");
        eventTableModel.addColumn("Start Time");
        eventTableModel.addColumn("End Time");
        eventTableModel.addColumn("All Day");

        eventTable = new JTable(eventTableModel);
        
        // Modify, Delete, Create 버튼 추가
        modifyBtn = new JButton("Modify");
        deleteBtn = new JButton("Delete");
        createBtn = new JButton("Create Event");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(modifyBtn);
        buttonPanel.add(deleteBtn);

        setLayout(new BorderLayout());
        add(new JScrollPane(eventTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
        add(createBtn, BorderLayout.SOUTH);
        
        // 이벤트 뷰
        JScrollPane scrollPane = new JScrollPane(eventTable);
        add(scrollPane);

        try {
            Connection connection = (Connection) DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/Calendar", "dbms_practice", "dbms_practice"
            );

            PreparedStatement st = (PreparedStatement) connection
                .prepareStatement(
                "select * from public.event " +
                "where event_id in ( " +
                    "select event_id " +
                    "from user_event " +
                    "where user_id = ? " +
                ")" 
                );
            
            st.setString(1, UserId);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String eventName = rs.getString("name");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                boolean allDay = rs.getBoolean("is_all_day");
            
                // 필요한 로직에 따라 이벤트 정보를 활용
                // 여기에서는 eventListModel에 추가하는 방식으로 예시를 들었습니다.
                System.out.println(eventName+ " "+ startTime+ " "+ endTime + " " + allDay);
                eventTableModel.addRow(new Object[]{eventName, startTime, endTime, allDay});
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        // 이벤트 Create 클릭시, 뷰
        createBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateEvent frame = new CreateEvent(UserId, userSes);
                frame.setVisible(true);
            }
        });
        



        // 이벤트 추가 , 이벤트 삭제 버튼

    }
    public static void main(String[] ar) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);

        DisplayEvents displayEvents = new DisplayEvents("jhkim", "jhkim4");
        frame.add(displayEvents);

        frame.setVisible(true);        
        // new DisplayEvents(4, "jhkim4");
    }
}
