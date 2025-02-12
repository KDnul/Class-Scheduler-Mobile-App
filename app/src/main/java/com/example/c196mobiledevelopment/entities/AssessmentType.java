package com.example.c196mobiledevelopment.entities;

import androidx.annotation.NonNull;

public enum AssessmentType {
    OBJ {
        @NonNull
        @Override
        public String toString() { return "Objective Assessment"; }
    },
    PERF {
        @NonNull
        @Override
        public String toString() { return "Performance Assessment"; }
    }
}