package br.com.dxc.cards.enuns;

import br.com.dxc.cards.exception.SGBException;

public enum FileEnum {
    MS("MS"),
    AP("AP"),
    TS("TS"),
    XS("XS"),
    BASIC("BASIC"),
    JIL("JIL");

    private String file;

    FileEnum(String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }

    public static FileEnum getByParameterName(String name) throws SGBException {
        for (FileEnum file : FileEnum.values()) {
            if (file.toString().equals(name)) {
                return file;
            }
        }
        throw new SGBException("Nenhum arquivo encontrada com a sigla: '" + name + "'");
    }
}
