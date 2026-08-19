package com.piscinas.gestion_piscinas.service;

import com.piscinas.gestion_piscinas.domain.CotizacionPiscinaForm;
import com.piscinas.gestion_piscinas.domain.CotizacionPiscinaResultado;

public interface CotizacionPiscinaService {

    CotizacionPiscinaResultado calcular(CotizacionPiscinaForm formulario);
}
