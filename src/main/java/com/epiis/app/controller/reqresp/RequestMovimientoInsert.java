package com.epiis.app.controller.reqresp;

import com.epiis.app.dto.DtoMovimientoInventario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestMovimientoInsert {
    @Getter
    @Setter
    public class Dto {
        private DtoMovimientoInventario movimiento;
    }

    private Dto dto = new Dto();
}
