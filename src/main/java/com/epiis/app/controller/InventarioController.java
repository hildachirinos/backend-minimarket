package com.epiis.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epiis.app.business.InventarioBusiness;
import com.epiis.app.controller.reqresp.RequestMovimientoInsert;
import com.epiis.app.controller.reqresp.ResponseMovimientoInsert;
import com.epiis.app.dto.DtoMovimientoInventario;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping(path = "inventario")
public class InventarioController {
    @Autowired
    private InventarioBusiness inventarioBusiness;

    @PostMapping(path = "entrada", consumes = "multipart/form-data")
    public ResponseEntity<ResponseMovimientoInsert> registrarEntrada(@ModelAttribute RequestMovimientoInsert request) {
        ResponseMovimientoInsert response = new ResponseMovimientoInsert();

        boolean result = this.inventarioBusiness.registrarEntrada(request.getDto().getMovimiento());

        if (result) {
            response.success();
            response.listMessage.add("Entrada de inventario registrada correctamente");
        } else {
            response.error();
            response.listMessage.add("Error al registrar la entrada. Verifique los datos.");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "salida", consumes = "multipart/form-data")
    public ResponseEntity<ResponseMovimientoInsert> registrarSalida(@ModelAttribute RequestMovimientoInsert request) {
        ResponseMovimientoInsert response = new ResponseMovimientoInsert();

        boolean result = this.inventarioBusiness.registrarSalida(request.getDto().getMovimiento());

        if (result) {
            response.success();
            response.listMessage.add("Salida de inventario registrada correctamente");
        } else {
            response.error();
            response.listMessage.add("Error al registrar la salida. Verifique que haya stock suficiente.");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "movimientos/getall")
    public ResponseEntity<List<DtoMovimientoInventario>> getAllMovimientos() {
        return new ResponseEntity<>(this.inventarioBusiness.getAllMovimientos(), HttpStatus.OK);
    }

    @GetMapping(path = "movimientos/producto/{id}")
    public ResponseEntity<List<DtoMovimientoInventario>> getMovimientosByProducto(@PathVariable("id") String id) {
        return new ResponseEntity<>(this.inventarioBusiness.getMovimientosByProducto(id), HttpStatus.OK);
    }

    @GetMapping(path = "movimientos/entradas")
    public ResponseEntity<List<DtoMovimientoInventario>> getEntradas() {
        return new ResponseEntity<>(this.inventarioBusiness.getEntradas(), HttpStatus.OK);
    }

    @GetMapping(path = "movimientos/salidas")
    public ResponseEntity<List<DtoMovimientoInventario>> getSalidas() {
        return new ResponseEntity<>(this.inventarioBusiness.getSalidas(), HttpStatus.OK);
    }
}
