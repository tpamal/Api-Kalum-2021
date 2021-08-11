package edu.kalum.notas.core.Controllers;

import edu.kalum.notas.core.entities.Seminario;
import edu.kalum.notas.core.services.ISeminarioServices;
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
public class SeminarioController {

    @Value("${edu.kalum.configuration.page.registros}")
    private Integer registros;

    private Logger logger = LoggerFactory.getLogger(SeminarioController.class);

    @Autowired
    private ISeminarioServices seminarioServices;

    @PostMapping("/seminario")
    public ResponseEntity<?> create(@Valid @RequestBody Seminario registro, BindingResult result){
        Seminario seminario = null;
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
            registro.setSeminarioId(UUID.randomUUID().toString());
            registro.setModuloId(UUID.randomUUID().toString());
            seminario = this.seminarioServices.save(registro);
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
        response.put("Mensaje", "El detalle seminario fue creado exitosamente");
        response.put("seminario", seminario);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/seminario/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Seminario update, BindingResult result, @PathVariable String id){
        Map<String, Object> response = new HashMap<>();
        if(result.hasErrors()){
            List<String> errores = result.getFieldErrors()
                    .stream().map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("Errores", errores);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        Seminario detalleSeminario = this.seminarioServices.findById(id);
        if(detalleSeminario == null){
            response.put("Mensaje", "No existe el seminario con el id ".concat(id));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try{
            detalleSeminario.setNombreSeminario(update.getNombreSeminario());
            detalleSeminario.setFechainicio(update.getFechainicio());
            detalleSeminario.setFechaFinal(update.getFechaFinal());
            this.seminarioServices.save(detalleSeminario);
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
        response.put("seminario", detalleSeminario);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @DeleteMapping("seminario/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        Map<String, Object> response = new HashMap<>();
        Seminario detalleSeminario = null;
        try{
            detalleSeminario = seminarioServices.findById(id);
            if(detalleSeminario == null){
                response.put("Mensaje", "No existe ningun detalle con el id".concat(id));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }else{
                seminarioServices.delete(detalleSeminario);
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
        response.put("detalleSeminario", detalleSeminario);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }


    @GetMapping("/seminario")
    private ResponseEntity<?> listarSeminario(){
        Map<String,Object> response = new HashMap<>();
        logger.debug("Iniciando el proceso de la consulta de los detalles  en la base de datos");
        try{
            logger.debug("Iniciando la consulta a la base de datos");
            List<Seminario> listarSeminario = seminarioServices.findAll();
            if(listarSeminario == null || listarSeminario.size() == 0){
                logger.warn("No existen registros de la tabla alumnos");
                response.put("Mensaje","No exiten registros en la tabla alumnos");
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NO_CONTENT);
            }else{
                logger.info("Obteniendo listado de la informacion de alumnos");
                return new ResponseEntity<List<Seminario>>(listarSeminario, HttpStatus.OK);
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
