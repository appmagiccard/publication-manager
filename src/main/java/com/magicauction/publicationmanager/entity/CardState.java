package com.magicauction.publicationmanager.entity;

import java.util.stream.Stream;

/**
 * NEAR_MINT, LIGHT_PLAY, HEAVY_PLAY, DAMAGED
 */
public enum CardState {
    NEAR_MINT("NM","NEAR_MINT"),
    LIGHT_PLAY("LP", "LIGHT_PLAY"),
    HEAVY_PLAY("HP", "HEAVY_PLAY"),
    DAMAGED("DG", "DAMAGED");

    private final String label;
    private final String name;

    public String getLabel() {
        return label;
    }

    CardState(String label, String name) {
        this.label = label;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CardState valueOfLabel(String label) {
        if (label == null) {
            return null;
        }

        return Stream.of(CardState.values())
                .filter(c -> c.getLabel().equals(label))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
