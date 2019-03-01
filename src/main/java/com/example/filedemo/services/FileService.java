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

    // Get all the files.
    public List<File> getAllFiles() {
        File df;
        List<File> files = new ArrayList<>();
        List<File> f = filerepository.findAll();//.forEach(files::add);
        Iterator iterator = f.iterator();
        while (iterator.hasNext()) {
            df = (File) iterator.next();
            if (df.type.equals("file")) {
                files.add(df);
            }
        }
        return files;
    }


    //   Get a file by id
    public File getFileById(Long id) {
        return filerepository.findById(id).orElse(null);
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


    // copy a file
    public File copyFile(Long id) {
        File file = filerepository.findById(id).orElse(null);
        File copyFile = new File();
        copyFile.setName(file.getName() + " Copy");
        copyFile.setContent(file.getContent());
        copyFile.setCreatedTime(file.getCreatedTime());
        copyFile.setType(file.getType());
        copyFile.setParentId(file.getParentId());
        f.clear();
        return filerepository.save(copyFile);
    }


    // move a file
    public File moveFile(File parentId, Long fileId) {
        File file = filerepository.findById(fileId).orElse(null);
        List<File> f = filerepository.findAll();//.forEach(files::add);
        Iterator itr = f.iterator();
        File df;
        while (itr.hasNext()) {
            df = (File) itr.next();
            if ((df.getId() == parentId.getParentId()) && (df.getType().equals("file"))) {
                throw new InvalidFileNameException("Invalid ParentId : " + parentId.getParentId());
            } else if ((df.getId() == parentId.getParentId()) && (df.getType().equals("directory"))) {
                file.setParentId(parentId.getParentId());
            }
        }
        f.clear();
        return filerepository.save(file);
    }

    // cut a file
    public void cutFile(Long id) {
        File file = filerepository.findById(id).orElse(null);
        f.add(file);
    }

    public File pasteFile(File file) throws IOException {
        if (f.isEmpty()) {
            throw new InvalidPasteException("Invalid Paste operation ");
        } else {
            List<File> allFiles = filerepository.findAll();//.forEach(files::add);
            Iterator allFilesItr = allFiles.iterator();
            File buffer, df, temp;

            Iterator bufferList = f.iterator();
            while (bufferList.hasNext()) {
                buffer = (File) bufferList.next();
                filerepository.delete(buffer);
                while (allFilesItr.hasNext()) {
                    df = (File) allFilesItr.next();
                    if ((df.getId() == file.getParentId()) && (df.getType().equals("file"))) {
                        throw new InvalidFileNameException("Invalid ParentId : " + file.getParentId());
                    } else if ((df.getId() == file.getParentId()) && (df.getType().equals("directory"))) {
                        buffer.setParentId(file.getParentId());
                        filerepository.save(buffer);
                    }
                }
            }
            return new File("File pasted");
        }
    }
}