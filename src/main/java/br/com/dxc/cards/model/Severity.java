package br.com.dxc.cards.model;

import java.util.Date;

public class Severity {
    private int code;
    private String description; //TODO: 1 URGENTE SLA 06:00Hrs - 2 GRAVE NÃO TEM SLA MAS PRECISA ATUAÇÃO PARA ENVIO DO ARQUIVO - 3 NORMAL AGUARDAR HORARIO COMERCIAL
    private Date sla;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getSla() {
        return sla;
    }

    public void setSla(Date sla) {
        this.sla = sla;
    }
}
