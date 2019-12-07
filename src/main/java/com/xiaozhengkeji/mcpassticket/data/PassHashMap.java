package com.xiaozhengkeji.mcpassticket.data;

import java.util.HashMap;
import java.util.List;

public class PassHashMap {
    private HashMap<String, List<String>> 免费map = new HashMap<>();
    private HashMap<String, List<String>> 付费map = new HashMap<>();
    private String date = "";
    private Integer exp = 0;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public HashMap<String, List<String>> get免费map() {
        return 免费map;
    }

    public void set免费map(HashMap<String, List<String>> 免费map) {
        this.免费map = 免费map;
    }

    public HashMap<String, List<String>> get付费map() {
        return 付费map;
    }

    public void set付费map(HashMap<String, List<String>> 付费map) {
        this.付费map = 付费map;
    }
}
