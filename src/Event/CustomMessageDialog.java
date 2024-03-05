package Event;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class CustomMessageDialog extends JDialog {

    public CustomMessageDialog(String message, String title) {
        setTitle(title);

        // HTML 태그를 사용하여 줄 바꿈 추가
        String htmlMessage = "<html>" + message.replaceAll("\n", "<br>") + "</html>";
        JLabel label = new JLabel(htmlMessage);
        add(label, BorderLayout.CENTER);

        setSize(300, 300); // 원하는 크기로 설정
        setLocationRelativeTo(null); // 화면 중앙에 표시
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        setVisible(true);
    }

    public static void main(String[] args) {
        String message = "Your custom message goes here.\nThis is a new line.";
        String title = "Custom Message";
        new CustomMessageDialog(message, title);
    }
}