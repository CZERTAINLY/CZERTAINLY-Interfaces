package com.czertainly.api.model.connector.cryptography.key.value;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(
        description = "Value of the Key",
        type = "object",
        subTypes = {
                RawKeyValue.class,
                SpkiKeyValue.class,
                PrkiKeyValue.class,
                EprkiKeyValue.class,
                CustomKeyValue.class
        }
)
public class KeyValue extends AbstractKeyValue {
}
