package br.com.dxc.cards.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.TimeZone;

public class Utils {
    private static final Logger LOG = LogManager.getLogger(Utils.class);

    public static final String SDF_AAAAMMDD = "yyyyMMdd";
    public static final String SDF_AAMMDD = "yyMMdd";
    public static final String SDF_AAAA_MM_DD = "yyyy-MM-dd";
    public static final String SDF_HHMMSS = "HHmmss";

    public static void printBannerDXC() {
        System.out.println(" \t  _______   _______    _____          _____  _____   _____            ____            \r\n" +
                " \t |  __ \\ \\ / / ____|  / ____|   /\\   |  __ \\|  __ \\ / ____|     /\\   / __ \\     /\\    \r\n" +
                " \t | |  | \\ V / |      | |       /  \\  | |__) | |  | | (___      /  \\ | |  | |   /  \\   \r\n" +
                " \t | |  | |> <| |      | |      / /\\ \\ |  _  /| |  | |\\___ \\    / /\\ \\| |  | |  / /\\ \\  \r\n" +
                " \t | |__| / ^ \\ |____  | |____ / ____ \\| | \\ \\| |__| |____) |  / ____ \\ |__| | / ____ \\ \r\n" +
                " \t |_____/_/ \\_\\_____|  \\_____/_/    \\_\\_|  \\_\\_____/|_____/  /_/    \\_\\___\\_\\/_/    \\_\\ \r\n " +
                " \t SGB - Sistema de gerenciamento Batch");
        System.out.println("");
    }

    /**
     * Returns the string format dd/MM/yyyy.
     *
     * @param		data contendo yyyyMMdd
     * @return      String
     */
    public static String getDataAsIntDD_MM_YYYY(String data) {

        if ( data == null || data.length() < 8 ) {
            return data;
        }
        return data.substring(0,2) + "/" + data.substring(2,4) + "/" + data.substring(4,8);
    }

    public static void listTS() {
    }
}
