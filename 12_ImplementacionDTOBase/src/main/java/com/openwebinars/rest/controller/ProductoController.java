package com.openwebinars.rest.controller;

import com.openwebinars.rest.dto.CreateProductoDTO;
import com.openwebinars.rest.dto.ProductoDTO;
import com.openwebinars.rest.dto.converter.CreateProductoDTOConverter;
import com.openwebinars.rest.dto.converter.ProductoDTOConverter;
import com.openwebinars.rest.modelo.Producto;
import com.openwebinars.rest.modelo.ProductoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductoController {

  private final ProductoRepositorio productoRepositorio;
  private final ProductoDTOConverter productoDTOConverter;
  private final CreateProductoDTOConverter createProductoDTOConverter;

  /**
   * Obtenemos todos los productos
   *
   * @return 404 si no hay productos, 200 y lista de productos si hay uno o más
   */
  @GetMapping("/producto")
  public ResponseEntity<?> obtenerTodos() {
    List<Producto> result = productoRepositorio.findAll();

    if (result.isEmpty()) {
      return ResponseEntity.notFound().build();
    } else {
      List<ProductoDTO> dtoList = result.stream().map(productoDTOConverter::convertToDTO).collect(Collectors.toList());
      return ResponseEntity.ok(dtoList);
    }
  }

  /**
   * Obtenemos un producto en base a su ID
   *
   * @param id
   * @return 404 si no encuentra el producto, 200 y el producto si lo encuentra
   */
  @GetMapping("/producto/{id}")
  public ResponseEntity<?> obtenerUno(@PathVariable Long id) {
    Producto result = productoRepositorio.findById(id).orElse(null);
    if (result == null) return ResponseEntity.notFound().build();
    else return ResponseEntity.ok(result);
  }

  /**
   * Insertamos un nuevo producto
   *
   * @param nuevo
   * @return 201 y el producto insertado
   */
  @PostMapping("/producto")
  public ResponseEntity<?> nuevoProducto(@RequestBody CreateProductoDTO nuevo) {
    Producto producto = createProductoDTOConverter.convertToModel(nuevo);
    return ResponseEntity.status(HttpStatus.CREATED).body(productoRepositorio.save(producto));
  }

  /**
   * @param editar
   * @param id
   * @return 200 Ok si la edición tiene éxito, 404 si no se encuentra el producto
   */
  @PutMapping("/producto/{id}")
  public ResponseEntity<?> editarProducto(@RequestBody CreateProductoDTO editar, @PathVariable Long id) {

    return productoRepositorio.findById(id).map(p -> {
      return ResponseEntity.ok(productoRepositorio.save(createProductoDTOConverter.convertToModel(editar)));
    }).orElseGet(() -> {
      return ResponseEntity.notFound().build();
    });
  }

  /**
   * Borra un producto del catálogo en base a su id
   *
   * @param id
   * @return Código 204 sin contenido
   */
  @DeleteMapping("/producto/{id}")
  public ResponseEntity<?> borrarProducto(@PathVariable Long id) {
    productoRepositorio.deleteById(id);
    return ResponseEntity.noContent().build();
  }

}
