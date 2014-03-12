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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;


public class StatusController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imgProfile;

    @FXML
    private Hyperlink lnkProfile;

    @FXML
    private TextArea txtStatus;

    @FXML
    void lnkProfileClick(ActionEvent event){
        
    }

    @FXML
    void initialize() {
        assert imgProfile != null : "fx:id=\"imgProfile\" was not injected: check your FXML file 'status.fxml'.";
        assert lnkProfile != null : "fx:id=\"lnkProfile\" was not injected: check your FXML file 'status.fxml'.";
        assert txtStatus != null : "fx:id=\"txtStatus\" was not injected: check your FXML file 'status.fxml'.";


    }

}
