package com.ulquertech;

import java.io.Serializable;

public class BusquedaFiltroData implements Serializable {
    private String criterio;

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }
}
