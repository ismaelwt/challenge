package com.project.filter;

import org.jboss.resteasy.spi.CorsHeaders;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class CorsFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        if(containerRequestContext.getMethod().equalsIgnoreCase("options") || containerRequestContext.getMethod().equalsIgnoreCase("option")){
            handle(containerRequestContext);
        }
    }

    private void handle(ContainerRequestContext requestContext) throws IOException {
        final Response.ResponseBuilder response = Response.ok();

        String requestHeaders = requestContext.getHeaderString(CorsHeaders.ACCESS_CONTROL_REQUEST_HEADERS);
        String requestMethods = requestContext.getHeaderString(CorsHeaders.ACCESS_CONTROL_REQUEST_METHOD);

        if (requestHeaders != null) {
            response.header(CorsHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders);
        }else{
            response.header(CorsHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "x-requested-with, Content-Type, origin, authorization, accept, client-security-token");
        }

        if (requestMethods != null) {
            response.header(CorsHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethods);
        }else{
            response.header(CorsHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, OPTIONS, DELETE, PUT, PATCH");
        }

        response.header(CorsHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.header(CorsHeaders.ACCESS_CONTROL_MAX_AGE, 600);

        requestContext.abortWith(response.build());
    }
}
