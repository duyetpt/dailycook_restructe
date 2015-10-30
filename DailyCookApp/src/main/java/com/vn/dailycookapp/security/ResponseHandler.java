package com.vn.dailycookapp.security;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@Priority(Priorities.HEADER_DECORATOR)
public class ResponseHandler implements ContainerResponseFilter {
	
	private final Logger	logger	= LoggerFactory.getLogger(getClass());
	
	@Override
	public void filter(ContainerRequestContext reqeust, ContainerResponseContext responseContext) throws IOException {
		
		responseContext.setStatusInfo(Status.OK);

		responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
		responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
		responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
		responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		
		logger.info("			=====================  End reqeust  =====================			");
	}
}
