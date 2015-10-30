package com.vn.dailycookapp.restmodel;

import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelResolver {
	
	private static final Logger								logger	= LoggerFactory.getLogger(ModelResolver.class);
	private static Hashtable<ModelDefine, AbstractModel>	mapedApi;
	static {
		mapedApi = new Hashtable<>();
		ModelDefine[] models = ModelDefine.values();
		for (ModelDefine model : models) {
			try {
				mapedApi.put(model, model.getModel().newInstance());
			} catch (InstantiationException e) {
				logger.error("init ModelResolver errro", e);
			} catch (IllegalAccessException e) {
				logger.error("init ModelResolver errro", e);
			}
		}
	}
	
	public static AbstractModel getApi(ModelDefine api) {
		return mapedApi.get(api);
	}
}
