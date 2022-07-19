package com.openwebinars.rest.controller;

import com.openwebinars.rest.modelo.Producto;
import com.openwebinars.rest.modelo.ProductoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoRepositorio productoRepositorio;

    /**
     * Obtenemos todos los productos
     *
     * @return
     */
    @GetMapping("/producto")
    public ResponseEntity<?> obtenerTodos() {
        List<Producto> result = productoRepositorio.findAll();

        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(result);
        }
    }

    /**
     * Obtenemos un producto en base a su ID
     *
     * @param id
     * @return Null si no encuentra el producto
     */
    @GetMapping("/producto/{id}")
    public ResponseEntity<?> obtenerUno(@PathVariable Long id) {
        Producto result = productoRepositorio.findById(id).orElse(null);

        if (result == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(result);
        }
    }

    /**
     * Insertamos un nuevo producto
     *
     * @param nuevo
     * @return producto insertado
     */
    @PostMapping("/producto")
    public ResponseEntity<Producto> nuevoProducto(@RequestBody Producto nuevo) {
        Producto saved = productoRepositorio.save(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * @param editar
     * @param id
     * @return
     */
    @PutMapping("/producto/{id}")
    public ResponseEntity<?> editarProducto(@RequestBody Producto editar, @PathVariable Long id) {
        return productoRepositorio.findById(id).map(p -> {
            p.setNombre(editar.getNombre());
            p.setPrecio(editar.getPrecio());
            return ResponseEntity.ok(productoRepositorio.save(p));
        }).orElseGet(() -> {
            return ResponseEntity.notFound().build();
        });
    }

    /**
     * Borra un producto del catálogo en base a su id
     *
     * @param id
     * @return
     */
    @DeleteMapping("/producto/{id}")
    public ResponseEntity<?> borrarProducto(@PathVariable Long id) {
        productoRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
