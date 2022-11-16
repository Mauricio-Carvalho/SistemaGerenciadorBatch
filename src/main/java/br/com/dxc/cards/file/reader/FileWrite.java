package br.com.dxc.cards.file.reader;

import br.com.dxc.cards.MainApp;
import br.com.dxc.cards.enuns.FileEnum;
import br.com.dxc.cards.enuns.PathVersionJavaEnum;
import br.com.dxc.cards.exception.SGBException;
import br.com.dxc.cards.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.core.lookup.StrSubstitutor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileWrite {
    private static final Logger LOGGER = LogManager.getLogger(FileWrite.class);

    public static void writeFileMS(MS ms, Path path) throws IOException {
        File templateScript = new File("src/data/MS.sh");
        String scriptFile = "";

        try {

            Map<String, String> valuesMap = new HashMap<>();
            valuesMap.put("Severity", String.valueOf(ms.getSeverity()));
            valuesMap.put("Squad", ms.getSquad());
            valuesMap.put("Job", ms.getJob());
            valuesMap.put("Description", ms.getDescription());
            valuesMap.put("Client", ms.getClient());
            valuesMap.put("Date", ms.getDate());
            valuesMap.put("MS", ms.getName());
            valuesMap.put("ap", ms.getAp());
            for (int i = 0; i < ms.getMsg().size(); i++){
                valuesMap.put("TS" + i + "", ms.getMsg().get(i));
            }

            StrSubstitutor sub = new StrSubstitutor(valuesMap,"#@@", "@@#");

            Scanner sc = new Scanner(templateScript);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.contains("rm -f $CMSAP_ROOT/msg/O_#@@TS0@@#") && (ms.getMsg().size() > 1)) {
                    for (int i = 0; i < ms.getMsg().size(); i++) {
                        scriptFile += "    rm -f $CMSAP_ROOT/msg/O_#@@TS" + i + "@@#\n";
                    }
                } else {
                    scriptFile += line + "\n";
                }
            }
            sc.close();

            String newScript = sub.replace(scriptFile);
            basePathGenerate(newScript, ms.getName(), ms.getType(), path);

        } catch (FileNotFoundException | SGBException e) {
            e.printStackTrace();
        }
    }

    public static void writeFileAP(AP ap, Path path) {
        File templateScript = new File("src/data/AP.tasks");
        String scriptFile = "";
        int orderInit = 100;
        int orderEnd = 200;

        try {

            Map<String, String> valuesMap = new HashMap<>();
            valuesMap.put("Name", ap.getName());
            for (int i = 0; i < ap.getMsg().size(); i++){
                valuesMap.put("TS" + i + "", ap.getMsg().get(i));
            }

            StrSubstitutor sub = new StrSubstitutor(valuesMap,"#@@", "@@#");

            Scanner sc = new Scanner(templateScript);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.contains("S100 # S900 # ABORT # ksh -c \"${CMSAP_ROOT}/#@@TS0@@#\"") && (ap.getMsg().size() > 1)) {
                    for (int i = 0; i < ap.getMsg().size(); i++) {
                        scriptFile += "S" + orderInit + " # S" + orderEnd + " # ABORT # ksh -c \"${CMSAP_ROOT}/#@@TS" + i + "@@#\"\n";
                        scriptFile += "#\n";
                        orderInit += 100;
                        orderEnd += 100;
                    }
                } else if (line.contains("S900 # END # ABORT # exit 0")){
                    scriptFile += "S" + orderInit + " # END # ABORT # exit 0";
                } else {
                    scriptFile += line + "\n";
                }
            }
            sc.close();

            String newScript = sub.replace(scriptFile);
            basePathGenerate(newScript, ap.getName(), ap.getType(), path);

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SGBException e) {
            e.printStackTrace();
        }

    }

    public static void writeFileTS(TS ts, Path path) {
        LOGGER.info("Inicio - " + ts.getName());
        File templateScript = new File("src/data/TS.sh");
        String scriptFile = "";

        try {

            Map<String, String> valuesMap = new HashMap<>();
            valuesMap.put("Severity", String.valueOf(ts.getSeverity()));
            valuesMap.put("Squad", ts.getSquad());
            valuesMap.put("Job", ts.getJob());
            valuesMap.put("Description", ts.getDescription());
            valuesMap.put("Client", ts.getClient());
            valuesMap.put("Date", ts.getDate());
            valuesMap.put("Bin", ts.getBin());
            valuesMap.put("Modelo", ts.getModelo());
            valuesMap.put("Properties", ts.getProperties());
            valuesMap.put("PathVersionJava", PathVersionJavaEnum.getVersionJava(path.getPathVersionJava()));
            valuesMap.put("MaxMemoryJava", "Xmx" + (path.getMaxMemoryJava() * 1024) + "m");

            StrSubstitutor sub = new StrSubstitutor(valuesMap,"#@@", "@@#");

            Scanner sc = new Scanner(templateScript);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                scriptFile += line + "\n";
            }
            sc.close();

            String newScript = sub.replace(scriptFile);
            basePathGenerate(newScript, ts.getName(), ts.getType(), path);

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SGBException e) {
            e.printStackTrace();
        }

    }

    public static void writeFileXS(XS xs, Path path) {
        File templateScript = new File("src/data/XS.sh");
        String scriptFile = "";

        try {

            Map<String, String> valuesMap = new HashMap<>();
            valuesMap.put("Severity", String.valueOf(xs.getSeverity()));
            valuesMap.put("Squad", xs.getSquad());
            valuesMap.put("Job", xs.getJob());
            valuesMap.put("Description", xs.getDescription());
            valuesMap.put("Client", xs.getClient());
            valuesMap.put("Date", xs.getDate());
            valuesMap.put("PathVersionJava", PathVersionJavaEnum.getVersionJava(path.getPathVersionJava()));
            valuesMap.put("MaxMemoryJava", "Xmx" + (path.getMaxMemoryJava() * 1024) + "m");
            valuesMap.put("Bin", xs.getBin());
            valuesMap.put("Modelo", xs.getModelo());
            valuesMap.put("Properties", xs.getProperties());
            valuesMap.put("Simultaneous", xs.getSimultaneous());
            valuesMap.put("Delay", xs.getDelay());

            StrSubstitutor sub = new StrSubstitutor(valuesMap,"#@@", "@@#");

            Scanner sc = new Scanner(templateScript);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                scriptFile += line + "\n";
            }
            sc.close();

            String newScript = sub.replace(scriptFile);
            basePathGenerate(newScript, xs.getName(), xs.getType(), path);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SGBException e) {
            e.printStackTrace();
        }
    }

    public static void writeFileBASIC(BASIC basic, Path path) {
        File templateScript = new File("src/data/BASIC.sh");
        String scriptFile = "";

        try {

            Map<String, String> valuesMap = new HashMap<>();
            valuesMap.put("Severity", String.valueOf(basic.getSeverity()));
            valuesMap.put("Squad", basic.getSquad());
            valuesMap.put("Job", basic.getJob());
            valuesMap.put("Description", basic.getDescription());
            valuesMap.put("Client", basic.getClient());
            valuesMap.put("Date", basic.getDate());
            valuesMap.put("Author", basic.getAuthor());
            valuesMap.put("Coments", basic.getComents());
            valuesMap.put("Ticket", basic.getTicket());

            StrSubstitutor sub = new StrSubstitutor(valuesMap,"#@@", "@@#");

            Scanner sc = new Scanner(templateScript);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                scriptFile += line + "\n";
            }
            sc.close();

            String newScript = sub.replace(scriptFile);
            basePathGenerate(newScript, basic.getName(), basic.getType(), path);

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SGBException e) {
            e.printStackTrace();
        }

    }

    public static void writeFileJIL(JIL jil, Path path) {

        try {

            Map<String, String> valuesMap = new HashMap<>();

            valuesMap.put("insert_job: ", String.valueOf());
            valuesMap.put("job_type: ", String.valueOf());
            valuesMap.put("description: ", String.valueOf());
            valuesMap.put("machine: ", String.valueOf());
            valuesMap.put("owner: ", String.valueOf());
            valuesMap.put("application: ", String.valueOf());

            if (jil.getBoxName().isEmpty() || jil.getBoxName() != null){
            valuesMap.put("box_name: ", String.valueOf());

            }

            if (!jil.getCalendar().isEmpty() || jil.getCondition() != null){
                valuesMap.put("condition: ", String.valueOf(jil.getCondition()));

            }

            if (jil.isDateCondition()) {
                valuesMap.put("date_conditions: ", String.valueOf());
                valuesMap.put("timezone: ", String.valueOf());
                valuesMap.put("days_of_week: ", String.valueOf());
                valuesMap.put("start_mins: ", String.valueOf());
                valuesMap.put("calendar: ", String.valueOf());

            }

            if (jil.getTypeJob().equals("cmd") || jil.getTypeJob().equals("fw")){
                valuesMap.put("permission: ", String.valueOf());
                valuesMap.put("max_run_alarm: ", String.valueOf());
                valuesMap.put("alarm_if_fail: ", String.valueOf());
                valuesMap.put("alarm_if_terminated: ", String.valueOf());
                valuesMap.put("send_notification: ", String.valueOf());
                valuesMap.put("notification_msg: ", String.valueOf());
                valuesMap.put("notification_emailaddress: ", String.valueOf());
                valuesMap.put("svcdesk_desc: ", String.valueOf());
                valuesMap.put("svcdesk_attr: ", String.valueOf());
                valuesMap.put("svcdesk_sev: ", String.valueOf());

            }

            if (jil.getTypeJob().equals("cmd")) {
                valuesMap.put("std_out_file: ", String.valueOf());
                valuesMap.put("std_err_file: ", String.valueOf());
                valuesMap.put("command: ", String.valueOf());

            }

            if (jil.getTypeJob().equals("FileWatcher")) {
                valuesMap.put("watch_file: ", String.valueOf());
                valuesMap.put("watch_file_min_size: ", String.valueOf());
                valuesMap.put("watch_interval: ", String.valueOf());

            }

            String newScript;
            basePathGenerate(newScript, xs.getName(), xs.getType(), path);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SGBException e) {
            e.printStackTrace();
        }

    }

    private static void basePathGenerate(String newScript, String name, String type, Path path) throws IOException, SGBException {

        if (path.isBaseCmsdev()) {
            fileGenerate(newScript, name, path.getAmbiente().getCmsdevOutput(), FileEnum.getFileExtension(type));
        }

        if (path.isBaseHomol()) {
            if (type.equals("MS") || type.equals("TS") || type.equals("XS")) {
                fileGenerate(newScript, name, path.getAmbiente().getCmsapOutput(), FileEnum.getFileExtension(type));
            }else if (type.equals("AP")){
                fileGenerate(newScript, name, path.getAmbiente().getCmsapOutput(), FileEnum.AP.getExtension());
            }else {
                fileGenerate(newScript, name, path.getAmbiente().getCmsScriptOutput(), FileEnum.AP.getExtension());
            }
        }

    }

    private static void fileGenerate(String newScript, String name, String path, String extension) throws IOException {

        File fileGenerate = new File("C:\\GitHub\\SistemaGerenciadorBatch\\src\\data\\" + name.replaceAll("\\s", "") + extension); //TODO: RODAR NA MAQUINA
//        File fileGenerate = new File(path + name.replaceAll("\\s", "") + ".sh");
        if (!fileGenerate.exists()) {
            fileGenerate.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(fileGenerate.getAbsoluteFile());
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(newScript);
        bufferedWriter.close();
    }

}
