package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteSinConsulta implements ValidadorDeConsultas{


    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DatosAgendarConsulta datosAgendarConsulta){
        var primerHorario = datosAgendarConsulta.fecha().withHour(7);
        var ultimoHorario = datosAgendarConsulta.fecha().withHour(18);

        var pacienteconConsulta= consultaRepository.existsByPacienteIdAndFechaBetween(datosAgendarConsulta.idPaciente(), primerHorario, ultimoHorario);

        if (pacienteconConsulta){
            throw new ValidationException("El paciente ya cuenta con una consulta programada para esa fecha");
        }
    }
}
