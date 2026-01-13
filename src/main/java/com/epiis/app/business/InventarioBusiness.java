package com.epiis.app.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epiis.app.dataaccess.MovimientoInventarioRepository;
import com.epiis.app.dataaccess.ProductoRepository;
import com.epiis.app.dto.DtoMovimientoInventario;
import com.epiis.app.entity.MovimientoInventario;
import com.epiis.app.entity.MovimientoInventario.TipoMovimiento;
import com.epiis.app.entity.Producto;

@Service
public class InventarioBusiness {
    @Autowired
    private MovimientoInventarioRepository movimientoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    public boolean registrarEntrada(DtoMovimientoInventario dtoMovimiento) {
        Producto producto = this.productoRepository.findById(dtoMovimiento.getIdProducto()).orElse(null);

        if (producto == null) {
            return false;
        }

        if (dtoMovimiento.getCantidad() <= 0) {
            return false;
        }

        int stockAnterior = producto.getStockActual();
        int stockResultante = stockAnterior + dtoMovimiento.getCantidad();

        producto.setStockActual(stockResultante);
        producto.setUpdatedAt(new java.sql.Timestamp(new Date().getTime()));
        this.productoRepository.save(producto);

        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setIdMovimiento(UUID.randomUUID().toString());
        movimiento.setProducto(producto);
        movimiento.setTipoMovimiento(TipoMovimiento.ENTRADA);
        movimiento.setCantidad(dtoMovimiento.getCantidad());
        movimiento.setStockAnterior(stockAnterior);
        movimiento.setStockResultante(stockResultante);
        movimiento.setMotivo(dtoMovimiento.getMotivo());
        movimiento.setObservacion(dtoMovimiento.getObservacion());
        movimiento.setDocumentoReferencia(dtoMovimiento.getDocumentoReferencia());
        movimiento.setCreatedAt(new java.sql.Timestamp(new Date().getTime()));
        movimiento.setUpdatedAt(movimiento.getCreatedAt());

        this.movimientoRepository.save(movimiento);

        return true;
    }

    @Transactional
    public boolean registrarSalida(DtoMovimientoInventario dtoMovimiento) {
        Producto producto = this.productoRepository.findById(dtoMovimiento.getIdProducto()).orElse(null);

        if (producto == null) {
            return false;
        }

        if (dtoMovimiento.getCantidad() <= 0) {
            return false;
        }

        int stockAnterior = producto.getStockActual();

        if (stockAnterior < dtoMovimiento.getCantidad()) {
            return false;
        }

        int stockResultante = stockAnterior - dtoMovimiento.getCantidad();

        producto.setStockActual(stockResultante);
        producto.setUpdatedAt(new java.sql.Timestamp(new Date().getTime()));
        this.productoRepository.save(producto);

        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setIdMovimiento(UUID.randomUUID().toString());
        movimiento.setProducto(producto);
        movimiento.setTipoMovimiento(TipoMovimiento.SALIDA);
        movimiento.setCantidad(dtoMovimiento.getCantidad());
        movimiento.setStockAnterior(stockAnterior);
        movimiento.setStockResultante(stockResultante);
        movimiento.setMotivo(dtoMovimiento.getMotivo());
        movimiento.setObservacion(dtoMovimiento.getObservacion());
        movimiento.setDocumentoReferencia(dtoMovimiento.getDocumentoReferencia());
        movimiento.setCreatedAt(new java.sql.Timestamp(new Date().getTime()));
        movimiento.setUpdatedAt(movimiento.getCreatedAt());

        this.movimientoRepository.save(movimiento);

        return true;
    }

    public List<DtoMovimientoInventario> getAllMovimientos() {
        List<MovimientoInventario> listMovimiento = this.movimientoRepository.getAll();

        List<DtoMovimientoInventario> listDtoMovimiento = new ArrayList<>();

        for (MovimientoInventario item : listMovimiento) {
            listDtoMovimiento.add(this.convertToDto(item));
        }

        return listDtoMovimiento;
    }

    public List<DtoMovimientoInventario> getMovimientosByProducto(String idProducto) {
        List<MovimientoInventario> listMovimiento = this.movimientoRepository.getByProducto(idProducto);

        List<DtoMovimientoInventario> listDtoMovimiento = new ArrayList<>();

        for (MovimientoInventario item : listMovimiento) {
            listDtoMovimiento.add(this.convertToDto(item));
        }

        return listDtoMovimiento;
    }

    public List<DtoMovimientoInventario> getEntradas() {
        List<MovimientoInventario> listMovimiento = this.movimientoRepository.getByTipo(TipoMovimiento.ENTRADA);

        List<DtoMovimientoInventario> listDtoMovimiento = new ArrayList<>();

        for (MovimientoInventario item : listMovimiento) {
            listDtoMovimiento.add(this.convertToDto(item));
        }

        return listDtoMovimiento;
    }

    public List<DtoMovimientoInventario> getSalidas() {
        List<MovimientoInventario> listMovimiento = this.movimientoRepository.getByTipo(TipoMovimiento.SALIDA);

        List<DtoMovimientoInventario> listDtoMovimiento = new ArrayList<>();

        for (MovimientoInventario item : listMovimiento) {
            listDtoMovimiento.add(this.convertToDto(item));
        }

        return listDtoMovimiento;
    }

    private DtoMovimientoInventario convertToDto(MovimientoInventario movimiento) {
        DtoMovimientoInventario dtoMovimiento = new DtoMovimientoInventario();

        dtoMovimiento.setIdMovimiento(movimiento.getIdMovimiento());

        if (movimiento.getProducto() != null) {
            dtoMovimiento.setIdProducto(movimiento.getProducto().getIdProducto());
            dtoMovimiento.setCodigoProducto(movimiento.getProducto().getCodigo());
            dtoMovimiento.setNombreProducto(movimiento.getProducto().getNombre());
        }

        dtoMovimiento.setTipoMovimiento(movimiento.getTipoMovimiento().name());
        dtoMovimiento.setCantidad(movimiento.getCantidad());
        dtoMovimiento.setStockAnterior(movimiento.getStockAnterior());
        dtoMovimiento.setStockResultante(movimiento.getStockResultante());
        dtoMovimiento.setMotivo(movimiento.getMotivo());
        dtoMovimiento.setObservacion(movimiento.getObservacion());
        dtoMovimiento.setDocumentoReferencia(movimiento.getDocumentoReferencia());
        dtoMovimiento.setCreatedAt(movimiento.getCreatedAt());
        dtoMovimiento.setUpdatedAt(movimiento.getUpdatedAt());

        return dtoMovimiento;
    }
}
