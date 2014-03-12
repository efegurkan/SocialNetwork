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
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import socialnetwork.SocialNetwork;


public class PeopleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCancel;

    @FXML
    private ListView lvPeople;

    @FXML
    private TextField txtSearch;


    @FXML
    void btnCancelHndl(ActionEvent event) throws Exception {
        SceneManager.getInstance().gotoFeed(socialnetwork.SocialNetwork.getLoggedUser());
    }

    @FXML
    void txtSearchHndl(ActionEvent event) {
        refresh(txtSearch.getText());
    }

    @FXML
    void initialize() {
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'people.fxml'.";
        assert lvPeople != null : "fx:id=\"lvPeople\" was not injected: check your FXML file 'people.fxml'.";
        assert txtSearch != null : "fx:id=\"txtSearch\" was not injected: check your FXML file 'people.fxml'.";


    }
    
    public void load(String search) {
        lvPeople.setCellFactory(new Callback<ListView<Member>, ListCell<Member>>(){

            @Override
            public ListCell<Member> call(ListView<Member> p) {
                ListCell peopleCell = new PeopleCell();
                peopleCell.setEditable(false);
                return peopleCell;
            }
        });
        
        if(search != null)
            txtSearch.setText(search);
        refresh(search);
        
    }

    private void refresh(String search) {
        ArrayList<Member> results = new ArrayList();
         if(search == null || search.isEmpty()){
            if(!SocialNetwork.memberList.isEmpty()){
                for (Member member : SocialNetwork.memberList) {
                    if(member.isSearchVisibility()) {
                        results.add(member);
                    }
                }
            }
         }else {
            
            for (Member member : SocialNetwork.memberList) {
                if(member.isSearchVisibility()
                    && member.getName().toLowerCase().contains(search.toLowerCase()))
                {
                    results.add(member);
                }
            }
            
        }
         lvPeople.setItems(FXCollections.observableList(results));
    }

}
