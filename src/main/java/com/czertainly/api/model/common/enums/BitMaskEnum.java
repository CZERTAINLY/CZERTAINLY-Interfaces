package com.czertainly.api.model.common.enums;

import java.util.HashSet;
import java.util.Set;

public interface BitMaskEnum<E extends Enum<E> & BitMaskEnum<E>> {

    int getBit();

    static <E extends Enum<E> & BitMaskEnum<E>> int convertListToBitMask(Set<E> enums) {
        int bitmask = 0;
        for (E enumEntry : enums) {
            bitmask |= enumEntry.getBit();
        }
        return bitmask;
    }

    static <E extends Enum<E> & BitMaskEnum<E>> Set<E> convertBitMaskToList(int bitmask, Class<E> enumClass) {
        Set<E> result = new HashSet<>();
        for (E constant : enumClass.getEnumConstants()) {
            if ((bitmask & constant.getBit()) != 0) {
                result.add(constant);
            }
        }
        return result;
    }
}
