package org.java.agenda.Agendademo.repository;

import org.java.agenda.Agendademo.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactoRepositorio extends JpaRepository<Contacto, Integer> {
}
