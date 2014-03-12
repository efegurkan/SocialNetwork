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
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.HyperlinkBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.layout.BorderPaneBuilder;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.FlowPaneBuilder;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;


public class StatusCell extends ListCell<Status>{
    Status status;
    Member sender;
    Node textNode;
    ImageView imageView;
    Image img;
    ListView listView;

    @Override
    protected void updateItem(Status t, boolean bln) {
        status = t;        
        super.updateItem(t, bln);
        if(t != null){
            sender = dblayer.DBConnection.getInstance().getMemberObject(t.getSenderId());
            setGraphic(createTopNode());
        }
    }
    
    Node createTopNode() {
        listView = getListView();
        
        Node displayNode = HBoxBuilder.create().spacing(6).children(
        imageView = ImageViewBuilder.create().image( 
                img = new Image( "/gui/defaultProfile.png") ).build(),
                textNode = createTextNode()
                ).build();
        return displayNode;
    }

    private Node createTextNode() {
        String text = this.status.getStatusText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yy");
        String timeStamp = dateFormat.format(this.status.getTime());
        listView = getListView();
        
        FlowPane flowPane = FlowPaneBuilder.create()
                .hgap(6)
                .vgap(6)
                .build();
        flowPane.setPrefWidth(listView.getWidth());
        
        Hyperlink memberName = HyperlinkBuilder.create()
                .id("senderProfileLink")
                .text(sender.getName())
                .font(Font.font(Font.getDefault().getName(),
                        FontWeight.BOLD,
                        Font.getDefault().getSize() + 4))
                .onAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                try {
                    SceneManager.getInstance().gotoProfile(sender);
                } catch (Exception ex) {
                    Logger.getLogger(StatusCell.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                })
                .build();
        
        //flowPane.getChildren().add(memberName);
        Node statusTextAsLabel = new Label(text);
        Label timeStampAsLabel = LabelBuilder.create()
                .text(timeStamp)
                .font(Font.font(Font.getDefault().getName(),
                                FontWeight.EXTRA_LIGHT,
                                FontPosture.ITALIC,
                                Font.getDefault().getSize() - 4))
                .build();
        
        flowPane.getChildren().add(statusTextAsLabel);
        
        return BorderPaneBuilder.create()
                .top(memberName)
                .center(flowPane)
                .bottom(timeStampAsLabel)
               .build();
    }   
}