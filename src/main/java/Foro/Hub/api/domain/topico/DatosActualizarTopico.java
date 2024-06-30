package Foro.Hub.api.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
        @NotNull Long id,
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @NotNull StatusTopico status,
        @NotBlank String autor,
        @NotBlank String curso) {
}