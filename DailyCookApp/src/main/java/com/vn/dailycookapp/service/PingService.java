package com.vn.dailycookapp.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.FileUtils;
import org.json.JsonTransformer;

import com.vn.dailycookapp.entity.response.DCAResponse;
import com.vn.dailycookapp.service.mediatypeopen.MediaTypeWithUtf8;

@Path("/dailycook")
public class PingService {
	
	@GET
	@Path("/ping")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ping() {
		
		String msg = "t-" + System.currentTimeMillis();
		DCAResponse dcaResponse = new DCAResponse(0);
		dcaResponse.setData(msg);
		
		return Response.ok(JsonTransformer.getInstance().marshall(dcaResponse)).status(Response.Status.OK).build();
	}

	@GET
	@Path("/policy")
	@Produces(MediaTypeWithUtf8.TEXT_HTML_UTF8)
	public Response demo() {
		FileUtils fileUtils = new FileUtils();
		String policy = fileUtils.readFile(ClassLoader.getSystemClassLoader().getResourceAsStream("policy.txt"));
		
		return Response.ok(policy).status(Response.Status.OK).build();
	}
}
