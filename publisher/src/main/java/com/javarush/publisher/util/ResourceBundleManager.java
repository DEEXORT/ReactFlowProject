package com.javarush.reactflow.util;

import lombok.experimental.UtilityClass;

import java.util.ResourceBundle;

@UtilityClass
public class ResourceBundleManager {
    public static final ResourceBundle BUNDLE_MESSAGES = ResourceBundle.getBundle("messages");
}
