package com.epiis.app.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epiis.app.dataaccess.CategoriaRepository;
import com.epiis.app.dataaccess.ProductoRepository;
import com.epiis.app.dataaccess.ProveedorRepository;
import com.epiis.app.dto.DtoProducto;
import com.epiis.app.entity.Categoria;
import com.epiis.app.entity.Producto;
import com.epiis.app.entity.Proveedor;

@Service
public class ProductoBusiness {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    public boolean insert(DtoProducto dtoProducto) {
        Producto existente = this.productoRepository.findByCodigo(dtoProducto.getCodigo());
        if (existente != null) {
            return false;
        }

        Categoria categoria = this.categoriaRepository.findById(dtoProducto.getIdCategoria()).orElse(null);
        if (categoria == null) {
            return false;
        }

        dtoProducto.setIdProducto(UUID.randomUUID().toString());
        dtoProducto.setCreatedAt(new Date());
        dtoProducto.setUpdatedAt(dtoProducto.getCreatedAt());

        Producto producto = new Producto();

        producto.setIdProducto(dtoProducto.getIdProducto());
        producto.setCodigo(dtoProducto.getCodigo());
        producto.setNombre(dtoProducto.getNombre());
        producto.setDescripcion(dtoProducto.getDescripcion());
        producto.setCategoria(categoria);

        if (dtoProducto.getIdProveedor() != null && !dtoProducto.getIdProveedor().isEmpty()) {
            Proveedor proveedor = this.proveedorRepository.findById(dtoProducto.getIdProveedor()).orElse(null);
            producto.setProveedor(proveedor);
        }

        producto.setPrecioCompra(dtoProducto.getPrecioCompra());
        producto.setPrecioVenta(dtoProducto.getPrecioVenta());
        producto.setStockActual(dtoProducto.getStockActual());
        producto.setStockMinimo(dtoProducto.getStockMinimo());
        producto.setUnidadMedida(dtoProducto.getUnidadMedida());
        producto.setActivo(true);
        producto.setCreatedAt(new java.sql.Timestamp(dtoProducto.getCreatedAt().getTime()));
        producto.setUpdatedAt(new java.sql.Timestamp(dtoProducto.getUpdatedAt().getTime()));

        this.productoRepository.save(producto);

        return true;
    }

    public boolean update(DtoProducto dtoProducto) {
        Producto producto = this.productoRepository.findById(dtoProducto.getIdProducto()).orElse(null);

        if (producto == null) {
            return false;
        }

        if (!producto.getCodigo().equals(dtoProducto.getCodigo())) {
            Producto existente = this.productoRepository.findByCodigo(dtoProducto.getCodigo());
            if (existente != null) {
                return false;
            }
        }

        Categoria categoria = this.categoriaRepository.findById(dtoProducto.getIdCategoria()).orElse(null);
        if (categoria == null) {
            return false;
        }

        dtoProducto.setUpdatedAt(new Date());

        producto.setCodigo(dtoProducto.getCodigo());
        producto.setNombre(dtoProducto.getNombre());
        producto.setDescripcion(dtoProducto.getDescripcion());
        producto.setCategoria(categoria);

        if (dtoProducto.getIdProveedor() != null && !dtoProducto.getIdProveedor().isEmpty()) {
            Proveedor proveedor = this.proveedorRepository.findById(dtoProducto.getIdProveedor()).orElse(null);
            producto.setProveedor(proveedor);
        } else {
            producto.setProveedor(null);
        }

        producto.setPrecioCompra(dtoProducto.getPrecioCompra());
        producto.setPrecioVenta(dtoProducto.getPrecioVenta());
        producto.setStockMinimo(dtoProducto.getStockMinimo());
        producto.setUnidadMedida(dtoProducto.getUnidadMedida());
        producto.setActivo(dtoProducto.isActivo());
        producto.setUpdatedAt(new java.sql.Timestamp(dtoProducto.getUpdatedAt().getTime()));

        this.productoRepository.save(producto);

        return true;
    }

    public boolean delete(String idProducto) {
        Producto producto = this.productoRepository.findById(idProducto).orElse(null);

        if (producto == null) {
            return false;
        }

        producto.setActivo(false);
        producto.setUpdatedAt(new java.sql.Timestamp(new Date().getTime()));

        this.productoRepository.save(producto);

        return true;
    }

    public List<DtoProducto> getAll() {
        List<Producto> listProducto = this.productoRepository.getAll();

        List<DtoProducto> listDtoProducto = new ArrayList<>();

        for (Producto item : listProducto) {
            listDtoProducto.add(this.convertToDto(item));
        }

        return listDtoProducto;
    }

    public List<DtoProducto> getAllActivos() {
        List<Producto> listProducto = this.productoRepository.getAllActivos();

        List<DtoProducto> listDtoProducto = new ArrayList<>();

        for (Producto item : listProducto) {
            listDtoProducto.add(this.convertToDto(item));
        }

        return listDtoProducto;
    }

    public List<DtoProducto> getStockBajo() {
        List<Producto> listProducto = this.productoRepository.getProductosConStockBajo();

        List<DtoProducto> listDtoProducto = new ArrayList<>();

        for (Producto item : listProducto) {
            DtoProducto dto = this.convertToDto(item);
            dto.setStockBajo(true);
            listDtoProducto.add(dto);
        }

        return listDtoProducto;
    }

    public DtoProducto getById(String idProducto) {
        Producto producto = this.productoRepository.getByIdWithRelations(idProducto);

        if (producto == null) {
            return null;
        }

        return this.convertToDto(producto);
    }

    private DtoProducto convertToDto(Producto producto) {
        DtoProducto dtoProducto = new DtoProducto();

        dtoProducto.setIdProducto(producto.getIdProducto());
        dtoProducto.setCodigo(producto.getCodigo());
        dtoProducto.setNombre(producto.getNombre());
        dtoProducto.setDescripcion(producto.getDescripcion());

        if (producto.getCategoria() != null) {
            dtoProducto.setIdCategoria(producto.getCategoria().getIdCategoria());
            dtoProducto.setNombreCategoria(producto.getCategoria().getNombre());
        }

        if (producto.getProveedor() != null) {
            dtoProducto.setIdProveedor(producto.getProveedor().getIdProveedor());
            dtoProducto.setNombreProveedor(producto.getProveedor().getRazonSocial());
        }

        dtoProducto.setPrecioCompra(producto.getPrecioCompra());
        dtoProducto.setPrecioVenta(producto.getPrecioVenta());
        dtoProducto.setStockActual(producto.getStockActual());
        dtoProducto.setStockMinimo(producto.getStockMinimo());
        dtoProducto.setUnidadMedida(producto.getUnidadMedida());
        dtoProducto.setActivo(producto.isActivo());
        dtoProducto.setStockBajo(producto.getStockActual() <= producto.getStockMinimo());
        dtoProducto.setCreatedAt(producto.getCreatedAt());
        dtoProducto.setUpdatedAt(producto.getUpdatedAt());

        return dtoProducto;
    }
}
