package com.telegram.qrcode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public class ReadQRCodeFromFile {
    public static void main(String... args) {
        String qrCodeFileName = "";
        File file = new File(qrCodeFileName);

        QRCodeReader qrCodeReader = new QRCodeReader();
        String encodedContent = qrCodeReader.readQRCode(file);

        System.out.println(encodedContent);
    }
    public static String getQrcode(String qrCodeFileName) {
        File file = null;

        try {
            URL url = new URL(qrCodeFileName);
            BufferedImage img = ImageIO.read(url);
             file = new File("downloaded.jpg");
            boolean jpg = ImageIO.write(img, "jpg", file);
            QRCodeReader qrCodeReader = new QRCodeReader();
            String encodedContent = qrCodeReader.readQRCode(file);

        if(encodedContent == null){
            System.out.println("Non trovato");
        }
        else{
            System.out.println("Trovato");
        }

            return encodedContent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}