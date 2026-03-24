package com.easyschedule.backend.shared.feature;

import org.springframework.context.annotation.Configuration;

@Configuration
public class FeatureFlagsConfig {
    private final boolean malla = true;
    private final boolean tomaMaterias = true;

    public boolean isMalla() {
        return malla;
    }

    public boolean isTomaMaterias() {
        return tomaMaterias;
    }
}
