package com.example.demo.user.dto;

import com.example.demo.config.filter.FilterValue;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Price implements FilterValue<Integer> {

    private Integer price;
    private String priceName;

    @Override
    public Integer getFilterValue() {
        return price;
    }
}
