package br.com.dxc.cards.model;

import java.util.List;

public class MS {

    private String type;
    private String name;
    private int severity;
    private String squad;
    private String job;
    private String description;
    private String client;
    private String date;
    private String ap;
    private List<String> msg;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public String getSquad() {
        return squad;
    }

    public void setSquad(String squad) {
        this.squad = squad;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAp() {
        return ap;
    }

    public void setAp(String ap) {
        this.ap = ap;
    }

    public List<String> getMsg() {
        return msg;
    }

    public void setMsg(List<String> msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "MS {" +
                "  severity=" + severity +
                ", squad='" + squad + '\'' +
                ", job='" + job + '\'' +
                ", description='" + description + '\'' +
                ", client='" + client + '\'' +
                ", date=" + date +
                '}';
    }
}
