package com.otilm.api.model.core.oid;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class SystemOidTest {

    /**
     * Codes that deliberately differ from BouncyCastle's symbol. Frozen: renaming one would change how
     * every stored subject DN renders. {@code PSEUDONYM} differs only in case, which parity ignores.
     */
    private static final Set<SystemOid> FROZEN_BC_SYMBOL_DIVERGENCES = Set.of(
            SystemOid.EMAIL,                          // ours EMAIL, BC E
            SystemOid.SURNAME,                        // ours SN (OpenSSL / RFC 4519 short name), BC SURNAME
            SystemOid.DISTINGUISHED_NAME_QUALIFIER    // ours DNQ, BC DN — which universally means distinguished name
    );

    /**
     * OIDs BouncyCastle has no symbol for, knowingly given a code. Such an OID renders as its dotted
     * form today, so a code changes stored DN text and needs an {@code updateCertificateDNs} migration.
     */
    private static final Set<SystemOid> RDN_CODES_WITHOUT_BC_SYMBOL = Set.of();

    /** Expected properties of one seeded certificate extension. */
    private record ExtensionExpectation(String displayName, boolean defaultCritical) {}

    private static final Map<String, ExtensionExpectation> EXPECTED_EXTENSIONS = Map.of(
            "2.5.29.37", new ExtensionExpectation("Extended Key Usage", false),
            "2.5.29.15", new ExtensionExpectation("Key Usage", true),
            "2.5.29.19", new ExtensionExpectation("Basic Constraints", true),
            "2.5.29.14", new ExtensionExpectation("Subject Key Identifier", false),
            "2.5.29.9", new ExtensionExpectation("Subject Directory Attributes", false),
            "2.5.29.30", new ExtensionExpectation("Name Constraints", true),
            "2.5.29.16", new ExtensionExpectation("Private Key Usage Period", false),
            "1.3.6.1.5.5.7.1.24", new ExtensionExpectation("TLS Feature", false)
    );

    @Test
    void seedsTheCertificateExtensionsThatRequestersPutInACsr() {
        // when
        List<SystemOid> extensions = byCategory(OidCategory.CERTIFICATE_EXTENSION);

        // then
        assertEquals(EXPECTED_EXTENSIONS.size(), extensions.size(), "unexpected number of certificate-extension entries");
        for (SystemOid entry : extensions) {
            ExtensionExpectation expected = EXPECTED_EXTENSIONS.get(entry.getOid());
            assertNotNull(expected, "unexpected certificate-extension OID " + entry.getOid());
            // The display name is what an operator picks from in the mapping dropdown, so a name/OID
            // mismatch would silently mint the wrong extension.
            assertEquals(expected.displayName(), entry.getDisplayName(), "wrong displayName for " + entry.getOid());
            assertEquals(expected.defaultCritical(), entry.getDefaultCritical(), "wrong defaultCritical for " + entry.getOid());
            assertEquals(ExtensionValueEncoding.DER, entry.getValueEncoding(), "wrong valueEncoding for " + entry.getOid());
        }
    }

    @Test
    void doesNotRegisterSubjectAlternativeNameAsACertificateExtension() {
        // given — rationale recorded on the CERTIFICATE_EXTENSION group in SystemOid
        var san = SystemOid.fromOID("2.5.29.17");

        // when / then
        assertNull(san, "2.5.29.17 must not be a system OID; use the SAN mapping target");
    }

    @Test
    void doesNotSeedSmimeCapabilitiesUntilItsCsrPlacementIsConfirmed() {
        // given — dual-natured: in its PKCS#9 CSR-attribute form the parser never sees it, so a
        // mapping would be a silent no-op. Out until a captured CSR proves the extensionRequest form.
        // when / then
        assertNull(SystemOid.fromOID("1.2.840.113549.1.9.15"),
                "smimeCapabilities must stay deferred until its extensionRequest placement is verified");
    }

    @Test
    void leavesExtensionPropertiesUnsetForNonExtensionEntries() {
        // given / when / then
        for (SystemOid entry : SystemOid.values()) {
            if (entry.getCategory() == OidCategory.CERTIFICATE_EXTENSION) {
                continue;
            }
            assertNull(entry.getDefaultCritical(), "defaultCritical must be null for " + entry.name());
            assertNull(entry.getValueEncoding(), "valueEncoding must be null for " + entry.name());
        }
    }

    @Test
    void givesEveryCertificateExtensionItsProjectionDefaults() {
        // given — the mirror of leavesExtensionPropertiesUnsetForNonExtensionEntries. An extension
        // declared through the no-properties constructor compiles fine and yields a null
        // defaultCritical, which is the shape behind emitting a critical extension as non-critical.
        List<SystemOid> extensions = byCategory(OidCategory.CERTIFICATE_EXTENSION);

        // when / then
        assertFalse(extensions.isEmpty(), "no certificate extensions are seeded");
        for (SystemOid entry : extensions) {
            assertNotNull(entry.getDefaultCritical(), "defaultCritical missing for " + entry.name());
            assertNotNull(entry.getValueEncoding(), "valueEncoding missing for " + entry.name());
        }
    }

    @Test
    void letsStreetAddressParseUnderItsOpenSslSpelling() {
        // given — the registry binds STREET and BouncyCastle binds only "street", so the OpenSSL long
        // name resolves in neither without an alt code
        SystemOid street = SystemOid.fromOID("2.5.4.9");

        // when
        Set<String> altCodes = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        altCodes.addAll(street.getAltCodes());

        // then — parse-only, so the rendered token stays STREET and no stored DN changes
        assertTrue(altCodes.contains("streetAddress"), "2.5.4.9 must accept the OpenSSL long name");
        assertEquals("STREET", street.getCode());
    }

    @Test
    void seedsTheRdnTypesNeededWhenAuthoringAMapping() {
        // given — OID, expected code (BouncyCastle's symbol verbatim, including its casing)
        Map<String, String> expected = Map.of(
                "2.5.4.5", "SERIALNUMBER",
                "2.5.4.9", "STREET",
                "2.5.4.17", "PostalCode",
                "2.5.4.15", "BusinessCategory",
                "0.9.2342.19200300.100.1.1", "UID"
        );

        // when / then
        expected.forEach((oid, code) -> {
            SystemOid entry = SystemOid.fromOID(oid);
            assertNotNull(entry, "missing RDN entry for " + oid);
            assertEquals(OidCategory.RDN_ATTRIBUTE_TYPE, entry.getCategory(), "wrong category for " + oid);
            assertEquals(code, entry.getCode(), "wrong code for " + oid);
        });
    }

    @Test
    void givesSerialNumberNoAltCodesSoItCanNeverBeReachedAsSn() {
        // given — SN is surname (2.5.4.4) everywhere. The RFC 4519 spelling of 2.5.4.5 differs from
        // the code only in case, and the code lookup is already case-insensitive.
        SystemOid serialNumber = SystemOid.fromOID("2.5.4.5");

        // when / then
        assertNotNull(serialNumber);
        assertTrue(serialNumber.getAltCodes().isEmpty(), "2.5.4.5 must declare no alt codes");
        assertEquals("SN", SystemOid.fromOID("2.5.4.4").getCode(), "SN must remain surname");
    }

    @Test
    void letsDnQualifierParseUnderItsStandardSpellings() {
        // given — a DN written dnQualifier=x resolves in neither the platform registry nor
        // BouncyCastle today, because BC binds only "dn"
        SystemOid dnQualifier = SystemOid.fromOID("2.5.4.46");

        // when
        Set<String> altCodes = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        altCodes.addAll(dnQualifier.getAltCodes());

        // then
        assertTrue(altCodes.contains("DN"), "2.5.4.46 must accept BouncyCastle's DN spelling");
        assertTrue(altCodes.contains("dnQualifier"), "2.5.4.46 must accept the RFC 4519 / OpenSSL spelling");
    }

    @Test
    void declaresAltCodesAsAnEmptyListRatherThanNull() {
        // given — consumers iterate altCodes across every category without a null guard
        // when / then
        for (SystemOid entry : SystemOid.values()) {
            assertNotNull(entry.getAltCodes(), "altCodes must not be null for " + entry.name());
        }
    }

    @Test
    void keepsEveryOidUniqueAcrossConstants() {
        // given — fromOID resolves with findFirst, so a duplicated OID silently returns whichever
        // constant is declared first
        // when
        long distinctOids = Arrays.stream(SystemOid.values()).map(SystemOid::getOid).distinct().count();

        // then
        assertEquals(SystemOid.values().length, distinctOids, "two constants share an OID");
    }

    @Test
    void rendersEveryRdnUnderBouncyCastlesSymbolExceptTheFrozenDivergences() {
        // given — matching BC's symbol keeps the rendered subject-DN token unchanged, so no stored DN
        // needs rewriting
        // when / then
        for (SystemOid entry : byCategory(OidCategory.RDN_ATTRIBUTE_TYPE)) {
            if (FROZEN_BC_SYMBOL_DIVERGENCES.contains(entry)) {
                continue;
            }
            String bcSymbol = BCStyle.INSTANCE.oidToDisplayName(new ASN1ObjectIdentifier(entry.getOid()));
            if (bcSymbol == null) {
                if (RDN_CODES_WITHOUT_BC_SYMBOL.contains(entry)) {
                    continue;
                }
                fail("no BouncyCastle symbol for " + entry.name() + " (" + entry.getOid() + "), so code '"
                        + entry.getCode() + "' changes rendering from the dotted OID and needs an "
                        + "updateCertificateDNs migration; then add it to RDN_CODES_WITHOUT_BC_SYMBOL");
            }
            assertEquals(bcSymbol.toLowerCase(Locale.ROOT), entry.getCode().toLowerCase(Locale.ROOT),
                    "code for " + entry.name() + " (" + entry.getOid() + ") diverges from BouncyCastle's symbol; "
                            + "either match it or migrate stored DNs with updateCertificateDNs");
        }
    }

    @Test
    void keepsEveryFrozenDivergenceActuallyDivergent() {
        // given — a frozen entry that no longer diverges must be removed from the list
        // when / then
        for (SystemOid entry : FROZEN_BC_SYMBOL_DIVERGENCES) {
            String bcSymbol = BCStyle.INSTANCE.oidToDisplayName(new ASN1ObjectIdentifier(entry.getOid()));
            assertNotNull(bcSymbol, "no BouncyCastle symbol for " + entry.name() + "; it cannot diverge");
            assertFalse(bcSymbol.equalsIgnoreCase(entry.getCode()),
                    entry.name() + " no longer diverges from BouncyCastle; drop it from the frozen list");
        }
    }

    @Test
    void keepsEveryRdnCodeAndAltCodeUniqueAcrossConstants() {
        // given — OidHandler flattens codes and altCodes into one case-insensitive map with no
        // collision check, so a duplicate resolves arbitrarily. Dedupe within a constant first:
        // several BC symbols differ from their RFC 4519 spelling only in case.
        Map<String, String> owner = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        // when / then
        for (SystemOid entry : byCategory(OidCategory.RDN_ATTRIBUTE_TYPE)) {
            for (String token : distinctTokens(entry)) {
                String previous = owner.put(token, entry.name());
                assertNull(previous, "token '" + token + "' is claimed by both " + previous + " and " + entry.name());
            }
        }
    }

    @Test
    void neverShadowsABouncyCastleKeywordBoundToADifferentOid() {
        // given — PlatformX500NameStyle resolves RDN codes registry-first, so a token we bind
        // silently overrides BouncyCastle's binding when parsing DN strings from other systems
        // when / then
        for (SystemOid entry : byCategory(OidCategory.RDN_ATTRIBUTE_TYPE)) {
            for (String token : distinctTokens(entry)) {
                ASN1ObjectIdentifier bcOid;
                try {
                    bcOid = BCStyle.INSTANCE.attrNameToOID(token);
                } catch (IllegalArgumentException e) {
                    continue; // BouncyCastle does not know this token, so nothing can be shadowed
                }
                assertEquals(entry.getOid(), bcOid.getId(),
                        "token '" + token + "' on " + entry.name() + " shadows BouncyCastle's binding to " + bcOid.getId());
            }
        }
    }

    private static List<SystemOid> byCategory(OidCategory category) {
        return Arrays.stream(SystemOid.values()).filter(e -> e.getCategory() == category).toList();
    }

    /** Codes and alt codes of one entry, deduplicated case-insensitively. */
    private static Set<String> distinctTokens(SystemOid entry) {
        Set<String> tokens = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        List<String> all = new ArrayList<>(entry.getAltCodes());
        if (entry.getCode() != null) {
            all.add(entry.getCode());
        }
        tokens.addAll(all);
        return tokens;
    }
}
