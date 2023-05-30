package org.utn.dominio.incidencia;

import org.utn.utils.StringValidatorUtils;
import org.utn.utils.exceptions.validador.FormatoCodigoCatalogoInvalidoException;

public class CodigoCatalogo {
    private final String codigo;

    public CodigoCatalogo(String codigo) throws FormatoCodigoCatalogoInvalidoException {
        validar(codigo);
        this.codigo = codigo;
    }

    private void validar(String codigo) throws FormatoCodigoCatalogoInvalidoException {
        if (!StringValidatorUtils.isCodigoCatalogo(codigo)) throw new FormatoCodigoCatalogoInvalidoException(codigo);
    }

    public String getCodigo() {
        return codigo;
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
