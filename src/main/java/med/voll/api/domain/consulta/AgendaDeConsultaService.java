package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultaService {


    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private ConsultaRepository consultaRepository;

    //Todos las clases que esten implementando la
    //interfas ValidadorDeConsultas seran inyectados en la lista validadores
    @Autowired
    List<ValidadorDeConsultas> validadores;

    public DatosDetalleConsulta agendar(DatosAgendarConsulta datosAgendarConsulta) {

        if(!pacienteRepository.findById(datosAgendarConsulta.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("Este ID para paciente no fue encontrado");

        }
        if(datosAgendarConsulta.idMedico() != null && !medicoRepository.existsById(datosAgendarConsulta.idMedico())){
            throw new ValidacionDeIntegridad("Este ID para medico no fue encontrado");
        }



        validadores.forEach(v -> v.validar(datosAgendarConsulta));


        var paciente = pacienteRepository.findById(datosAgendarConsulta.idPaciente()).get();

        var medico = selecionarMedico(datosAgendarConsulta);

        if(medico == null){
            throw new ValidacionDeIntegridad("No hay medicos disponible para este horario y especialidad");

        }

        var consulta = new Consulta(null, paciente, medico, datosAgendarConsulta.fecha());

       consultaRepository.save(consulta);

       return new DatosDetalleConsulta(consulta);


    }

    private Medico selecionarMedico(DatosAgendarConsulta datosAgendarConsulta) {
        if (datosAgendarConsulta.idMedico() != null){
            return medicoRepository.getReferenceById(datosAgendarConsulta.idMedico());
        }
        if(datosAgendarConsulta.especialidad() == null){
            throw new ValidacionDeIntegridad("Debe seleccionar una especialidad para el medico");
        }
        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datosAgendarConsulta.especialidad(), datosAgendarConsulta.fecha());
    }
}
