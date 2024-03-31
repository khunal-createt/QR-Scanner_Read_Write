package com.qr.scanner.controller;

import com.google.zxing.WriterException;
import com.qr.scanner.entity.StudentsEntity;
import com.qr.scanner.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/QR/Student")
public class StudentQRController {

    @Autowired
    private final StudentService studentService;

    @GetMapping("/getStudentList")
    public ResponseEntity<List<StudentsEntity>> studentsQRDetails() throws IOException, WriterException {
        return ResponseEntity.ok(studentService.getStudents());
    }

    @PostMapping("/saveStudent")
    public ResponseEntity<StudentsEntity> saveStudent(@RequestBody StudentsEntity studenstsEntity){
        return ResponseEntity.ok(studentService.saveStudent(studenstsEntity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentsEntity> findStudentById(@PathVariable("id") Long id){
        return ResponseEntity.ok(studentService.findById(id));
    }
}
