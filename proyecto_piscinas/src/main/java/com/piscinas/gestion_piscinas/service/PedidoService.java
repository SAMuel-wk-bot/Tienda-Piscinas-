package com.piscinas.gestion_piscinas.service;

import com.piscinas.gestion_piscinas.domain.CarritoSesion;
import com.piscinas.gestion_piscinas.domain.DetallePedido;
import com.piscinas.gestion_piscinas.domain.Pedido;
import java.util.List;

public interface PedidoService {

    Pedido finalizarCompra(CarritoSesion carrito, String correoUsuario);

    Pedido obtenerPedidoDelCliente(Long idPedido, String correoUsuario);

    List<DetallePedido> obtenerDetalles(Long idPedido);
}
