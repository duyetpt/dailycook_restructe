/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.security.authentication;

import com.vn.dailycookapp.utils.DCAException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

/**
 *
 * @author duyetpt
 */
public class BanedUserException extends DCAException {

    private static final long serialVersionUID = 1L;

    public BanedUserException(ErrorCodeConstant error) {
        setDescription(error.getMessage());
        setErrorCode(error.getErrorCode());
    }
}
