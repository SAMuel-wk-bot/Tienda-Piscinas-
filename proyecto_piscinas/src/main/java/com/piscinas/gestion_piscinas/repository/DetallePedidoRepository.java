package com.piscinas.gestion_piscinas.repository;

import com.piscinas.gestion_piscinas.domain.DetallePedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

    List<DetallePedido> findByPedidoIdPedido(Long idPedido);

    boolean existsByProductoIdProducto(Long idProducto);
}
