package com.piscinas.gestion_piscinas.repository;

import com.piscinas.gestion_piscinas.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    boolean existsByCategoriaIdCategoria(Long idCategoria);
}
