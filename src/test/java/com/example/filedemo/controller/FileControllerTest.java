package com.example.filedemo.controller;

import com.example.filedemo.model.File;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testng.Assert.assertEquals;

@WebAppConfiguration

public class FileControllerTest extends AbstractTest {

//    @Override
//    @BeforeClass
//    public void setUp() {
//        super.setUp();
//    }



    @Test
    public void getAllFiles() throws Exception {
        File file = new File();
        file.setId(1L);
        file.setContent("Java");
        file.setParentId(0L);
        file.setType("file");
        file.setName("JAVA File");
        when(fileService.getAllFiles()).thenReturn((Collections.singletonList(file)));
        mockMvc.perform(get("/files"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("JAVA File"))
                .andExpect(jsonPath("$[0].content").value("Java"))
                .andExpect(jsonPath("$[0].type").value("file"))
                .andExpect(jsonPath("$[0].parentId").value("0"));
    }



    @Test
    public void getFileById() throws Exception {
        String uri = "/files/2";
        File file = new File();
        file.setId(1L);
        file.setContent("Java");
        file.setParentId(0L);
        file.setType("file");
        file.setName("JAVA File");

        String inputJson = super.mapToJson(file);
        MvcResult mvcResult = mockMvc.perform(get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);


    }

//
//    @Test
//    public void getFileByType() throws Exception {
//        String uri = "/files/file";
//        File file = new File();
//        file.setId(1L);
//        file.setContent("Java");
//        file.setParentId(0L);
//        file.setType("file");
//        file.setName("JAVA File");
//        String inputJson = super.mapToJson(file);
//        MvcResult mvcResult = mockMvc.perform(get(uri)
//                .accept(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
//        int status = mvcResult.getResponse().getStatus();
//        assertEquals(200, status);
//        String content = mvcResult.getResponse().getContentAsString();
//        assertTrue(content.isEmpty() == false);
//       }

    @Test
    public void createFile() throws Exception {

        File file = new File();
        file.setId(1L);
        file.setContent("Java");
        file.setParentId(0L);
        file.setType("file");      file.setId(1L);
        file.setName("JAVA File");
        when(fileService.storeFile(argThat(file1 -> file1.id.equals(file.id)))).thenReturn(file);
        mockMvc.perform(post("/files").contentType("application/json").content(new ObjectMapper().writeValueAsString(file)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value("JAVA File"))
                .andExpect(jsonPath("content").value("Java"))
                .andExpect(jsonPath("type").value("file"))
                .andExpect(jsonPath("parentId").value(0L));
    }


    @Test
    public void deleteFile() throws Exception {
        String uri = "/files/2";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        //  assertEquals(content, "Product is deleted successsfully");
    }

    @Test
    public void updateAFile() throws Exception {
        String uri = "/files/2";
        File file = new File();
        file.setName("New file it is....... ");
        String inputJson = super.mapToJson(file);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        //      assertEquals(content, "Product is updated successsfully");
    }


//    @Test
//    public void getAllFiles() throws Exception {
//        String uri = "/files";
//        File file = new File();
//        file.setId(1L);
//        file.setContent("Java");
//        file.setParentId(0L);
//        file.setType("file");
//        file.setName("JAVA File");
//
//        String inputJson = super.mapToJson(file);
//        MvcResult mvcResult = mvc.perform(get(uri)
//                .accept(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        assertEquals(200, status);
//        String content = mvcResult.getResponse().getContentAsString();
//        File[] fileList = super.mapFromJson(content, File[].class);
//        assertTrue(fileList.length > 0);
//    }
//
//
//    @Test
//    public void updateFile() throws Exception {
//        String uri = "/files/2";
//        File file = new File();
//        file.setName("New file it is");
//
//        when(fileService.updateFile(2L , file)).thenReturn(file);
//        mockMvc.perform(put("/files/id").contentType("application/json"))
//                .andDo(print())
//
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").value(2L))
//                .andExpect(jsonPath("$[0].name").value("New file it is"))
//                .andExpect(jsonPath("$[0].content").value("Java"))
//                .andExpect(jsonPath("$[0].type").value("file"))
//                .andExpect(jsonPath("$[0].parentId").value("0"));
//
//
//    }
//
//


//        String content = mvcResult.getResponse().getContentAsString();
//        assertTrue(content.isEmpty() == false);
//        //        String uri = "/files/2";
////        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
////                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
////
////        int status = mvcResult.getResponse().getStatus();
////        assertEquals(200, status);
////        String content = mvcResult.getResponse().getContentAsString();
////        File[] fileList = super.mapFromJson(content, File[].class);
////        assertTrue(fileList.length == 0);
//
    //  }
//


    @Test
    public void moveFile() throws Exception {
        String uri = "/files/2/move";
        File file = new File();
        file.setName("New file it is....... ");
        String inputJson = super.mapToJson(file);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        //      assertEquals(content, "Product is updated successsfully");
    }

    // RequestBody in content
//    @Test
//    public void testMoveFile() throws Exception {
//        File file = new File();
//        file.setContent("Java");
//        mockMvc.perform(patch("/files/1/move").contentType("application/json").content("10")).andExpect(status().isOk());
//        verify(fileService, times(1)).moveFile(file, 1L);
//    }

    @Test
    public void testDuplicateFile() throws Exception {
        String uri = "/files/1/duplicate";
        File file = new File();
        file.setId(1L);
        file.setContent("Java");
        file.setParentId(0L);
        file.setType("file");
        file.setName("JAVA File");
        String inputJson = super.mapToJson(file);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        //   assertEquals(content, "Product is created successfully");
    }


//    @Test
//    public void createProduct() throws Exception {
//        String uri = "/files";
//        File file = new File();
//        file.setId(1L);
//        file.setContent("Java");
//        file.setParentId(0L);
//        file.setType("file");
//        file.setName("JAVA File");
//
//        String inputJson = super.mapToJson(file);
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(inputJson)).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        assertEquals(200, status);
//        String content = mvcResult.getResponse().getContentAsString();
//        //   assertEquals(content, "file is created successfully");
//    }
//
//
//    @Test
//    public void getAllFiles() throws Exception {
//        String uri = "/files";
//        File file = new File();
//        file.setId(1L);
//        file.setContent("Java");
//        file.setParentId(0L);
//        file.setType("file");
//        file.setName("JAVA File");
//
//        String inputJson = super.mapToJson(file);
//        MvcResult mvcResult = mvc.perform(get(uri)
//                .accept(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        assertEquals(200, status);
//        String content = mvcResult.getResponse().getContentAsString();
//        File[] fileList = super.mapFromJson(content, File[].class);
//        assertTrue(fileList.length > 0);
//    }
//
//

}
//
//
//    @Test
//    public void deleteFile() throws Exception {
//        String uri = "/files/2";
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
//        int status = mvcResult.getResponse().getStatus();
//        assertEquals(200, status);
//        String content = mvcResult.getResponse().getContentAsString();
//        //  assertEquals(content, "Product is deleted successsfully");
//    }
//
//
//    @Test
//    public void getFileById() throws Exception {
//        String uri = "/files";
//        File file = new File();
//        file.setId(1L);
//        file.setContent("Java");
//        file.setParentId(0L);
//        file.setType("file");
//        file.setName("JAVA File");
//
//        String inputJson = super.mapToJson(file);
//        MvcResult mvcResult = mvc.perform(get(uri)
//                .accept(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        assertEquals(200, status);
//        String content = mvcResult.getResponse().getContentAsString();
//        assertTrue(content.isEmpty() == false);
//        //        String uri = "/files/2";
////        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
////                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
////
////        int status = mvcResult.getResponse().getStatus();
////        assertEquals(200, status);
////        String content = mvcResult.getResponse().getContentAsString();
////        File[] fileList = super.mapFromJson(content, File[].class);
////        assertTrue(fileList.length == 0);
//
//
//    }
//
//
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
//        String inputJson = super.mapToJson(file);
//        MvcResult mvcResult = mvc.perform(get(uri)
//                .accept(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        assertEquals(200, status);
//        String content = mvcResult.getResponse().getContentAsString();
//        assertTrue(content.isEmpty() == false);
//
//
//    }
//
//
//}


//@RunWith(SpringJUnit4ClassRunner.class)
////@SpringBootTest(classes = FileController.class)
////@SpringApplicationConfiguration(classes = FileController.class)
//@ContextConfiguration(classes = MockServletContext.class)
//@WebAppConfiguration
//
//public class FileControllerTest {
//
//    protected MockMvc mvc;
//    @InjectMocks
//    FileController fileController;
//    @Mock
//    FileService fileservice;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        mvc = standaloneSetup(fileController).build();
//    }


//
//
//    @Test
//    public void createProduct() throws Exception {
//
//        File file = new File();
//        file.setId(1L);
//        file.setContent("Java");
//        file.setParentId(0L);
//        file.setType("file");
//        file.setName("JAVA File");
//        //fileservice.get("name").thenR("sfs");
//        when(fileservice.storeFile(any())).thenReturn(file);
//        mvc.perform(post("/files").contentType("application/json").content(new ObjectMapper().writeValueAsBytes(new File())))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("id").value(1))
//                .andExpect(jsonPath("content").value("Java"))
//                .andExpect(jsonPath("name").value("JAVA File"))
//                .andExpect(jsonPath("parentId").value(0L))
//                .andExpect(jsonPath("type").value("file"));
//    }
//    @Test
//    public void getFileById() throws Exception {
//        File file = new File();
//        file.setId(1L);
//        file.setContent("Java");
//        file.setParentId(0L);
//        file.setType("file");
//        file.setName("JAVA File");
//        //fileservice.get("name").thenR("sfs");
//        when(fileservice.getFileById(7L)).thenReturn(file);
//        mvc.perform(get("/files/7"))//.contentType("application/json").content(new ObjectMapper().writeValueAsBytes(new File())))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("id").value(1))
//                .andExpect(jsonPath("content").value("Java"))
//                .andExpect(jsonPath("name").value("JAVA File"))
//                .andExpect(jsonPath("parentId").value(0L))
//                .andExpect(jsonPath("type").value("file"));
//
//
//    }
//
//}