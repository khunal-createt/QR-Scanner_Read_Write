package com.qr.scanner.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.qr.scanner.entity.StudentsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class QRCodeGenerator {

//    @Autowired
//    private ResourceLoader resourceLoader;

    static final File dir = new File("D:/Java/QRGenerator/");

    static final String[] EXTENSIONS = new String[]{
            "png" // and other formats you need
    };
    // filter to identify images based on their extensions
    static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };

    public static void generateQR(StudentsEntity studentsEntity) throws WriterException, IOException {
        String qrCodePath = "D:/Java/QRGenerator/";
        String qrCodeName = qrCodePath + studentsEntity.getFirstName()
                + studentsEntity.getId()
                + "-QRCODE.png";
        var qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(
                "ID : " +  studentsEntity.getId() + "\n" +
                "First Name : " + studentsEntity.getFirstName() + "\n" +
                "Last Name : " + studentsEntity.getLastName() + "\n" +
                "Email Id : " + studentsEntity.getEmail() + "\n" +
                "Phone Number : " + studentsEntity.getPhoneNo(),
                BarcodeFormat.QR_CODE, 400, 400);

        Path path = FileSystems.getDefault().getPath(qrCodeName);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }

    public static List<StudentsEntity> qrScan(){
        List<StudentsEntity> students = new ArrayList<>();
        if (dir.isDirectory()) { // make sure it's a directory
            for (final File f : Objects.requireNonNull(dir.listFiles(IMAGE_FILTER))) {
                StudentsEntity studentsEntity = new StudentsEntity();
                try {

                    Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
                    hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

//                    Gson gson = new Gson();
                    String[] qrText = readQrCode(f, hintMap).split("\n");

                    for(String textConversion : qrText){
                        String[] splitMap = textConversion.split(" : ");
                        if(Objects.equals(splitMap[0], "ID")) studentsEntity.setId(Long.parseLong(splitMap[1]));
                        else if(Objects.equals(splitMap[0], "First Name")) studentsEntity.setFirstName(splitMap[1]);
                        else if(Objects.equals(splitMap[0], "Last Name")) studentsEntity.setLastName(splitMap[1]);
                        else if(Objects.equals(splitMap[0], "Email Id")) studentsEntity.setEmail(splitMap[1]);
                        else if (Objects.equals(splitMap[0], "Phone Number")) studentsEntity.setPhoneNo(splitMap[1]);

                    }
                    students.add(studentsEntity);
//                    JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
//                    studentsEntity.setLastName(jsonObject.get("lastName").getAsString());
//                    studentsEntity.setId(jsonObject.get("id").getAsLong());
//                    studentsEntity.setFirstName(jsonObject.get("firstName").toString());
//                    studentsEntity.setEmail(jsonObject.get("email").toString());
//                    studentsEntity.setPhoneNo(jsonObject.get("phoneNo").toString());
                } catch (final IOException | NotFoundException e) {
                    // handle errors here
                }
            }
        }
        return students;
    }

    public static String readQrCode(File filePath, Map hintMap) throws IOException, NotFoundException {
        BinaryBitmap binaryBitmap = new BinaryBitmap(
                new HybridBinarizer(
                    new BufferedImageLuminanceSource(ImageIO.read(
                        new FileInputStream(filePath.toString())))
        ));
        Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap, hintMap);
        return qrCodeResult.getText();
    }
}
