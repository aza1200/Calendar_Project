package Event;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import javax.swing.border.EmptyBorder;

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

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class CheckAvailability extends JPanel {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField eventNameField;
    private JLabel enterStartTime, enterEndTime, enterEventName, enterIsAllDay, enterOtherUsers;
    private DateTimePicker eventEndTimePicker, eventStartTimePicker;    
    private Date fromDate;
    private JButton findBtn;

    public CheckAvailability(String UserId, String userSes) {

        setLayout(null);

        // Start Time : 이벤트 시작 시간입력란 
        enterStartTime = new JLabel("Start Time");
        enterStartTime.setFont(new Font("Tahoma", Font.PLAIN, 30));
        enterStartTime.setBounds(50, 50, 300, 100);
        this.add(enterStartTime);

        eventStartTimePicker = new DateTimePicker();
        eventStartTimePicker.setFont(new Font("Tahoma", Font.PLAIN, 30));
        eventStartTimePicker.setBounds(400, 50, 300, 100);
        this.add(eventStartTimePicker);

        enterEndTime = new JLabel("End Time");
        enterEndTime.setFont(new Font("Tahoma", Font.PLAIN, 30));
        enterEndTime.setBounds(50, 200, 300, 100);
        this.add(enterEndTime);

        eventEndTimePicker = new DateTimePicker();
        eventEndTimePicker.setFont(new Font("Tahoma", Font.PLAIN, 30));
        eventEndTimePicker.setBounds(400, 200, 300, 100);
        this.add(eventEndTimePicker);

        findBtn = new JButton("Find Availability");
        findBtn.setFont(new Font("Tahoma", Font.PLAIN, 26));
        findBtn.setBounds(350, 350, 300, 100);
        findBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                LocalDateTime inputStartTime = eventStartTimePicker.getDateTimePermissive();
                LocalDateTime inputEndTime = eventEndTimePicker.getDateTimePermissive();
                
                Timestamp startTimeStamp = Timestamp.valueOf(inputStartTime);
                Timestamp endTimeTimestamp = Timestamp.valueOf(inputEndTime);

                try {
                    Connection connection = (Connection) DriverManager.getConnection(
                        "jdbc:postgresql://127.0.0.1:5432/Calendar", "dbms_practice", "dbms_practice"
                    );

                    PreparedStatement st = (PreparedStatement) connection.prepareStatement(
                        "select u.name, case when " +
                        "( " +
                        "    select  count(*) " +
                        "    from public.event e left join public.user_event ue " +
                        "    on e.event_id = ue.event_id " +
                        "    where " +
                        "    ( " +
                        "        (start_time <= ? and ? <= end_time) " +
                        "        or " +
                        "        (start_time <= ? and ? <= end_time) " +
                        "        or " +
                        "        (? <=start_time and end_time <= ?) " +
                        "    ) and ue.user_id = u.user_id " +
                        ") > 0 then false else 'true' end as is_possible " +
                        "from public.user u " +
                        "where u.user_id <> ?"
                    );

                    st.setTimestamp(1, startTimeStamp);
                    st.setTimestamp(2, startTimeStamp);
                    st.setTimestamp(3, endTimeTimestamp);
                    st.setTimestamp(4, endTimeTimestamp);
                    st.setTimestamp(5, startTimeStamp);
                    st.setTimestamp(6, endTimeTimestamp);
                    st.setString(7, UserId);
                    
                    String ret_message = "<html>";
                    ResultSet rs = st.executeQuery();
                    while(rs.next()){
                        String userName = rs.getString("name");
                        boolean isPossible = rs.getBoolean("is_possible");
                        ret_message += "<br>";
                        ret_message += (userName) + " : " + (isPossible);
                    }
                    ret_message += "</html>";
                    new CustomMessageDialog(ret_message, "Check Availability");
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        });
        this.add(findBtn);
    }

    public static void main(String[] ar) {

        CheckAvailability checkAvailability = new CheckAvailability("jhkim", "jhkim");
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 800);

        frame.add(checkAvailability);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(true);
    }
}
