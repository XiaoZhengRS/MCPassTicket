package com.xiaozhengkeji.mcpassticket.entitys;

import java.util.List;

public class FreePassTicket {
    private List<String> giveOK;

    public FreePassTicket(List<String> giveOK) {
        this.giveOK = giveOK;
    }

    public List<String> getGiveOK() {
        return giveOK;
    }

    public void setGiveOK(List<String> giveOK) {
        this.giveOK = giveOK;
    }
}
