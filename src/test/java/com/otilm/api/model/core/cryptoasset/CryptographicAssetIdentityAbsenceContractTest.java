package com.otilm.api.model.core.cryptoasset;

import com.otilm.api.interfaces.core.web.CryptographicAssetController;
import com.otilm.api.interfaces.core.web.StatisticsController;
import com.otilm.api.model.client.dashboard.CryptographicAssetStatisticsDto;
import com.otilm.api.model.client.dashboard.CryptographicAssetSyncCompletenessDto;
import com.otilm.api.model.common.enums.IPlatformEnum;
import com.otilm.api.model.core.cbom.CbomAssetSyncState;
import com.otilm.api.model.core.cbom.CbomDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Proves the asset identity key stays off the wire. The key hashes low-entropy material values, so any surface that
 * carries or names it would let a client recover what redaction removed. The DTOs are defined in this artifact, so this
 * is where the guarantee holds or fails — the sweep below rejects any field name; every {@code @Schema} description,
 * title and example (both the singular {@code example()} and the plural {@code examples()}) at class and field level;
 * every operation's {@code @Operation} summary/description, {@code @ApiResponse} descriptions and {@code @Parameter}
 * descriptions; the inventory controller's class-level {@code @Tag} name and description; and the three wire enums'
 * code, label and description — on every surface this contract serves.
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
        sweepTag(CryptographicAssetController.class, problems);
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

    @Test
    void noEnumConstantExposesTheIdentityKeyInItsCodeLabelOrDescription() {
        List<String> problems = new ArrayList<>();
        sweepEnum(CryptographicAssetType.class, problems);
        sweepEnum(PqcVerdict.class, problems);
        sweepEnum(CbomAssetSyncState.class, problems);
        assertTrue(problems.isEmpty(), String.join("\n", problems));
    }

    /** Proves the sweep actually fails on a violating type, rather than passing over it silently. */
    @Test
    void theSweepCatchesAViolatingField() {
        List<String> problems = new ArrayList<>();
        sweep(ViolatingFixture.class, problems);
        assertEquals(4, problems.size(), String.join("\n", problems));
        assertTrue(problems.stream().anyMatch(p -> p.contains("identityKey")), String.join("\n", problems));
        assertTrue(problems.stream().anyMatch(p -> p.contains("fingerprint")), String.join("\n", problems));
        assertTrue(problems.stream().anyMatch(p -> p.contains("class schema") && p.contains("identity")),
                String.join("\n", problems));
        assertTrue(problems.stream().anyMatch(p -> p.contains("schema example") && p.contains("fingerprint")),
                String.join("\n", problems));
    }

    private static void sweep(Class<?> type, List<String> problems) {
        for (Class<?> current = type; current != null && current != Object.class; current = current.getSuperclass()) {
            Schema classSchema = current.getAnnotation(Schema.class);
            if (classSchema != null) {
                String classLocation = current.getSimpleName() + " class schema";
                reportBannedTokens(classLocation + " description", classSchema.description(), problems);
                reportBannedTokens(classLocation + " title", classSchema.title(), problems);
                reportBannedTokens(classLocation + " example", classSchema.example(), problems);
                for (String example : classSchema.examples()) {
                    reportBannedTokens(classLocation + " examples", example, problems);
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
                    reportBannedTokens(location + " schema title", schema.title(), problems);
                    reportBannedTokens(location + " schema example", schema.example(), problems);
                    for (String example : schema.examples()) {
                        reportBannedTokens(location + " schema examples", example, problems);
                    }
                }
            }
        }
    }

    private static void sweepOperation(Method method, List<String> problems) {
        Operation operation = method.getAnnotation(Operation.class);
        if (operation != null) {
            reportBannedTokens(method.getName() + " summary", operation.summary(), problems);
            reportBannedTokens(method.getName() + " description", operation.description(), problems);
        }
        ApiResponses responses = method.getAnnotation(ApiResponses.class);
        if (responses != null) {
            for (ApiResponse response : responses.value()) {
                reportBannedTokens(method.getName() + " " + response.responseCode() + " response description",
                        response.description(), problems);
            }
        }
        for (Parameter parameter : method.getParameters()) {
            // Fully qualified deliberately: java.lang.reflect.Parameter is already imported for the reflected method
            // parameter this loop walks, so the annotation type has to stay unimported to avoid shadowing it.
            io.swagger.v3.oas.annotations.Parameter param = parameter
                    .getAnnotation(io.swagger.v3.oas.annotations.Parameter.class);
            if (param != null) {
                reportBannedTokens(method.getName() + " parameter description", param.description(), problems);
            }
        }
    }

    private static void sweepTag(Class<?> controller, List<String> problems) {
        Tag tag = controller.getAnnotation(Tag.class);
        if (tag != null) {
            reportBannedTokens(controller.getSimpleName() + " @Tag name", tag.name(), problems);
            reportBannedTokens(controller.getSimpleName() + " @Tag description", tag.description(), problems);
        }
    }

    private static void sweepEnum(Class<? extends IPlatformEnum> enumType, List<String> problems) {
        for (IPlatformEnum constant : enumType.getEnumConstants()) {
            String location = enumType.getSimpleName() + "." + constant.name();
            reportBannedTokens(location + " code", constant.getCode(), problems);
            reportBannedTokens(location + " label", constant.getLabel(), problems);
            if (constant.getDescription() != null) {
                reportBannedTokens(location + " description", constant.getDescription(), problems);
            }
        }
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

        @Schema(description = "described by its fingerprint", example = "fingerprint of the material")
        String value;
    }
}
