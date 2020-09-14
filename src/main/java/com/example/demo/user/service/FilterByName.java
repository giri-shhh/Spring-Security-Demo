package com.example.demo.user.service;

import com.example.demo.config.filter.FilterValue;
import com.google.common.collect.Sets;

import java.util.Set;
import java.util.function.Predicate;

public class FilterByName implements Predicate<FilterValue<String>> {

    private final Set<String> users = Sets.newHashSet("Girish", "Prakash");

    @Override
    public boolean test(FilterValue<String> filterValue) {
        return users.contains(filterValue.getFilterValue());
    }
}