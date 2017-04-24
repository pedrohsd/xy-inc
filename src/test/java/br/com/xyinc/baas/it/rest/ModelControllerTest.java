package br.com.xyinc.baas.it.rest;


import br.com.xyinc.baas.domain.MetaModel;
import br.com.xyinc.baas.it.AbstractRestIT;
import br.com.xyinc.baas.rest.ModelController;
import br.com.xyinc.baas.service.MetaModelService;
import br.com.xyinc.baas.service.ModelService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
            .andExpect( content().contentType( APPLICATION_JSON_UTF8_VALUE ) );
    }

}
