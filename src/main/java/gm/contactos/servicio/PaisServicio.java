package gm.contactos.servicio;

import gm.contactos.modelo.Pais;
import gm.contactos.repositorio.PaisRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaisServicio implements IPaisServicio {

    @Autowired
    private PaisRepositorio paisRepositorio;

    @Override
    public List<Pais> listarPaises() {
        return paisRepositorio.findAll();
    }

    @Override
    public Pais buscarPaisPorId(Integer idPais) {
        return paisRepositorio.findById(idPais).orElse(null);
    }

    @Override
    public void guardarPais(Pais pais) {
        paisRepositorio.save(pais);
    }

    @Override
    public void eliminarPais(Pais pais) {
        paisRepositorio.delete(pais);
    }
}
