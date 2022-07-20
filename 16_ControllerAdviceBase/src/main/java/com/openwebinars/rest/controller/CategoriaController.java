package com.openwebinars.rest.controller;

import com.openwebinars.rest.error.CategoriaNotFoundException;
import com.openwebinars.rest.modelo.Categoria;
import com.openwebinars.rest.modelo.CategoriaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
@RequiredArgsConstructor
public class CategoriaController {
  private final CategoriaRepositorio categoriaRepositorio;

  @GetMapping
  public List<Categoria> ontenerTodos() {
    return categoriaRepositorio.findAll();
  }

  @GetMapping("/{id}")
  public Categoria obtenerUno(@PathVariable long id) {
    return categoriaRepositorio.findById(id).orElse(null);
  }

  @PostMapping
  public Categoria nuevaCategoria(@RequestBody Categoria categoria) {
    return categoriaRepositorio.save(categoria);
  }

  @PutMapping("/{id}")
  public Categoria editarCategoria(@RequestBody Categoria categoria, @PathVariable long id) {
    return categoriaRepositorio.findById(id).map(c -> {
      c.setId(id);
      return categoriaRepositorio.save(c);
    }).orElseThrow(() -> new CategoriaNotFoundException(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> borrarCategoria(@PathVariable long id) {
    Categoria categoria = categoriaRepositorio.findById(id)
        .orElseThrow(() -> new CategoriaNotFoundException(id));
    categoriaRepositorio.delete(categoria);
    return ResponseEntity.noContent().build();
  }
}
