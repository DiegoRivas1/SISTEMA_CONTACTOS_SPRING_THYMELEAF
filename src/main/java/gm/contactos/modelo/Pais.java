package gm.contactos.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPais;//id_pais en la base se crea asi

    private String nombrePais;
    private String codigoCelular;

    //Opcional
    @OneToMany(mappedBy = "pais")
    private List<Contacto> contactos;
}
