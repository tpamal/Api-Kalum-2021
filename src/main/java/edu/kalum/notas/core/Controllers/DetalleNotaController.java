package edu.kalum.notas.core.Controllers;

import edu.kalum.notas.core.entities.Alumno;
import edu.kalum.notas.core.entities.DetalleNota;
import edu.kalum.notas.core.services.IAlumnoServices;
import edu.kalum.notas.core.services.IDetalleNotaServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/kalum-notas/v1")
public class DetalleNotaController {
    @Value("${edu.kalum.configuration.page.registros}")
    private Integer registros;

    private Logger logger = LoggerFactory.getLogger(DetalleNotaController.class);

    @Autowired
    private IDetalleNotaServices detalleNotaServices;

    @Autowired
    private IAlumnoServices alumnoService;

    @PostMapping("/detalle")
    public ResponseEntity<?> create(@Valid @RequestBody DetalleNota registro, BindingResult result){
        DetalleNota detalleNota = null;
        Map<String, Object> response = new HashMap<>();
        if(result.hasErrors()){
            List<String> errores = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errores", errores);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try{
            Alumno alumno = alumnoService.findByCarne(registro.getAlumno().getCarne());
            if(alumno == null){
                response.put("Mensaje", "No existe el alumno con el carne".concat(registro.getAlumno().getCarne()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }
            registro.setDetalleNotaId(UUID.randomUUID().toString());
            registro.setDetalleActividadId(UUID.randomUUID().toString());
            detalleNota = this.detalleNotaServices.save(registro);
        }catch (CannotCreateTransactionException e){
            logger.error("Error al momento de conectarse a la base de datos");
            response.put("Mensaje", "Error al momento de conectarse a la base de datos");
            response.put("Error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            logger.error("Error al momento de insertar de informacion a la base de datos");
            response.put("Mensaje", "Error al momento de insertar la informacion a la base de datos");
            response.put("Error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
        response.put("Mensaje", "El detalle fue creado exitosamente");
        response.put("Asignacion", detalleNota);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/detalle/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody DetalleNota update, BindingResult result, @PathVariable String id){
        Map<String, Object> response = new HashMap<>();
        if(result.hasErrors()){
            List<String> errores = result.getFieldErrors()
                    .stream().map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("Errores", errores);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        DetalleNota detalle = this.detalleNotaServices.findById(id);
        if(detalle == null){
            response.put("Mensaje", "No existe el detalle con el id ".concat(id));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try{
            Alumno alumno = alumnoService.findByCarne(update.getAlumno().getCarne());
            if(alumno == null){
                response.put("Mensaje", "No existe el alumno con el carne".concat(update.getAlumno().getCarne()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }
            detalle.setValorNota(update.getValorNota());
            detalle.setAlumno(alumno);
            this.detalleNotaServices.save(detalle);
        }catch (CannotCreateTransactionException e){
            logger.error("Error al momento de conectarse a la base de datos");
            response.put("Mensaje", "Error al momento de conectarse a la base de datos");
            response.put("Error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            logger.error("Error al momento de insertar de informacion a la base de datos");
            response.put("Mensaje", "Error al momento de insertar la informacion a la base de datos");
            response.put("Error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
        response.put("Mensaje", "La modificacion del campo se ha realizado correctamente");
        response.put("detalle", detalle);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @DeleteMapping("detalle/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        Map<String, Object> response = new HashMap<>();
        DetalleNota detalle = null;
        try{
            detalle = detalleNotaServices.findById(id);
            if(detalle == null){
                response.put("Mensaje", "No existe ningun detalle con el id".concat(id));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }else{
                detalleNotaServices.delete(detalle);
            }
        }catch (CannotCreateTransactionException e){
            logger.error("Error al momento de conectarse a la base de datos");
            response.put("Mensaje", "Error al momento de conectarse a la base de datos");
            response.put("Error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            logger.error("Error al momento de insertar de informacion a la base de datos");
            response.put("Mensaje", "Error al momento de insertar la informacion a la base de datos");
            response.put("Error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
        response.put("Mensaje", "El detalle fue eliminada correctamente");
        response.put("detalle", detalle);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @GetMapping("/detalle")
    private ResponseEntity<?> listarDetalle(){
        Map<String,Object> response = new HashMap<>();
        logger.debug("Iniciando el proceso de la consulta de los detalles  en la base de datos");
        try{
            logger.debug("Iniciando la consulta a la base de datos");
            List<DetalleNota> listarDetalle = detalleNotaServices.findAll();
            if(listarDetalle == null || listarDetalle.size() == 0){
                logger.warn("No existen registros de la tabla alumnos");
                response.put("Mensaje","No exiten registros en la tabla alumnos");
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NO_CONTENT);
            }else{
                logger.info("Obteniendo listado de la informacion de alumnos");
                return new ResponseEntity<List<DetalleNota>>(listarDetalle, HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
            logger.error("Error al momento de conectarse a la base de datos");
            response.put("Mensaje", "Error al momento de conectarse a la base de datos ");
            response.put("Error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            logger.error("Error al momento de consultar la informacion a la base de datos");
            response.put("Mensaje","Error al memento de consultar la informacion a la base de datos ");
            response.put("Error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        }
    }
}
