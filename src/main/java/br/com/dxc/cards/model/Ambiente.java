package br.com.dxc.cards.model;

public class Ambiente {


    private static String cmsdevOutput;
    private static String cmsapOutput;
    private static String cfgOutput;
    private static String cmsacqrOutput;
    private static String cmsissrOutput;

    public static String getCmsdevOutput() {
        return cmsdevOutput;
    }

    public static void setCmsdevOutput(String cmsdevOutput) {
        Ambiente.cmsdevOutput = cmsdevOutput;
    }

    public static String getCmsapOutput() {
        return cmsapOutput;
    }

    public static void setCmsapOutput(String cmsapOutput) {
        Ambiente.cmsapOutput = cmsapOutput;
    }

    public static String getCfgOutput() {
        return cfgOutput;
    }

    public static void setCfgOutput(String cfgOutput) {
        Ambiente.cfgOutput = cfgOutput;
    }

    public static String getCmsacqrOutput() {
        return cmsacqrOutput;
    }

    public static void setCmsacqrOutput(String cmsacqrOutput) {
        Ambiente.cmsacqrOutput = cmsacqrOutput;
    }

    public static String getCmsissrOutput() {
        return cmsissrOutput;
    }

    public static void setCmsissrOutput(String cmsissrOutput) {
        Ambiente.cmsissrOutput = cmsissrOutput;
    }
}
