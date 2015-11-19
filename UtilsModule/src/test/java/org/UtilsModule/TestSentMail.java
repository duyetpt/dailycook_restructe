package org.UtilsModule;

import org.dao.RecipeDAO;

public class TestSentMail {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        RecipeDAO.getInstance().getAllRecipe();
        long end = System.currentTimeMillis();
        
        System.out.println("time(seconds): " + (end - start)/1000);
    }
}
