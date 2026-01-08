package com.javarush.reactflow.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ResourceBundleManagerTest {

    @Test
    void getResourceBundle() {
        String message = ResourceBundleManager.BUNDLE_MESSAGES.getString("exception.writer_already_exists");
        Assertions.assertNotNull(message);
    }
}