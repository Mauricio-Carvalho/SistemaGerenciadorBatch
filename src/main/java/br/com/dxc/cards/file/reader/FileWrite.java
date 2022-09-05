package br.com.dxc.cards.file.reader;

import br.com.dxc.cards.model.*;
import br.com.dxc.cards.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileWrite {

    private static final Logger LOGGER = LogManager.getLogger(FileWrite.class);

    public static void writeFileMS(MS ms) throws IOException {
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

            File fileGenerate = new File(ms.getName().replaceAll("\\s", "") + ".sh");
            if (!fileGenerate.exists()) {
                fileGenerate.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(fileGenerate.getAbsoluteFile());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(newScript);
            bufferedWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static void writeFileAP(AP ap) {
    }

    public static void writeFileTS(TS ts) {
    }

    public static void writeFileXS(XS xs) {
    }

    public static void writeFileBASIC(BASIC basic) {
    }

    public static void writeFileJIL(JIL jil) {
    }

}
