package com.nop990.pistachio.models;

public class OptionsObject {
    private String csv_path;
    private int scout_id;
    private String team_id;
    private int gb_weight;

    public OptionsObject() {
    }

    public OptionsObject(String csv_path, int scout_id, String team_id, int gb_weight) {
        this.csv_path = csv_path;
        this.scout_id = scout_id;
        this.team_id = team_id;
        this.gb_weight = gb_weight;
    }

    public String getCsv_path() {
        return csv_path;
    }

    public void setCsv_path(String csv_path) {
        this.csv_path = csv_path;
    }

    public int getScout_id() {
        return scout_id;
    }

    public void setScout_id(int scout_id) {
        this.scout_id = scout_id;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public int getGb_weight() {
        return gb_weight;
    }

    public void setGb_weight(int gb_weight) {
        this.gb_weight = gb_weight;
    }
}