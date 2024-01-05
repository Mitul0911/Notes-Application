package com.notes.service;

import com.notes.domain.NotesDomain;
import com.notes.domain.UserDomain;
import com.notes.domain.query.QNotesDomain;
import com.notes.domain.query.QUserDomain;
import com.notes.dto.NotesDto;
import com.notes.dto.RequestDto;
import com.notes.dto.ResponseObjectDto;
import com.notes.response.Response;
import com.notes.response.ResponseHandler;
import com.notes.tools.NotesTools;
import com.notes.tools.UserTools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService implements ResponseHandler {

    @Autowired
    NotesTools notesTools;

    @Autowired
    UserTools userTools;

    public Response searchNotes(String userName, String query) {
        Response response = new Response();
        if (StringUtils.isNotBlank(userName)) {
            UserDomain userDomain = new QUserDomain().userName.eq(userName).findOne();
            if (userDomain != null) {
                List<NotesDomain> notesDomainList = new QNotesDomain().user.eq(userDomain).where().description.icontains(query).findList();
                List<NotesDto> notesDtoList = notesTools.convertNotesDomainListToList(notesDomainList);

                ResponseObjectDto responseObjectDto = userTools.mapAllDetails(userDomain);
                responseObjectDto.setNotes(notesDtoList);

                response.setStatus(200);
                response.setSuccessful(true);
                response.setResponseObject(responseObjectDto);
            } else {
                addErrorCodeToResponse(response, 400, "InvalidRequest", "no user by username: " + userName);
            }
        } else {
            addErrorCodeToResponse(response, 400, "MissingDetail", "Username is missing");
        }

        return response;
    }
}
