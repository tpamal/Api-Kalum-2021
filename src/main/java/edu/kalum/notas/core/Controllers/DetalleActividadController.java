package edu.kalum.notas.core.Controllers;

import edu.kalum.notas.core.entities.DetalleActividad;
import edu.kalum.notas.core.services.IDetalleActividadServices;
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
public class DetalleActividadController {

    @Value("${edu.kalum.configuration.page.registros}")
    private Integer registros;

    private Logger logger = LoggerFactory.getLogger(DetalleNotaController.class);

    @Autowired
    private IDetalleActividadServices detalleActividadServices;

    @PostMapping("/detalleActividad")
    public ResponseEntity<?> create(@Valid @RequestBody DetalleActividad registro, BindingResult result){
        DetalleActividad detalleActividad = null;
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
            registro.setDetalleActividadId(UUID.randomUUID().toString());
            registro.setSeminarioId(UUID.randomUUID().toString());
            detalleActividad = this.detalleActividadServices.save(registro);
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
        response.put("detalleActividad", detalleActividad);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/detalleActividad/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody DetalleActividad update, BindingResult result, @PathVariable String id){
        Map<String, Object> response = new HashMap<>();
        if(result.hasErrors()){
            List<String> errores = result.getFieldErrors()
                    .stream().map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("Errores", errores);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        DetalleActividad detalle = this.detalleActividadServices.findById(id);
        if(detalle == null){
            response.put("Mensaje", "No existe el detalle con el id ".concat(id));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try{
            detalle.setNombreActividad(update.getNombreActividad());
            detalle.setNotaActividad(update.getNotaActividad());
            detalle.setFechaCreacion(update.getFechaCreacion());
            detalle.setFechaEntrega(update.getFechaEntrega());
            detalle.setFechaPostergacion(update.getFechaPostergacion());
            detalle.setEstado(update.getEstado());
            this.detalleActividadServices.save(detalle);
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

    @DeleteMapping("detalleActividad/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        Map<String, Object> response = new HashMap<>();
        DetalleActividad detalle = null;
        try{
            detalle = detalleActividadServices.findById(id);
            if(detalle == null){
                response.put("Mensaje", "No existe ningun detalle con el id".concat(id));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }else{
                detalleActividadServices.delete(detalle);
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

    @GetMapping("/detalleActividad")
    private ResponseEntity<?> listarDetalle(){
        Map<String,Object> response = new HashMap<>();
        logger.debug("Iniciando el proceso de la consulta de los detalles  en la base de datos");
        try{
            logger.debug("Iniciando la consulta a la base de datos");
            List<DetalleActividad> listarDetalle = detalleActividadServices.findAll();
            if(listarDetalle == null || listarDetalle.size() == 0){
                logger.warn("No existen registros de la tabla alumnos");
                response.put("Mensaje","No exiten registros en la tabla alumnos");
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NO_CONTENT);
            }else{
                logger.info("Obteniendo listado de la informacion de alumnos");
                return new ResponseEntity<List<DetalleActividad>>(listarDetalle, HttpStatus.OK);
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
