package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRegistroMedico(@NotBlank String nombre,
                                  @NotBlank @Email String email,
                                  @NotBlank String telefono,
                                  @NotBlank @Pattern(regexp = "\\d{4,6}") String documento,
                                  @NotNull Especialidad especialidad /*Se utilizara un enum por que en el modelo es un dropdown con 4 opciones)*/,
                                  @NotNull @Valid DatosDireccion direccion)  {
}
