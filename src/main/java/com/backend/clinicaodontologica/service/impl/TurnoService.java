package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.entity.Turno;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.repository.TurnoRepository;
import com.backend.clinicaodontologica.service.ITurnoService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
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

    public TurnoService(TurnoRepository turnoRepository, ModelMapper modelMapper, OdontologoService odontologoService, PacienteService pacienteService) {
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
        this.odontologoService = odontologoService;
        this.pacienteService = pacienteService;
        configureMapping();
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turno) throws BadRequestException{
        //veridicar que el paciente y el odontologo existan
        OdontologoSalidaDto odontologoBuscado = odontologoService.buscarOdontologoPorId(turno.getOdontologoId());
        PacienteSalidaDto pacienteBuscado = pacienteService.buscarPacientePorId(turno.getPacienteId());
        if(odontologoBuscado == null){
            throw new BadRequestException("odontologo no existe");
        }
        if(pacienteBuscado == null){
            throw new BadRequestException("odontologo es nulo");
        }
        if (odontologoBuscado != null && pacienteBuscado != null) {
            LOGGER.info("TurnoEntradaDto: " + JsonPrinter.toString(turno));
            Turno TurnoEntidad =  modelMapper.map(turno,Turno.class);

            Turno turnoAPersistir= turnoRepository.save(TurnoEntidad);

            TurnoSalidaDto turnoSalidaDto = modelMapper.map(turnoAPersistir, TurnoSalidaDto.class);
            LOGGER.info("TurnoSalidaDto: " + JsonPrinter.toString(turnoSalidaDto));
            return turnoSalidaDto;
        } else {
       throw new BadRequestException("odontologo y paciente no existen");
        }
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
