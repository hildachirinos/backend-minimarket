package com.epiis.app.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epiis.app.dataaccess.ProveedorRepository;
import com.epiis.app.dto.DtoProveedor;
import com.epiis.app.entity.Proveedor;

@Service
public class ProveedorBusiness {
    @Autowired
    private ProveedorRepository proveedorRepository;

    public boolean insert(DtoProveedor dtoProveedor) {
        Proveedor existente = this.proveedorRepository.findByRuc(dtoProveedor.getRuc());
        if (existente != null) {
            return false;
        }

        dtoProveedor.setIdProveedor(UUID.randomUUID().toString());
        dtoProveedor.setCreatedAt(new Date());
        dtoProveedor.setUpdatedAt(dtoProveedor.getCreatedAt());

        Proveedor proveedor = new Proveedor();

        proveedor.setIdProveedor(dtoProveedor.getIdProveedor());
        proveedor.setRuc(dtoProveedor.getRuc());
        proveedor.setRazonSocial(dtoProveedor.getRazonSocial());
        proveedor.setDireccion(dtoProveedor.getDireccion());
        proveedor.setTelefono(dtoProveedor.getTelefono());
        proveedor.setEmail(dtoProveedor.getEmail());
        proveedor.setContacto(dtoProveedor.getContacto());
        proveedor.setActivo(true);
        proveedor.setCreatedAt(new java.sql.Timestamp(dtoProveedor.getCreatedAt().getTime()));
        proveedor.setUpdatedAt(new java.sql.Timestamp(dtoProveedor.getUpdatedAt().getTime()));

        this.proveedorRepository.save(proveedor);

        return true;
    }

    public boolean update(DtoProveedor dtoProveedor) {
        Proveedor proveedor = this.proveedorRepository.findById(dtoProveedor.getIdProveedor()).orElse(null);

        if (proveedor == null) {
            return false;
        }

        if (!proveedor.getRuc().equals(dtoProveedor.getRuc())) {
            Proveedor existente = this.proveedorRepository.findByRuc(dtoProveedor.getRuc());
            if (existente != null) {
                return false;
            }
        }

        dtoProveedor.setUpdatedAt(new Date());

        proveedor.setRuc(dtoProveedor.getRuc());
        proveedor.setRazonSocial(dtoProveedor.getRazonSocial());
        proveedor.setDireccion(dtoProveedor.getDireccion());
        proveedor.setTelefono(dtoProveedor.getTelefono());
        proveedor.setEmail(dtoProveedor.getEmail());
        proveedor.setContacto(dtoProveedor.getContacto());
        proveedor.setActivo(dtoProveedor.isActivo());
        proveedor.setUpdatedAt(new java.sql.Timestamp(dtoProveedor.getUpdatedAt().getTime()));

        this.proveedorRepository.save(proveedor);

        return true;
    }

    public boolean delete(String idProveedor) {
        Proveedor proveedor = this.proveedorRepository.findById(idProveedor).orElse(null);

        if (proveedor == null) {
            return false;
        }

        proveedor.setActivo(false);
        proveedor.setUpdatedAt(new java.sql.Timestamp(new Date().getTime()));

        this.proveedorRepository.save(proveedor);

        return true;
    }

    public List<DtoProveedor> getAll() {
        List<Proveedor> listProveedor = this.proveedorRepository.getAll();

        List<DtoProveedor> listDtoProveedor = new ArrayList<>();

        for (Proveedor item : listProveedor) {
            DtoProveedor dtoProveedorTemp = this.convertToDto(item);
            dtoProveedorTemp.setCantidadProductos(item.getListProducto() != null ? item.getListProducto().size() : 0);
            listDtoProveedor.add(dtoProveedorTemp);
        }

        return listDtoProveedor;
    }

    public List<DtoProveedor> getAllActivos() {
        List<Proveedor> listProveedor = this.proveedorRepository.getAllActivos();

        List<DtoProveedor> listDtoProveedor = new ArrayList<>();

        for (Proveedor item : listProveedor) {
            listDtoProveedor.add(this.convertToDto(item));
        }

        return listDtoProveedor;
    }

    public DtoProveedor getById(String idProveedor) {
        Proveedor proveedor = this.proveedorRepository.findById(idProveedor).orElse(null);

        if (proveedor == null) {
            return null;
        }

        return this.convertToDto(proveedor);
    }

    private DtoProveedor convertToDto(Proveedor proveedor) {
        DtoProveedor dtoProveedor = new DtoProveedor();

        dtoProveedor.setIdProveedor(proveedor.getIdProveedor());
        dtoProveedor.setRuc(proveedor.getRuc());
        dtoProveedor.setRazonSocial(proveedor.getRazonSocial());
        dtoProveedor.setDireccion(proveedor.getDireccion());
        dtoProveedor.setTelefono(proveedor.getTelefono());
        dtoProveedor.setEmail(proveedor.getEmail());
        dtoProveedor.setContacto(proveedor.getContacto());
        dtoProveedor.setActivo(proveedor.isActivo());
        dtoProveedor.setCreatedAt(proveedor.getCreatedAt());
        dtoProveedor.setUpdatedAt(proveedor.getUpdatedAt());

        return dtoProveedor;
    }
}
