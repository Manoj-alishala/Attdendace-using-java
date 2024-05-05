import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

class Attendance {
    JFrame f1;
    JLabel attend, date;
    JCheckBox[] check;
    JTextArea text;

    public static void main(String[] args) {
        new Attendance().createGUI();
    }

    private void createGUI() {
        f1 = new JFrame("Attendance");
        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        attend = new JLabel("Attendance:");
        attend.setFont(new Font("Arial", Font.BOLD, 16));
        attend.setBounds(40, 40, 300, 30);

        date = new JLabel("Date: ");
        date.setFont(new Font("Arial", Font.BOLD, 16));
        date.setBounds(40, 10, 300, 30);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date dateNow = new Date();
        date.setText("Date: " + formatter.format(dateNow));

        String[] rollNumbers = {"2303A51252", "2303A51179", "2303A51189", "2303A51200", "2303A51201", "2303A51202", "2303A51203", "2303A51204", "2303A51205", "2303A51206"};
        check = new JCheckBox[rollNumbers.length];
        for (int i = 0; i < rollNumbers.length; i++) {
            check[i] = new JCheckBox(rollNumbers[i]);
            check[i].addActionListener(new AttendanceListener());
            check[i].setBounds(40, 80 + i * 30, 300, 30);
            f1.getContentPane().add(check[i]);
        }

        text = new JTextArea(10, 30);
        text.setEditable(false);
        text.setBounds(40, 400, 300, 200);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitListener());
        submitButton.setBounds(40, 620, 100, 30);

        f1.getContentPane().add(attend);
        f1.getContentPane().add(date);
        f1.getContentPane().add(text);
        f1.getContentPane().add(submitButton);

        f1.setSize(500, 1000);
        f1.setLayout(null);
        f1.setVisible(true);
    }

    class AttendanceListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            String rollNumber = checkBox.getText();
            if (checkBox.isSelected()) {
                System.out.println(rollNumber + " present");
                text.append(rollNumber + " : P\n");
            } else {
                System.out.println(rollNumber + " absent");
                text.append(rollNumber + " : A\n");
            }
        }
    }

    class SubmitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                FileWriter fw = new FileWriter("attendance.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                for (JCheckBox checkBox : check) {
                    String rollNumber = checkBox.getText();
                    if (checkBox.isSelected()) {
                        bw.write(rollNumber + " : P");
                    } else {
                        bw.write(rollNumber + " : A");
                    }
                    bw.newLine();
                }
                bw.close();
                f1.dispose();
                new Attendance().createGUI();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
