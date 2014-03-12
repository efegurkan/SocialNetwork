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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import socialnetwork.SocialNetwork;

//Singleton
public class SceneManager {
    private static SceneManager instance = null;
    private FXMLLoader loader = null;
    
    protected SceneManager(){}
    
    public static SceneManager getInstance(){
        if(instance == null)
            instance = new SceneManager();
        return instance;
    }
    
    public void gotoFeed(Member member) throws Exception{
        this.selectScene("feed.fxml");
        FeedController fc = loader.getController();
        fc.load(member);
    }
    
    public void gotoLogin() throws Exception{
        if(SocialNetwork.getLoggedUser() != null)
            SocialNetwork.setLoggedUser(null);
        this.selectScene("login.fxml");
        
    }
    
    public void gotoPeople(String searchString) throws Exception{
        this.selectScene("people.fxml");
        PeopleController pc = loader.getController();
        pc.load(searchString);
        
    }
    
    public void gotoProfile(Member member) throws Exception{
        this.selectScene("profile.fxml");
        ProfileController profileController = loader.getController();
        profileController.load(member);
        
    }
    
    public void gotoRegister() throws Exception{
        this.selectScene("register.fxml");
    }
    
    public void selectScene(String sceneUrl) throws Exception{
        Parent p;
        loader = new FXMLLoader(getClass().getResource(sceneUrl));
        p = (Parent) loader.load();
        SocialNetwork.instance.setRoot(p);
        
        if(SocialNetwork.instance.getCurrentScene()== null)
           SocialNetwork.instance.getStage().setScene(new Scene(SocialNetwork.instance.getRoot()));
        else
            SocialNetwork.instance.getStage().getScene().setRoot(SocialNetwork.instance.getRoot());
    }
    
}
