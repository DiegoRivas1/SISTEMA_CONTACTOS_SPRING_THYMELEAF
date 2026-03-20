package gm.contactos.servicio;

import gm.contactos.modelo.Contacto;
import gm.contactos.modelo.Pais;

import java.util.List;

public interface IPaisServicio {

    public List<Pais> listarPaises();
    public Pais buscarPaisPorId(Integer idPais);
    public void guardarPais(Pais pais);
    public void eliminarPais(Pais pais);
}
