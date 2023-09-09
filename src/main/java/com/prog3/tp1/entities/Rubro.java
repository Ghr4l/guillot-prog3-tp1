package com.prog3.tp1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rubro implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String denominacion;

        //Relacion de rubro con producto, Un rubro a Muchos productos.
        @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
        @JoinColumn(name = "rubro_id")
        @Builder.Default
        private List<Producto> productos= new ArrayList<>();

        public void agregarProducto(Producto producto) {

                this.productos.add(producto);
        }

}




