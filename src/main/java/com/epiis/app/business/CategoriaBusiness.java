package com.epiis.app.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epiis.app.dataaccess.CategoriaRepository;
import com.epiis.app.dto.DtoCategoria;
import com.epiis.app.entity.Categoria;

@Service
public class CategoriaBusiness {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public boolean insert(DtoCategoria dtoCategoria) {
        dtoCategoria.setIdCategoria(UUID.randomUUID().toString());
        dtoCategoria.setCreatedAt(new Date());
        dtoCategoria.setUpdatedAt(dtoCategoria.getCreatedAt());

        Categoria categoria = new Categoria();

        categoria.setIdCategoria(dtoCategoria.getIdCategoria());
        categoria.setNombre(dtoCategoria.getNombre());
        categoria.setDescripcion(dtoCategoria.getDescripcion());
        categoria.setActivo(true);
        categoria.setCreatedAt(new java.sql.Timestamp(dtoCategoria.getCreatedAt().getTime()));
        categoria.setUpdatedAt(new java.sql.Timestamp(dtoCategoria.getUpdatedAt().getTime()));

        this.categoriaRepository.save(categoria);

        return true;
    }

    public boolean update(DtoCategoria dtoCategoria) {
        Categoria categoria = this.categoriaRepository.findById(dtoCategoria.getIdCategoria()).orElse(null);

        if (categoria == null) {
            return false;
        }

        dtoCategoria.setUpdatedAt(new Date());

        categoria.setNombre(dtoCategoria.getNombre());
        categoria.setDescripcion(dtoCategoria.getDescripcion());
        categoria.setActivo(dtoCategoria.isActivo());
        categoria.setUpdatedAt(new java.sql.Timestamp(dtoCategoria.getUpdatedAt().getTime()));

        this.categoriaRepository.save(categoria);

        return true;
    }

    public boolean delete(String idCategoria) {
        Categoria categoria = this.categoriaRepository.findById(idCategoria).orElse(null);

        if (categoria == null) {
            return false;
        }

        categoria.setActivo(false);
        categoria.setUpdatedAt(new java.sql.Timestamp(new Date().getTime()));

        this.categoriaRepository.save(categoria);

        return true;
    }

    public List<DtoCategoria> getAll() {
        List<Categoria> listCategoria = this.categoriaRepository.getAll();

        List<DtoCategoria> listDtoCategoria = new ArrayList<>();

        for (Categoria item : listCategoria) {
            DtoCategoria dtoCategoriaTemp = this.convertToDto(item);
            dtoCategoriaTemp.setCantidadProductos(item.getListProducto() != null ? item.getListProducto().size() : 0);
            listDtoCategoria.add(dtoCategoriaTemp);
        }

        return listDtoCategoria;
    }

    public List<DtoCategoria> getAllActivas() {
        List<Categoria> listCategoria = this.categoriaRepository.getAllActivas();

        List<DtoCategoria> listDtoCategoria = new ArrayList<>();

        for (Categoria item : listCategoria) {
            listDtoCategoria.add(this.convertToDto(item));
        }

        return listDtoCategoria;
    }

    public DtoCategoria getById(String idCategoria) {
        Categoria categoria = this.categoriaRepository.findById(idCategoria).orElse(null);

        if (categoria == null) {
            return null;
        }

        return this.convertToDto(categoria);
    }

    private DtoCategoria convertToDto(Categoria categoria) {
        DtoCategoria dtoCategoria = new DtoCategoria();

        dtoCategoria.setIdCategoria(categoria.getIdCategoria());
        dtoCategoria.setNombre(categoria.getNombre());
        dtoCategoria.setDescripcion(categoria.getDescripcion());
        dtoCategoria.setActivo(categoria.isActivo());
        dtoCategoria.setCreatedAt(categoria.getCreatedAt());
        dtoCategoria.setUpdatedAt(categoria.getUpdatedAt());

        return dtoCategoria;
    }
}
