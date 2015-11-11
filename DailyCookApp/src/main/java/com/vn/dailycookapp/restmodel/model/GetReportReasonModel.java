/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.restmodel.model;

import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.utils.ErrorCodeConstant;
import com.vn.dailycookapp.utils.lang.Language;
import java.util.List;

/**
 *
 * @author duyetpt
 */
public class GetReportReasonModel extends AbstractModel {

    private String language;

    @Override
    protected void preExecute(String... data) throws Exception {
        language = data[0];
    }

    @Override
    protected DCAResponse execute() throws Exception {
        DCAResponse response = new DCAResponse(ErrorCodeConstant.SUCCESSUL.getErrorCode());
        List<String> reasons = Language.getInstance().listReportReason(language);

        response.setData(reasons);

        return response;
    }

}
