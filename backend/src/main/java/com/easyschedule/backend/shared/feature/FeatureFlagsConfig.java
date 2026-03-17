package com.easyschedule.backend.shared.feature;

import org.springframework.context.annotation.Configuration;

@Configuration
public class FeatureFlagsConfig {
    //here we have to activate the feature toggles
    private final boolean malla = false;
    private final boolean tomaMaterias = true;

    public boolean isMalla() {
        return malla;
    }

    public boolean isTomaMaterias() {
        return tomaMaterias;
    }
}
