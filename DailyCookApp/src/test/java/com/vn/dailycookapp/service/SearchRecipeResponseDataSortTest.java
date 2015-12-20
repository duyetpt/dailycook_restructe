/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.service;

import com.vn.dailycookapp.entity.response.SearchRecipeResponseData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author duyetpt
 */
public class SearchRecipeResponseDataSortTest {

    @Test
    public void testCompareCreateTime() {
        SearchRecipeResponseData data1 = new SearchRecipeResponseData();
        data1.setCreateTime(0);
        data1.setFavorite(true);
        data1.setNFavorite(0);
        data1.setPercentMatch(0);
        data1.setTitlel("recipe1");

        SearchRecipeResponseData data2 = new SearchRecipeResponseData();
        data2.setCreateTime(1);
        data2.setFavorite(true);
        data2.setNFavorite(0);
        data2.setPercentMatch(0);
        data2.setTitlel("recipe2");

        List<SearchRecipeResponseData> list = new ArrayList<>();
        list.add(data1);
        list.add(data2);

        Collections.sort(list);

        assertEquals("recipe2", list.get(0).getTitlel());
        assertEquals("recipe1", list.get(1).getTitlel());
    }

    @Test
    public void testCompareFavorite() {
        SearchRecipeResponseData data1 = new SearchRecipeResponseData();
        data1.setCreateTime(0);
        data1.setFavorite(false);
        data1.setNFavorite(0);
        data1.setPercentMatch(0);
        data1.setTitlel("recipe1");

        SearchRecipeResponseData data2 = new SearchRecipeResponseData();
        data2.setCreateTime(0);
        data2.setFavorite(true);
        data2.setNFavorite(0);
        data2.setPercentMatch(0);
        data2.setTitlel("recipe2");

        List<SearchRecipeResponseData> list = new ArrayList<>();
        list.add(data1);
        list.add(data2);

        Collections.sort(list);

        assertEquals("recipe2", list.get(0).getTitlel());
        assertEquals("recipe1", list.get(1).getTitlel());
    }

    @Test
    public void testCompareNFavorite() {
        SearchRecipeResponseData data1 = new SearchRecipeResponseData();
        data1.setCreateTime(0);
        data1.setFavorite(false);
        data1.setNFavorite(0);
        data1.setPercentMatch(0);
        data1.setTitlel("recipe1");

        SearchRecipeResponseData data2 = new SearchRecipeResponseData();
        data2.setCreateTime(0);
        data2.setFavorite(false);
        data2.setNFavorite(1);
        data2.setPercentMatch(0);
        data2.setTitlel("recipe2");

        List<SearchRecipeResponseData> list = new ArrayList<>();
        list.add(data1);
        list.add(data2);

        Collections.sort(list);

        assertEquals("recipe2", list.get(0).getTitlel());
        assertEquals("recipe1", list.get(1).getTitlel());
    }

    @Test
    public void testComparePercentMatch() {
        SearchRecipeResponseData data1 = new SearchRecipeResponseData();
        data1.setCreateTime(100);
        data1.setFavorite(true);
        data1.setNFavorite(12);
        data1.setPercentMatch(50);
        data1.setTitlel("recipe1");

        SearchRecipeResponseData data2 = new SearchRecipeResponseData();
        data2.setCreateTime(100);
        data2.setFavorite(true);
        data2.setNFavorite(13);
        data2.setPercentMatch(60);
        data2.setTitlel("recipe2");

        List<SearchRecipeResponseData> list = new ArrayList<>();
        list.add(data1);
        list.add(data2);

        Collections.sort(list);

        assertEquals("recipe2", list.get(0).getTitlel());
        assertEquals("recipe1", list.get(1).getTitlel());
    }
}
