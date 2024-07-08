package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HoraDeAnticipacion implements ValidadorDeConsultas {

    public void validar(DatosAgendarConsulta datosAgendarConsulta){

        var horaActual = LocalDateTime.now();
        var horaConsulta = datosAgendarConsulta.fecha();

        var diferenciaDe30Min = Duration.between(horaActual,horaConsulta).toMinutes()<30;

        if(diferenciaDe30Min){
            throw new ValidationException("Las consultas deben programarse con almenos 30 Min de anticipacion.");
        }


    }
}
