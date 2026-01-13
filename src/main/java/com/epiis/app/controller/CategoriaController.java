package com.epiis.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epiis.app.business.CategoriaBusiness;
import com.epiis.app.controller.reqresp.RequestCategoriaInsert;
import com.epiis.app.controller.reqresp.ResponseCategoriaInsert;
import com.epiis.app.dto.DtoCategoria;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(path = "categoria")
public class CategoriaController {
    @Autowired
    private CategoriaBusiness categoriaBusiness;

    @PostMapping(path = "insert", consumes = "multipart/form-data")
    public ResponseEntity<ResponseCategoriaInsert> insert(@ModelAttribute RequestCategoriaInsert request) {
        ResponseCategoriaInsert response = new ResponseCategoriaInsert();

        boolean result = this.categoriaBusiness.insert(request.getDto().getCategoria());

        if (result) {
            response.success();
            response.listMessage.add("Categoría registrada correctamente");
        } else {
            response.error();
            response.listMessage.add("Error al registrar la categoría");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(path = "update", consumes = "multipart/form-data")
    public ResponseEntity<ResponseCategoriaInsert> update(@ModelAttribute RequestCategoriaInsert request) {
        ResponseCategoriaInsert response = new ResponseCategoriaInsert();

        boolean result = this.categoriaBusiness.update(request.getDto().getCategoria());

        if (result) {
            response.success();
            response.listMessage.add("Categoría actualizada correctamente");
        } else {
            response.error();
            response.listMessage.add("Error al actualizar la categoría");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity<ResponseCategoriaInsert> delete(@PathVariable("id") String id) {
        ResponseCategoriaInsert response = new ResponseCategoriaInsert();

        boolean result = this.categoriaBusiness.delete(id);

        if (result) {
            response.success();
            response.listMessage.add("Categoría eliminada correctamente");
        } else {
            response.error();
            response.listMessage.add("Error al eliminar la categoría");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "getall")
    public ResponseEntity<List<DtoCategoria>> getAll() {
        return new ResponseEntity<>(this.categoriaBusiness.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "getallactivas")
    public ResponseEntity<List<DtoCategoria>> getAllActivas() {
        return new ResponseEntity<>(this.categoriaBusiness.getAllActivas(), HttpStatus.OK);
    }

    @GetMapping(path = "getbyid/{id}")
    public ResponseEntity<DtoCategoria> getById(@PathVariable("id") String id) {
        DtoCategoria categoria = this.categoriaBusiness.getById(id);

        if (categoria != null) {
            return new ResponseEntity<>(categoria, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
