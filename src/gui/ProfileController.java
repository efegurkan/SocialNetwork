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
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import socialnetwork.SocialNetwork;


public class ProfileController {

    private Member memberRef;
    private boolean isSelf;
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCancel;

    @FXML
    private ToggleButton tglFollow;

    @FXML
    private ImageView ivProfile;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblName;

    @FXML
    private ListView lvProfileFeed;


    @FXML
    void initialize() {
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'profile.fxml'.";
        assert tglFollow != null : "fx:id=\"btnFollow\" was not injected: check your FXML file 'profile.fxml'.";
        assert ivProfile != null : "fx:id=\"ivProfile\" was not injected: check your FXML file 'profile.fxml'.";
        assert lblEmail != null : "fx:id=\"lblEmail\" was not injected: check your FXML file 'profile.fxml'.";
        assert lblName != null : "fx:id=\"lblName\" was not injected: check your FXML file 'profile.fxml'.";
        assert lvProfileFeed != null : "fx:id=\"lvProfileFeed\" was not injected: check your FXML file 'profile.fxml'.";


    }

    public void load(Member member){
        this.memberRef = member;
        lvProfileFeed.setCellFactory(new Callback<ListView<Status>, ListCell<Status>>(){

            @Override
            public ListCell<Status> call(ListView<Status> p) {
                ListCell statusCell = new StatusCell();
                statusCell.setEditable(false);
                return statusCell;
            }
        });
        
        if(this.memberRef.equals(SocialNetwork.getLoggedUser())){
            this.isSelf = true;
            tglFollow.setText("Visible");
            tglFollow.setSelected(this.memberRef.isSearchVisibility());
        }
        else if(SocialNetwork.getLoggedUser().isInFollowing(member.getMemberID()))
        {
            this.isSelf = false;
            tglFollow.setSelected(true);
        }
        this.refresh(member);
    }

    private void refresh(Member member) {
        lblName.setText(member.getName());
        lblEmail.setText(member.getEmail());
        ivProfile.setImage(member.getProfilePicture());
        
        if( !member.getStatusList().isEmpty())
            lvProfileFeed.setItems(FXCollections.observableList(member.getStatusList()));
        
    }
    
    @FXML
    void tglFollowHndl() {
        if(!isSelf && tglFollow.isSelected()){
            SocialNetwork.getLoggedUser().follow(memberRef.getMemberID());
        }else if(!isSelf && !tglFollow.isSelected()){
            SocialNetwork.getLoggedUser().unfollow(memberRef.getMemberID());
        }else if(isSelf){
            this.memberRef.setSearchVisibility(tglFollow.isSelected());
        }
    }
 
    @FXML
    void btnCancelHndl () throws Exception{
        SceneManager.getInstance().gotoFeed(socialnetwork.SocialNetwork.getLoggedUser());
    }
}
