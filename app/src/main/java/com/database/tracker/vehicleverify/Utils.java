package com.database.tracker.vehicleverify;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<Links> globalList = new ArrayList<Links>();
}

class Links {

    String linkName, LinkUrl;

    public Links() {
    }

    public Links(String linkName, String linkUrl) {
        this.linkName = linkName;
        LinkUrl = linkUrl;
    }

    public String getLinkName() {
        return linkName;
    }

    public String getLinkUrl() {
        return LinkUrl;
    }
}

