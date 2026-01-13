package com.epiis.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epiis.app.business.ProveedorBusiness;
import com.epiis.app.controller.reqresp.RequestProveedorInsert;
import com.epiis.app.controller.reqresp.ResponseProveedorInsert;
import com.epiis.app.dto.DtoProveedor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(path = "proveedor")
public class ProveedorController {
    @Autowired
    private ProveedorBusiness proveedorBusiness;

    @PostMapping(path = "insert", consumes = "multipart/form-data")
    public ResponseEntity<ResponseProveedorInsert> insert(@ModelAttribute RequestProveedorInsert request) {
        ResponseProveedorInsert response = new ResponseProveedorInsert();

        boolean result = this.proveedorBusiness.insert(request.getDto().getProveedor());

        if (result) {
            response.success();
            response.listMessage.add("Proveedor registrado correctamente");
        } else {
            response.error();
            response.listMessage.add("Error al registrar el proveedor. Verifique que el RUC no esté duplicado.");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(path = "update", consumes = "multipart/form-data")
    public ResponseEntity<ResponseProveedorInsert> update(@ModelAttribute RequestProveedorInsert request) {
        ResponseProveedorInsert response = new ResponseProveedorInsert();

        boolean result = this.proveedorBusiness.update(request.getDto().getProveedor());

        if (result) {
            response.success();
            response.listMessage.add("Proveedor actualizado correctamente");
        } else {
            response.error();
            response.listMessage.add("Error al actualizar el proveedor");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity<ResponseProveedorInsert> delete(@PathVariable("id") String id) {
        ResponseProveedorInsert response = new ResponseProveedorInsert();

        boolean result = this.proveedorBusiness.delete(id);

        if (result) {
            response.success();
            response.listMessage.add("Proveedor eliminado correctamente");
        } else {
            response.error();
            response.listMessage.add("Error al eliminar el proveedor");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "getall")
    public ResponseEntity<List<DtoProveedor>> getAll() {
        return new ResponseEntity<>(this.proveedorBusiness.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "getallactivos")
    public ResponseEntity<List<DtoProveedor>> getAllActivos() {
        return new ResponseEntity<>(this.proveedorBusiness.getAllActivos(), HttpStatus.OK);
    }

    @GetMapping(path = "getbyid/{id}")
    public ResponseEntity<DtoProveedor> getById(@PathVariable("id") String id) {
        DtoProveedor proveedor = this.proveedorBusiness.getById(id);

        if (proveedor != null) {
            return new ResponseEntity<>(proveedor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
