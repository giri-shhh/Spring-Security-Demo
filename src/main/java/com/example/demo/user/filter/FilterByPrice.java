package com.example.demo.user.filter;

import com.example.demo.config.filter.FilterValue;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class FilterByPrice implements Predicate<FilterValue<Integer>> {

    @Override
    public boolean test(FilterValue<Integer> filterValue) {
        return filterValue.getFilterValue() < 500;
    }
}
