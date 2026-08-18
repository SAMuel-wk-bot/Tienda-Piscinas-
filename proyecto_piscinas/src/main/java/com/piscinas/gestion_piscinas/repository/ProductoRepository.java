package com.piscinas.gestion_piscinas.repository;

import com.piscinas.gestion_piscinas.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    boolean existsByCategoriaIdCategoria(Long idCategoria);

    List<Producto> findByNombreProductoContainingIgnoreCaseOrderByNombreProductoAsc(
            String nombreProducto);

    List<Producto> findByCategoriaIdCategoriaOrderByNombreProductoAsc(Long idCategoria);

    @Query("SELECT p FROM Producto p "
            + "WHERE (:nombre IS NULL OR LOWER(p.nombreProducto) LIKE "
            + "LOWER(CONCAT('%', :nombre, '%'))) "
            + "AND (:idCategoria IS NULL OR p.categoria.idCategoria = :idCategoria) "
            + "AND (:soloDisponibles = false OR p.stock > 0) "
            + "ORDER BY p.nombreProducto ASC")
    List<Producto> buscarConFiltros(@Param("nombre") String nombre,
            @Param("idCategoria") Long idCategoria,
            @Param("soloDisponibles") boolean soloDisponibles);
}
