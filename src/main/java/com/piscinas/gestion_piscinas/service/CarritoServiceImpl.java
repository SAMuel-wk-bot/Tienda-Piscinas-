package com.piscinas.gestion_piscinas.service;

import com.piscinas.gestion_piscinas.domain.CarritoSesion;
import com.piscinas.gestion_piscinas.domain.ItemCarrito;
import com.piscinas.gestion_piscinas.domain.Producto;
import com.piscinas.gestion_piscinas.domain.ResumenCarrito;
import com.piscinas.gestion_piscinas.repository.ProductoRepository;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CarritoServiceImpl implements CarritoService {

    public static final String ATRIBUTO_CARRITO = "carrito";

    private final ProductoRepository productoRepository;

    public CarritoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public CarritoSesion obtenerCarrito(HttpSession session) {
        CarritoSesion carrito = (CarritoSesion) session.getAttribute(ATRIBUTO_CARRITO);
        if (carrito == null) {
            carrito = new CarritoSesion();
            session.setAttribute(ATRIBUTO_CARRITO, carrito);
        }
        return carrito;
    }

    @Override
    public ResumenCarrito obtenerResumen(HttpSession session) {
        CarritoSesion carrito = obtenerCarrito(session);
        List<ItemCarrito> items = new ArrayList<>();
        List<Long> productosEliminados = new ArrayList<>();

        for (Map.Entry<Long, Integer> entrada : carrito.getCantidades().entrySet()) {
            Producto producto = productoRepository.findById(entrada.getKey()).orElse(null);
            if (producto == null) {
                productosEliminados.add(entrada.getKey());
            } else {
                items.add(new ItemCarrito(producto, entrada.getValue()));
            }
        }
        productosEliminados.forEach(carrito.getCantidades()::remove);
        return new ResumenCarrito(items);
    }

    @Override
    public void agregarProducto(Long idProducto, int cantidad, HttpSession session) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("business.cart.quantityPositive");
        }
        Producto producto = obtenerProducto(idProducto);
        CarritoSesion carrito = obtenerCarrito(session);
        int cantidadActual = carrito.getCantidades().getOrDefault(idProducto, 0);
        int nuevaCantidad = cantidadActual + cantidad;
        validarStock(producto, nuevaCantidad);
        carrito.getCantidades().put(idProducto, nuevaCantidad);
    }

    @Override
    public void aumentarProducto(Long idProducto, HttpSession session) {
        agregarProducto(idProducto, 1, session);
    }

    @Override
    public void reducirProducto(Long idProducto, HttpSession session) {
        CarritoSesion carrito = obtenerCarrito(session);
        Integer cantidad = carrito.getCantidades().get(idProducto);
        if (cantidad == null) {
            throw new IllegalArgumentException("business.cart.notInCart");
        }
        if (cantidad <= 1) {
            carrito.getCantidades().remove(idProducto);
        } else {
            carrito.getCantidades().put(idProducto, cantidad - 1);
        }
    }

    @Override
    public void eliminarProducto(Long idProducto, HttpSession session) {
        obtenerCarrito(session).getCantidades().remove(idProducto);
    }

    @Override
    public void vaciar(HttpSession session) {
        session.removeAttribute(ATRIBUTO_CARRITO);
    }

    private Producto obtenerProducto(Long idProducto) {
        return productoRepository.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("business.product.notFound"));
    }

    private void validarStock(Producto producto, int cantidad) {
        if (producto.getStock() <= 0) {
            throw new IllegalStateException("business.cart.outOfStock");
        }
        if (cantidad > producto.getStock()) {
            throw new IllegalStateException("business.cart.stockExceeded");
        }
    }
}
