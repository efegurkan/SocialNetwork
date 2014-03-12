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

import core.Member;
import dblayer.DBConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import socialnetwork.SocialNetwork;

public class LoginController implements Initializable{    
    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegister;
    
    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;
 
    
    @FXML
    private Label lblLoginFailed;
    
    public Member login(String email, String password) throws SQLException{
        if( email != null || password != null){
            Member mem = DBConnection.getInstance().login(email, password);
                SocialNetwork.setLoggedUser(mem);
                return mem;
        }
    return null;
    }
    
    @FXML
    void registerBtnHndl(ActionEvent event) throws Exception {
        SocialNetwork.instance.getSceneManager().gotoRegister();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        assert btnLogin != null : "fx:id=\"btnLogin\" was not injected: check your FXML file 'login.fxml'.";
        assert btnRegister != null : "fx:id=\"btnRegister\" was not injected: check your FXML file 'login.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'login.fxml'.";
        assert txtUserName != null : "fx:id=\"txtUserName\" was not injected: check your FXML file 'login.fxml'.";
        assert lblLoginFailed != null : "fx:id=\"lblLoginFailed\" was not injected: check your FXML file 'login.fxml'.";


        btnLogin.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                Member memberReference = null;
                try {
                    memberReference = login(txtUserName.getText(), txtPassword.getText() );
                } catch (SQLException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if(  memberReference != null ){
                    try {
                        SocialNetwork.instance.getSceneManager().gotoFeed(memberReference);
                    } catch (Exception ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                else{
                    lblLoginFailed.setText("Username and/or password is wrong!");
                    
                }
            }
        });
    


    }
    
}
