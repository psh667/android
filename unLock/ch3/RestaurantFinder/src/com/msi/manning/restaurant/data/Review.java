package com.msi.manning.restaurant.data;

import java.util.Date;

/**
 * Simple data bean to represent a Review - no getters and setters on purpose (more efficient on
 * Android).
 * 
 * @author ccollins
 */
public class Review {

    public String author;
    public String content;
    public String cuisine; // input only
    public Date date;
    public String imageLink;
    public String link;
    public String location;
    public String name;
    public String phone;
    public String rating;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("*Review*\n");
        sb.append("name:" + this.name + "\n");
        sb.append("author:" + this.author + "\n");
        sb.append("link:" + this.link + "\n");
        sb.append("imageLink:" + this.imageLink + "\n");
        sb.append("location:" + this.location + "\n");
        sb.append("phone:" + this.phone + "\n");
        sb.append("rating:" + this.rating + "\n");
        sb.append("date:" + this.date + "\n");
        sb.append("content:" + this.content);
        return sb.toString();
    }
}
