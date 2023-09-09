package com.prog3.tp1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Factura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fecha;
    private int numero;
    private double descuento;
    @Enumerated(EnumType.STRING)
    private FormaPago formaPago;
    private double total;

    public enum FormaPago {
        EFECTIVO,
        TARJETA
    }
}
