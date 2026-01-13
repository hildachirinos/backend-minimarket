package com.epiis.app.controller.reqresp;

import com.epiis.app.dto.DtoProducto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestProductoInsert {
    @Getter
    @Setter
    public class Dto {
        private DtoProducto producto;
    }

    private Dto dto = new Dto();
}
