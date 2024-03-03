package entities;
import com.google.zxing.BarcodeFormat;
        import com.google.zxing.EncodeHintType;
        import com.google.zxing.WriterException;
        import com.google.zxing.common.BitMatrix;
        import com.google.zxing.qrcode.QRCodeWriter;
        import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

        import java.io.ByteArrayOutputStream;
        import java.io.IOException;
        import java.util.HashMap;
        import java.util.Map;
import com.google.zxing.client.j2se.MatrixToImageWriter;

public class QRCodeGenerator {

    public static byte[] generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hints.put(EncodeHintType.MARGIN, 1);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        writeToStream(bitMatrix, "PNG", outputStream);

        return outputStream.toByteArray();
    }

    private static void writeToStream(BitMatrix matrix, String format, ByteArrayOutputStream stream) throws IOException {
        MatrixToImageWriter.writeToStream(matrix, format, stream);
    }
}

