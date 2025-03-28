package com.agir.maidai.util;

import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;

public class ModelAttributes {

    private final Model model;
    private final Map<String, Object> attributes = new HashMap<>();


    public ModelAttributes(Model model) {
        this.model = model;
    }

    public ModelAttributes add(String key, Object value) {
        attributes.put(key, value);
        return this;
    }

    public void apply() {
        attributes.forEach(model::addAttribute);
    }
}
