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
//    TODO: CRIAR UMA FLAG PARA DISPARAR UM EMAIL COM OS ITENS NO PACOTE SEMANAL, CRIANDO O DIRETORIO DE ENTREGA NO CMSDEV

    public static void main(String... args) throws Exception {

        Utils.printBannerDXC();
        LOGGER.info("Iniciando...");
        LOGGER.info("Versao do Batch: " + APP_VERSION);

        Path path = new Path();//TODO: PASSAR O OBJ ENTREGA
        path.setUser("dxca4815xk"); //TODO: USAR PARA CRIAR DIRETORIO NO CMSDEV
        path.setTicket("CASU-777"); //TODO: USAR PARA CRIAR DIRETORIO NO CMSDEV
        path.setBasePath("hom8"); //TODO: DIRETORIO NO AMBIENTE DE HOMOLOGAÇÃO H08
        path.setBaseHomol(true); //TODO: USAR PARA CRIAR DIRETORIO NO AMBIENTE DE HOMOLOGAÇÃO H08
        path.setBaseCmsdev(true); //TODO: USAR PARA CRIAR DIRETORIO NO AMBIENTE DE HOMOLOGAÇÃO H08

        propertiesFile(path);

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
                    FileWrite.writeFileMS(ms, Path.getCmsapOutput());
                    break;
                case AP:
                    FileWrite.writeFileAP(ap, Path.getCfgOutput());
                    break;
                case TS:
                    FileWrite.writeFileTS(ts, Path.getCmsapOutput());
                    break;
                case XS:
                    FileWrite.writeFileXS(xs, Path.getCmsapOutput());
                    break;
                case BASIC:
                    FileWrite.writeFileBASIC(basic, Path.getCmsapOutput());
                    break;
                case JIL:
                    FileWrite.writeFileJIL(jil, Path.getCmsapOutput());
                    break;
            }

        } catch (Exception e) {
            LOGGER.fatal("Erro Fatal.");
            e.printStackTrace();
            throw e;
        }

    }


    private static void propertiesFile(Path path) throws IOException, SGBException {
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/sgb-file-processor.properties")) {
            Properties prop = new Properties();
            prop.load(fileInputStream);

            FileEnum basePath = BaseEnum.getByParameterName(path.getBasePath());

            Ambiente ambiente = new Ambiente(); //TODO: USAR PARA DISPONIBILIZAR NO CMSDEV OU HOML
            ambiente.setCmsdevOutput(prop.getProperty("dir.output.cmsdev").replace("{0}", path.getUser() + "/" + path.getTicket()));
            ambiente.setCmsapOutput(prop.getProperty("dir.output.cmsap").replace("{0}", basePath.toString()));
            ambiente.setCfgOutput(prop.getProperty("dir.output.cfg").replace("{0}", basePath.toString()));
            ambiente.setCmsacqrOutput(prop.getProperty("dir.output.cmsacqr").replace("{0}", basePath.toString()));
            ambiente.setCmsissrOutput(prop.getProperty("dir.output.cmsissr").replace("{0}", basePath.toString()));

        } catch (IOException | SGBException ex) {
            LOGGER.error("Falha ao ler arquivos properties");
            throw ex;
        }
    }

}

