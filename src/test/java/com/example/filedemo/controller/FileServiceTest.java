package com.example.filedemo.controller;

import com.example.filedemo.model.File;
import com.example.filedemo.repository.FileRepository;
import com.example.filedemo.services.FileService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class FileServiceTest {

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FileService fileService;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetAllFiles() {
        List<File> file = new ArrayList<File>();
        file.add(new File("File1", "file", 0L, "File1 Content", "29-June-2018"));
        file.add(new File("File2", "file", 0L, "File2 Content", "29-June-2018"));
        file.add(new File("File3", "file", 0L, "File3 Content", "29-June-2018"));

        when(fileRepository.findAll()).thenReturn(file);

        List<File> result = fileService.getAllFiles();
        assertEquals(3, result.size());
    }

    @Test
    public void getByIdTest() {
        File file = new File("File1", "file", 0L, "File1 Content", "29-June-2018");
        file.setId(1L);
        when(fileRepository.findById(1L)).thenReturn(Optional.of(file));
        File result = fileService.getFileById(1L);
        assertEquals((Long) 1L, result.getId());
        assertEquals("File1 Content", result.getContent());
        assertEquals("File1", result.getName());
        assertEquals("file", result.getType());
        assertEquals((Long) 0L, result.getParentId());
        // assertEquals("Todo Sample 1", result.get);
        //    assertEquals(true, result.isCompleted());
    }


    @Test
    public void saveToDo() throws IOException {
        File file = new File("File1", "file", 0L, "File1 Content", "29-June-2018");
        when(fileRepository.save(file)).thenReturn(file);
        File result = fileService.storeFile(file);
        result.setId(1L);
        assertEquals((Long) 1L, result.getId());
        assertEquals("File1 Content", result.getContent());
        assertEquals("File1", result.getName());
        assertEquals("file", result.getType());
        assertEquals((Long) 0L, result.getParentId());
        //assertEquals(new Date(), result.getCreatedTime());


    }


    @Test
    public void removeFile() {
        File file = new File("File1", "file", 0L, "File1 Content", "29-June-2018");
        file.setId(1L);
        when(fileRepository.findAll()).thenReturn(Collections.singletonList(file));
        fileService.deleteFile(file.getId());
        Mockito.verify(fileRepository, times(1)).delete(file);
    }


    @Test
    public void updateToDo() throws IOException {
        File file = new File();
        file.setId(1L);
        file.setName("File1");
        when(fileRepository.save(file)).thenReturn(file);
        File result = fileService.updateFile(file.getId(), file);
        assertEquals((Long) 1L, result.getId());
        assertEquals("File1", result.getName());
    }


    @Test
    public void DuplicateToDo() throws IOException {
        File file = new File("File1", "directory", 0L, "File1 Content", "29-June-2018");
        file.setId(1L);
        when(fileRepository.save(file)).thenReturn(file);
        when(fileRepository.findById(file.getId())).thenReturn(Optional.of(file));
        when(fileRepository.findAll()).thenReturn(Collections.singletonList(file));
        List<File> result = fileService.duplicateFile(file.getId());
        Iterator it = result.iterator();
        File f = new File();
        while (it.hasNext()) {
            f = (File) it.next();
            assertEquals((Long) 1L, f.getId());
            assertEquals("File1 Content", f.getContent());
            assertEquals("File1", f.getName());
            assertEquals("file", f.getType());
            assertEquals((Long) 0L, f.getParentId());
            //assertEquals(new Date(), result.getCreatedTime());
        }

    }

//Actually I did a mistake in filling   previous week time sheet. Since I was left with no leaves (casual and earned) and was on loss of pay.

    @Test
    public void moveFileTest() throws IOException {
        File file = new File("File1", "directory", 0L, "File1 Content", "29-June-2018");
        file.setId(1L);
        when(fileRepository.save(file)).thenReturn(file);
        when(fileRepository.findById(file.getId())).thenReturn(Optional.of(file));
        File result = fileService.moveFile(file.getParentId(), file.getId());
        if (result != null) {
            assertEquals((Long) 1L, result.getId());
            assertEquals("File1 Content", result.getContent());
            assertEquals("File1", result.getName());
            assertEquals("file", result.getType());
            assertEquals((Long) 0L, result.getParentId());

        }

    }
}
