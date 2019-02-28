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

    // Get all the files
    @RequestMapping("/files")
    public List<File> getAllFiles() {
        System.out.println(fileservice.getAllFiles());
        return fileservice.getAllFiles();
    }

    //   Get a file by id
    @RequestMapping(value = "/files/{id}", produces = {"application/json"}, method = RequestMethod.GET)
    public File getFileById(@PathVariable Long id) {
        System.out.println(id);
        return fileservice.getFileById(id);
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

    // copy  a file

    @RequestMapping(method = RequestMethod.POST, value = "/files/{id}/copy")
    public File copyFile(@PathVariable Long id) {
        return fileservice.copyFile(id);
    }


    // move a file

    @RequestMapping(method = RequestMethod.PATCH, value = "/files/{id}/move", consumes = "application/json", produces = "application/json")
    //, produces = "text/plain")//, consumes= MediaType.MULTIPART_FORM_DATA_VALUE ,  produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public File moveFile(@RequestBody File parentId, @PathVariable Long id) throws IOException {
        return fileservice.moveFile(parentId , id);
    }

    // cut a file
    @RequestMapping(method = RequestMethod.DELETE, value = "/files/{id}/cut")
    public void cutFile(@PathVariable Long id) {
       fileservice.cutFile(id);
    }

    //paste a file

    @RequestMapping(method = RequestMethod.POST, value = "/files/paste", consumes = "application/json", produces = "application/json")
    //, produces = "text/plain")//, consumes= MediaType.MULTIPART_FORM_DATA_VALUE ,  produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public File pasteFile(@RequestBody File file) throws IOException {
        return fileservice.pasteFile(file);
    }
}