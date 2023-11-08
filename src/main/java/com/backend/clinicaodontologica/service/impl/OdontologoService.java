package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.model.Odontologo;
import com.backend.clinicaodontologica.repository.IRepository;

import java.util.List;

public class OdontologoService {
    private IRepository<Odontologo> odontologoRepository;

    public OdontologoService(IRepository<Odontologo> odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public Odontologo guardarOdontologo(Odontologo odontologo) {
        return odontologoRepository.registrar(odontologo);
    }

    public Odontologo buscarOdontologoPorId(int id) {
        return odontologoRepository.buscarPorId(id);
    }

    public List<Odontologo> listarOdontologos() {
        return odontologoRepository.listarTodos();
    }


}
