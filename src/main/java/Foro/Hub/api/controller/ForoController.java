package Foro.Hub.api.controller;


import Foro.Hub.api.domain.topico.*;
import Foro.Hub.api.infra.errores.IdRequeridoException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class ForoController {

    @Autowired
    private TopicoService topicoService;

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    public ResponseEntity<?> registrarTopico(@Valid @RequestBody DatosRegistroTopico datosRegistroTopico) {
        Topico topico = topicoService.registrarTopico(datosRegistroTopico);
        return ResponseEntity.ok().body(new DatosDetalleTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopicos>> listartopicos(
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion
    ) {
        return ResponseEntity.ok().body(
                topicoRepository.findAll(paginacion).map(DatosListadoTopicos::new)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerTopicoPorId(@PathVariable(required = false) Long id) {
        if (id == null) {
            throw new IdRequeridoException("ID es obligatorio para esta consulta");
        }
        Topico topico = topicoService.obtenerTopicoPorId(id);
        DatosDetalleTopico detalleTopico = new DatosDetalleTopico(topico);
        return ResponseEntity.ok().body(detalleTopico);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id) {
        Topico topico = topicoService.eliminarTopico(id);
        return ResponseEntity.ok().body(new DatosDetalleTopico(topico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTopico(@PathVariable Long id, @Valid @RequestBody DatosActualizarTopico datosActualizarTopico) {
        Topico topico = topicoService.actualizarTopico(id, datosActualizarTopico);
        return ResponseEntity.ok().body(new DatosDetalleTopico(topico));
    }
}
