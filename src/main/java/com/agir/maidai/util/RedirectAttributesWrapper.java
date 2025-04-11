package com.agir.maidai.util;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

public class RedirectAttributesWrapper {

    private final RedirectAttributes redirectAttributes;
    private final Map<String, Object> flashAttributes = new HashMap<>();

    public RedirectAttributesWrapper(RedirectAttributes redirectAttributes) {
        this.redirectAttributes = redirectAttributes;
    }

    public RedirectAttributesWrapper add(String key, Object value) {
        flashAttributes.put(key, value);
        return this;
    }

    public void apply() {
        flashAttributes.forEach(redirectAttributes::addFlashAttribute);
    }
}
