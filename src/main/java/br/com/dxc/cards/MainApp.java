package br.com.dxc.cards;

import br.com.dxc.cards.enuns.FileEnum;
import br.com.dxc.cards.exception.SGBException;
import br.com.dxc.cards.file.reader.FileWrite;
import br.com.dxc.cards.model.*;
import br.com.dxc.cards.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class MainApp {

    private static final Logger LOGGER = LogManager.getLogger(MainApp.class);
    private static final String APP_VERSION = "1.0.0";

    /**
     * A main() so we can easily run these routing rules in our IDE
     */

    public static void main(String... args) throws Exception {

        Utils.printBannerDXC();
        LOGGER.info("Iniciando...");
        LOGGER.info("Versao do Batch: " + APP_VERSION);
        propertiesFile();

        ArrayList listMSG = new ArrayList();
        listMSG.add("TSACAPROJETOACQIOGERAEXEMPLOTEST1");
        listMSG.add("TSACAPROJETOACQIOGERAEXEMPLOTEST2");

        MS ms = new MS();
        ms.setName("MSACAPROJETOACQIOGERAEXEMPLOTEST");
        ms.setAp("apa_ca_portal_ep_gera_futuro_programado");
        ms.setMsg(listMSG);
        ms.setType("MS");
        ms.setSeverity(1);
        ms.setSquad("Canais Adquirente");
        ms.setJob("br_crdu_acqr_projeto_adquirente_gera_exemple_test_00000_d_c");
        ms.setDescription("GERA ARQUIVO EXEMPLE_TEST.TXT");
        ms.setClient("ACQIO");
        ms.setDate("20/09/2022");


        AP ap = new AP();
        TS ts = new TS();
        XS xs = new XS();
        BASIC basic = new BASIC();
        JIL jil = new JIL();

        try {

            switch (FileEnum.getByParameterName(ms.getType())) {

                case MS:
                    FileWrite.writeFileMS(ms);
                    break;
                case AP:
                    FileWrite.writeFileAP(ap);
                    break;
                case TS:
                    FileWrite.writeFileTS(ts);
                    break;
                case XS:
                    FileWrite.writeFileXS(xs);
                    break;
                case BASIC:
                    FileWrite.writeFileBASIC(basic);
                    break;
                case JIL:
                    FileWrite.writeFileJIL(jil);
                    break;
            }

        } catch (Exception e) {
            LOGGER.fatal("Erro Fatal.");
            e.printStackTrace();
            throw e;
        }

    }

    private static void propertiesFile() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/sgb-file-processor.properties")) {
            Properties prop = new Properties();
            prop.load(fileInputStream);
            BatchProperties.setAmbiente(prop.getProperty("ambiente"));
            BatchProperties.setOutputCmsap(prop.getProperty("dir.output.cmsap"));
            BatchProperties.setOutputCfg(prop.getProperty("dir.output.cfg"));
            BatchProperties.setOutputCmsacqr(prop.getProperty("dir.output.cmsacqr"));
            BatchProperties.setOutputCmsissr(prop.getProperty("dir.output.cmsissr"));

        } catch (IOException ex) {
            LOGGER.error("Falha ao ler arquivos properties");
            throw ex;
        }
    }

}

