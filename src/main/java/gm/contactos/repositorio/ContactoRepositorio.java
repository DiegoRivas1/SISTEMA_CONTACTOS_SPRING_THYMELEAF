package gm.contactos.repositorio;

import gm.contactos.modelo.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactoRepositorio extends JpaRepository<Contacto, Integer> {
    //findAll, fyndById, save, delete (los implementa por defecto)
}
