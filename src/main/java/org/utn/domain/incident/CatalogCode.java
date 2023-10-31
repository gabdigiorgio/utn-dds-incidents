package org.utn.domain.incident;

import com.fasterxml.jackson.annotation.JsonValue;
import org.utn.utils.StringValidatorUtils;
import org.utn.utils.exceptions.validator.InvalidCatalogCodeException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class CatalogCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pk;
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

    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
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
