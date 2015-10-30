package com.vn.dailycookapp.restmodel.model;

import java.util.List;

import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;
import com.vn.dailycookapp.restmodel.InvalidParamException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;
import com.vn.dailycookapp.utils.lang.Language;

public class GetUnitsModel extends AbstractModel {
	private String	language;
	
	@Override
	protected void preExecute(String... data) throws Exception {
		try {
			language = data[0];
		} catch (Exception ex) {
			throw new InvalidParamException();
		}
		
	}
	
	@Override
	protected DCAResponse execute() throws Exception {
		DCAResponse response = new DCAResponse();
		List<String> units = Language.getInstance().listUnit(language);
		response.setData(units);
		response.setError(ErrorCodeConstant.SUCCESSUL.getErrorCode());
		return response;
	}
	
}
