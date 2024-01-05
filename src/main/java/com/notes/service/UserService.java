package com.notes.service;

import com.notes.domain.UserDomain;
import com.notes.domain.query.QUserDomain;
import com.notes.dto.RequestDto;
import com.notes.dto.ResponseObjectDto;
import com.notes.dto.UserDto;
import com.notes.response.Response;
import com.notes.response.ResponseHandler;
import com.notes.tools.JwtTools;
import com.notes.tools.UserTools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements ResponseHandler {

    @Autowired
    UserTools userTools;

    @Autowired
    JwtTools jwtTools;

    public Response registerUser(RequestDto dto) {
        Response response = new Response();
        if (dto != null) {
            String userName = dto.getUserName();
            String firstName = dto.getFirstName();
            String password = dto.getPassword();
            String confirmPassword = dto.getConfirmPassword();
            if (StringUtils.isNotBlank(userName)
                    && StringUtils.isNotBlank(firstName)
                    && StringUtils.isNotBlank(password)
                    && StringUtils.isNotBlank(confirmPassword)) {
                if (!new QUserDomain().userName.eq(userName).exists()) {
                    if (password.equals(confirmPassword)) {
                        UserDomain userDomain = new UserDomain();
                        userDomain.setFirstName(firstName);
                        userDomain.setLastName(dto.getLastName());
                        userDomain.setUserName(userName);
                        userDomain.setPassword(password);
                        userDomain.save();

                        ResponseObjectDto responseObjectDto = userTools.mapAllDetails(userDomain);
                        responseObjectDto.setToken(jwtTools.createJwtToken(userDomain));

                        response.setStatus(200);
                        response.setSuccessful(true);
                        response.setResponseObject(responseObjectDto);
                    } else {
                        addErrorCodeToResponse(response, 400, "InvalidRequest", "Password doesn't match");
                    }
                } else {
                    addErrorCodeToResponse(response, 400, "InvalidRequest", "Username already exists");
                }
            } else {
                addErrorCodeToResponse(response, 400, "MissingDetails", "Fill all required information");
            }
        }

        return response;
    }

    public Response checkLoginCredentials(RequestDto dto) {
        Response response = new Response();
        if (dto != null) {
            String userName= dto.getUserName();
            String password = dto.getPassword();
            if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
                UserDomain domain = new QUserDomain().userName.eq(userName).findOne();
                if (domain != null) {
                    if (domain.getPassword().equals(password)) {
                        ResponseObjectDto responseObjectDto = userTools.mapAllDetails(domain);
                        responseObjectDto.setToken(jwtTools.createJwtToken(domain));

                        response.setSuccessful(true);
                        response.setStatus(200);
                        response.setResponseObject(responseObjectDto);
                    } else {
                        addErrorCodeToResponse(response, 400, "WrongPassword", "Entered password is incorrect");
                    }
                } else {
                    addErrorCodeToResponse(response, 400, "InvalidRequest", "Username doesn't exist");
                }
            } else {
                addErrorCodeToResponse(response, 400, "MissingDetails", "Fill all required information");
            }
        }

        return response;
    }
}
