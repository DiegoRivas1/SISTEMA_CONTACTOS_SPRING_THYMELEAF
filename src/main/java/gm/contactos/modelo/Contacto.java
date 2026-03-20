package gm.contactos.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idContacto;//id_contacto enla bse se crea asi
    String nombre;
    String celular;
    String email;
    boolean estado;

    //pais_id sera un columna en Contacto, pero apra llamar en index.html se usara contactos.pais (contactos la variable que mandamos desde controlador a la vista)
    @ManyToOne
    @JoinColumn(name="pais_id")//Usa la llave primary de pais y la coloca en la tabla contacto como llavae foranea
    private Pais pais;

    public String getCelularCompleto(){
        return "+" + pais.getCodigoCelular() + " " + celular;
    }

    public String getEstadoTexto(){
        return estado ? "ACTIVO" : "INACTIVO";
    }
}


/*

EL objeto queda asi, ejemplo:
contacto {
    idContacto: 1,
    nombre: "Juan",
    celular: "987654321",
    pais: {
        idPais: 1,
        nombrePais: "Peru",
        codigoTelefono: "51"
    }
}
 */