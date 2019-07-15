package com.example.filedemo.services;

import com.example.filedemo.exception.InvalidFileNameException;
import com.example.filedemo.exception.InvalidPasteException;
import com.example.filedemo.model.File;
import com.example.filedemo.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class FileService {

    List<File> f = new ArrayList<>();
    @Autowired
    private FileRepository filerepository;

    // Get  files by its type
    public List<File> getFilesByType(String type) {
        File df;
        List<File> files = new ArrayList<>();
        List<File> f = filerepository.findAll();//.forEach(files::add);
        Iterator iterator = f.iterator();
        while (iterator.hasNext()) {
            df = (File) iterator.next();
            if (df.type.equals(type)) {
                files.add(df);
            }

        }
        return files;
    }


    public List<File> getAllFiles() {
        File df;
        List<File> files = new ArrayList<>();
        List<File> f = filerepository.findAll();//.forEach(files::add);
        Iterator iterator = f.iterator();
        while (iterator.hasNext()) {
            df = (File) iterator.next();
            files.add(df);
        }
        return files;
    }


    //   Get a file by id
    public File getFileById(Long id) {
        return filerepository.findById(id).orElse(null);
    }


    // Get File Contents
    public List<File> getFileContents(String type, Long id) {
        File f1;
        List<File> files = new ArrayList<>();
        List<File> f = filerepository.findAll();//.forEach(files::add);
        Iterator iterator = f.iterator();
        while (iterator.hasNext()) {
            f1 = (File) iterator.next();
            if ((f1.getParentId() == id)) {
                files.add(f1);
            }
        }
        return files;
    }

    // Create a file
    public File storeFile(File file) throws IOException {
        if (file.name.contains("..")) {
            throw new InvalidFileNameException("Sorry! Filename contains invalid path sequence " + file.name);
        }
        Date date = new Date();
        file.setCreatedTime(date.toString());
        List<File> files = filerepository.findAll();
        Iterator itr = files.iterator();
        File fl;
        while (itr.hasNext()) {
            fl = (File) itr.next();
            if ((file.getParentId() == fl.getId()) && (fl.getType().equals("directory"))) {
                return filerepository.save(file);
            } else if ((file.getParentId() == fl.getId()) && (fl.getType().equals("file"))) {
                throw new InvalidFileNameException("Can't create" + file.getName());
            }
        }
        f.clear();
        file.setParentId(0L);
        return filerepository.save(file);
    }


    // Update  a file
    public File updateFile(Long id, File resource) {
        f.clear();
        return filerepository.save(resource);
    }


    // delete  a file
    public String deleteFile(Long id) {
        File file = filerepository.findById(id).orElse(null);
        if (file.type.equals("file")) {
            filerepository.delete(file);
        } else if (file.type.equals("directory")) {
            List<File> f = filerepository.findAll();//.forEach(files::add);
            Iterator itr = f.iterator();
            File df;
            File folder = filerepository.findById(id).orElse(null);
            Long fid = folder.getId();
            if ((folder.type.equals("directory")) && (folder.getParentId() == 0)) {
                while (itr.hasNext()) {
                    df = (File) itr.next();
                    if (df.getParentId() == fid) {
                        filerepository.delete(df);
                    }
                    filerepository.delete(folder);
                }
            }
        }
        f.clear();
        return "File Deleted";
    }

    // creating a duplicate copy of a file
    public List<File> duplicateFile(File target, Long fileId) {
        File file = filerepository.findById(fileId).orElse(null);
        List<File> f = filerepository.findAll();//.forEach(files::add);
        Iterator itr = f.iterator();
        File df;
        File copyFile = new File();
        List<File> copiedFilesList = new ArrayList<>();
        while (itr.hasNext()) {
            df = (File) itr.next();
            if ((df.getId() == target.getParentId()) && (df.getType().equals("file"))) {
                throw new InvalidFileNameException("Invalid ParentId : " + target.getParentId());
            } else if ((df.getId() == target.getParentId()) && (df.getType().equals("directory"))) {
                //       df.setContent(df.getContent() + "|" + fileId);
                //     filerepository.save(df);

                // If file to be copy is a file
                if (file.getType().equals("file")) {
                    copyFile.setName(file.getName() + " Copy");
                    copyFile.setContent(file.getContent());
                    copyFile.setCreatedTime(file.getCreatedTime());
                    copyFile.setType(file.getType());
                    copyFile.setParentId(target.getParentId());
                    copiedFilesList.add(copyFile);
                    filerepository.save(copyFile);
                } else if (file.getType().equals("directory")) {    // If file to be copy is a directory
                    directory(file, target.getParentId());
                }
            }
        }
        return copiedFilesList;
    }


    public List<File> directory(File file, Long parentId) { // If file to be copy is a directory
        List<File> copiedFilesList = new ArrayList<>();
        File copyFile = new File();
        copyFile.setName(file.getName() + " Copy");
        copyFile.setContent(file.getContent());
        Date date = new Date();
        copyFile.setCreatedTime(date.toString());
        copyFile.setType(file.getType());
        copyFile.setParentId(parentId);
        copiedFilesList.add(copyFile);
        filerepository.save(copyFile);

        // copying directory descendants
        List<File> allFiles = filerepository.findAll();//.forEach(files::add);
        Iterator iterator = allFiles.iterator();
        File tempFile;
        File duplicateFile;
        while (iterator.hasNext()) {
            tempFile = (File) iterator.next();
            //If descendant is a file
            if ((file.getId() == tempFile.getParentId()) && (tempFile.getType().equals("file"))) {
                duplicateFile = new File();
                duplicateFile.setName(tempFile.getName());
                duplicateFile.setContent(tempFile.getContent());
                duplicateFile.setCreatedTime(tempFile.getCreatedTime());
                duplicateFile.setType(tempFile.getType());
                duplicateFile.setParentId(copyFile.getId());
                copiedFilesList.add(duplicateFile);
                filerepository.save(duplicateFile);

                // If descendant is a  directory
            } else if ((copyFile.getId() == tempFile.getParentId()) && (tempFile.getType().equals("directory"))) {
                directory(tempFile, copyFile.getId());  // recursive call to a function if file is a directory
            }
        }
        f.clear();
        return copiedFilesList;
    }

    // Move  a file

    public File moveFile(File target, Long fileId) {
        File file = filerepository.findById(fileId).orElse(null);
        List<File> f = filerepository.findAll();//.forEach(files::add);
        Iterator itr = f.iterator();
        File df;
        while (itr.hasNext()) {
            df = (File) itr.next();
            if ((df.getId() == target.getParentId()) && (df.getType().equals("file"))) {
                throw new InvalidFileNameException("Invalid ParentId : " + target.getParentId());
            } else if ((df.getId() == target.getParentId()) && (df.getType().equals("directory"))) {
                df.setContent(df.getContent() + "|" + fileId);
                filerepository.save(df);
                file.setParentId(target.getParentId());
            }
        }
        f.clear();
        return filerepository.save(file);
    }
}