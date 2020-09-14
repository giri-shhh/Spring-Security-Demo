package com.example.demo.user.service;

import com.example.demo.config.filter.FilterValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements FilterValue<String> {

    private String userName;

    @Override
    @JsonIgnore
    public String getFilterValue() {
        return userName;
    }
}
