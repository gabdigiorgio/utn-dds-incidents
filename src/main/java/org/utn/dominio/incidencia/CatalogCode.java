package org.utn.dominio.incidencia;

import com.fasterxml.jackson.annotation.JsonValue;
import org.utn.utils.StringValidatorUtils;
import org.utn.utils.exceptions.validador.InvalidCatalogCodeFormatException;

public class CatalogCode {
    private final String code;

    public CatalogCode(String code) throws InvalidCatalogCodeFormatException {
        validate(code);
        this.code = code;
    }

    private void validate(String code) throws InvalidCatalogCodeFormatException {
        if (!StringValidatorUtils.isCatalogCode(code)) throw new InvalidCatalogCodeFormatException(code);
    }
    @JsonValue
    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CatalogCode otherCode = (CatalogCode) obj;
        if (code == null) {
            return otherCode.code == null;
        }
        return code.equals(otherCode.code);
    }
}
