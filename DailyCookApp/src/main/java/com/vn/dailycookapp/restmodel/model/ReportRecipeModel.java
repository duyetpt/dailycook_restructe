/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.restmodel.model;

import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;
import org.dao.RecipeDAO;
import org.dao.ReportDAO;
import org.dao.UserDAO;
import org.entity.Recipe;
import org.entity.Report;
import org.json.JsonTransformer;

/**
 *
 * @author duyetpt
 */
public class ReportRecipeModel extends AbstractModel {

    private Report report;

    @Override
    protected void preExecute(String... data) throws InvalidParamException {
        myId = data[0];
        report = JsonTransformer.getInstance().unmarshall(data[1], Report.class);
    }

    @Override
    protected DCAResponse execute() throws Exception {
        DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
        report.setReporter(myId);
        // save to dao
        ReportDAO.getInstance().save(report);
        UserDAO.getInstance().increateReportNumber(myId);
        RecipeDAO.getInstance().updateRecipeStatus(report.getRecipe(), Recipe.REPORTED_FLAG);
        return response;
    }

}
