package com.agir.maidai.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.ServletRequestUtils.getIntParameter;
import static org.springframework.web.bind.ServletRequestUtils.getStringParameter;

public class FilteredParams {

    private Pageable pageable;
    private Map<String, String> filters;
    private Map<String, String> parameters;
    private String sort;

    public FilteredParams() {
        this.sort = "";
        this.filters = new HashMap<>();
        this.parameters = new HashMap<>();
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    // Obtain the filters to param filter
    public Map<String, String> getFilters(HttpServletRequest request) {

        if(request.getParameterMap().containsKey("filter")) {
            return filters = parseFilterGroups(request.getParameter("filter"));
        }
        return filters;
    }

    // Define pageable
    public Pageable getPageable(HttpServletRequest request) {

        int page = getIntParameter(request, "page", 0);
        int size = getIntParameter(request, "size", 10);
        String sort = getStringParameter(request, "sort", null);

        if(sort != null) {
            if(!sort.isEmpty()) {
                this.sort = sort;
                String[] sortParams = sort.split(",");
                String sortField = sortParams[0];
                Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;
                this.pageable = PageRequest.of(page, size, direction, sortField);
            }
        } else {
            this.pageable = PageRequest.of(page, size);
        }
        return pageable;
    }

    public Map<String, String> getParparameters(HttpServletRequest request) {
        request.getParameterMap().forEach((key, values) -> {
            if(!isExcludedParameter(key) && values.length > 0 && !values[0].isEmpty()) {
                parameters.put(key, values[0]);
            }
        });
        return parameters;
    }

    // Verify if the parameter it's pagination/sorting/filter
    private boolean isExcludedParameter(String param) {
        return param.equals("page") || param.equals("size") || param.equals("sort") || param.equals("filter");
    }

    // Helper function to getFilters
    private Map<String, String> parseFilterGroups(String filterParam) {
        Map<String, String> filters = new HashMap<>();
        if (filterParam == null || filterParam.isEmpty()) {
            return filters;
        }

        // Split multiple filters: "status:vigente,name:John" → ["status:vigente", "name:John"]
        String[] filterPairs = filterParam.split(",");

        for (String pair : filterPairs) {
            // Split each pair: "status:vigente" → ["status", "vigente"]
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                filters.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }

        return filters;
    }
}
