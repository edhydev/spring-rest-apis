package com.openwebinars.rest.dto.converter;

import com.openwebinars.rest.dto.CreateProductoDTO;
import com.openwebinars.rest.modelo.Categoria;
import com.openwebinars.rest.modelo.CategoriaRepositorio;
import com.openwebinars.rest.modelo.Producto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateProductoDTOConverter {
    private final CategoriaRepositorio categoriaRepositorio;

    private final ModelMapper modelMapper;

    public CreateProductoDTO convertToDTO(Producto producto) {
        return modelMapper.map(producto, CreateProductoDTO.class);
    }

    public Producto convertToModel(CreateProductoDTO createProductoDTO) {
        Producto producto = new Producto();
        producto.setNombre(createProductoDTO.getNombre());
        producto.setPrecio(createProductoDTO.getPrecio());
        Categoria categoria = categoriaRepositorio.findById(createProductoDTO.getCategoriaId()).orElse(null);
        producto.setCategoria(categoria);
        return producto;
    }
}
