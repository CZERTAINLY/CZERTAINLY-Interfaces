package com.otilm.api.model.core.cryptoasset;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PqcVerdictTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void findByCode_resolvesWireCode() {
        Assertions.assertEquals(PqcVerdict.READY, PqcVerdict.findByCode("ready"));
        Assertions.assertEquals(PqcVerdict.NOT_READY, PqcVerdict.findByCode("notReady"));
        Assertions.assertEquals(PqcVerdict.NOT_APPLICABLE, PqcVerdict.findByCode("notApplicable"));
        Assertions.assertEquals(PqcVerdict.UNKNOWN, PqcVerdict.findByCode("unknown"));
    }

    @Test
    void findByCode_rejectsUnknownCode() {
        Assertions.assertThrows(ValidationException.class, () -> PqcVerdict.findByCode("maybe"));
    }

    @Test
    void serializesToWireCode() throws Exception {
        Assertions.assertEquals("\"notReady\"", mapper.writeValueAsString(PqcVerdict.NOT_READY));
        Assertions.assertEquals("\"notApplicable\"", mapper.writeValueAsString(PqcVerdict.NOT_APPLICABLE));
    }

    @Test
    void deserializesFromWireCode() throws Exception {
        Assertions.assertEquals(PqcVerdict.READY, mapper.readValue("\"ready\"", PqcVerdict.class));
    }
}
