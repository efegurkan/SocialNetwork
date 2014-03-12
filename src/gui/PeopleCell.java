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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.HyperlinkBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.layout.BorderPaneBuilder;
import javafx.scene.layout.HBoxBuilder;

public class PeopleCell extends ListCell<Member>{
    Member member;
    ImageView imageView;
    Image image;
    Node textPart;
    ListView listview;
    
    Node createTopNode(){
        listview = getListView();
        
        Node displayNode = HBoxBuilder.create().spacing(6).children(
            imageView = ImageViewBuilder.create().image( image = member.getProfilePicture()).build(),
                textPart = createTextPart()
                
        ).build();
        
        return displayNode;    
    }

    private Node createTextPart() {
        listview = getListView();
        
        Node nameAsLink = HyperlinkBuilder.create()
                .id("personName")
                .text(member.getName())
                .onAction(new EventHandler<ActionEvent> () {

            @Override
            public void handle(ActionEvent t) {
                try {
                    SceneManager.getInstance().gotoProfile(member);
                } catch (Exception ex) {
                    Logger.getLogger(PeopleCell.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        })
                .build();
        
        Node emailAsLabel = new Label(member.getEmail());
        
        return BorderPaneBuilder.create().top(nameAsLink).bottom(emailAsLabel).build();
    }

    @Override
    protected void updateItem(Member t, boolean bln) {
        member = t;
        super.updateItem(t, bln);
        if(t != null )
        {
            setGraphic(createTopNode());
        }
    }
    
}
