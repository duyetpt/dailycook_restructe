/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.entity.response;

import java.util.List;
import org.json.JsonIgnoreEmpty;

/**
 *
 * @author duyetpt
 */
@JsonIgnoreEmpty
public class SearchResponseData {

    private int resultNumber;
    private List<SearchRecipeResponseData> recipes;
    private List<SearchUserResponseData> users;

    public int getResultNumber() {
        return resultNumber;
    }

    public void setResultNumber(int resultNumber) {
        this.resultNumber = resultNumber;
    }

    public List<SearchRecipeResponseData> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<SearchRecipeResponseData> recipes) {
        this.recipes = recipes;
    }

    public List<SearchUserResponseData> getUsers() {
        return users;
    }

    public void setUsers(List<SearchUserResponseData> users) {
        this.users = users;
    }

}
