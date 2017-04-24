package br.com.xyinc.baas.rest;


import br.com.xyinc.baas.domain.MetaModel;
import br.com.xyinc.baas.exception.MetaModelNotFoundException;
import br.com.xyinc.baas.rest.dto.MetaModelDTO;
import br.com.xyinc.baas.service.MetaModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/metamodel")
public class MetaModelController {

    @Autowired
    private MetaModelService metaModelService;


    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus( HttpStatus.CREATED)
    public MetaModelDTO create(@RequestBody @Valid MetaModelDTO metaModelDTO) {
        MetaModel metaModel = metaModelService.create(metaModelDTO.unwrap());
        return new MetaModelDTO( metaModel );
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public MetaModelDTO delete(@PathVariable("id") String id) {
        return new MetaModelDTO( metaModelService.delete(id));
    }

    @RequestMapping(method = RequestMethod.GET)
    private List<MetaModelDTO> findAll() {
        return metaModelService.findAll().stream()
            .map( m -> new MetaModelDTO( m ) )
            .collect( Collectors.toList() );
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public MetaModelDTO findById(@PathVariable("id") String id) {
        return new MetaModelDTO( metaModelService.findById(id) );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleTodoNotFound(MetaModelNotFoundException e) {}
}
