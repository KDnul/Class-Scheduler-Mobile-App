package com.example.c196mobiledevelopment.entities;

import androidx.annotation.NonNull;

public enum CourseStatus  {
    PLAN_TO_TAKE {
        @NonNull
        @Override
        public String toString() { return "Plan To Take"; }
    },
    IN_PROGRESS {
        @NonNull
        @Override
        public String toString() { return "In Progress"; }
    },
    COMPLETED {
        @NonNull
        @Override
        public String toString() { return "Completed"; }
    },
    DROPPED{
        @NonNull
        @Override
        public String toString() { return "Dropped"; }
    };
}