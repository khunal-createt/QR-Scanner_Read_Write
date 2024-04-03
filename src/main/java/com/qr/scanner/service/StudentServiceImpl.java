package com.qr.scanner.service;

import com.google.zxing.WriterException;
import com.qr.scanner.entity.StudentsEntity;
import com.qr.scanner.repository.StudentRepository;
import com.qr.scanner.utils.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<StudentsEntity> getStudents() throws IOException, WriterException {
        List<StudentsEntity> students = studentRepository.findAll();
        if(!students.isEmpty()){
            for(StudentsEntity student : students)
                QRCodeGenerator.generateQR(student);
        }
        return students;
    }

    public StudentsEntity saveStudent(StudentsEntity studenstsEntity){
        return studentRepository.save(studenstsEntity);
    }

    public StudentsEntity findById(Long id){
        return studentRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public List<StudentsEntity> getQrData(){
        return QRCodeGenerator.qrScan();
    }
}
