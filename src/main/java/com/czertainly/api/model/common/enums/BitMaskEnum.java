package com.czertainly.api.model.common.enums;

import java.util.HashSet;
import java.util.Set;

public interface BitMaskEnum {

    int getBit();

    static <E extends BitMaskEnum> int convertListToBitMask(Set<E> enums) {
        int bitmask = 0;
        for (E enumEntry : enums) {
            bitmask |= enumEntry.getBit();
        }
        return bitmask;
    }

    static <E extends BitMaskEnum> Set<E> convertBitMaskToList(int bitmask, Class<E> enumClass) {
        Set<E> result = new HashSet<>();
        for (E constant : enumClass.getEnumConstants()) {
            if ((bitmask & constant.getBit()) != 0) {
                result.add(constant);
            }
        }
        return result;
    }

}
