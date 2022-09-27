package br.com.dxc.cards;

import br.com.dxc.cards.enuns.BaseEnum;
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
//    TODO: CRIAR UMA TABLE PARA REGISTRAR TODA SOLICITAÇÃO (NOME DO JOB, USER, CASUT, DATA)

    public static void main(String... args) throws Exception {

        Utils.printBannerDXC();
        LOGGER.info("Iniciando...");
        LOGGER.info("Versao do Batch: " + APP_VERSION);

        Path path = new Path();//TODO: PASSAR O OBJ ENTREGA
        Ambiente ambiente = new Ambiente();

        path.setUser("dxca4815xk"); //TODO: USAR PARA CRIAR DIRETORIO NO CMSDEV
        path.setTicket("CASU-777"); //TODO: USAR PARA CRIAR DIRETORIO NO CMSDEV
        path.setBasePath("HOM08"); //TODO: DIRETORIO NO AMBIENTE DE HOMOLOGAÇÃO H08
        path.setCmsScriptOutput("Adquirente/Canais/Portal"); //TODO: DIRETORIO NO AMBIENTE DE HOMOLOGAÇÃO H08 PARA SHELL BASIC
        path.setBaseHomol(true); //TODO: CRIAR ARQUIVOS NO AMBIENTE DE HOMOLOGAÇÃO H08
        path.setBaseCmsdev(true); //TODO: CRIAR ARQUIVOS DIRETORIO NO CMSDEV
        ambiente = propertiesFile(path);

        path.setAmbiente(ambiente);

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
        ms.setJob("br_crdu_acqr_ca_projeto_adquirente_gera_exemple_test_00000_d_c");
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
                    FileWrite.writeFileMS(ms, path);
                    break;
                case AP:
//                    FileWrite.writeFileAP(ap, Path.getCfgOutput());
                    break;
                case TS:
//                    FileWrite.writeFileTS(ts, Path.getCmsapOutput());
                    break;
                case XS:
//                    FileWrite.writeFileXS(xs, Path.getCmsapOutput());
                    break;
                case BASIC:
//                    FileWrite.writeFileBASIC();
                    break;
                case JIL:
//                    FileWrite.writeFileJIL(jil, Path.getCmsapOutput());
                    break;
            }

        } catch (Exception e) {
            LOGGER.fatal("Erro Fatal.");
            e.printStackTrace();
            throw e;
        }

    }


    private static Ambiente propertiesFile(Path path) throws IOException, SGBException {
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/sgb-file-processor.properties")) {
            Properties prop = new Properties();
            prop.load(fileInputStream);

            BaseEnum.getByParameterName(path.getBasePath());
            String basePath = BaseEnum.valueOf(path.getBasePath()).getBaseValue();

            Ambiente ambiente = new Ambiente(); //TODO: USAR PARA DISPONIBILIZAR NO CMSDEV OU HOML
            ambiente.setCmsdevOutput(prop.getProperty("dir.output.cmsdev").replace("{?}", path.getUser() + "/" + path.getTicket()));
            ambiente.setCmsapOutput(prop.getProperty("dir.output.cmsap").replace("{?}", basePath.toString()));
            ambiente.setCfgOutput(prop.getProperty("dir.output.cfg").replace("{?}", basePath.toString()));
            ambiente.setCmsScriptOutput(prop.getProperty("dir.output.script").replace("{0}", basePath.toString()).replace("{1}", path.getCmsScriptOutput()));

            return ambiente;

        } catch (IOException | SGBException ex) {
            LOGGER.error("Falha ao ler arquivos properties");
            throw ex;
        }
    }

}

