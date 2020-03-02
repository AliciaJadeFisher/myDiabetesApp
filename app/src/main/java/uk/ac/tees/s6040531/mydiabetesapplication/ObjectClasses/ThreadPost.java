package uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses;

import java.util.Date;

public class ThreadPost
{
    /**
     * Attributes for ThreadPost
     */
    String threadID, postID, senderID, senderName, message;
    Date postDate;


    /**
     * Empty constructor
     */
    public ThreadPost(){}

    /**
     * Main constructor
     * @param tID
     * @param sID
     * @param m
     */
    public ThreadPost(String tID, String pID, String sID, String n, String m, Date pD)
    {
        threadID = tID;
        postID = pID;
        senderID = sID;
        senderName = n;
        message = m;
        postDate = pD;
    }

    /**
     * Retrieves the thread id
     * @return threadID
     */
    public String getThreadID()
    {
        return threadID;
    }

    public String getPostID() {
        return postID;
    }

    /**
     * Retrieves teh sender id
     * @return senderID
     */
    public String getSenderID()
    {
        return senderID;
    }

    public String getSenderName()
    {
        return senderName;
    }

    /**
     * Retrieves the message
     * @return message
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Retrieves the post date
     * @return postDate
     */
    public Date getPostDate()
    {
        return postDate;
    }

    /**
     * Sets the thread id
     * @param threadID
     */
    public void setThreadID(String threadID)
    {
        this.threadID = threadID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    /**
     * Sets the sender id
     * @param senderID
     */
    public void setSenderID(String senderID)
    {
        this.senderID = senderID;
    }

    public void setSenderName(String senderName)
    {
        this.senderName = senderName;
    }

    /**
     * Sets the message
     * @param message
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * Sets the post date
     * @param postDate
     */
    public void setPostDate(Date postDate)
    {
        this.postDate = postDate;
    }
}
