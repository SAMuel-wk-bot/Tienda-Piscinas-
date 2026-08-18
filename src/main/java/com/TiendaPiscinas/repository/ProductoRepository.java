package com.TiendaPiscinas.repository;

import com.TiendaPiscinas.model.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    @EntityGraph(attributePaths = "categoria")
    List<Producto> findTop8ByActivoTrueOrderByDestacadoDescNombreAsc();
}
