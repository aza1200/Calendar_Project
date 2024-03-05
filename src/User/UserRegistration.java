package User;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class UserRegistration extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField emailField,pwdField,nameField, IDField; 
    private JLabel lblEnterNewPassword, lblEnterNewID, lblEnterNewName;
    private ResultSet rs;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public UserRegistration() {
        setBounds(450, 360, 1200, 600);
        setResizable(true);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        IDField = new JTextField();
        IDField.setFont(new Font("Tahoma", Font.PLAIN, 34));
        IDField.setBounds(385, 35, 609, 67);
        contentPane.add(IDField);
        IDField.setColumns(10);
        
        pwdField = new JTextField();
        pwdField.setFont(new Font("Tahoma", Font.PLAIN, 34));
        pwdField.setBounds(385, 150, 609, 67);
        contentPane.add(pwdField);
        pwdField.setColumns(10);

        nameField = new JTextField();
        nameField.setFont(new Font("Tahoma", Font.PLAIN, 34));
        nameField.setBounds(385, 240, 609, 67);
        contentPane.add(nameField);
        nameField.setColumns(10);

        emailField = new JTextField();
        emailField.setFont(new Font("Tahoma", Font.PLAIN, 34));
        emailField.setBounds(385, 330, 609, 67);
        contentPane.add(emailField);
        emailField.setColumns(10);

        JButton btnSearch = new JButton("Enter");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String newID = IDField.getText();
                String newPwd = pwdField.getText();
                String newName = nameField.getText();
                String newEmail = emailField.getText();

                try {
                    Connection con = (Connection) DriverManager.getConnection(
                        "jdbc:postgresql://127.0.0.1:5432/Calendar", "dbms_practice", "dbms_practice"
                    );
                        
                    PreparedStatement st = (PreparedStatement) con
                        .prepareStatement("select count(*) from public.user where user_id = ?");
                    
                    st.setString(1, newID);
                    
                    ResultSet rs = st.executeQuery();

                    if(rs.next()){
                        int count = rs.getInt(1);
                        if (count >0){
                            JOptionPane.showMessageDialog(btnSearch, "The user is already exists! Please enter another name");
                        }else{
                            // 새로운 유저 생성 가능 함
                            PreparedStatement userCreateStatement = (PreparedStatement) con
                            .prepareStatement("insert into public.user (user_id, name, password, email) values(?, ?, ?, ?)");

                            userCreateStatement.setString(1, newID);
                            userCreateStatement.setString(2, newName);
                            userCreateStatement.setString(3, newPwd);
                            userCreateStatement.setString(4, newEmail);

                            int r_user = userCreateStatement.executeUpdate();
                            System.out.println("변경된 user row : "+r_user);
                            // 1. 해당 정보로 유저 생성
                            // 2. User id 에 해당하는 calendar 생성

                            // PreparedStatement calendarCreateStatement = (PreparedStatement) con
                            // .prepareStatement("insert into public.calendar (user_id, calendar_name) select user_id, ? from public.user where name = ?");
                            // calendarCreateStatement.setString(1, newName);
                            // calendarCreateStatement.setString(2, newName);

                            // int r_calendar = calendarCreateStatement.executeUpdate();
                            // System.out.println("insert 된 calendar row : "+ r_calendar);

                            dispose();
                            // UserLogin loginScreen = new UserLogin();
                            // loginScreen.setTitle("Welcome");
                            // loginScreen.setVisible(true);
                            JOptionPane.showMessageDialog(btnSearch, "You have successfully created User");
                        }
                    }; // Move to the first row of the result set


                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }

            }
        });

        lblEnterNewID = new JLabel("New Id :");
        lblEnterNewID.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblEnterNewID.setBounds(45, 37, 326, 67);
        contentPane.add(lblEnterNewID);

        lblEnterNewPassword = new JLabel("New Password :");
        lblEnterNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblEnterNewPassword.setBounds(45, 150, 326, 67);
        contentPane.add(lblEnterNewPassword);

        lblEnterNewName = new JLabel("Your Name :");
        lblEnterNewName.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblEnterNewName.setBounds(45, 240, 326, 67);
        contentPane.add(lblEnterNewName);

        lblEnterNewName = new JLabel("Email(Choice) :");
        lblEnterNewName.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblEnterNewName.setBounds(45, 330, 326, 67);
        contentPane.add(lblEnterNewName);

        btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 29));
        btnSearch.setBackground(new Color(240, 240, 240));
        btnSearch.setBounds(438, 430, 170, 59);
        contentPane.add(btnSearch);

    }
}