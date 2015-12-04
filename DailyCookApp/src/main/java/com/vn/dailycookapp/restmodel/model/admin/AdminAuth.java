/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vn.dailycookapp.restmodel.model.admin;

import com.vn.dailycookapp.entity.request.RegisterInfo;
import com.vn.dailycookapp.security.authentication.LoginFailException;
import com.vn.dailycookapp.utils.ErrorCodeConstant;
import org.EncryptDataException;
import org.EncryptHelper;
import org.dao.DAOException;
import org.dao.UserDAO;
import org.entity.User;

/**
 *
 * @author duyetpt
 */
public class AdminAuth {

    static void auth(RegisterInfo adminAcc) throws LoginFailException, DAOException, EncryptDataException {
        User user = UserDAO.getInstance().getUserInfoByEmail(adminAcc.getEmail());
        if (user == null) {
            throw new LoginFailException(ErrorCodeConstant.USER_NOT_FOUND);
//        } else if (!user.getPassword().equals(EncryptHelper.encrypt(adminAcc.getPassword()))) {
        } else if (!user.getPassword().equals(EncryptHelper.encrypt(adminAcc.getPassword())) || user.getRole().equals(User.NORMAL_USER_ROLE)) {
            throw new LoginFailException(ErrorCodeConstant.PASSWORD_INCORRECT);
        }
    }
}
