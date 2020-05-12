package setproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Base64;

public class BaseDemo {
    // encode image to base64string
    public static String encodeImage(String imgPath, String savePath) throws Exception{

        // read image from file
        FileInputStream imageStream = new FileInputStream(imgPath);

        // get byte array from image stream
        byte[] data = imageStream.readAllBytes();

        String imageString = Base64.getEncoder().encodeToString(data);

        // write string to a file on the disk , we use this string later to decode the image from base64 to image
        FileWriter fileWriter = new FileWriter(savePath);


        fileWriter.write(imageString);

        // close streams
        fileWriter.close();
        imageStream.close();

        return imageString;

    }

    //decode image from base64 string stored in a file to an image
    public static void decodeImage(String txtPath, String savePath) throws Exception{

        // read from text file
        FileInputStream inputStream = new FileInputStream(txtPath);

        byte[] data = Base64.getDecoder().decode(new String(inputStream.readAllBytes()));

        // Base64.getDecoder().decode(inputStream.readAllBytes());

        FileOutputStream fileOutputStream = new FileOutputStream(savePath);

        // write array of bytes to an image file
        fileOutputStream.write(data);

        // close streams
        fileOutputStream.close();
        inputStream.close();

        File myObj1 = new File(txtPath);
		myObj1.delete();
    }
}




