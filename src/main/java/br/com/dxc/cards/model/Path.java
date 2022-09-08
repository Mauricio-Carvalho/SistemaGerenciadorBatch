package br.com.dxc.cards.model;

public class Path extends Ambiente{

    private static String user;
    private static String ticket;
    private static String basePath;
    private static boolean baseHomol;
    private static boolean baseCmsdev;
    private static Ambiente ambiente;

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        Path.user = user;
    }

    public static String getTicket() {
        return ticket;
    }

    public static void setTicket(String ticket) {
        Path.ticket = ticket;
    }

    public static String getBasePath() {
        return basePath;
    }

    public static void setBasePath(String basePath) {
        Path.basePath = basePath;
    }

    public static boolean isBaseHomol() {
        return baseHomol;
    }

    public static void setBaseHomol(boolean baseHomol) {
        Path.baseHomol = baseHomol;
    }

    public static boolean isBaseCmsdev() {
        return baseCmsdev;
    }

    public static void setBaseCmsdev(boolean baseCmsdev) {
        Path.baseCmsdev = baseCmsdev;
    }

    public static Ambiente getAmbiente() {
        return ambiente;
    }

    public static void setAmbiente(Ambiente ambiente) {
        Path.ambiente = ambiente;
    }
}
