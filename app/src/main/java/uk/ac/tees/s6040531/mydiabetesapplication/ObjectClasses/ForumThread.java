package uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses;

import java.io.Serializable;

public class ForumThread implements Serializable
{

    /**
     * Attributes for Thread
     */
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
     * Retrieves the thread id
     * @return threadID
     */
    public String getThreadID()
    {
        return threadID;
    }

    /**
     * Retrieves the title
     * @return title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Retrieves the description
     * @return desc
     */
    public String getDesc()
    {
        return desc;
    }

    /**
     * Retrieves the number of posts
     * @return posts
     */
    public String getPosts()
    {
        return posts;
    }

    /**
     * Sets the thread id
     * @param threadID
     */
    public void setThreadID(String threadID)
    {
        this.threadID = threadID;
    }

    /**
     * Sets the title
     * @param title
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Sets the description
     * @param desc
     */
    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    /**
     * Sets the posts
     * @param posts
     */
    public void setPosts(String posts)
    {
        this.posts = posts;
    }
}
