package br.com.xyinc.baas.it.rest;


import br.com.xyinc.baas.domain.MetaModel;
import br.com.xyinc.baas.it.AbstractRestIT;
import br.com.xyinc.baas.rest.ModelController;
import br.com.xyinc.baas.service.MetaModelService;
import br.com.xyinc.baas.service.ModelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ModelControllerTest extends AbstractRestIT {

    @Autowired
    private ModelService modelService;

    @Autowired
    private MetaModelService metaModelService;

    private MockMvc restBusMockMvc;

    @Before
    public void setup() {

        ModelController busResource = new ModelController();
        ReflectionTestUtils.setField( busResource, "modelService", modelService );
        this.restBusMockMvc = MockMvcBuilders.standaloneSetup( busResource ).build();
    }

    @Test
    public void shouldCreateModel_Successfully()
        throws Exception {

        MetaModel metaModel = createMetaModel("");
        metaModelService.create( metaModel );

        Map model = createModel();

        restBusMockMvc.perform( post( "/api/model/" + metaModel.getName() )
            .contentType( contentType )
            .content( this.convertObjectToJsonBytes( model ) )
            .accept( APPLICATION_JSON_UTF8_VALUE ) )
            .andDo( print() )
            .andExpect( status().isOk() )
            .andExpect( content().contentType( APPLICATION_JSON_UTF8_VALUE ) )
            .andDo( print() )
            .andExpect( jsonPath( "$._id" ).exists() )
            .andExpect( jsonPath( "$.name" ).value( model.get( "name" ) ))
            .andExpect( jsonPath( "$.price" ).value( model.get( "price" ) ))
            .andExpect( jsonPath( "$.builtAt" ).value( model.get( "builtAt" ) ))
            .andExpect( jsonPath( "$.number" ).value( model.get( "number" ) ));
    }

    @Test
    public void shouldUpdateModel_Successfully()
        throws Exception {

        MetaModel metaModel = createMetaModel("");
        metaModelService.create( metaModel );

        Map model = createModel();

        MvcResult result = restBusMockMvc.perform( post( "/api/model/" + metaModel.getName() )
            .contentType( contentType )
            .content( this.convertObjectToJsonBytes( model ) )
            .accept( APPLICATION_JSON_UTF8_VALUE ) )
            .andReturn();

        Map<String,Object> json =
            new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);


        model.put( "_id", json.get( "_id" ) );
        restBusMockMvc.perform( put( "/api/model/" + metaModel.getName() + "/" + json.get( "_id" ))
            .contentType( contentType )
            .content( this.convertObjectToJsonBytes( model ) )
            .accept( APPLICATION_JSON_UTF8_VALUE ) )
            .andDo( print() )
            .andExpect( status().isOk() )
            .andExpect( content().contentType( APPLICATION_JSON_UTF8_VALUE ) )
            .andDo( print() )
            .andExpect( jsonPath( "$._id" ).value( json.get( "_id" ) ) )
            .andExpect( jsonPath( "$.name" ).value( model.get( "name" ) ))
            .andExpect( jsonPath( "$.price" ).value( model.get( "price" ) ))
            .andExpect( jsonPath( "$.builtAt" ).value( model.get( "builtAt" ) ))
            .andExpect( jsonPath( "$.number" ).value( model.get( "number" ) ));
    }

    @Test
    public void shouldDeleteModel_Successfully()
        throws Exception {

        MetaModel metaModel = createMetaModel("");
        metaModelService.create( metaModel );

        Map model = createModel();

        MvcResult result = restBusMockMvc.perform( post( "/api/model/" + metaModel.getName() )
            .contentType( contentType )
            .content( this.convertObjectToJsonBytes( model ) )
            .accept( APPLICATION_JSON_UTF8_VALUE ) )
            .andDo( print() )
            .andExpect( status().isOk() )
            .andExpect( content().contentType( APPLICATION_JSON_UTF8_VALUE ) )
            .andReturn();

        Map<String,Object> json =
            new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);

        restBusMockMvc.perform( delete( "/api/model/" + metaModel.getName() + "/" + json.get( "_id" ) )
            .contentType( contentType )
            .content( this.convertObjectToJsonBytes( model ) )
            .accept( APPLICATION_JSON_UTF8_VALUE ) )
            .andDo( print() )
            .andExpect( status().isOk() );
    }

    @Test
    public void shouldFindAllModels_Successfully()
        throws Exception {

        MetaModel metaModel = createMetaModel("");
        metaModelService.create( metaModel );

        for(int i = 0; i < 5; i++) {
            Map model = createModel();
            modelService.save( metaModel.getName(), model );
        }

        restBusMockMvc.perform( get( "/api/model/" + metaModel.getName() )
            .accept( APPLICATION_JSON_UTF8_VALUE ) )
            .andDo( print() )
            .andExpect( status().isOk() )
            .andExpect( content().contentType( APPLICATION_JSON_UTF8_VALUE ) )
            .andExpect( jsonPath( "$.length()" ).value( 5));

    }

    @Test
    public void shouldFindModelById_Successfully()
        throws Exception {

        MetaModel metaModel = createMetaModel("");
        metaModelService.create( metaModel );

        Map model = createModel();

        MvcResult result = restBusMockMvc.perform( post( "/api/model/" + metaModel.getName() )
            .contentType( contentType )
            .content( this.convertObjectToJsonBytes( model ) )
            .accept( APPLICATION_JSON_UTF8_VALUE ) )
            .andDo( print() )
            .andExpect( status().isOk() )
            .andExpect( content().contentType( APPLICATION_JSON_UTF8_VALUE ) )
            .andReturn();

        Map<String,Object> json =
            new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);

        restBusMockMvc.perform( get( "/api/model/" + metaModel.getName() + "/" + json.get("_id") )
            .contentType( contentType )
            .content( this.convertObjectToJsonBytes( model ) )
            .accept( APPLICATION_JSON_UTF8_VALUE ) )
            .andDo( print() )
            .andExpect( status().isOk() )
            .andExpect( content().contentType( APPLICATION_JSON_UTF8_VALUE ) )
            .andExpect( jsonPath( "$.name" ).value( model.get( "name" ) ))
            .andExpect( jsonPath( "$.price" ).value( model.get( "price" ) ))
            .andExpect( jsonPath( "$.number" ).value( model.get( "number" ) ));
    }

}
