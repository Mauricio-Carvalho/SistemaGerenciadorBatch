package br.com.dxc.cards.enuns;

import br.com.dxc.cards.exception.SGBException;

public enum FileEnum {
    MS("MS", ".sh"),
    AP("AP", ".tasks"),
    TS("TS", ".sh"),
    XS("XS", ".sh"),
    BASIC("BASIC", ".sh"),
    JIL("JIL", ".txt");

    private String file;
    private String extension;

    FileEnum(String file, String extension) {
        this.file = file;
        this.extension = extension;
    }

    public String getFile() {
        return file;
    }

    public String getExtension() {
        return extension;
    }

    public static String getFileExtension(String type) throws SGBException {
        for (FileEnum file : FileEnum.values()) {
            if (file.toString().equals(type)) {
                return file.getExtension();
            }
        }
        throw new SGBException("Nenhum arquivo encontrada com a sigla: '" + type + "'");
    }

    public static FileEnum getTypeFile(String name) throws SGBException {
        for (FileEnum file : FileEnum.values()) {
            if (file.toString().equals(name)) {
                return file;
            }
        }
        throw new SGBException("Nenhum arquivo encontrada com a sigla: '" + name + "'");
    }
}
