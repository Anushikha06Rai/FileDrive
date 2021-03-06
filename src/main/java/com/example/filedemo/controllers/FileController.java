package com.example.filedemo.controllers;

import com.example.filedemo.model.File;
import com.example.filedemo.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class FileController {

    @Autowired
    private FileService fileservice;


    // Get  files  by type
    @RequestMapping("/files/{type}")
    public List<File> getFilesByType(@PathVariable String type) {
        return fileservice.getFilesByType(type);
    }

    // Get all  files
    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public List<File> getAllFiles() {
        return fileservice.getAllFiles();
    }



    //   Get a file by id
    @RequestMapping(value = "/files/{id}", produces = {"application/json"}, method = RequestMethod.GET)
    public File getFileById(@PathVariable Long id) {
        System.out.println(id);
        return fileservice.getFileById(id);
    }

    // Get File Contents
    @RequestMapping("/files/{type}/{id}")
    public List<File> getParticualarFileByType(@PathVariable String type, @PathVariable Long id) {
        return fileservice.getFileContents(type, id);
    }

    // Create a file
    @RequestMapping(method = RequestMethod.POST, value = "/files", consumes = "application/json", produces = "application/json")
    //, produces = "text/plain")//, consumes= MediaType.MULTIPART_FORM_DATA_VALUE ,  produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public File createFile(@RequestBody File file) throws IOException {
        return fileservice.storeFile(file);
    }

    // Update  a file

    @RequestMapping(method = RequestMethod.PUT, value = "/files/{id}", consumes = "application/json", produces = "application/json")
    public File updateFile(@RequestBody File file, @PathVariable Long id) {
        return fileservice.updateFile(id, file);
    }

    // delete  a file

    @RequestMapping(method = RequestMethod.DELETE, value = "/files/{id}")
    public String deleteFile(@PathVariable Long id) {
        return fileservice.deleteFile(id);
    }

    //   creating a duplicate copy   a file
//
//    @RequestMapping(method = RequestMethod.POST, value = "/files/{id}/duplicate")
//    public List<File> duplicateFile(@RequestBody File file, @PathVariable Long id) {
//        return fileservice.duplicateFile(file, id);
//    }

    @RequestMapping(method = RequestMethod.POST, value = "/files/{id}/duplicate")
    public List<File> duplicateFile(@PathVariable Long id) {
        return fileservice.duplicateFile(id);
    }


    // move a file
    @RequestMapping(method = RequestMethod.PATCH, value = "/files/{id}/move", consumes = "application/json", produces = "application/json")
    //, produces = "text/plain")//, consumes= MediaType.MULTIPART_FORM_DATA_VALUE ,  produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public File moveFile(@RequestBody Long parentId, @PathVariable Long id) throws IOException {
        return fileservice.moveFile(parentId, id);
    }
}