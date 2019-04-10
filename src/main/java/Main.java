import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static long startTime;
    private static long endTime;
    private static ImageInfo imageInfo;

    public static void main(String[] args) throws IOException {
        List<String> fileFormats = Arrays.asList("jpg", "png", "bmp");

        for (String fileFormat : fileFormats) {
            File outputFile = new File("C:\\img." + fileFormat);
            BufferedImage image = ImageIO.read(Main.class.getResource("railway." + fileFormat));
            imageInfo = new ImageInfo(image, fileFormat, outputFile);

            imageIOWriteImage();
            byteArrayWriteImage();
            bufferChannelWriteImage();
            filesWriteImage();
        }
    }

    private static void imageIOWriteImage() throws IOException {
        timerStart();

        ImageIO.write(imageInfo.image, imageInfo.fileFormat, imageInfo.outputFile);

        timerEnd("imageIOWriteImage      ");
    }

    private static void byteArrayWriteImage() throws IOException {
        timerStart();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imageInfo.image, imageInfo.fileFormat, baos);
        baos.flush();
        byte[] imageBytes = baos.toByteArray();
        baos.close();

        FileOutputStream outputStream = new FileOutputStream(imageInfo.outputFile);
        outputStream.write(imageBytes);
        outputStream.close();

        timerEnd("byteArrayWriteImage    ");
    }

    private static void filesWriteImage() throws IOException {
        timerStart();

        ByteArrayOutputStream baos = new ByteArrayOutputStream(); // image to byte[]
        ImageIO.write(imageInfo.image, imageInfo.fileFormat, baos);
        baos.flush();
        byte[] imageBytes = baos.toByteArray();
        baos.close();

        Files.write(imageInfo.outputFile.toPath(), imageBytes);

        timerEnd("filesWriteImage        ");
    }

    private static void bufferChannelWriteImage() throws IOException {
        timerStart();

        ByteArrayOutputStream baos = new ByteArrayOutputStream(); // image to byte[]
        ImageIO.write(imageInfo.image, imageInfo.fileFormat, baos);
        baos.flush();
        byte[] imageBytes = baos.toByteArray();
        baos.close();

        RandomAccessFile stream = new RandomAccessFile(imageInfo.outputFile, "rw"); // write to file with buffer/channel
        FileChannel channel = stream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(imageBytes.length);
        buffer.put(imageBytes);
        buffer.flip();
        channel.write(buffer);
        stream.close();
        channel.close();

        timerEnd("bufferChannelWriteImage");
    }

    private static class ImageInfo {
        private BufferedImage image;
        private String fileFormat;
        private File outputFile;

        private ImageInfo(BufferedImage image, String fileFormat, File outputFile) {
            this.image = image;
            this.fileFormat = fileFormat;
            this.outputFile = outputFile;
        }
    }

    private static void timerStart() {
        Main.startTime = System.currentTimeMillis();
    }

    private static void timerEnd(String methodName) {
        Main.endTime = System.currentTimeMillis();
        logTime(methodName);
    }

    private static void logTime(String methodName) {
        System.out.println(methodName + "| Time to write " + imageInfo.fileFormat + ": " + (endTime - startTime) + " ms.");
    }
}