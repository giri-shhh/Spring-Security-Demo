package com.example.demo.config.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface FilterValue<T> {
    @JsonIgnore
    T getFilterValue();
}
