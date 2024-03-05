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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.sql.*;

public class ChangeUserInfo extends JFrame {

    private JPanel contentPane;
    private JTextField nameField, passwordField, emailField;
    private String userPassword, userName, userEmail;
    private JLabel enterEmail, enterPassword, enterName;

    public static void main(String[] args){
        ChangeUserInfo my_info = new ChangeUserInfo("jhkim");
        my_info.setVisible(true);
    }
    /**
     * Create the frame.
     */
    public ChangeUserInfo(String userID) {
        setBounds(40, 40, 1000, 700);
        setResizable(true);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        enterPassword = new JLabel("Password :");
        enterPassword.setFont(new Font("Tahoma", Font.PLAIN, 30));
        enterPassword.setBounds(45, 40, 326, 67);
        contentPane.add(enterPassword);

        enterName = new JLabel("Name :");
        enterName.setFont(new Font("Tahoma", Font.PLAIN, 30));
        enterName.setBounds(45, 120, 326, 67);
        contentPane.add(enterName);

        enterEmail = new JLabel("Email :");
        enterEmail.setFont(new Font("Tahoma", Font.PLAIN, 30));
        enterEmail.setBounds(45, 200, 326, 67);
        contentPane.add(enterEmail);

        try{
            Connection con = (Connection) DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/Calendar", "dbms_practice", "dbms_practice"
            );
                
            PreparedStatement st = (PreparedStatement) con
                .prepareStatement("Select password, name, email from public.user where user_id = ?");

            st.setString(1, userID);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                userPassword = rs.getString("password");
                userName = rs.getString("name");
                userEmail = rs.getString("email"); 
            } 
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        passwordField = new JTextField();
        passwordField.setText(userPassword);
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 28));
        passwordField.setBounds(250, 40, 400, 67);
        contentPane.add(passwordField);
        passwordField.setColumns(10);

        nameField = new JTextField();
        nameField.setFont(new Font("Tahoma", Font.PLAIN, 28));
        nameField.setBounds(250, 120, 400, 67);
        nameField.setText(userName);
        contentPane.add(nameField);
        nameField.setColumns(10);

        emailField = new JTextField();
        emailField.setFont(new Font("Tahoma", Font.PLAIN, 28));
        emailField.setBounds(250, 200, 400, 67);
        contentPane.add(emailField);
        emailField.setColumns(10);
        emailField.setText(userEmail);

        JButton btnSearch = new JButton("Change!");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = (Connection) DriverManager.getConnection(
                        "jdbc:postgresql://127.0.0.1:5432/Calendar", "dbms_practice", "dbms_practice"
                    );
                        
                    PreparedStatement st = (PreparedStatement) con
                        .prepareStatement("Update public.user set password=?, name=?, email=? where user_id=?");

                    st.setString(1, passwordField.getText());
                    st.setString(2, nameField.getText());
                    st.setString(3, emailField.getText());
                    st.setString(4, userID);
                
                    st.executeUpdate();
                    JOptionPane.showMessageDialog(btnSearch, "UserInfo has been successfully changed");

                    dispose();

                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }

            }
        });
        btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 29));
        btnSearch.setBackground(new Color(240, 240, 240));
        btnSearch.setBounds(438, 300, 170, 59);
        contentPane.add(btnSearch);

    }
 

}