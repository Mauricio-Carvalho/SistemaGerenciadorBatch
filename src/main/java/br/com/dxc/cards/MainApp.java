package br.com.dxc.cards;

import br.com.dxc.cards.enuns.BaseEnum;
import br.com.dxc.cards.enuns.FileEnum;
import br.com.dxc.cards.exception.SGBException;
import br.com.dxc.cards.file.reader.FileWrite;
import br.com.dxc.cards.model.*;
import br.com.dxc.cards.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.util.calendar.BaseCalendar;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;


public class MainApp {

    private static final Logger LOGGER = LogManager.getLogger(MainApp.class);
    private static final String APP_VERSION = "1.0.0";
    private static final String SEVERITY = "3 - NORMAL";

    /**
     *
     * A main() so we can easily run these routing rules in our IDE
     *
    **/

//    TODO: CRIAR UMA FLAG PARA DISPARAR UM EMAIL COM OS ITENS NO PACOTE SEMANAL, CRIANDO O DIRETORIO DE ENTREGA NO CMSDEV
//    TODO: CRIAR UMA TABLE PARA REGISTRAR TODA SOLICITAÇÃO (NOME DO JOB, USER, CASUT, DATA)
//    TODO: SUGERIR ENVIO DE CRIAÇÃO DE VIEW DO AUTOSYS PARA PROD

    public static void main(String... args) throws Exception {

        Utils.printBannerDXC();
        LOGGER.info("Iniciando...");
        LOGGER.info("Versao do Batch: " + APP_VERSION);
        LOGGER.info("SEVERIDADE: " + SEVERITY);

        Path path = new Path();//TODO: PASSAR O OBJ ENTREGA
        Ambiente ambiente = new Ambiente();

        path.setUser("dxca4815xk"); //TODO: USAR PARA CRIAR DIRETORIO NO CMSDEV
        path.setTicket("CASU-777"); //TODO: USAR PARA CRIAR DIRETORIO NO CMSDEV
        path.setBasePath("HOM08"); //TODO: DIRETORIO NO AMBIENTE DE HOMOLOGAÇÃO H08
        path.setCmsScriptOutput("Adquirente/Canais/Portal"); //TODO: DIRETORIO NO AMBIENTE DE HOMOLOGAÇÃO H08 PARA SHELL BASIC
        path.setBaseHomol(true); //TODO: CRIAR ARQUIVOS NO AMBIENTE DE HOMOLOGAÇÃO H08
        path.setBaseCmsdev(true); //TODO: CRIAR ARQUIVOS DIRETORIO NO CMSDEV
        path.setPathVersionJava(8);
        path.setMaxMemoryJava(2);

        ambiente = propertiesFile(path);

        path.setAmbiente(ambiente);

//        ArrayList listMSG = new ArrayList();
//        listMSG.add("TSACAPROJETOACQIOGERAEXEMPLOTEST1");
//        listMSG.add("TSACAPROJETOACQIOGERAEXEMPLOTEST2");
//        MS ms = new MS();
//        ms.setName("MSACAPROJETOACQIOGERAEXEMPLOTEST");
//        ms.setAp("apa_ca_portal_ep_gera_futuro_programado");
//        ms.setMsg(listMSG);
//        ms.setType("MS");

//        Severity severity = new Severity();
//        severity.setCode(1);
//        severity.setDescription("Urgente - SLA atrelada");
//        severity.setSla("33");

//        ts.setSeverity(severity);
//        ms.setSquad("Canais Adquirente");
//        ms.setJob("br_crdu_acqr_ca_projeto_adquirente_gera_exemple_test_00000_d_c");
//        ms.setDescription("GERA ARQUIVO EXEMPLE_TEST.TXT");
//        ms.setClient("ACQIO");
//        ms.setDate("20/09/2022");
//--------------------------------------------------------------------------------------------


//        AP ap = new AP();
//        ArrayList listMSG = new ArrayList();
//        listMSG.add("TSACAPROJETOEPGERAREXEMPLOTEST1");
//        listMSG.add("TSACAPROJETOEPGERAREXEMPLOTEST2");
//        ap.setName("apa_ep_gerar_teste");
//        ap.setMsg(listMSG);
//        ap.setType("AP");
//--------------------------------------------------------------------------------------------


//        TS ts = new TS();
//        ts.setType("TS");
//        ts.setName("TSACAPROJETOEPGERAREXEMPLOTEST");

//        Severity severity = new Severity();
//        severity.setCode(1);
//        severity.setDescription("Urgente - SLA atrelada");
//        severity.setSla("33");

//        ts.setSeverity(severity);
//        ts.setSquad("Canais Adquirente");
//        ts.setJob("br_crdu_acqr_ca_projeto_adquirente_gerar_exemple_test_00000_d_c");
//        ts.setDescription("GERAR ARQUIVO EXEMPLE_TEST.TXT");
//        ts.setClient("EP");
//        ts.setDate("30/09/2022");
//        ts.setBin("test-bin.jar");
//        ts.setModelo("ARQUIVOTEST");
//        ts.setProperties("test-bin.properties");
//--------------------------------------------------------------------------------------------


//        XS xs = new XS();
//        xs.setName("XSACAPROJETOACQIOGERAEXEMPLOTEST");
//        xs.setType("XS");

//        Severity severity = new Severity();
//        severity.setCode(1);
//        severity.setDescription("Urgente - SLA atrelada");
//        severity.setSla("33");

//        xs.setSeverity(severity);
//        xs.setSquad("Canais Adquirente");
//        xs.setJob("br_crdu_acqr_ca_projeto_adquirente_gera_exemple_test_00000_d_c");
//        xs.setDescription("GERA ARQUIVO EXEMPLE_TEST.TXT");
//        xs.setClient("ACQIO");
//        xs.setDate("20/09/2022");
//        xs.setBin("test-bin.jar");
//        xs.setModelo("ARQUIVOTEST");
//        xs.setProperties("test-bin.properties");
//        xs.setSimultaneous("5");
//        xs.setDelay("10");
//--------------------------------------------------------------------------------------------


//        BASIC basic = new BASIC();
//        basic.setName("");
//        basic.setType("BASIC");

//        Severity severity = new Severity();
//        severity.setCode(1);
//        severity.setDescription("Urgente - SLA atrelada");
//        severity.setSla("33");

//        basic.setSeverity(severity);
//        basic.setSquad("Canais Adquirente");
//        basic.setJob("br_crdu_acqr_ca_projeto_adquirente_gera_exemple_test_00000_d_c");
//        basic.setDescription("GERA ARQUIVO EXEMPLE_TEST.TXT");
//        basic.setClient("EP");
//        basic.setDate("30/09/2022");
//        basic.setAuthor("Bianca Antunes Passador");
//        basic.setComents("Não é fácil não kkkk");
//        basic.setTicket("CASUT-666");
//--------------------------------------------------------------------------------------------

        FileWatch fileWatcher = new FileWatch(
                "JIL",
                "fw",
                "br_crdu_acqr_ca_demographics_ep_gera_arq_gns_org_6958_m_c",
                "JOB - Gera arquivo a AMEX (GNS.ORG012.P1100012.TAX) para a EP",
                "prodacqr",
                "CARDS-CANAIS-DEMOGRAPHICS-EP",
                "/home/producao/logs/*.csv",
                "0",
                "10"
        );

        Job job = new Job(
                "JIL",
                "cmd",
                "br_crdu_acqr_ca_demographics_ep_gera_arq_gns_org_6958_m_c",
                "JOB - Gera arquivo a AMEX (GNS.ORG012.P1100012.TAX) para a EP",
                "prodacqr",
                "CARDS-CANAIS-DEMOGRAPHICS-EP",
                "/home/producao/logs/*.csv",
                "0",
                "10"
        );


        try {

            switch (FileEnum.getTypeFile(fileWatcher.getType())) {
                case MS:
//                    FileWrite.writeFileMS(ms, path);
                    break;
                case AP:
//                    FileWrite.writeFileAP(ap, path);
                    break;
                case TS:
//                    FileWrite.writeFileTS(ts, path);
                    break;
                case XS:
//                    FileWrite.writeFileXS(xs, path);
                    break;
                case BASIC:
//                    FileWrite.writeFileBASIC();
                    break;
                case JIL:
                    FileWrite.writeFileJIL(fileWatcher, path);
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
            String basePath = BaseEnum.getByParameterName(path.getBasePath());

            Ambiente ambiente = new Ambiente(); //TODO: USAR PARA DISPONIBILIZAR NO CMSDEV OU HOML
            ambiente.setCmsdevOutput(prop.getProperty("dir.output.cmsdev").replace("{0}", path.getUser() + "/" + path.getTicket()));
            ambiente.setCmsapOutput(prop.getProperty("dir.output.cmsap").replace("{0}", basePath.toString()));
            ambiente.setCfgOutput(prop.getProperty("dir.output.cfg").replace("{0}", basePath.toString()));
            ambiente.setCmsScriptOutput(prop.getProperty("dir.output.script").replace("{0}", basePath.toString()).replace("{1}", path.getCmsScriptOutput()));

            return ambiente;

        } catch (IOException | SGBException ex) {
            LOGGER.error("Falha ao ler arquivos properties");
            throw ex;
        }
    }

}

