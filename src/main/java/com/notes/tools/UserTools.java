package com.notes.tools;

import com.notes.domain.UserDomain;
import com.notes.dto.ResponseObjectDto;
import com.notes.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserTools {

    public ResponseObjectDto mapAllDetails(UserDomain userDomain) {
        ResponseObjectDto dto = new ResponseObjectDto();
        dto.setUserDetails(mapUserDomainToDto(userDomain));
        return dto;
    }

    private UserDto mapUserDomainToDto(UserDomain userDomain) {
        UserDto dto = new UserDto();
        dto.setUserName(userDomain.getUserName());
        dto.setFirstName(userDomain.getFirstName());
        dto.setLastName(userDomain.getLastName());
        return dto;
    }
}
