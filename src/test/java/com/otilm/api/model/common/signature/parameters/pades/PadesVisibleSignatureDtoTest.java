package com.otilm.api.model.common.signature.parameters.pades;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PadesVisibleSignatureDtoTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    private final ObjectMapper mapper = new ObjectMapper();

    private static PadesVisibleSignatureDto stamp() {
        PadesVisibleSignatureDto visibleSignature = new PadesVisibleSignatureDto();
        visibleSignature.setVisible(true);
        visibleSignature.setText("Signed by Jane Doe");
        visibleSignature.setImage(new byte[]{1, 2, 3});
        visibleSignature.setImageMimeType("image/png");
        return visibleSignature;
    }

    private static boolean valid(PadesVisibleSignatureDto visibleSignature) {
        return VALIDATOR.validate(visibleSignature).isEmpty();
    }

    @Test
    void aFullyPopulatedStampIsValid() {
        PadesVisibleSignatureDto visibleSignature = stamp();
        PadesVisibleSignaturePlacementDto placement = new PadesVisibleSignaturePlacementDto();
        placement.setFieldId("Signature1");
        visibleSignature.setPlacement(placement);
        assertTrue(valid(visibleSignature));
    }

    @Test
    void everyFieldIsOptional() {
        assertTrue(valid(new PadesVisibleSignatureDto()));
    }

    /** The bytes and the type that reads them are one unit: half of it tells the connector nothing it can render. */
    @Test
    void theImageAndItsMimeTypeArriveTogetherOrNotAtAll() {
        PadesVisibleSignatureDto bytesOnly = stamp();
        bytesOnly.setImageMimeType(null);
        assertFalse(valid(bytesOnly));

        PadesVisibleSignatureDto typeOnly = stamp();
        typeOnly.setImage(null);
        assertFalse(valid(typeOnly));
    }

    @Test
    void onlyPngAndJpegAreAccepted() {
        for (String mimeType : new String[]{"image/png", "image/jpeg"}) {
            PadesVisibleSignatureDto visibleSignature = stamp();
            visibleSignature.setImageMimeType(mimeType);
            assertTrue(valid(visibleSignature), mimeType);
        }
        for (String mimeType : new String[]{"image/svg+xml", "image/gif", "application/pdf", "IMAGE/PNG"}) {
            PadesVisibleSignatureDto visibleSignature = stamp();
            visibleSignature.setImageMimeType(mimeType);
            assertFalse(valid(visibleSignature), mimeType);
        }
    }

    @Test
    void theCapsOnTheCaptionAndTheImageAreEnforced() {
        PadesVisibleSignatureDto longText = stamp();
        longText.setText("t".repeat(4097));
        assertFalse(valid(longText));

        PadesVisibleSignatureDto atTheCap = stamp();
        atTheCap.setText("t".repeat(4096));
        atTheCap.setImage(new byte[262144]);
        assertTrue(valid(atTheCap));

        PadesVisibleSignatureDto oversizedImage = stamp();
        oversizedImage.setImage(new byte[262145]);
        assertFalse(valid(oversizedImage));
    }

    /** Lombok renders a byte[] field byte by byte, so an unexcluded image would print 256 KiB into a log line. */
    @Test
    void theStampBytesStayOutOfToString() {
        assertFalse(stamp().toString().contains("1, 2, 3"));
    }

    @Test
    void theStampRoundTripsWithTheImageAsBase64() throws Exception {
        String json = mapper.writeValueAsString(stamp());
        assertTrue(json.contains("\"image\":\"AQID\""), json);

        PadesVisibleSignatureDto decoded = mapper.readValue(json, PadesVisibleSignatureDto.class);
        assertEquals(Boolean.TRUE, decoded.getVisible());
        assertEquals("Signed by Jane Doe", decoded.getText());
        assertEquals("image/png", decoded.getImageMimeType());
        assertArrayEquals(new byte[]{1, 2, 3}, decoded.getImage());
    }
}
