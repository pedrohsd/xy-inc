package br.com.xyinc.baas.it.rest;


import br.com.xyinc.baas.domain.MetaModel;
import br.com.xyinc.baas.it.AbstractRestIT;
import br.com.xyinc.baas.rest.MetaModelController;
import br.com.xyinc.baas.service.MetaModelService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class MetaModelControllerTest extends AbstractRestIT {

    @Autowired
    private MetaModelService metaModelService;

    private MockMvc restBusMockMvc;

    @Before
    public void setup() {

        MetaModelController busResource = new MetaModelController();
        ReflectionTestUtils.setField( busResource, "metaModelService", metaModelService );
        this.restBusMockMvc = MockMvcBuilders.standaloneSetup( busResource ).build();
    }

    @Test
    public void shouldReturnAllMetaModels_Successfully()
        throws Exception {

        MetaModel metaModel = createMetaModel("");
        metaModelService.create( metaModel );

        restBusMockMvc.perform( get( "/api/metamodel" )
            .accept( APPLICATION_JSON_UTF8_VALUE ) )
            .andDo( print() )
            .andExpect( status().isOk() )
            .andExpect( content().contentType( APPLICATION_JSON_UTF8_VALUE ) )
            .andExpect(jsonPath("$[0].id").exists())
            .andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    public void shouldCreateMetaModel_Successfully() throws Exception {

        MetaModel metaModel = createMetaModel("");

        restBusMockMvc.perform( post( "/api/metamodel" )
            .contentType( contentType )
            .content( this.convertObjectToJsonBytes( metaModel ) )
            .accept( MediaType.APPLICATION_JSON_UTF8_VALUE ) )
            .andExpect( status().is( HttpStatus.CREATED.value() ) )
            .andExpect( content().contentType( MediaType.APPLICATION_JSON_UTF8_VALUE ) )
            .andDo( print() )
            .andExpect( jsonPath( "$.id" ).exists() )
            .andExpect( jsonPath( "$.name" ).value( metaModel.getName() ) )
            .andExpect( jsonPath( "$.metaAttributes" ).exists() )
            .andExpect( jsonPath( "$.metaAttributes", hasSize(5) ) );
    }

    @Test
    public void shouldReturnExistingMetaModel_Successfully() throws Exception {

        MetaModel metaModel = createMetaModel("");
        metaModelService.create( metaModel );

        restBusMockMvc.perform( get( "/api/metamodel/" + metaModel.getId() )
            .contentType( contentType )
            .content( this.convertObjectToJsonBytes( metaModel ) )
            .accept( MediaType.APPLICATION_JSON_UTF8_VALUE ) )
            .andExpect( status().isOk() )
            .andExpect( content().contentType( MediaType.APPLICATION_JSON_UTF8_VALUE ) )
            .andDo( print() )
            .andExpect( jsonPath( "$.id" ).value( metaModel.getId() ) )
            .andExpect( jsonPath( "$.name" ).value( metaModel.getName() ) )
            .andExpect( jsonPath( "$.metaAttributes" ).exists() )
            .andExpect( jsonPath( "$.metaAttributes", hasSize(5) ) );
    }

    @Test
    public void shouldReturnConflict_WhenMetaModelAlreadyExists() throws Exception {

        MetaModel metaModel = createMetaModel("");
        metaModelService.create( metaModel );

        restBusMockMvc.perform( post( "/api/metamodel" )
            .contentType( contentType )
            .content( this.convertObjectToJsonBytes( metaModel ) )
            .accept( MediaType.APPLICATION_JSON_UTF8_VALUE ) )
            .andExpect( status().isConflict() )
            .andDo( print() );
    }

    @Test
    public void shouldDeleteMetaModel_Successfully() throws Exception {

        MetaModel metaModel = createMetaModel("");
        metaModelService.create( metaModel );

        restBusMockMvc.perform( delete( "/api/metamodel/" + metaModel.getId() )
            .contentType( contentType )
            .content( this.convertObjectToJsonBytes( metaModel ) )
            .accept( MediaType.APPLICATION_JSON_UTF8_VALUE ) )
            .andExpect( status().isOk() )
            .andExpect( content().contentType( MediaType.APPLICATION_JSON_UTF8_VALUE ) )
            .andDo( print() );

        restBusMockMvc.perform( get( "/api/metamodel/" + metaModel.getId() )
            .contentType( contentType )
            .content( this.convertObjectToJsonBytes( metaModel ) )
            .accept( MediaType.APPLICATION_JSON_UTF8_VALUE ) )
            .andExpect( status().isNotFound() )
            .andDo( print() );
    }

}
