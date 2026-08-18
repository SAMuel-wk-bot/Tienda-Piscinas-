package com.piscinas.gestion_piscinas.repository;

import com.piscinas.gestion_piscinas.domain.EstadoPedido;
import com.piscinas.gestion_piscinas.domain.Pedido;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteUsuarioEmailUsuarioOrderByFechaPedidoDesc(String emailUsuario);

    List<Pedido> findByEstadoOrderByFechaPedidoDesc(EstadoPedido estado);

    Optional<Pedido> findByIdPedidoAndClienteUsuarioEmailUsuario(
            Long idPedido, String emailUsuario);
}
