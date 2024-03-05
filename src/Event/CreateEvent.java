package Event;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;


import java.text.SimpleDateFormat;
import java.time.chrono.JapaneseDate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import User.BasicScreenTab;

import java.util.Date;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeArea;
import com.github.lgooddatepicker.zinternaltools.InternalUtilities;

import java.sql.ResultSet;
import java.sql.Timestamp;


public class CreateEvent extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField eventNameField;
    private JLabel enterStartTime, enterEndTime, enterEventName, enterIsAllDay, enterOtherUsers;
    private JCheckBox isAllDay;
    private DateTimePicker eventEndTimePicker, eventStartTimePicker;    
    private Date fromDate;
    private List<UserData> userListFromDatabase;
    private DefaultListModel<UserData> userListModel;
    private JList<UserData> userJList;
    private JScrollPane userScrollPane;
    private UserData UserData;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CreateEvent frame = new CreateEvent("jhkim", "jhkim");
                    frame.setSize(1300, 800);
                    frame.setVisible(true);
                    frame.setLocation(40, 40);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public CreateEvent(String UserId,String name) {
        setBounds(40, 40, 1300, 1000);
        setResizable(true);



        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        // Enter Name : 이벤트 명 입력란
        enterEventName = new JLabel("Event Name");
        enterEventName.setFont(new Font("Tahoma", Font.PLAIN, 30));
        enterEventName.setBounds(45, 37, 326, 67);
        contentPane.add(enterEventName);

        eventNameField = new JTextField();
        eventNameField.setFont(new Font("Tahoma", Font.PLAIN, 34));
        eventNameField.setBounds(280, 35, 500, 67);
        contentPane.add(eventNameField);

        // Start Time : 이벤트 시작 시간입력란 
        enterStartTime = new JLabel("Start Time");
        enterStartTime.setFont(new Font("Tahoma", Font.PLAIN, 30));
        enterStartTime.setBounds(45, 150, 326, 67);
        contentPane.add(enterStartTime);

        eventStartTimePicker = new DateTimePicker();
        eventStartTimePicker.setFont(new Font("Tahoma", Font.PLAIN, 30));
        eventStartTimePicker.setBounds(280, 150, 500, 67);
        contentPane.add(eventStartTimePicker);

        // End Time : 이벤트 끝나는 시간 입력란
        enterEndTime = new JLabel("End Time");
        enterEndTime.setFont(new Font("Tahoma", Font.PLAIN, 30));
        enterEndTime.setBounds(45, 265, 326, 67);
        contentPane.add(enterEndTime);

        eventEndTimePicker = new DateTimePicker();
        eventEndTimePicker.setFont(new Font("Tahoma", Font.PLAIN, 30));
        eventEndTimePicker.setBounds(280, 265, 500, 67);
        contentPane.add(eventEndTimePicker);
                    
        // All_Day 여부 체크박스로 표시

        enterIsAllDay = new JLabel("Is All Day?");
        enterIsAllDay.setFont(new Font("Tahoma", Font.PLAIN, 30));
        enterIsAllDay.setBounds(45, 380, 326, 67);
        contentPane.add(enterIsAllDay);

        JCheckBox IsAllday = new JCheckBox();
        IsAllday.setFont(new Font("Tahoma", Font.PLAIN, 20));
        IsAllday.setBounds(385, 380, 50, 50); // Adjust the bounds accordingly
        contentPane.add(IsAllday);

        // User 목록 보여주기 아무도 없으면 본인만 Insert해서 추가
        enterOtherUsers = new JLabel("Invite Users");
        enterOtherUsers.setFont(new Font("Tahoma", Font.PLAIN, 30));
        enterOtherUsers.setBounds(450, 380, 326, 67);
        contentPane.add(enterOtherUsers);

        // PostGreSQL 에서 유저 리스트 가지고와서 리스트에 담기
        userListModel = new DefaultListModel<>();
        List<UserData> userListFromDatabase = fetchUserListFromDatabase();

        for (UserData userData : userListFromDatabase) {
            userListModel.addElement(userData);
        }

        userJList = new JList<>(userListModel);
        userJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        userJList.setCellRenderer(new UserListCellRenderer());

        // Add JList to JScrollPane
        userScrollPane = new JScrollPane(userJList);
        userScrollPane.setBounds(700, 380, 300, 200);
        contentPane.add(userScrollPane);

        // Insert 버튼 생성
        JButton createEventBtn = new JButton("Create Event\r\n");
        createEventBtn.setBackground(UIManager.getColor("Button.disabledForeground"));
        createEventBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputEventName = eventNameField.getText();
                LocalDateTime inputStartTime = eventStartTimePicker.getDateTimePermissive();
                LocalDateTime inputEndTime = eventEndTimePicker.getDateTimePermissive();
                boolean inputIsAllDay = IsAllday.isSelected();
                // List<UserData> selected

                int[] selectedIndices = userJList.getSelectedIndices();
                System.out.print("Selected Users: ");
                for (int index : selectedIndices) {
                    System.out.print(userListModel.getElementAt(index).getUserID() + " "+userListModel.getElementAt(index).getUserName());
                }
                System.out.println();  // Move to the next line

                int a = JOptionPane.showConfirmDialog(createEventBtn, "Really wanna Create Event?");
                if (a == JOptionPane.YES_OPTION){

                    // 이벤트 테이블에 이벤트 집어넘
                    Integer CreatedEventId = createEvent(inputEventName, inputStartTime, inputEndTime, inputIsAllDay);
                    // user event 테이블 생성
                    createUserEvent(UserId, CreatedEventId);
                    createUserNotification(UserId, inputStartTime ,CreatedEventId);

                    if(selectedIndices.length >0){
                        for (int index : selectedIndices) {
                            createUserEvent(userListModel.getElementAt(index).getUserID(), CreatedEventId);
                            createUserNotification(userListModel.getElementAt(index).getUserID(), inputStartTime ,CreatedEventId);
                        }
                        // 한명한명 돌아가면서 request 이벤트 생성
                    }
                    dispose();

                    JOptionPane.showMessageDialog(createEventBtn, "You have successfully Created Event");
                }
                // create event 전 사전메세지 뜨고
                // 사전 메세지 잘누르면
                // 현재창 닫고 이벤트 성공적으로 생성됬다고 말해줌 
                
            }
        });
        createEventBtn.setBounds(230, 500, 400, 100);
        createEventBtn.setFont(new Font("Tahoma", Font.PLAIN, 39));
        contentPane.add(createEventBtn);
        // Insert 시에 event insert 해주고, 유저 존재시 event request 보냄.
        
    }

    private void createUserNotification(String userID, LocalDateTime EffecticeTime, int eventID){
        try{
            Connection con = (Connection) DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/Calendar", "dbms_practice", "dbms_practice"
            );

            PreparedStatement st = (PreparedStatement) con.prepareStatement(
                "insert into public.notification (user_id, effective_time, event_id) values(?, ?, ?)"
            );

            st.setString(1, userID);
            st.setTimestamp(2, Timestamp.valueOf(EffecticeTime.minusMinutes(30)));
            st.setInt(3, eventID);
            
            st.executeUpdate();

        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    private void createUserEvent(String userId, Integer eventID){
        try{
            Connection con = (Connection) DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/Calendar", "dbms_practice", "dbms_practice"
            );

            PreparedStatement st = (PreparedStatement) con.prepareStatement(
                "insert into public.user_event (user_id, event_id) values(?, ?)"
            );

            st.setString(1, userId);
            st.setInt(2, eventID);
            
            st.executeUpdate();

        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    private int createEvent(
        String  eventName, LocalDateTime startTime, LocalDateTime endDateTime, boolean isAllDay
    )
    {
        try {
            Connection con = (Connection) DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/Calendar", "dbms_practice", "dbms_practice"
            );
                
            // 쿼리에 RETURNING을 추가하여 생성된 키를 반환하도록 합니다.
            PreparedStatement st = (PreparedStatement) con.prepareStatement(
                    "insert into public.event (name, start_time, end_time, is_all_day) values(?, ?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, eventName);
            st.setTimestamp(2, Timestamp.valueOf(startTime));
            st.setTimestamp(3, Timestamp.valueOf(endDateTime));
            st.setBoolean(4, isAllDay);

            st.executeUpdate();

                    // 생성된 키를 얻습니다.
            ResultSet generatedKeys = st.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedEventId = generatedKeys.getInt(1);
                System.out.println("Event Successfully Created with ID: " + generatedEventId);
                return generatedEventId;
            } else {
                System.out.println("Event creation failed, couldn't retrieve generated ID.");
                return -1; // 실패 시 -1 반환 또는 예외 처리
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return -1;
        }
    }

    public class UserData {
        private String userID;
        private String userName;
    
        public UserData(String userID, String userName) {
            this.userID = userID;
            this.userName = userName;
        }
    
        public String getUserID() {
            return this.userID;
        }
    
        public String getUserName() {
            return this.userName;
        }

        @Override
        public String toString(){
            return this.userName;
        }
    }

    private static class UserListCellRenderer extends JLabel implements ListCellRenderer<UserData> {

        @Override
        public Component getListCellRendererComponent(JList<? extends UserData> list, UserData value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            setText(value.toString());

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            
            setEnabled(list.isEnabled());
            setOpaque(true);
            setFont(list.getFont());

            return this;
        }
    }

    private List<UserData> fetchUserListFromDatabase() {
        List<UserData> userList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(
            "jdbc:postgresql://127.0.0.1:5432/Calendar", "dbms_practice", "dbms_practice"
        );
             PreparedStatement st = connection.prepareStatement("Select user_id,name from public.user");
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                String userID = rs.getString("user_id");
                String userName = rs.getString("name");
                UserData tmpUserData = new UserData(userID, userName);
                userList.add(tmpUserData);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return userList;
    }


}