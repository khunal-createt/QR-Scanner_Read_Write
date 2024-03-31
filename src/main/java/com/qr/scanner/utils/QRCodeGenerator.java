package com.qr.scanner.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.qr.scanner.entity.StudentsEntity;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QRCodeGenerator {

    public static void generateQR(StudentsEntity studentsEntity) throws WriterException, IOException {
        String qrCodePath = "D:/Java/QRGenerator/";
        String qrCodeName = qrCodePath + studentsEntity.getFirstName()
                + studentsEntity.getId()
                + "-QRCODE.png";
        var qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(
                "ID : " +  studentsEntity.getId() + "/n" +
                "First Name : " + studentsEntity.getFirstName() + "/n" +
                "Last Name : " + studentsEntity.getLastName() + "/n" +
                "Email Id : " + studentsEntity.getEmail() + "/n" +
                "Phone Number : " + studentsEntity.getPhoneNo(),
                BarcodeFormat.QR_CODE, 400, 400);

        Path path = FileSystems.getDefault().getPath(qrCodeName);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }
}
