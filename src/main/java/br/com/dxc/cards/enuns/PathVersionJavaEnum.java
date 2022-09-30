package br.com.dxc.cards.enuns;

import br.com.dxc.cards.exception.SGBException;

public enum PathVersionJavaEnum {

    VERSION7(7, "/usr/bin/java -jar"),
    VERSION8(8, "/app/jvm/jdk8/bin/java - jar");

    private int javaCode;
    private String javaValue;

    PathVersionJavaEnum(int javaCode, String javaValue) {
        this.javaCode = javaCode;
        this.javaValue = javaValue;
    }

    public int getJavaCode() {
        return javaCode;
    }

    public String getJavaValue() {
        return javaValue;
    }

    public static String getVersionJava(int java) throws SGBException {
        for (PathVersionJavaEnum version : PathVersionJavaEnum.values()) {
            if (version.getJavaCode() == java) {
                return version.getJavaValue();
            }
        }
        throw new SGBException("Nenhum versão encontrada : '" + java + "'");
    }
}
