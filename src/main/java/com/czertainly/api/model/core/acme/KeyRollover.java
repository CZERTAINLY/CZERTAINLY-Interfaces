package com.czertainly.api.model.core.acme;

import io.swagger.v3.oas.annotations.media.Schema;

public class KeyRollover {
    @Schema(description = "Account URL", example = "https://example.com/acme/acct/evOfKhNU60wg", required = true)
    private String account;
    @Schema(description = "Old key of the Account", example = "<Account old key content>", required = true)
    private String oldKey;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOldKey() {
        return oldKey;
    }

    public void setOldKey(String oldKey) {
        this.oldKey = oldKey;
    }
}
