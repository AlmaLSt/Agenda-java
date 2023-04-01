package org.java.agenda.Agendademo.controller;

import jakarta.validation.Valid;
import org.java.agenda.Agendademo.model.Contacto;
import org.java.agenda.Agendademo.repository.ContactoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ContactoControlador {

    @Autowired
    private ContactoRepositorio contactoRepositorio;
    @GetMapping({"/",""})
        public String paginaInicio(Model model){
        List<Contacto> contactos = contactoRepositorio.findAll();
        model.addAttribute("contactos", contactos);
        return "index";
        }

        @GetMapping("/nuevo")
    public String formularioRegistro(Model model){
        model.addAttribute("contacto", new Contacto());
        return "nuevo";
        }

        @PostMapping("/nuevo")
//    public String guardaRegistro(@Validated Contacto contacto, BindingResult bindingResult,
//                                 RedirectAttributes redirAttrs, Model model){
//        if(bindingResult.hasErrors()){
//            model.addAttribute("contacto", contacto);
//            return "nuevo";
//        }
        public String guardaRegistro(@Valid Contacto contacto, Errors errors,
                                     RedirectAttributes redirAttrs, Model model){
            if(errors.hasErrors()){
                model.addAttribute("contacto", contacto);
                return "nuevo";
            }
        contactoRepositorio.save(contacto);
        redirAttrs.addFlashAttribute("msgSuccess", "Contacto guardado correctamente");
        return "redirect:/";
        }

    @GetMapping("/editar/{id}")
    public String editarRegistro(@PathVariable Integer id, Model model){
        Contacto contacto = contactoRepositorio.getReferenceById(id);
        model.addAttribute("contacto", contacto);
        return "nuevo";
    }

    @PostMapping("/editar/{id}")
    public String actualizaRegistro(@PathVariable Integer id, @Valid Contacto contacto, Errors errors,
                                    RedirectAttributes redirAttrs, Model model){
        Contacto contactoDB = contactoRepositorio.getReferenceById(id);
        if(errors.hasErrors()){
            model.addAttribute("contacto", contacto);
            return "nuevo";
        }
        contactoDB.setNombre(contacto.getNombre());
        contactoDB.setEmail(contacto.getEmail());
        contactoDB.setTelefono(contacto.getTelefono());;
        contactoDB.setFechaNacimiento(contacto.getFechaNacimiento());

        contactoRepositorio.save(contactoDB);
        redirAttrs.addFlashAttribute("msgSuccess", "Contacto actualizado correctamente");
        return "redirect:/";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarRegistro(@PathVariable Integer id, RedirectAttributes redirAttrs){
        Contacto contacto = contactoRepositorio.getReferenceById(id);
        contactoRepositorio.delete(contacto);
        redirAttrs.addFlashAttribute("contacto", contacto);
        return "redirect:/";
    }


}

