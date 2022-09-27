package br.com.dxc.cards.enuns;

import br.com.dxc.cards.exception.SGBException;

public enum BaseEnum {
    HOM01("01"),
    HOM02("02"),
    HOM03("03"),
    HOM04("04"),
    HOM05("05"),
    HOM06("06"),
    HOM07("07"),
    HOM08("08"),
    HOM09("09"),
    HOM010("10");

    private String baseValue;

    BaseEnum(String baseValue) {
        this.baseValue = baseValue;
    }

    public String getBaseValue() {
        return baseValue;
    }

    public static BaseEnum getByParameterName(String name) throws SGBException {
        for (BaseEnum base : BaseEnum.values()) {
            if (base.toString().equals(name)) {
                return base;
            }
        }
        throw new SGBException("Nenhum base encontrada com : '" + name + "'");
    }
}
