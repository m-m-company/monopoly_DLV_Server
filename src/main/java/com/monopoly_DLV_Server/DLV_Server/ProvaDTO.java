package com.monopoly_DLV_Server.DLV_Server;

public class ProvaDTO {
    String ciao = "";
    Integer c = 0;

    public String getCiao() {
        return ciao;
    }

    public void setCiao(String ciao) {
        this.ciao = ciao;
    }

    public Integer getC() {
        return c;
    }

    public void setC(Integer c) {
        this.c = c;
    }

    public ProvaDTO(String ciao, Integer c) {
        this.ciao = ciao;
        this.c = c;
    }
}
