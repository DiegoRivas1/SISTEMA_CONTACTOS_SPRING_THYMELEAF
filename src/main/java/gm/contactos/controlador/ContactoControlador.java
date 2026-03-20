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
        return "index";// index.html
    }
    @GetMapping("/agregar")
    public String mostrarAgregar(ModelMap modelo){
        // Crear un objeto Contacto vacío para el formulario
        modelo.addAttribute("contacto", new Contacto());

        // Cargar la lista de países para el select
        List<Pais> paises = paisServicio.listarPaises();
        modelo.addAttribute("paises", paises);

        return "agregar";
    }

    @PostMapping("/agregar")
    public String agregarContacto(@ModelAttribute("contacto") Contacto contacto){//public String agregarContacto(@ModelAttribute("contacto") Contacto contacto)
        // El país ya viene con el ID del select, solo guardamos
        contactoServicio.guardarContacto(contacto);
        return "redirect:/";
    }
}
