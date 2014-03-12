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

package core;

/**
 *
 * @author efe
 */

import static core.Status.createStatus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import java.util.Iterator;
import javafx.scene.image.Image;


public class Member extends Observable implements Observer, java.io.Serializable{


    private long statusIDCounter = 1;
    private final long memberID;
    private String name;
    private String email;
    private String password;
    private Image profilePicture;
    private final ArrayList<Long> followerList;
    private final ArrayList<Long> followedList;
    private boolean searchVisibility;
    //private ArrayList<Integer> preferences;
    //private ArrayList<Message> messageBox;
    private final ArrayList<Status> statusList;
    private final ArrayList<Status> feed;

    
    //Factory method implementation
    public static Member createMember(long memberId, String name, String password, String email) {
        Member ret = new Member(memberId, name, password, email);
        
        ret.addObserver(ret);
        return ret;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        
        //if the update is Status add/remove operation
        if( arg instanceof Status )
        {
            Status tmpStatus = (Status) arg;
            
            if( tmpStatus.getIsDeleted() )
            {
                this.getFeed().remove(tmpStatus);
            }
            else if( !tmpStatus.getIsDeleted() ) {
                this.getFeed().add(tmpStatus);
            
            }
        }
    }
    
    public void sendStatus(String statusString) {
        //Create status
        Status newStatus = createStatus(this.memberID,this.statusIDCounter, statusString, 0);
        newStatus.setIsDeleted(false);
        this.statusIDCounter++;
        this.getStatusList().add(newStatus);
        setChanged();
        notifyObservers(newStatus);
    }
    
    public void deleteStatus(long statusId) {
        Status deletedStatus = null;
        for (Iterator<Status> it = getStatusList().iterator(); it.hasNext();) {
             deletedStatus = it.next();
            
            if(deletedStatus.getStatusId() == statusId ) { 
                this.getStatusList().remove(deletedStatus);
                this.getFeed().remove(deletedStatus);
                deletedStatus.setIsDeleted(true);
                setChanged();
                break;
            }
            
        }
        notifyObservers(deletedStatus);
    }
    
    //public void sendMessage(long recieverId, String message) {
    //    throw new UnsupportedOperationException("Not supported yet."); 
    //}
    
//    public void deleteMessage(long messageId) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    public void clearMessages() {
//        throw new UnsupportedOperationException("Not supported yet."); 
//        //this.messagebox.clear();
//    }
    
    public boolean follow(long followedUserId) {
        for (Iterator<Member> it = socialnetwork.SocialNetwork.memberList.iterator(); it.hasNext();) {
            Member followedMember = it.next();
            
            if(followedMember.getMemberID() == followedUserId) {
                followedMember.followed(this);
                break;
            }
            
        }
        return this.followedList.add(followedUserId);
    }
    
    public boolean unfollow(long unfollowedUserId) {
        for (Iterator<Member> it = socialnetwork.SocialNetwork.memberList.iterator(); it.hasNext();) {
            Member unfollowedMember = it.next();
            
            if(unfollowedMember.getMemberID() == unfollowedUserId) {
                unfollowedMember.unfollowed(this);
                break;
            }
            
        }
        
        //feed'den bu kişinin postlarını çıkar
        return this.followedList.remove(unfollowedUserId);
    }
    
    public boolean followed(Member follower ) {
        //TODO: blocked member control.
        addObserver(follower);
        if( this.followerList.add(follower.getMemberID())){
            follower.getFeed().addAll(this.statusList);
            Collections.sort(follower.getFeed(),new StatusComparator());
            return true;
        }
        return false;
    }
    
    public boolean unfollowed(Member unfollower) {
        deleteObserver(this);
        if(this.followerList.add(unfollower.getMemberID())){
           unfollower.getFeed().removeAll(this.statusList);
           Collections.sort(unfollower.getFeed(),new StatusComparator());
           return true;
        }
        return false;
    }

    /**
     * @return the memberID
     */
    public long getMemberID() {
        return memberID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the profilePicture
     */
    public Image getProfilePicture() {
        return profilePicture;
    }

    /**
     * @param profilePicture the profilePicture to set
     */
    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
        
    private Member(long memberId, String name, String password, String email ) {
        this.memberID = memberId;
        this.name = name;
        this.password= password;
        this.email = email;
        this.searchVisibility = true;
        this.profilePicture = new Image("/gui/defaultProfile.png");
        followerList = new ArrayList<>();
        followedList = new ArrayList<>();
        statusList = new ArrayList<>();
        feed = new ArrayList<>();
    }

    /**
     * @return the statusList
     */
    public ArrayList<Status> getStatusList() {
        return statusList;
    }

    /**
     * @return the feed
     */
    public ArrayList<Status> getFeed() {
        return feed;
    }
    
    public int getFollowerCount() {
        return this.followerList.size();
    }
    
    public int getFollowingCount() {
        return this.followedList.size();
    }

    public boolean isInFollowing(long memberID) {
        return this.followedList.contains(memberID);
    }

    /**
     * @return the searchVisibility
     */
    public boolean isSearchVisibility() {
        return searchVisibility;
    }

    /**
     * @param searchVisibility the searchVisibility to set
     */
    public void setSearchVisibility(boolean searchVisibility) {
        this.searchVisibility = searchVisibility;
    }
}
