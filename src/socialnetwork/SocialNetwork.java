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

package socialnetwork;

/**
 *
 * @author efegurkan
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;
import core.Member;
import dblayer.DBConnection;
import gui.SceneManager;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.stage.WindowEvent;


public class SocialNetwork extends Application {
    
    public static SocialNetwork instance;
    //globals
    public static ArrayList<Member> memberList= new ArrayList<>();
    public static long memberIdCount=1;
    private static Member loggedUser = null;

    private Stage stage;
    private Parent root;
    private SceneManager sceneManager = null;
    private Scene currentScene = null;
    private DBConnection dbconnection = null;
    
    @Override
    public void start(Stage mainStage) throws Exception {
        instance = this;
        sceneManager = SceneManager.getInstance();
        dbconnection = DBConnection.getInstance();
        dbconnection.connect();

        setStage(mainStage);
        
        
        //Default scene
        sceneManager.gotoLogin();
        getStage().setTitle("Social Network Application");
        getStage().show();   
        
        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>(){

            @Override
            public void handle(WindowEvent t) {
            }
            
        });
    }
    
    
    public SceneManager getSceneManager() throws Exception{
        if(this.sceneManager == null){ 
            throw new Exception("Scene Manager not initialized!");
        }
        
        return sceneManager;
    }
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * @return the root
     */
    public Parent getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(Parent root) {
        this.root = root;
    }

    /**
     * @return the currentScene
     */
    public Scene getCurrentScene() {
        return currentScene;
    }

    /**
     * @param currentScene the currentScene to set
     */
    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }
    
    /**
     * @return the loggedUser
     */
    public static Member getLoggedUser() {
        return loggedUser;
    }

    /**
     * @param aLoggedUser the loggedUser to set
     */
    public static void setLoggedUser(Member aLoggedUser) {
        loggedUser = aLoggedUser;
    }
    
    
}
