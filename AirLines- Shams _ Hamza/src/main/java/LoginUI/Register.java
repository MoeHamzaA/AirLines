package LoginUI;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Register {
  private static JFrame frame;
  private static JPanel panel;
  private static Border border;
  private static JLabel title;
  private static JLabel email;
  private static JTextField emailInput;
  private static JLabel password;
  private static JPasswordField passwordInput;
  private static JLabel rePassword;
  private static JPasswordField rePasswordInput;
  private static JButton submit;
  private static JLabel status1;
  private static JLabel status2;
  private static JLabel status3;
  private static JLabel statusMessage;
  private static JButton backButton;

  public void register() {
    //Settings
    frame = new JFrame("Register");
    panel = new JPanel();
    frame.setSize(325, 250);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.add(panel);
    panel.setLayout(null);
    panel.setForeground(new Color(218, 218, 218));
    border = BorderFactory.createLineBorder(new Color(171, 171, 171), 5, true);
    panel.setBorder(border);
    frame.setLocationRelativeTo(null);

    //Title
    title = new JLabel("Create account.");
    title.setBounds(100, 10, 100, 25);
    panel.add(title);

    //Email 
    email = new JLabel("Email");
    email.setBounds(10, 40, 50, 25);
    panel.add(email);

    //Email Field
    emailInput = new JTextField();
    emailInput.setBounds(95, 40, 165, 25);
    panel.add(emailInput);

    //Password
    password = new JLabel("Password");
    password.setBounds(10, 75, 70, 25);
    panel.add(password);

    //Password field 
    passwordInput = new JPasswordField();
    passwordInput.setBounds(95, 75, 165, 25);
    panel.add(passwordInput);

    //password re enter
    rePassword = new JLabel("Re-enter pass");
    rePassword.setBounds(10, 110, 100, 25);
    panel.add(rePassword);

    //re enter field 
    rePasswordInput = new JPasswordField();
    rePasswordInput.setBounds(95, 110, 165, 25);
    panel.add(rePasswordInput);

    //submit button
    submit = new JButton("submit");
    submit.setBounds(175, 150, 85, 25);
    panel.add(submit);

    //back button 
    backButton = new JButton("Back");
    backButton.setBounds(175, 180, 85, 25);
    panel.add(backButton);

    //status text
    status1 = new JLabel("");
    status1.setBounds(265, 40, 80, 25);
    status1.setFont(new Font("Arial", Font.PLAIN, 20));
    panel.add(status1);
    //status text
    status2 = new JLabel("");
    status2.setBounds(265, 75, 80, 25);
    status2.setFont(new Font("Arial", Font.PLAIN, 20));
    panel.add(status2);

    //status text
    status3 = new JLabel("");
    status3.setBounds(265, 110, 80, 25);
    status3.setFont(new Font("Arial", Font.PLAIN, 20));
    panel.add(status3);

    //overall Status
    statusMessage = new JLabel("");
    statusMessage.setBounds(10, 150, 90, 25);
    panel.add(statusMessage);

    //Invoke method
    SetupButtonListener();


    //set visibility
    frame.setVisible(true);

  }

  public static void SetupButtonListener() {
    ActionListener submitListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String emailUser = null;
        char[] passChar;
        String pass = null;
        char[] rePassChar;
        String rePass = null;
        boolean isEmailRegistered = false;
        String fileReader;

        //get the text input
        emailUser = emailInput.getText();

        try {
          //read file if the current email exists
          FileReader fr = new FileReader("AirLines- Shams _ Hamza/src/main/java/LoginUI/login.txt");
          BufferedReader br = new BufferedReader(fr);
          fileReader = br.readLine();
          while (fileReader !=null) {
            if (emailUser.equals(fileReader)) {
              isEmailRegistered = true;
            }
            fileReader = br.readLine();
            fileReader = br.readLine();
          }
          br.close();
          
        } catch (IOException e1) {
          e1.printStackTrace();
        }

        //get the password in the password field
        passChar = passwordInput.getPassword();
        pass = String.valueOf(passChar);

        //get value in field
        rePassChar = rePasswordInput.getPassword();
        rePass = String.valueOf(rePassChar);

        //Status check
        //if not input exists
        if (emailUser.equals("") || pass.equals("") || rePass.equals("")) {
          if (emailUser.equals("")) {
            status1.setText("*");
          }
          if (pass.equals("")) {
            status2.setText("*");
          }
          if (rePass.equals("")) {
            status3.setText("*");
          }

        }
        //if wrong pass inputted
        if (!(pass.equals(rePass))) {
          statusMessage.setText("pass not same*");
          status2.setText("*");
          status3.setText("*");

          //Clear all status and print success
        } else if (pass.equals(rePass) && (emailUser.contains("@")) && (emailUser.contains(".com")) && (isEmailRegistered !=true)) {
          if (!(emailUser.equals(""))) {
            statusMessage.setText("Success");
            status1.setText("");
            status2.setText("");
            status3.setText("");

            try {
              //Write the email and pass in the account log
              FileWriter fw = new FileWriter("AirLines- Shams _ Hamza/src/main/java/LoginUI/login.txt", true);
              PrintWriter pw = new PrintWriter(fw);
              pw.println(emailUser);
              pw.println(pass);
              pw.close();

            } catch (IOException a) {

            }
          }
          //if its already registered, prompt to use new email
        }else if (isEmailRegistered) {
          statusMessage.setText("use new email");
        }
      }
    };
    //open the login panel once the back button is clicked
    ActionListener backListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Login log = new Login();
        log.createGUi();
        frame.dispose();

      }
    };
    submit.addActionListener(submitListener);
    backButton.addActionListener(backListener);
  }

}
