package org.antonio.test.springboot.app;

import java.math.BigDecimal;

import org.antonio.test.springboot.app.models.Banco;
import org.antonio.test.springboot.app.models.Cuenta;

public class Datos {
    public static final Cuenta CUENTA_001 = new Cuenta(1L, "Andrés", new BigDecimal("1000"));
    public static final Cuenta CUENTA_002 = new Cuenta(2L, "John", new BigDecimal("2000"));
    public static final Banco BANCO = new Banco(1L, "El banco financiero", 0);

}
