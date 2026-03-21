package gm.contactos.controlador;

import gm.contactos.modelo.Contacto;
import gm.contactos.modelo.Pais;
import gm.contactos.servicio.ContactoServicio;
import gm.contactos.servicio.PaisServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ContactoControlador {
    private static final Logger logger = LoggerFactory.getLogger(ContactoControlador.class);

    @Autowired
    ContactoServicio contactoServicio;
    @Autowired
    PaisServicio paisServicio;

    @GetMapping("/")
    public String iniciar(ModelMap modelo){
        List<Contacto> contactos = contactoServicio.listarContactos();
        contactos.forEach( contacto -> {
            System.out.println(contacto.getNombre() + " - " + contacto.getCelularCompleto());
        });
        modelo.put("contactos", contactos);

        //Cantidad de usuarios activos
        List<Contacto> contactosActivos = contactoServicio.buscarPorEstado(true);
        modelo.put("contactosActivos", contactosActivos);
        return "index";// index.html
    }
    @GetMapping("/agregar")
    public String mostrarAgregar(ModelMap modelo){//public String agregarContacto(@ModelAttribute("contacto") Contacto contacto)
        // Crear un objeto Contacto vacío para el formulario
        modelo.addAttribute("contacto", new Contacto());

        // Cargar la lista de países para el select
        List<Pais> paises = paisServicio.listarPaises();
        modelo.addAttribute("paises", paises);

        return "agregar";
    }

    @PostMapping("/agregar")
    public String agregarContacto(@ModelAttribute Contacto contacto){
        // El país ya viene con el ID del select, solo guardamos
        contactoServicio.guardarContacto(contacto);
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String mostrarEditar(ModelMap modelo, @PathVariable(value = "id") int idContacto){
        Contacto contacto = contactoServicio.buscarContactoPorId(idContacto);
        modelo.put("contacto", contacto);

        // Cargar la lista de países para el select
        List<Pais> paises = paisServicio.listarPaises();
        modelo.addAttribute("paises", paises);
        return "editar";//editar.html
    }

    @PostMapping("/editar")
    public String editarContacto(@ModelAttribute Contacto contacto){
        contactoServicio.guardarContacto(contacto);
        return "redirect:/";
    }

    //
    @GetMapping("/eliminar/{id}")
    public String eliminarContacto(@PathVariable(value = "id") int idContacto){
        Contacto contactoEliminar = contactoServicio.buscarContactoPorId(idContacto);
        contactoServicio.eliminarContacto(contactoEliminar);
        return "redirect:/";
    }


}
