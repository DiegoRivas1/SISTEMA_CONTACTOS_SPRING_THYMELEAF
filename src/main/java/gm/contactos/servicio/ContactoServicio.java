package gm.contactos.servicio;

import gm.contactos.modelo.Contacto;
import gm.contactos.repositorio.ContactoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactoServicio implements IContactoServicio{

    @Autowired
    private ContactoRepositorio contactoRepositorio;

    @Override
    public List<Contacto> listarContactos() {
        return contactoRepositorio.findAll();
    }

    @Override
    public Contacto buscarContactoPorId(Integer idContacto) {
        return contactoRepositorio.findById(idContacto).orElse(null);
    }

    @Override
    public void guardarContacto(Contacto contacto) {
        contactoRepositorio.save(contacto);//idcontacto=null(insercion), nonull (actualizacion)
    }

    @Override
    public void eliminarContacto(Contacto contacto) {
        contactoRepositorio.delete(contacto);
    }

    @Override
    public List<Contacto> buscarPorEstado(Boolean estado) {
        return contactoRepositorio.findByEstado(estado);
    }
}
