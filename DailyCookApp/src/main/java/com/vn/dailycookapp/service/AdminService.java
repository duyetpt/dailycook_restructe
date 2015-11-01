package com.vn.dailycookapp.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JsonTransformer;

import com.vn.dailycookapp.security.session.SessionManager;

@Path("/dailycook/admin/putin93/")
public class AdminService {
	
	@GET
	@Path("list/session")
	@Produces(MediaType.APPLICATION_JSON)
	public String listSession() {
		String result = null;
		result = JsonTransformer.getInstance().marshall(SessionManager.getInstance().getAllSession());
		return result;
	}
}
