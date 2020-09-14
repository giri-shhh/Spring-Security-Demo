package com.example.demo.user.service;

import com.example.demo.config.filter.FilterValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Price implements FilterValue<Integer> {

    private Integer price;

    @Override
    @JsonIgnore
    public Integer getFilterValue() {
        return price;
    }
}
