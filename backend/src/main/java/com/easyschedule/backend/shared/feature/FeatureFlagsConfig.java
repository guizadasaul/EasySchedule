package com.easyschedule.backend.shared.feature;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FeatureFlagsConfig {
    private final boolean malla;
    private final boolean tomaMaterias;

    public FeatureFlagsConfig(
        @Value("${features.malla:true}") boolean malla,
        @Value("${features.toma-materias:true}") boolean tomaMaterias
    ) {
        this.malla = malla;
        this.tomaMaterias = tomaMaterias;
    }

    public boolean isMalla() {
        return malla;
    }

    public boolean isTomaMaterias() {
        return tomaMaterias;
    }
}
