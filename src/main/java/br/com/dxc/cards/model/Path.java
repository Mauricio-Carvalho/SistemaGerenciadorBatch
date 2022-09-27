package br.com.dxc.cards.model;

public class Path extends Ambiente{

    public String user;
    public String ticket;
    public String basePath;
    public boolean baseHomol;
    public boolean baseCmsdev;
    public Ambiente ambiente;

    public Path() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public boolean isBaseHomol() {
        return baseHomol;
    }

    public void setBaseHomol(boolean baseHomol) {
        this.baseHomol = baseHomol;
    }

    public boolean isBaseCmsdev() {
        return baseCmsdev;
    }

    public void setBaseCmsdev(boolean baseCmsdev) {
        this.baseCmsdev = baseCmsdev;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }
}
