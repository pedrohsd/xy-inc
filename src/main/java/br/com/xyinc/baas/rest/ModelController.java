package br.com.xyinc.baas.rest;


import br.com.xyinc.baas.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/model")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @RequestMapping(value = "{name}", method = RequestMethod.POST)
    private ResponseEntity create(@PathVariable("name") String name, @RequestBody Object model) {
        modelService.save( name, model );
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "{name}", method = RequestMethod.GET)
    private ResponseEntity<List<?>> findAll(@PathVariable("name") String name) {
        return ResponseEntity.ok( modelService.findAll( name ) );
    }
}
