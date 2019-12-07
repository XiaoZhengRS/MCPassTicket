package com.xiaozhengkeji.mcpassticket.entitys;

public class PlayerPassTicket {
    private FreePassTicket freePassTicket;
    private PayPassTicket payPassTicket;
    private Integer exp;

    public PlayerPassTicket(FreePassTicket freePassTicket, PayPassTicket payPassTicket, Integer exp) {
        this.freePassTicket = freePassTicket;
        this.payPassTicket = payPassTicket;
        this.exp = exp;
    }

    public FreePassTicket getFreePassTicket() {
        return freePassTicket;
    }

    public void setFreePassTicket(FreePassTicket freePassTicket) {
        this.freePassTicket = freePassTicket;
    }

    public PayPassTicket getPayPassTicket() {
        return payPassTicket;
    }

    public void setPayPassTicket(PayPassTicket payPassTicket) {
        this.payPassTicket = payPassTicket;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }
}
