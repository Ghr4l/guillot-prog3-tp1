package com.prog3.tp1;

import com.prog3.tp1.entities.*;
import com.prog3.tp1.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class Tp1Application {
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	DetallePedidoRepository detallePedidoRepository;
	@Autowired
	FacturaRepository facturaRepository;
	@Autowired
	ProductoRepository productoRepository;
	@Autowired
	PedidoRepository pedidoRepository;
	@Autowired
	RubroRepository rubroRepository;
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	DomicilioRepository domicilioRepository;
	@Autowired
	ConfiguracionGeneralRepository configuracionGeneralRepository;
	public static void main(String[] args) {


		SpringApplication.run(Tp1Application.class, args);
		System.out.println("Funcionando...");
	}

	@Bean
	@Transactional
	public CommandLineRunner init(ClienteRepository clienteRepository, DetallePedidoRepository detallePedidoRepository, DomicilioRepository domicilioRepository, FacturaRepository facturaRepository, PedidoRepository pedidoRepository, ProductoRepository productoRepository, RubroRepository rubroRepository, UsuarioRepository usuarioRepository) {
		return args -> {
			ConfiguracionGeneral configuracionGeneral = new ConfiguracionGeneral();
			configuracionGeneral.setCantidadCocineros(5);
			configuracionGeneral.setEmailEmpresa("panaderia@business.com");
			configuracionGeneral.setTokenMercadoPago("mi.panaderia.23");

			configuracionGeneralRepository.save(configuracionGeneral);

			Usuario u1 = Usuario.builder()
					.nombre("rob")
					.password("pikachu")
					.rol("cliente")
					.build();
			usuarioRepository.save(u1);

			Rubro r1 = Rubro.builder().denominacion("Panaderia").build();
			rubroRepository.save(r1);

			Cliente c1 = Cliente.builder()
					.nombre("Roberto")
					.apellido("Estropajo")
					.telefono("123456789")
					.email("roberto@yahoo.com")
					.pedidos(new ArrayList<>())
					.build();
			clienteRepository.save(c1);

			Producto prod1 = Producto.builder()
					.tipo(Producto.TipoProducto.MANUFACTURADO)
					.tiempoEstimadoCocina(10)
					.denominacion("Medialuna")
					.precioVenta(50.0)
					.precioCompra(20.0)
					.stockActual(12)
					.stockMinimo(3)
					.unidadMedida("unidad")
					.foto("https://medialuna.jpg")
					.receta("receta")
					.build();
			productoRepository.save(prod1);

			Producto prod2 = Producto.builder()
					.tipo(Producto.TipoProducto.INSUMO)
					.tiempoEstimadoCocina(15)
					.denominacion("Tortita pinchada")
					.precioVenta(20.0)
					.precioCompra(5.0)
					.stockActual(24)
					.stockMinimo(6)
					.unidadMedida("unidad")
					.foto("https://tortita-p.jpg")
					.receta("receta")
					.build();
			productoRepository.save(prod2);

			r1.agregarProducto(prod1);
			r1.agregarProducto(prod2);
			rubroRepository.save(r1);

			Domicilio domicilioCliente1 = Domicilio.builder()
					.calle("Coronel Rodriguez")
					.numero("273")
					.localidad("Capital")
					.cliente(c1)
					.build();
			domicilioRepository.save(domicilioCliente1);


			Pedido pedido1 = Pedido.builder()
					.fecha("2023-09-10")
					.horaEstimadaEntrega("10:15")
					.estado(Pedido.EstadoPedido.INICIADO)
					.tipoEnvio(Pedido.TipoEnvio.DELIVERY)
					.total(250.0)
					.build();

			domicilioCliente1.getPedidos().add(pedido1);
			pedidoRepository.save(pedido1);
			domicilioRepository.save(domicilioCliente1);
			u1.getPedidos().add(pedido1);
			usuarioRepository.save(u1);

			DetallePedido detalle1 = DetallePedido.builder().cantidad(2).subtotal(40.0).producto(prod1).build();
			pedido1.setDetallesPedido(Arrays.asList(detalle1));

			c1.getPedidos().add(pedido1);
			clienteRepository.save(c1);

			Factura factura1 = Factura.builder()
					.fecha("2023-09-10")
					.numero(1)
					.descuento(0.0)
					.formaPago(Factura.FormaPago.TARJETA)
					.total(250.0)
					.build();

			pedido1.setFactura(factura1);
			facturaRepository.save(factura1);
			pedidoRepository.save(pedido1);

		};
	}

}
