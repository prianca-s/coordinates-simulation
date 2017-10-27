package com.locus.project.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * @author priyanka_s [priyanka.singh@zoomcar.com]
 *         Part of locusProject
 *         on 26/10/17.
 */
public class BusinessException extends WebApplicationException {
    public BusinessException() {
    }
    public BusinessException(String message, Response.Status status) {
        super(message, status);
    }

    public BusinessException(String message, int status) {
        super(message, status);
    }

    public BusinessException(Response.Status status){
        super(status);
    }

    public BusinessException(int status){
        super(status);
    }
}