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
 * @author efegurkan
 */

public class Status{

    public static Status createStatus(long senderId, long statusId, String statusText, long timestamp) {
        return new Status(senderId, statusId, statusText, timestamp);
    }
    
    private final long statusId;
    private final long senderId;
    //private int visibility;
    private final long time;
    private String statusText;
    private boolean isDeleted;

    /**
     * @return the statusId
     */
    public long getStatusId() {
        return statusId;
    }

    /**
     * @return the statusText
     */
    public String getStatusText() {
        return statusText;
    }

    /**
     * @param statusText the statusText to set
     */
    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
    
    private Status(long senderId, long statusId, String statusText, long time ){
        this.senderId = senderId;
        this.statusId = statusId;
        this.statusText = statusText;
        this.isDeleted = false;
        if(time == 0)//default value
            this.time = System.currentTimeMillis();
        else
            this.time = time;
    }

    /**
     * @return the isDeleted
     */
    public boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * @return the senderRef
     */
    public long getSenderId() {
        return senderId;
    }

    /**
     * @return the time
     */
    public Long getTime() {
        return time;
    }
            
}