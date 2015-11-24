/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.utils;

import com.vn.dailycookapp.utils.lang.FmtLoader;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.entity.Recipe;

/**
 *
 * @author duyetpt
 */
public class FmBuilder {

    Configuration cfg;
    private static final FmBuilder instance = new FmBuilder();

    private FmBuilder() {
        cfg = new Configuration(new Version(2, 3, 23));
        try {
            File directory = new File(FmtLoader.getInstance().templatePath);
            cfg.setDirectoryForTemplateLoading(directory);
        } catch (IOException ex) {
            Logger.getLogger(FmBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Some other recommended settings:
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public static FmBuilder getInstance() {
        return instance;
    }

    public String build(String language, Recipe recipe) {
        try {
            String tempStr = FmtLoader.getInstance().getTemplate(language);
            Template template = cfg.getTemplate(tempStr, "UTF-8");

            Map<String, Object> input = new HashMap<>();
            input.put("title", recipe.getTitle());
            input.put("pictureUrl", recipe.getPictureUrl());
            input.put("story", recipe.getStory());
            input.put("viewNumber", recipe.getView());
            input.put("favoriteNumber", recipe.getFavoriteNumber());

            input.put("tags", recipe.getCategoryIds());

            input.put("owner", recipe.getOwner());
            input.put("ingredients", recipe.getIngredients());
            input.put("steps", recipe.getSteps());

            StringWriter sw = new StringWriter();
            template.process(input, sw);

            return sw.toString();
        } catch (MalformedTemplateNameException ex) {
            Logger.getLogger(FmBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FmBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | TemplateException ex) {
            Logger.getLogger(FmBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
