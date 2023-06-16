package org.utn.dominio.incidencia;

import com.fasterxml.jackson.annotation.JsonValue;
import org.utn.utils.StringValidatorUtils;
import org.utn.utils.exceptions.validador.FormatoCodigoCatalogoInvalidoException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class CodigoCatalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pk;
    private String codigo;

    public CodigoCatalogo(String codigo) throws FormatoCodigoCatalogoInvalidoException {
        validar(codigo);
        this.codigo = codigo;
    }

    protected CodigoCatalogo() {
        super();
    }

    private void validar(String codigo) throws FormatoCodigoCatalogoInvalidoException {
        if (!StringValidatorUtils.isCodigoCatalogo(codigo)) throw new FormatoCodigoCatalogoInvalidoException(codigo);
    }
    @JsonValue
    public String getCodigo() {
        return codigo;
    }

    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CodigoCatalogo otroCodigo = (CodigoCatalogo) obj;
        if (codigo == null) {
            return otroCodigo.codigo == null;
        }
        return codigo.equals(otroCodigo.codigo);
    }
}
