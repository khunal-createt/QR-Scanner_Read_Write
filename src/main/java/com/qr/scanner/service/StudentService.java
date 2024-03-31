package com.qr.scanner.service;

import com.google.zxing.WriterException;
import com.qr.scanner.entity.StudentsEntity;

import java.io.IOException;
import java.util.List;

public interface StudentService {

    public List<StudentsEntity> getStudents() throws IOException, WriterException;

    public StudentsEntity saveStudent(StudentsEntity studenstsEntity);

    public StudentsEntity findById(Long Id);
}
