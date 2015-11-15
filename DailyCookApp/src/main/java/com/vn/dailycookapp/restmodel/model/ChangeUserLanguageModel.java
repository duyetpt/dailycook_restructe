/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.restmodel.model;

import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.utils.ErrorCodeConstant;
import org.dao.UserDAO;

/**
 *
 * @author duyetpt
 */
public class ChangeUserLanguageModel extends AbstractModel{

    private String lang;
    @Override
    protected void preExecute(String... data) throws Exception {
        myId = data[0];
        lang = data[1];
    }

    @Override
    protected DCAResponse execute() throws Exception {
        DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
        UserDAO.getInstance().updateLanguage(myId, lang);
        return response;
    }
    
}
