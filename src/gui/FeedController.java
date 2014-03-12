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
import core.Status;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import socialnetwork.SocialNetwork;


public class FeedController {
    
    private Member memberRef;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnSend;

    @FXML
    private ImageView imgProfile;

    @FXML
    private Hyperlink lnkHome;
    
    @FXML
    private Hyperlink lnkProfile;
    
    @FXML
    private Hyperlink lnkSettings;
    
    @FXML
    private Hyperlink lnkPeople;
    
    @FXML
    private Hyperlink lnkExit;
    
    @FXML
    private Hyperlink lnkStatusCount;

    @FXML
    private TextArea txtStatus;
    
    @FXML
    private ListView lvFeed;

    @FXML
    private TextField txtSearch;
    
    @FXML
    void send(ActionEvent event) {
        if(!txtStatus.getText().isEmpty()){
            this.memberRef.sendStatus(txtStatus.getText());
            txtStatus.clear();
        }
        this.refresh();
    }

    @FXML
    void initialize() {
        assert btnSend != null : "fx:id=\"btnSend\" was not injected: check your FXML file 'feed.fxml'.";
        assert imgProfile != null : "fx:id=\"imgProfile\" was not injected: check your FXML file 'feed.fxml'.";
        assert lnkStatusCount != null : "fx:id=\"lnkStatusCount\" was not injected: check your FXML file 'feed.fxml'.";
        assert txtStatus != null : "fx:id=\"txtStatus\" was not injected: check your FXML file 'feed.fxml'.";

    }
    
    public void load(Member mem){
        this.memberRef = mem;
        lvFeed.setCellFactory(new Callback<ListView<Status>, ListCell<Status>>(){

            @Override
            public ListCell<Status> call(ListView<Status> p) {
                ListCell statusCell = new StatusCell();
                statusCell.setEditable(false);
                return statusCell;
            }
        });
        this.refresh();
    }
    
    public void refresh(){
        imgProfile.setImage(memberRef.getProfilePicture());
        lnkStatusCount.setText( "Status count : " 
                + this.memberRef.getStatusList().size() +"  Follower: " 
                + this.memberRef.getFollowerCount() + "  Following :"
                + this.memberRef.getFollowingCount());
        
        if( !memberRef.getFeed().isEmpty()){
            ObservableList<Status> observableArrayList = 
                    FXCollections.observableArrayList(memberRef.getFeed());
            FXCollections.reverse(observableArrayList);
            
            lvFeed.setItems(observableArrayList);
        }
    }
    
    @FXML
    void lnkHomeHndl() throws Exception{
        SocialNetwork.instance.getSceneManager().gotoFeed(SocialNetwork.getLoggedUser());
    }
    
    @FXML
    void lnkPeopleHndl() throws Exception{
        SceneManager.getInstance().gotoPeople("");
    }
    
    @FXML
    void lnkProfileHndl(){
        try {
            SceneManager.getInstance().gotoProfile(SocialNetwork.getLoggedUser());
        } catch (Exception ex) {
            Logger.getLogger(FeedController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void lnkSettingsHndl(){
        throw new UnsupportedOperationException();
    }
    
    @FXML
    void lnkExitHndl() throws Exception{
    }
    
    @FXML
    void txtSearchHndl() throws Exception {
        if(!txtSearch.getText().isEmpty()){
            SceneManager.getInstance().gotoPeople(txtSearch.getText());
        }
    }
    
    @FXML
    void lnkStatusCountHndl() throws Exception{
        SceneManager.getInstance().gotoProfile(SocialNetwork.getLoggedUser());
    }
}