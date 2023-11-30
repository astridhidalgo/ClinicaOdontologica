package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.OdontologoTurnoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.PacienteTurnoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto_old;
import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.entity.Paciente;
import com.backend.clinicaodontologica.entity.Turno;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.repository.TurnoRepository;
import com.backend.clinicaodontologica.service.ITurnoService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        OdontologoSalidaDto odontologoBuscado = odontologoService.buscarOdontologoPorId(turno.getOdontologo());
        PacienteSalidaDto pacienteBuscado = pacienteService.buscarPacientePorId(turno.getPaciente());

        if(odontologoBuscado == null){
            throw new BadRequestException("odontologo no existe");
        }
        if(pacienteBuscado == null){
            throw new BadRequestException("paciente es nulo");
        }

        if (existeTurnoEnFechaYHora(turno.getFechayHora())) {
            throw new BadRequestException("Ya existe un turno en la misma fecha y hora");
        }

        PacienteTurnoSalidaDto paciente= modelMapper.map(pacienteBuscado, PacienteTurnoSalidaDto.class);
        OdontologoTurnoSalidaDto odontologo= modelMapper.map(odontologoBuscado, OdontologoTurnoSalidaDto.class);

            LOGGER.info("TurnoEntradaDto: " + JsonPrinter.toString(turno));
            Turno turnoEntidad =  modelMapper.map(turno,Turno.class);

            Turno turnoAPersistir= turnoRepository.save(turnoEntidad);

            TurnoSalidaDto turnoSalidaDto= modelMapper.map(turnoAPersistir, TurnoSalidaDto.class);
            turnoSalidaDto.setPacienteTurnoSalidaDto(paciente);
            turnoSalidaDto.setOdontologoTurnoSalidaDto(odontologo);

            LOGGER.info("TurnoSalidaDto: " + JsonPrinter.toString(turnoSalidaDto));
            return turnoSalidaDto;
    }


    private boolean existeTurnoEnFechaYHora(LocalDateTime fechaYHora) {
        List<Turno> turnosEnFechaYHora = turnoRepository.findByFechaYHora(fechaYHora);
        return !turnosEnFechaYHora.isEmpty();
    }

    @Override
    public List<TurnoSalidaDto> listarTurnos() {
        return null;
    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) throws BadRequestException {
            Turno turnoBuscado = turnoRepository.findById(id).orElse(null);
            PacienteSalidaDto pacienteEncontrado= modelMapper.map(turnoBuscado.getPaciente(), PacienteSalidaDto.class);
            OdontologoSalidaDto odontologoEncontrado= modelMapper.map(turnoBuscado.getOdontologo(), OdontologoSalidaDto.class);


//            if (turnoBuscado != null) {
//                PacienteSalidaDto pacienteEncontrado = null;
//                OdontologoSalidaDto odontologoEncontrado = null;
//
//                if (turnoBuscado.getPaciente() != null) {
//                    pacienteEncontrado = modelMapper.map(turnoBuscado.getPaciente(), PacienteSalidaDto.class);
//                }
//
//                if (turnoBuscado.getOdontologo() != null) {
//                    odontologoEncontrado = modelMapper.map(turnoBuscado.getOdontologo(), OdontologoSalidaDto.class);
//                }
//
                PacienteTurnoSalidaDto paciente= modelMapper.map(pacienteEncontrado, PacienteTurnoSalidaDto.class);
                OdontologoTurnoSalidaDto odontologo= modelMapper.map(odontologoEncontrado, OdontologoTurnoSalidaDto.class);

                TurnoSalidaDto turnoEncontrado = modelMapper.map(turnoBuscado, TurnoSalidaDto.class);
                turnoEncontrado.setPacienteTurnoSalidaDto(paciente);
                turnoEncontrado.setOdontologoTurnoSalidaDto(odontologo);

                LOGGER.info("Turno encontrado: {}", JsonPrinter.toString(turnoBuscado));
                return turnoEncontrado;
//            } else {
//                LOGGER.error("El id no se encuentra registrado en la base de datos");
//                throw new BadRequestException("El id no se encuentra registrado en la base de datos");
//            }
        }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno) throws BadRequestException {
        Turno turnoRecibido = modelMapper.map(turno, Turno.class);
        Turno turnoActualizar = turnoRepository.findById(turnoRecibido.getId()).orElse(null);

        //OdontologoSalidaDto odontologoBuscado = odontologoService.buscarOdontologoPorId(turno.getOdontologo());
        //PacienteSalidaDto pacienteBuscado = pacienteService.buscarPacientePorId(turno.getPaciente());

        //como consulto tablas anidadas pivotes

        TurnoSalidaDto turnoSalidaDto = null;

        if (turnoActualizar != null) {
            turnoActualizar = turnoRecibido;
            turnoRepository.save(turnoActualizar);

            turnoSalidaDto = modelMapper.map(turnoActualizar, TurnoSalidaDto.class);
            //turnoSalidaDto.setPacienteTurnoSalidaDto(paciente);
            //turnoSalidaDto.setOdontologoTurnoSalidaDto(odontologo);
            LOGGER.warn("Paciente actualizado: {}", JsonPrinter.toString(turnoSalidaDto));

        } else {
            LOGGER.error("No fue posible actualizar el paciente porque no se encuentra en nuestra base de datos");
            throw new BadRequestException("No fue posible actualizar el turno porque no se encuentra en nuestra base de datos");
        }

        return turnoSalidaDto;
    }

    @Override
    public void eliminarTurno(Long id) throws BadRequestException {
        if (turnoRepository.findById(id).orElse(null) != null) {
            turnoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el turno con id: {}", id);
        } else {
            LOGGER.error("No se ha encontrado el turno con id {}", id);
            throw new BadRequestException("No se ha encontrado el turno con id " + id);
        }

    }

    private void configureMapping() {
        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class)
                .addMappings(mapper -> {
                    mapper.map(TurnoEntradaDto::getPaciente, Turno::setPaciente);
                    mapper.map(TurnoEntradaDto::getOdontologo, Turno::setOdontologo);
                });

        // Mapeo de Turno a TurnoSalidaDto
        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class)
                .addMappings(mapper -> {
                    mapper.map(Turno::getFechaYHora, TurnoSalidaDto::setFechaYHora);
                    mapper.map(src -> src.getPaciente().getNombre(), TurnoSalidaDto::setPacienteTurnoSalidaDto);
                    mapper.map(src -> src.getOdontologo().getNombre(), TurnoSalidaDto::setOdontologoTurnoSalidaDto);
                });

        // Mapeo de Paciente a PacienteTurnoSalidaDto
        modelMapper.typeMap(Paciente.class, PacienteTurnoSalidaDto.class)
                .addMappings(mapper -> {
                    mapper.map(Paciente::getId, PacienteTurnoSalidaDto::setId);
                    mapper.map(Paciente::getNombre, PacienteTurnoSalidaDto::setNombre);
                    mapper.map(Paciente::getApellido, PacienteTurnoSalidaDto::setApellido);
                });

        // Mapeo de Odontologo a OdontologoTurnoSalidaDto
        modelMapper.typeMap(Odontologo.class, OdontologoTurnoSalidaDto.class)
                .addMappings(mapper -> {
                    mapper.map(Odontologo::getId, OdontologoTurnoSalidaDto::setId);
                    mapper.map(Odontologo::getNombre, OdontologoTurnoSalidaDto::setNombre);
                    mapper.map(Odontologo::getApellido, OdontologoTurnoSalidaDto::setApellido);
                });
    }

}
