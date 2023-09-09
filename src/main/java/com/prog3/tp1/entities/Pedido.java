package com.prog3.tp1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Pedido implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String fecha;
    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;
    private String horaEstimadaEntrega;
    @Enumerated(EnumType.STRING)
    private TipoEnvio tipoEnvio;
    private double total;
    //Hacemos la relacion de 1 pedido a N detallesPedido
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "pedido_id")
    @Builder.Default
    private List<DetallePedido> detallesPedido = new ArrayList<>();
    //Relacion uno a uno con Factura
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Factura factura;

    public enum EstadoPedido {
        INICIADO,
        PREPARACION,
        ENTREGADO
    }

    public enum TipoEnvio {
        DELIVERY,
        RETIRO
    }

}
