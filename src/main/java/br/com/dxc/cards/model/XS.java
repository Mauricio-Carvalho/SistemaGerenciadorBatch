package br.com.dxc.cards.model;

public class XS {
    private String type;
    private String name;
    private int severity;
    private String squad;
    private String job;
    private String description;
    private String client;
    private String date;
    private String bin;
    private String modelo;
    private String properties;
    private String simultaneous;
    private String delay;

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

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getSimultaneous() {
        return simultaneous;
    }

    public void setSimultaneous(String simultaneous) {
        this.simultaneous = simultaneous;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    @Override
    public String toString() {
        return "XS{" +
                "  bin='" + bin + '\'' +
                ", client='" + client + '\'' +
                ", modelo='" + modelo + '\'' +
                ", properties='" + properties + '\'' +
                '}';
    }
}
