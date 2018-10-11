/* @Author Mark Darr
 *
 *
 *
 */
package sha256withpasswordpolicy;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class SHA256withPasswordPolicy extends Frame implements ActionListener{
   
   
   public static void main(String[] args) {
      final JFrame frame;
      JPanel panel;
      JButton loginButton;
      JButton regButton;

      frame = new JFrame("Credentials!");
      frame.setSize(400, 150);
      loginButton = new JButton("Login");
      regButton = new JButton("Register");
      
      loginButton.setBounds(10, 80, 80, 25);
      regButton.setBounds(180, 80, 80, 25);
      
      JLabel userLabel = new JLabel("User");
	   userLabel.setBounds(10, 10, 80, 25);
		

		final JTextField userText = new JTextField(28);
		userText.setBounds(100, 10, 160, 25);
      
		//String user = userText.getText();

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(20, 40, 80, 30);
		

		final JPasswordField passwordText = new JPasswordField(25);
		passwordText.setBounds(100, 40, 160, 25);
	

      
      
      
     // Register an event listener with all 2 buttons.
      
      regButton.addActionListener(new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
              //Declear Variables
              boolean valid = true;
              String tempUser = userText.getText();
              String user = tempUser.toLowerCase();
              String passcode;
              String hashedCode = null;
              passcode = passwordText.getText();
              
              //Calls sha256 method to hash the user input value
              try {
                  hashedCode =  sha256(passcode);
              } catch (UnsupportedEncodingException ex) {
                  Logger.getLogger(SHA256withPasswordPolicy.class.getName()).log(Level.SEVERE, null, ex);
              }
                //The next couple of lines sets the password making the user unable to register if they do not abide by the rule.
                if (passcode.length() > 15 || passcode.length() < 8)
                 {
                        JOptionPane.showMessageDialog(frame,
                            "Password should be less than 15 and more than 8 characters in length.",
                            "Error!!!!!",
                            JOptionPane.ERROR_MESSAGE);
                         valid = false;
                 }
                 if (passcode.indexOf(user) > -1)
                 {
                        JOptionPane.showMessageDialog(frame,
                            "Password Should not be same as user name.",
                            "Error!!!!!",
                            JOptionPane.ERROR_MESSAGE);
                         valid = false;
                 }
                 String upperCaseChars = "(.*[A-Z].*)";
                 if (!passcode.matches(upperCaseChars ))
                 {
                         JOptionPane.showMessageDialog(frame,
                            "Password should contain at least one upper case alphabet.",
                            "Error!!!!!",
                            JOptionPane.ERROR_MESSAGE);
                         valid = false;
                 }
                 String lowerCaseChars = "(.*[a-z].*)";
                 if (!passcode.matches(lowerCaseChars ))
                 {
                         JOptionPane.showMessageDialog(frame,
                            "Password should contain at least one lower case alphabet.",
                            "Error!!!!!",
                            JOptionPane.ERROR_MESSAGE);
                         valid = false;
                 }
                 String numbers = "(.*[0-9].*)";
                 if (!passcode.matches(numbers))
                 {
                         JOptionPane.showMessageDialog(frame,
                            "Password should contain at least one number.",
                            "Error!!!!!",
                            JOptionPane.ERROR_MESSAGE);
                         valid = false;
                 }
                 String specialChars = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";
                 if (!passcode.matches(specialChars ))
                 {                         
                          JOptionPane.showMessageDialog(frame,
                            "Password should contain at least one special character.",
                            "Error!!!!!",
                            JOptionPane.ERROR_MESSAGE);
                         valid = false;
                 }
              //If user abides by rules, then write credentials to file
              else if(valid){
              BufferedWriter bufferedWriter = null;
              try {
                  bufferedWriter = new BufferedWriter(new FileWriter("info.txt"));
              } catch (IOException ex) {
                  Logger.getLogger(SHA256withPasswordPolicy.class.getName()).log(Level.SEVERE, null, ex);
              }
              try {
                  bufferedWriter.write("User: " + user);
                 
                  
              } catch (IOException ex) {
                  Logger.getLogger(SHA256withPasswordPolicy.class.getName()).log(Level.SEVERE, null, ex);
              }
              try {
                  bufferedWriter.newLine();
                  bufferedWriter.write("Password: " + hashedCode);
              } catch (IOException ex) {
                  Logger.getLogger(SHA256withPasswordPolicy.class.getName()).log(Level.SEVERE, null, ex);
              }
              try {
                  bufferedWriter.close();
              } catch (IOException ex) {
                  Logger.getLogger(SHA256withPasswordPolicy.class.getName()).log(Level.SEVERE, null, ex);
              }
              JOptionPane.showMessageDialog(null, "You Registered");
              
              
          
        }
       }
      });
     
      //Login button action
      loginButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            BufferedReader bufferedReader = null;
            FileReader fr = null;
            //Reads file
              try {
                  fr = new FileReader("info.txt");
              } catch (FileNotFoundException ex) {
                  Logger.getLogger(SHA256withPasswordPolicy.class.getName()).log(Level.SEVERE, null, ex);
              }
            bufferedReader = new BufferedReader(new BufferedReader(fr));
            String s;
            ArrayList<String[]> list;
            list = new ArrayList<>();
            String[][] fin = null;
            //Splits the username and password by a space to store in the fin array
              try {
                  while((s = bufferedReader.readLine()) != null) {
                      String[] values = s.split("[\\s]+");
                      list.add(values);
                    
                  } } catch (IOException ex) {
                  Logger.getLogger(SHA256withPasswordPolicy.class.getName()).log(Level.SEVERE, null, ex);
              }
              
              try {
                  fr.close();
              } catch (IOException ex) {
                  Logger.getLogger(SHA256withPasswordPolicy.class.getName()).log(Level.SEVERE, null, ex);
              }
              fin = new String[list.size()][list.get(0).length];
              fin = list.toArray(fin);

               System.out.println();
              for (int i=0; i<fin.length; i++){
            // The Code below allows the user to check where the stored values are
            //
            //
            //for (int j=0; j<fin[i].length; j++){
               // System.out.println("fin[" + (i+1) + "][" + (j+1) + "]=" + fin[i][j]);
           // }
        }
              //Gets the currently entered credentials
              String enteredPasscode;
              enteredPasscode = passwordText.getText();
              String enteredHash = null;
              try {
                  enteredHash = sha256(enteredPasscode);
              } catch (UnsupportedEncodingException ex) {
                  Logger.getLogger(SHA256withPasswordPolicy.class.getName()).log(Level.SEVERE, null, ex);
              }
              String tempEnteredUser = userText.getText();
              String enteredUser = tempEnteredUser.toLowerCase();
              
               //Compares current credentials with stored creditials. If valid than login, else reject login
               if (enteredUser.equals(fin[0][1]) && enteredHash.equals(fin[1][1])) {
                    JOptionPane.showMessageDialog(null, "You Logged in");
              } else{
                   JOptionPane.showMessageDialog(frame,
                            "You do not have the correct credentials \n#AccessDenied!!!!",
                            "Error!!!!!",
                            JOptionPane.ERROR_MESSAGE);
               }
               
           // JOptionPane.showMessageDialog(null, "You Logged in");
          }
      });
      

      // Create a panel and add the buttons to it.
      panel = new JPanel();
      panel.add(userLabel);
      panel.add(userText);
      panel.add(passwordLabel);
      panel.add(passwordText);
      panel.add(loginButton);
      panel.add(regButton);
      

      // Add the panel to the content pane.
      frame.add(panel);

      // Display the window.
      frame.setVisible(true);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
      
   }

    //Needed to avoid compilation error
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    //Hash Method
    public static String sha256(String input) throws UnsupportedEncodingException {
         
        String sha256 = null;
         
        if(null == input) return null;
         
        try {
             
        //Create MessageDigest object for MD5
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
         
        //Update input string in message digest
        digest.update(input.getBytes("UTF-16"), 0, input.length());
 
        //Converts message digest value in base 16 (hex)
        sha256 = new BigInteger(1, digest.digest()).toString(16);
        
        } catch (NoSuchAlgorithmException e) {
 
            e.printStackTrace();
        }
        return sha256;
    }
}
