package com.example.filedemo.controller;

import com.example.filedemo.controllers.FileController;
import com.example.filedemo.services.FileService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@WebAppConfiguration
@Test
public class AbstractTest extends AbstractTestNGSpringContextTests {
    protected MockMvc mockMvc;
    @Mock
    FileService fileService;
    @InjectMocks
    FileController fileController;
 //   @Autowired
  //  WebApplicationContext webApplicationContext;

    @BeforeMethod
    protected void setUp() {
        //  mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(fileController).build();
    }




    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    @Test
    public void empty() {

    }

//    @Test
//    public void getFileByType() throws Exception {
//        String uri = "/files/type";
//        File file = new File();
//        file.setId(1L);
//        file.setContent("Java");
//        file.setParentId(0L);
//        file.setType("file");
//        file.setName("JAVA File");
//
//        String inputJson = mapToJson(file);
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
//                .accept(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        assertEquals(200, status);
//        String content = mvcResult.getResponse().getContentAsString();
//        assertTrue(content.isEmpty() == false);
//
//
//    }
}
