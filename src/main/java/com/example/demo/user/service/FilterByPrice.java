package com.example.demo.user.service;

import com.example.demo.config.filter.FilterValue;

import java.util.function.Predicate;

public class FilterByPrice implements Predicate<FilterValue<Integer>> {

    @Override
    public boolean test(FilterValue<Integer> filterValue) {
        return filterValue.getFilterValue() > 100;
    }
}
