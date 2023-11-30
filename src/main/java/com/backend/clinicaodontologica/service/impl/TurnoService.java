package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.entity.Paciente;
import com.backend.clinicaodontologica.entity.Turno;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.repository.TurnoRepository;
import com.backend.clinicaodontologica.service.ITurnoService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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
        if(turno != null){
            LOGGER.warn("no soy nulo");
            LOGGER.warn("odontologo: " + turno.getOdontologo() + " - paciente: " + turno.getPaciente() + " - Fecha: " + turno.getFechaYHora());
        }

        //veridicar que el paciente y el odontologo existan
        OdontologoSalidaDto odontologoBuscado = odontologoService.buscarOdontologoPorId(turno.getOdontologo());
        PacienteSalidaDto pacienteBuscado = pacienteService.buscarPacientePorId(turno.getPaciente());

        if(odontologoBuscado == null){
            throw new BadRequestException("odontologo no existe");
        }
        if(pacienteBuscado == null){
            throw new BadRequestException("paciente es nulo");
        }

        if (existeTurnoEnFechaYHora(turno.getFechaYHora())) {
            throw new BadRequestException("Ya existe un turno en la misma fecha y hora");
        }

        Paciente paciente = modelMapper.map(pacienteBuscado, Paciente.class);
        Odontologo odontologo = modelMapper.map(odontologoBuscado, Odontologo.class);

        LOGGER.info("TurnoEntradaDto: " + JsonPrinter.toString(turno));
        Turno turnoEntidad = modelMapper.map(turno, Turno.class);
        turnoEntidad.setOdontologo(odontologo);

        Turno turnoAPersistir = turnoRepository.save(turnoEntidad);

        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turnoAPersistir, TurnoSalidaDto.class);
        turnoSalidaDto.setPaciente(paciente);
        turnoSalidaDto.setOdontologo(odontologo);

        LOGGER.info("TurnoSalidaDto: " + JsonPrinter.toString(turnoSalidaDto));
        return turnoSalidaDto;
    }


    private boolean existeTurnoEnFechaYHora(LocalDateTime fechaYHora) {
        List<Turno> turnosEnFechaYHora = turnoRepository.findByFechaYHora(fechaYHora);
        return !turnosEnFechaYHora.isEmpty();
    }

    @Override
    public List<TurnoSalidaDto> listarTurnos() throws ResourceNotFoundException {
        List<TurnoSalidaDto> turnosSalida = turnoRepository.findAll()
                .stream()
                .map(turno -> modelMapper.map(turno, TurnoSalidaDto.class))
                .toList();
        if (LOGGER.isInfoEnabled())
            if(turnosSalida.size() == 0){
                throw new ResourceNotFoundException("No existen turnos");
            }
        LOGGER.info("Listado de todos los turnos: {}", JsonPrinter.toString(turnosSalida));
        return turnosSalida;
    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) throws BadRequestException {
        Turno turnoBuscado = turnoRepository.findById(id).orElse(null);
        TurnoSalidaDto turnoEncontrado = null;

        if (turnoBuscado != null) {
            turnoEncontrado = modelMapper.map(turnoBuscado, TurnoSalidaDto.class);
            LOGGER.info("Paciente encontrado: {}", JsonPrinter.toString(turnoEncontrado));
            return turnoEncontrado;
        } else LOGGER.error("El id no se encuentra registrado en la base de datos");
        throw new BadRequestException("No se ha encontrado el turno con id " + id);
    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno) throws BadRequestException {
        Turno turnoRecibido = modelMapper.map(turno, Turno.class);
        Turno turnoActualizar = turnoRepository.findById(turnoRecibido.getId()).orElse(null);

        TurnoSalidaDto turnoSalidaDto = null;

        if (turnoActualizar != null) {
            turnoActualizar = turnoRecibido;
            turnoRepository.save(turnoActualizar);

            turnoSalidaDto = modelMapper.map(turnoActualizar, TurnoSalidaDto.class);
            LOGGER.warn("Turno actualizado: {}", JsonPrinter.toString(turnoSalidaDto));

        } else {
            LOGGER.error("No fue posible actualizar el paciente porque no se encuentra en nuestra base de datos");
            throw new BadRequestException("No fue posible actualizar el Turno porque no se encuentra en nuestra base de datos");
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
            .addMappings(new PropertyMap<TurnoEntradaDto, Turno>() {
                protected void configure() {
                map().setFechaYHora(source.getFechaYHora());
                map().getOdontologo().setId(source.getOdontologo());
                map().getPaciente().setId(source.getPaciente());
                }
            });

        modelMapper.typeMap(TurnoModificacionEntradaDto.class, Turno.class)
                .addMappings(new PropertyMap<TurnoModificacionEntradaDto, Turno>() {
                    protected void configure() {
                        map().setFechaYHora(source.getFechaYHora());
                    }
                });
        }
    }
