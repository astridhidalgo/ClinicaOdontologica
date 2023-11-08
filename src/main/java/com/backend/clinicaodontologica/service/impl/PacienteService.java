package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.model.Paciente;
import com.backend.clinicaodontologica.repository.IRepository;
import com.backend.clinicaodontologica.service.IPacienteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService implements IPacienteService {

    private IRepository<Paciente> pacienteRepository;

    public PacienteService(IRepository<Paciente> pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente registrarPaciente(Paciente paciente){
        return pacienteRepository.registrar(paciente);
    }

    public List<Paciente> listarPacientes(){
        return pacienteRepository.listarTodos();
    }

    @Override
    public Paciente buscarPacientePorId(int id) {
        return pacienteRepository.buscarPorId(id);
    }

    @Override
    public Paciente actualizarPaciente(Paciente paciente) {
        return pacienteRepository.actualizar(paciente);
    }


}
