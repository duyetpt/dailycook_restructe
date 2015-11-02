package com.vn.dailycookapp.restmodel.model;

import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.restmodel.AbstractModel;

public class GetPlanMeal extends AbstractModel {
	
	@Override
	protected void preExecute(String... data) throws Exception {
		myId = data[0];
	}
	
	@Override
	protected DCAResponse execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
