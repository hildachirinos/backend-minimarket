package com.epiis.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epiis.app.business.ProductoBusiness;
import com.epiis.app.controller.reqresp.RequestProductoInsert;
import com.epiis.app.controller.reqresp.ResponseProductoInsert;
import com.epiis.app.dto.DtoProducto;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(path = "producto")
public class ProductoController {
    @Autowired
    private ProductoBusiness productoBusiness;

    @PostMapping(path = "insert", consumes = "multipart/form-data")
    public ResponseEntity<ResponseProductoInsert> insert(@ModelAttribute RequestProductoInsert request) {
        ResponseProductoInsert response = new ResponseProductoInsert();

        boolean result = this.productoBusiness.insert(request.getDto().getProducto());

        if (result) {
            response.success();
            response.listMessage.add("Producto registrado correctamente");
        } else {
            response.error();
            response.listMessage.add("Error al registrar el producto. Verifique que el código no esté duplicado.");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "update", consumes = "multipart/form-data")
    public ResponseEntity<ResponseProductoInsert> update(@ModelAttribute RequestProductoInsert request) {
        ResponseProductoInsert response = new ResponseProductoInsert();

        boolean result = this.productoBusiness.update(request.getDto().getProducto());

        if (result) {
            response.success();
            response.listMessage.add("Producto actualizado correctamente");
        } else {
            response.error();
            response.listMessage.add("Error al actualizar el producto");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity<ResponseProductoInsert> delete(@PathVariable("id") String id) {
        ResponseProductoInsert response = new ResponseProductoInsert();

        boolean result = this.productoBusiness.delete(id);

        if (result) {
            response.success();
            response.listMessage.add("Producto eliminado correctamente");
        } else {
            response.error();
            response.listMessage.add("Error al eliminar el producto");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "getall")
    public ResponseEntity<List<DtoProducto>> getAll() {
        return new ResponseEntity<>(this.productoBusiness.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "getallactivos")
    public ResponseEntity<List<DtoProducto>> getAllActivos() {
        return new ResponseEntity<>(this.productoBusiness.getAllActivos(), HttpStatus.OK);
    }

    @GetMapping(path = "getstockbajo")
    public ResponseEntity<List<DtoProducto>> getStockBajo() {
        return new ResponseEntity<>(this.productoBusiness.getStockBajo(), HttpStatus.OK);
    }

    @GetMapping(path = "getbyid/{id}")
    public ResponseEntity<DtoProducto> getById(@PathVariable("id") String id) {
        DtoProducto producto = this.productoBusiness.getById(id);

        if (producto != null) {
            return new ResponseEntity<>(producto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
