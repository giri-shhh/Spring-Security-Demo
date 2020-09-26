package com.example.demo.user.filter;

import com.example.demo.config.filter.FilterValue;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.function.Predicate;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FilterByName implements Predicate<FilterValue<String>> {

    private final Set<String> users = Sets.newHashSet("Girish", "Prakash");

    @Override
    public boolean test(FilterValue<String> filterValue) {
        return users.contains(filterValue.getFilterValue());
    }
}