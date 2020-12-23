package com.database.tracker.vehicleverify;

public class notifData {
    String iD, title,body,date,link;

    public notifData(String iD, String title, String body, String date, String link) {
        this.iD = iD;
        this.title = title;
        this.body = body;
        this.date = date;
        this.link = link;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
