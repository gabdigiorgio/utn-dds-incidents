package org.utn.domain.incident;

import com.fasterxml.jackson.annotation.JsonValue;
import org.utn.utils.StringValidatorUtils;
import org.utn.utils.exceptions.validator.InvalidCatalogCodeException;

public class CatalogCode {
    private String code;

    public CatalogCode(String code) throws InvalidCatalogCodeException {
        validate(code);
        this.code = code;
    }

    protected CatalogCode() {
        super();
    }

    private void validate(String code) throws InvalidCatalogCodeException {
        if (!StringValidatorUtils.isCatalogCode(code)) throw new InvalidCatalogCodeException(code);
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
