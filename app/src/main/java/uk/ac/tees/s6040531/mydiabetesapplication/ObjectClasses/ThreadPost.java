package uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses;

import java.util.Date;

/**
 * ThreadPost Object Class
 */
public class ThreadPost
{
    // Class attributes
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
     * Returns the thread id attribute
     * @return threadID
     */
    public String getThreadID()
    {
        return threadID;
    }

    /**
     * Returns the post id attribute
     * @return postID
     */
    public String getPostID() {
        return postID;
    }

    /**
     * Returns the sender id attribute
     * @return senderID
     */
    public String getSenderID()
    {
        return senderID;
    }

    /**
     * Returns the sender name attribute
     * @return
     */
    public String getSenderName()
    {
        return senderName;
    }

    /**
     * Returns the message attribute
     * @return message
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Returns the post date attribute
     * @return postDate
     */
    public Date getPostDate()
    {
        return postDate;
    }

    /**
     * Sets the thread id attribute
     * @param threadID
     */
    public void setThreadID(String threadID)
    {
        this.threadID = threadID;
    }

    /**
     * Sets the post id attribute
     * @param postID
     */
    public void setPostID(String postID) {
        this.postID = postID;
    }

    /**
     * Sets the sender id attribute
     * @param senderID
     */
    public void setSenderID(String senderID)
    {
        this.senderID = senderID;
    }

    /**
     * Sets the sender name attribute
     * @param senderName
     */
    public void setSenderName(String senderName)
    {
        this.senderName = senderName;
    }

    /**
     * Sets the message attribute
     * @param message
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * Sets the post date attribute
     * @param postDate
     */
    public void setPostDate(Date postDate)
    {
        this.postDate = postDate;
    }
}