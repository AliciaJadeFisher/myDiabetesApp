package uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses;

import java.util.Date;

/**
 * ThreadPost Object Class
 */
public class ThreadPost
{
    // Class attributes
    private String threadID, postID, senderID, senderName, message;
    private Date postDate;

    /**
     * Empty constructor
     */
    public ThreadPost(){}

    /**
     * Main constructor
     * @param tID - thread id
     * @param pID - post id
     * @param sID - sender id
     * @param n - sender name
     * @param m - post message
     * @param pD - post date
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
     * @return senderName
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
     * @param threadID - thread ID
     */
    public void setThreadID(String threadID)
    {
        this.threadID = threadID;
    }

    /**
     * Sets the post id attribute
     * @param postID - post ID
     */
    public void setPostID(String postID) {
        this.postID = postID;
    }

    /**
     * Sets the sender id attribute
     * @param senderID - sender ID
     */
    public void setSenderID(String senderID)
    {
        this.senderID = senderID;
    }

    /**
     * Sets the sender name attribute
     * @param senderName - sender name
     */
    public void setSenderName(String senderName)
    {
        this.senderName = senderName;
    }

    /**
     * Sets the message attribute
     * @param message - post message
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * Sets the post date attribute
     * @param postDate - post date
     */
    public void setPostDate(Date postDate)
    {
        this.postDate = postDate;
    }
}