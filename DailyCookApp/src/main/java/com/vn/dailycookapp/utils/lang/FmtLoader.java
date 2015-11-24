/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.utils.lang;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.FileUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author duyetpt
 */
public class FmtLoader {
    
    public String templatePath;
    private static final FmtLoader instance = new FmtLoader();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static Map<String, String> templates;
    
    private FmtLoader() {
        try {
            templates = new HashMap<>();
            init();
        } catch (Exception e) {
            logger.error("init multi language support error", e);
        }
    }

    public static FmtLoader getInstance() {
        return instance;
    }

    private void init() throws Exception {
        File directory = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        String langDirectoryPath = directory.getParent() + File.separator + "fmt";
        this.templatePath = langDirectoryPath;
        
        File langDir = new File(langDirectoryPath);
        File[] files = langDir.listFiles();
        for (File file : files) {
            String lang = file.getName().substring(0, 2);
            templates.put(lang, file.getName());
        }
    }
    
    public String getTemplate(String language) {
        return templates.get(language);
    }
}
