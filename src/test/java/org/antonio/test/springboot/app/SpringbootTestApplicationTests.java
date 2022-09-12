package org.antonio.test.springboot.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.antonio.test.springboot.app.exceptions.DineroInsuficienteException;
import org.antonio.test.springboot.app.models.Banco;
import org.antonio.test.springboot.app.models.Cuenta;
import org.antonio.test.springboot.app.repositories.BancoRepository;
import org.antonio.test.springboot.app.repositories.CuentaRepository;
import org.antonio.test.springboot.app.services.CuentaService;
import org.antonio.test.springboot.app.services.CuentaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootTestApplicationTests {

	CuentaRepository cuentaRepository;
	BancoRepository bancoRepository;

	CuentaService service;

	@BeforeEach
	void setUp() {
		cuentaRepository = mock(CuentaRepository.class);
		bancoRepository = mock(BancoRepository.class);
		service = new CuentaServiceImpl(cuentaRepository, bancoRepository);
	}

	@Test
	void contextLoads() {
		when(cuentaRepository.findById(1L)).thenReturn(Datos.crearCuenta001());
		when(cuentaRepository.findById(2L)).thenReturn(Datos.crearCuenta002());
		when(bancoRepository.findById(1L)).thenReturn(Datos.crearBanco());

		BigDecimal saldoOrigen = service.revisarSaldo(1L);
		BigDecimal saldoDestino = service.revisarSaldo(2L);

		assertEquals("1000", saldoOrigen.toPlainString());
		assertEquals("2000", saldoDestino.toPlainString());

		service.transferir(1L, 2L, new BigDecimal("100"), 1L);

		saldoOrigen = service.revisarSaldo(1L);
		saldoDestino = service.revisarSaldo(2L);

		assertEquals("900", saldoOrigen.toPlainString());
		assertEquals("2100", saldoDestino.toPlainString());

		verify(cuentaRepository, times(3)).findById(1L);
		verify(cuentaRepository, times(3)).findById(2L);
		verify(cuentaRepository, times(2)).update(any(Cuenta.class));

		verify(bancoRepository, times(1)).findById(1L);
		verify(bancoRepository).update(any(Banco.class));
	}

	@Test
	void contextLoads2() {
		when(cuentaRepository.findById(1L)).thenReturn(Datos.crearCuenta001());
		when(cuentaRepository.findById(2L)).thenReturn(Datos.crearCuenta002());
		when(bancoRepository.findById(1L)).thenReturn(Datos.crearBanco());

		BigDecimal saldoOrigen = service.revisarSaldo(1L);
		BigDecimal saldoDestino = service.revisarSaldo(2L);

		assertEquals("1000", saldoOrigen.toPlainString());
		assertEquals("2000", saldoDestino.toPlainString());

		assertThrows(DineroInsuficienteException.class, () -> {
			service.transferir(1L, 2L, new BigDecimal("1200"), 1L);
		});
		// service.transferir(1L, 2L, new BigDecimal("1200"), 1L);

		saldoOrigen = service.revisarSaldo(1L);
		saldoDestino = service.revisarSaldo(2L);

		assertEquals("1000", saldoOrigen.toPlainString());
		assertEquals("2000", saldoDestino.toPlainString());

		int total = service.revisarTotalTransferencias(1L);
		assertEquals(0, total);

		verify(cuentaRepository, times(3)).findById(1L);
		verify(cuentaRepository, times(2)).findById(2L);
		verify(cuentaRepository, never()).update(any(Cuenta.class));

		verify(bancoRepository, times(1)).findById(1L);
		verify(bancoRepository, never()).update(any(Banco.class));
	}

}
