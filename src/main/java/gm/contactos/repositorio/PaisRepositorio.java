package gm.contactos.repositorio;

import gm.contactos.modelo.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaisRepositorio extends JpaRepository<Pais, Integer> {
}
