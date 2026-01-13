package com.epiis.app.controller.reqresp;

import com.epiis.app.dto.DtoCategoria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestCategoriaInsert {
    @Getter
    @Setter
    public class Dto {
        private DtoCategoria categoria;
    }

    private Dto dto = new Dto();
}
