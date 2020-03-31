package uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses;

import java.io.Serializable;

/**
 * ForumThread Object Class
 */
public class ForumThread implements Serializable
{

    // Class attributes
    String threadID, title, desc, posts;

    /**
     * Empty constructor
     */
    public ForumThread(){}

    /**
     * Main constructor
     * @param t
     * @param d
     * @param p
     */
    public ForumThread(String tID, String t, String d, String p)
    {
        threadID = tID;
        title = t;
        desc = d;
        posts = p;
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
     * Returns the title attribute
     * @return title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Returns the description attribute
     * @return desc
     */
    public String getDesc()
    {
        return desc;
    }

    /**
     * Returns the posts attribute
     * @return posts
     */
    public String getPosts()
    {
        return posts;
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
     * Sets the title attribute
     * @param title
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Sets the description attribute
     * @param desc
     */
    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    /**
     * Sets the posts attribute
     * @param posts
     */
    public void setPosts(String posts)
    {
        this.posts = posts;
    }
}