package com.example.project_mobileprog_aa;

import okhttp3.internal.platform.Platform;

public class ZeldaGames {

    private Integer ID;
    private String Name;
    private String img_Cover_URL;
    private Integer Release_year_in_Europe;
    private String Modes;
    private String Original_Platforms;
    private Integer Dungeons;
    private String Remakes;

    public Integer getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getImg_Cover_URL() {
        return img_Cover_URL;
    }

    public Integer getRelease_year_in_Europe() {
        return Release_year_in_Europe;
    }

    public String getModes() {
        return Modes;
    }

    public String getOriginal_Platforms() {
        return Original_Platforms;
    }

    public Integer getDungeons() {
        return Dungeons;
    }

    public String getRemakes() {
        return Remakes;
    }
}
