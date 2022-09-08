package br.com.dxc.cards.model;

import br.com.dxc.cards.enuns.FileEnum;
import br.com.dxc.cards.exception.SGBException;

public enum BaseEnum {
    hom01("01"),
    hom02("02"),
    hom03("03"),
    hom04("04"),
    hom05("05"),
    hom06("06"),
    hom07("07"),
    hom08("08"),
    hom09("09"),
    hom010("10");

    private String base;

    BaseEnum(String base) {
        this.base = base;
    }

    public static FileEnum getByParameterName(String name) throws SGBException {
        for (FileEnum base : FileEnum.values()) {
            if (base.toString().equals(name)) {
                return base;
            }
        }
        throw new SGBException("Nenhum base encontrada com : '" + name + "'");
    }
}
