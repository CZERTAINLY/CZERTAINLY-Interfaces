package com.otilm.api.model.core.cryptoasset;

import com.otilm.api.interfaces.core.web.CryptographicAssetController;
import com.otilm.api.interfaces.core.web.StatisticsController;
import com.otilm.api.model.client.dashboard.CryptographicAssetStatisticsDto;
import com.otilm.api.model.client.dashboard.CryptographicAssetSyncCompletenessDto;
import com.otilm.api.model.core.cbom.CbomDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Proves the asset identity key stays off the wire. The key hashes low-entropy material values, so any surface that
 * carries or names it would let a client recover what redaction removed. The DTOs are defined in this artifact, so this
 * is where the guarantee holds or fails — the sweep below rejects any field or documented prose that names the key, on
 * every type this contract serves.
 */
class CryptographicAssetIdentityAbsenceContractTest {

    /**
     * "identity" catches the key under its own name and any of its spellings; "fingerprint" catches the historical name
     * the investigation used for the same value. Nothing this inventory legitimately serves needs either word:
     * certificates are referenced by serial number and DN, assets by UUID.
     */
    private static final List<String> BANNED_TOKENS = List.of("identity", "fingerprint");

    private static final List<Class<?>> WIRE_TYPES = List
            .of(CryptographicAssetDto.class, CryptographicAssetDetailDto.class, CryptographicAssetVerdictDto.class,
                    CryptographicAssetSourceDto.class, CryptographicAssetEvidenceDto.class,
                    CryptographicAssetOidDto.class, CryptographicAssetStatisticsDto.class,
                    CryptographicAssetSyncCompletenessDto.class, CbomDto.class);

    @Test
    void noWireTypeCarriesAnIdentityKeyFieldOrMentionsOneInItsSchema() {
        List<String> problems = new ArrayList<>();
        for (Class<?> type : WIRE_TYPES) {
            sweep(type, problems);
        }
        assertTrue(problems.isEmpty(), String.join("\n", problems));
    }

    @Test
    void noInventoryOperationProseMentionsTheIdentityKey() {
        List<String> problems = new ArrayList<>();
        for (Method method : CryptographicAssetController.class.getDeclaredMethods()) {
            sweepOperation(method, problems);
        }
        for (Method method : StatisticsController.class.getDeclaredMethods()) {
            if (method.getName().equals("getCryptographicAssetStatistics")) {
                sweepOperation(method, problems);
            }
        }
        assertTrue(problems.isEmpty(), String.join("\n", problems));
    }

    /** Proves the sweep actually fails on a violating type, rather than passing over it silently. */
    @Test
    void theSweepCatchesAViolatingField() {
        List<String> problems = new ArrayList<>();
        sweep(ViolatingFixture.class, problems);
        assertEquals(3, problems.size(), String.join("\n", problems));
        assertTrue(problems.stream().anyMatch(p -> p.contains("identityKey")), String.join("\n", problems));
        assertTrue(problems.stream().anyMatch(p -> p.contains("fingerprint")), String.join("\n", problems));
        assertTrue(problems.stream().anyMatch(p -> p.contains("class schema") && p.contains("identity")),
                String.join("\n", problems));
    }

    private static void sweep(Class<?> type, List<String> problems) {
        for (Class<?> current = type; current != null && current != Object.class; current = current.getSuperclass()) {
            Schema classSchema = current.getAnnotation(Schema.class);
            if (classSchema != null) {
                reportBannedTokens(current.getSimpleName() + " class schema description", classSchema.description(),
                        problems);
                for (String example : classSchema.examples()) {
                    reportBannedTokens(current.getSimpleName() + " class schema example", example, problems);
                }
            }
            for (Field field : current.getDeclaredFields()) {
                if (field.isSynthetic() || Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                String location = current.getSimpleName() + "." + field.getName();
                reportBannedTokens(location + " field name", field.getName(), problems);
                Schema schema = field.getAnnotation(Schema.class);
                if (schema != null) {
                    reportBannedTokens(location + " schema description", schema.description(), problems);
                    for (String example : schema.examples()) {
                        reportBannedTokens(location + " schema example", example, problems);
                    }
                }
            }
        }
    }

    private static void sweepOperation(Method method, List<String> problems) {
        Operation operation = method.getAnnotation(Operation.class);
        if (operation == null) {
            return;
        }
        reportBannedTokens(method.getName() + " summary", operation.summary(), problems);
        reportBannedTokens(method.getName() + " description", operation.description(), problems);
    }

    private static void reportBannedTokens(String location, String text, List<String> problems) {
        String lower = text.toLowerCase(Locale.ROOT);
        for (String token : BANNED_TOKENS) {
            if (lower.contains(token)) {
                problems.add(location + " must not mention \"" + token + "\"");
            }
        }
    }

    /** Default access on purpose: a private field would trip Sonar's unused-private-field rule (java:S1068). */
    @Schema(description = "keyed by identity")
    private static final class ViolatingFixture {

        String identityKey;

        @Schema(description = "described by its fingerprint")
        String value;
    }
}
