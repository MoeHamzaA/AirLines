package LoginUI;

import javax.swing.*;
import java.io.*;
import java.awt.*;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login{
  private static JFrame frame;
  private static JPanel panel;
  private static Border border;
  private static ImageIcon frameIcon;
  private static JLabel userInput;
  private static JTextField userText;
  private static JLabel passInput;
  private static JPasswordField passText;
  private static JButton login;
  private static JButton register;
  private static JLabel status;
  private static JLabel fieldInputUser;
  private static JLabel fieldInputPass;
  public static boolean loginTrue = false;
  private static String userName = null;
  private static char[] passChar;
  private static String pass = null;
  private static String fileUser = null;
  private static boolean loginContain = false;

  public JFrame createGUi() {
    // setting
    frame = new JFrame("Login");
    panel = new JPanel();
    frame.setSize(300, 200);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.setAlwaysOnTop(false);
    frame.add(panel);
    panel.setLayout(null);
    panel.setForeground(new Color(218, 218, 218));
    border = BorderFactory.createLineBorder(new Color(171, 171, 171), 5, true);
    panel.setBorder(border);
    frameIcon = new ImageIcon("appleLogo.png");
    frame.setIconImage(frameIcon.getImage());

    // Creating username text
    userInput = new JLabel("Email");
    userInput.setBounds(10, 20, 80, 25);
    panel.add(userInput);

    // Creating username typing field
    userText = new JTextField();
    userText.setBounds(80, 20, 165, 25);
    panel.add(userText);

    // Creating password text
    passInput = new JLabel("Password");
    passInput.setBounds(10, 50, 80, 25);
    panel.add(passInput);

    // Creating password typing field
    passText = new JPasswordField();
    passText.setBounds(80, 50, 165, 25);
    panel.add(passText);

    // Creating Button
    login = new JButton("Login");
    login.setBounds(173, 90, 70, 25);
    panel.add(login);

    //creating register button
    register = new JButton("Register");
    register.setBounds(158, 120, 85, 25);
    panel.add(register);

    // Create status output
    status = new JLabel("");
    status.setBounds(10, 90, 80, 25);
    panel.add(status);

    // Creating no username input indiction
    fieldInputUser = new JLabel("");
    fieldInputUser.setBounds(250, 20, 80, 25);
    fieldInputUser.setFont(new Font("Arial", Font.PLAIN, 20));
    panel.add(fieldInputUser);

    // Creating no password input indication
    fieldInputPass = new JLabel("");
    fieldInputPass.setBounds(250, 50, 80, 25);
    fieldInputPass.setFont(new Font("Arial", Font.PLAIN, 20));
    panel.add(fieldInputPass);
    buttonSetupListeners();

    // set frame visibility
    frame.setVisible(true);
    return frame;

  }
  

  //create a button listener to check when the button is pressed
  public static void buttonSetupListeners() {
    ActionListener button1Listener = new ActionListener() {
      //override
      @Override
      public void actionPerformed(ActionEvent e) {
        //get values of the values inputted
        userName = userText.getText();
        passChar = passText.getPassword();
        pass = String.valueOf(passChar);
        
        //Check if file contains the username and password
        try {
          FileReader fr = new FileReader("AirLines- Shams _ Hamza/src/main/java/LoginUI/login.txt");
          BufferedReader br = new BufferedReader(fr);
          FileWriter fw = new FileWriter("AirLines- Shams _ Hamza/src/main/java/LoginUI/email.txt", true);
          PrintWriter pw = new PrintWriter(fw);
          //read first line
          fileUser = br.readLine();
          while (fileUser != null) {
            //if the inputted username is equal to the one stored in system
            if (userName.equals(fileUser)) {
              //set loginContain to true
              loginContain = true;
              //get password of the account
              fileUser = br.readLine();
              if (pass.equals(fileUser)) {
                pw.println(userName); 
                //set status text in panel to "Success"               
                status.setText("Success");
                loginTrue = true;
                //reset all changes
                status.setForeground(new Color(4, 219, 18));
                fieldInputUser.setText("");
                fieldInputPass.setText("");
                //set the String to null
                fileUser = null;
                //pause for 500 ms then close the frame 
                try {
                  Thread.sleep(500);
                  frame.dispose();
                } catch (InterruptedException e1) {
                  e1.printStackTrace();
                }
                

              }
              //if the user clicked login without inputted anything, set fieldInput to *
            } else if (userName.equals("") || pass.equals("")) {
              if (userName.equals("")) {
                fieldInputUser.setText("*");
              }
              if (pass.equals("")) {
                fieldInputPass.setText("*");
              }
              //Prompts user to create an acc or retry the password
            } else {
              if (!loginContain) {
                status.setText("Create acc");
              }
              if (loginContain == true && loginTrue == false) {
                status.setText("wrong pass");
              }else {
                status.setText("Failed, retry");
                status.setForeground(new Color(235, 1, 1));
                fieldInputUser.setText("");
                fieldInputPass.setText("");
              }
            }
            //read next line
            fileUser = br.readLine();
          }
          //close reader and writer
          br.close();
          pw.close();

        } catch (IOException a) {
          System.out.println("File Error...: " + a);
        }
      }
    };
    //ActionListener if the user clicks register
    ActionListener button2Listener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        Register reg = new Register();
        reg.register();
        frame.dispose();
      }
    };
    //Add the listeners to the buttons
    login.addActionListener(button1Listener);
    register.addActionListener(button2Listener);
  }
}
