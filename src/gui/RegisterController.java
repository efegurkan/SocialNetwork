/*
 * Copyright (C) 2014 Efe Gürkan YALAMAN
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package gui;

/**
 *
 * @author efegurkan
 */

import dblayer.DBConnection;
import socialnetwork.SocialNetwork;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;


public class RegisterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnRegister;
    
    @FXML
    private Button btnCancel;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtPasswd;

    @FXML
    private PasswordField txtPasswdVerify;
    
    @FXML
    private Label lblError;
    
    @FXML
    private Label lblName;
    
    @FXML
    private Label lblEmail;
        
    @FXML
    private Label lblPasswd;
            
    @FXML
    private Label lblPasswdVerify;
    
    @FXML
    private void register(){
        Color black = Color.web("#292929");
        
        lblEmail.setTextFill(black);
        lblName.setTextFill(black);
        lblPasswd.setTextFill(black);
        lblPasswdVerify.setTextFill(black);
        lblError.setText("");
        
        Color color = Color.web("#FF0000");
        boolean error = false;
        if(txtName.getText().isEmpty()){
            lblName.setTextFill(color);
            txtName.setPromptText("This field cannot be empty!");
            error = true;
        }  
        
        if(txtEmail.getText().isEmpty()){
            lblEmail.setTextFill(color);
            txtEmail.setPromptText("This field cannot be empty!");
            error = true;
        }
        
        if(txtPasswd.getText().isEmpty()){
            lblPasswd.setTextFill(color);
            txtPasswd.setPromptText("This field cannot be empty!");
            error = true;
        }
        
        if(txtPasswdVerify.getText().isEmpty()){
            lblPasswdVerify.setTextFill(color);
            txtPasswdVerify.setPromptText("This field cannot be empty!");
            error = true;
        }
        
        
        if( !txtPasswd.getText().equals(txtPasswdVerify.getText())){
            lblPasswd.setTextFill(color);
            lblPasswdVerify.setTextFill(color);
            txtPasswd.clear();
            txtPasswdVerify.clear();
            txtPasswd.setPromptText("Passwords doesn't match!");
            txtPasswdVerify.setPromptText("Passwords doesn't match!");
        }else if( !error ){
            try {
                  DBConnection.getInstance().register(txtName.getText(),
                          txtEmail.getText(),
                          txtPasswd.getText());
                  
//                Member newMember = Member.createMember(SocialNetwork.memberIdCount+1,
//                        txtName.getText(),
//                        txtPasswd.getText(),
//                        txtEmail.getText());
//                SocialNetwork.setLoggedUser(newMember);
//                SocialNetwork.instance.getSceneManager().gotoFeed(newMember);
//                dbConnection.register(newMember.getName(), newMember.getEmail(), newMember.getPassword());
                
                return;
            } catch (Exception ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    lblError.setText("Check the red areas!");
    lblError.setTextFill(color);
            
    }

    @FXML
    private void cancel() throws Exception{
        SocialNetwork.instance.getSceneManager().gotoLogin();
    }

    @FXML
    void initialize() {
        assert btnRegister != null : "fx:id=\"btnRegister\" was not injected: check your FXML file 'register.fxml'.";
        assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file 'register.fxml'.";
        assert txtName != null : "fx:id=\"txtName\" was not injected: check your FXML file 'register.fxml'.";
        assert txtPasswd != null : "fx:id=\"txtPasswd\" was not injected: check your FXML file 'register.fxml'.";
        assert txtPasswdVerify != null : "fx:id=\"txtPasswdVerify\" was not injected: check your FXML file 'register.fxml'.";


    }

}