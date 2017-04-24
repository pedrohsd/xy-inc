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
    private ResponseEntity<Object> create(@PathVariable("name") String name, @RequestBody Object model) {
        modelService.save( name, model );
        return ResponseEntity.ok(model);
    }

    @RequestMapping(value = "{name}/{id}", method = RequestMethod.PUT)
    private ResponseEntity<Object> update(@PathVariable("name") String name, @RequestBody Object model) {
        modelService.save( name, model );
        return ResponseEntity.ok(model);
    }

    @RequestMapping(value = "{name}", method = RequestMethod.GET)
    private ResponseEntity<List<?>> findAll(@PathVariable("name") String name) {
        List result = modelService.findAll( name );
        return ResponseEntity.ok( result );
    }

    @RequestMapping(value = "{name}/{id}", method = RequestMethod.GET)
    private ResponseEntity<List<?>> findById(@PathVariable("name") String name, @PathVariable("id") String id) {
        return ResponseEntity.ok( modelService.findById( name, id ) );
    }
}
