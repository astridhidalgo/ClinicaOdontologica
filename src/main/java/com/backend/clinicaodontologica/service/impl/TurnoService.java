package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.repository.TurnoRepository;
import com.backend.clinicaodontologica.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TurnoService implements ITurnoService {

    private final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);

    private final TurnoRepository turnoRepository;

    private ModelMapper modelMapper;

    private final OdontologoService odontologoService;
    private final PacienteService pacienteService;
    private final Odontologo odontologo;

    public TurnoService(TurnoRepository turnoRepository, ModelMapper modelMapper, OdontologoService odontologoService, PacienteService pacienteService, Odontologo odontologo) {
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
        this.odontologoService = odontologoService;
        this.pacienteService = pacienteService;
        this.odontologo = odontologo;
        configureMapping();
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turno) {

        //veridicar que el paciente y el odontologo existan
        OdontologoSalidaDto odontologoBuscado = odontologoService.buscarOdontologoPorId(turno.getOdontologoId());
        PacienteSalidaDto pacienteBuscado = pacienteService.buscarPacientePorId(turno.getPacienteId());

        if (odontologoBuscado != null && pacienteBuscado != null) {
            //registrar turno

        } else {
            //mandar exception
        }

        return null;
    }

    @Override
    public List<TurnoSalidaDto> listarTurnos() {
        return null;
    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {
        return null;
    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno) {
        return null;
    }

    @Override
    public void eliminarTurno(Long id) {

    }

    private void configureMapping() {

    }
}
