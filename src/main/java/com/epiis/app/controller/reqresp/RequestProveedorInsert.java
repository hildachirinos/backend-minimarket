package com.epiis.app.controller.reqresp;

import com.epiis.app.dto.DtoProveedor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestProveedorInsert {
    @Getter
    @Setter
    public class Dto {
        private DtoProveedor proveedor;
    }

    private Dto dto = new Dto();
}
