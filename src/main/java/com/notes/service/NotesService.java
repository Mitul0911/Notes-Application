package com.notes.service;

import com.notes.domain.NotesDomain;
import com.notes.domain.SharedNotesDomain;
import com.notes.domain.UserDomain;
import com.notes.domain.query.QNotesDomain;
import com.notes.domain.query.QSharedNotesDomain;
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
public class NotesService implements ResponseHandler {

    @Autowired
    NotesTools notesTools;

    @Autowired
    UserTools userTools;

    public Response fetchAllNotes(String userName) {
        Response response = new Response();
        if (StringUtils.isNotBlank(userName)) {
            UserDomain userDomain = new QUserDomain().userName.eq(userName).findOne();
            if (userDomain != null) {
                List<NotesDomain> notesDomainList = new QNotesDomain().user.userName.eq(userName).findList();
                List<NotesDto> notes = notesTools.convertNotesDomainListToList(notesDomainList);

                List<SharedNotesDomain> sharedNotesDomainList = new QSharedNotesDomain().sharedUser.userName.eq(userName).findList();
                notes.addAll(notesTools.convertSharedNotesDomainToDto(sharedNotesDomainList));

                ResponseObjectDto responseObjectDto = userTools.mapAllDetails(userDomain);
                responseObjectDto.setNotes(notes);

                response.setStatus(200);
                response.setSuccessful(true);
                response.setResponseObject(notes);
            } else {
                addErrorCodeToResponse(response, 400, "InvalidRequest", "Invalid username");
            }
        } else {
            addErrorCodeToResponse(response, 400, "UsernameMissing", "Username not present");
        }

        return response;
    }

    public Response fetchNote(long id, String userName) {
        Response response = new Response();
        NotesDomain domain = new QNotesDomain().id.eq(id).user.userName.eq(userName).findOne();
        if (domain == null) {
            SharedNotesDomain sharedNotesDomain = new QSharedNotesDomain().sharedUser.userName.eq(userName).note.id.eq(id).findOne();
            if (sharedNotesDomain != null) {
                domain = sharedNotesDomain.getNote();
            }
        }
        if (domain != null) {
            NotesDto dto = notesTools.convertNotesDomainToDto(domain);

            ResponseObjectDto responseObjectDto = new ResponseObjectDto();
            responseObjectDto.setNotes(List.of(dto));

            response.setSuccessful(true);
            response.setStatus(200);
            response.setResponseObject(responseObjectDto);
        } else {
            addErrorCodeToResponse(response, 400, "InvalidRequest", "Invalid request");
        }

        return response;
    }

    public Response createNote(RequestDto dto) {
        Response response = new Response();
        if (dto != null) {
            String userName = dto.getUserName();
            String description = dto.getDescription();
            if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(description)) {
                UserDomain userDomain = new QUserDomain().userName.eq(userName).findOne();
                if (userDomain != null) {
                    NotesDomain domain = new NotesDomain();
                    domain.setDescription(description);
                    domain.setUser(userDomain);
                    domain.save();

                    response.setSuccessful(true);
                    response.setStatus(200);
                } else {
                    addErrorCodeToResponse(response, 400, "InvalidRequest", "Invalid username");
                }
            } else {
                addErrorCodeToResponse(response, 400, "MissingDetails", "Fill all required information");
            }
        }

        return response;
    }

    public Response updateNote(RequestDto dto) {
        Response response = new Response();
        if (dto != null) {
            String description = dto.getDescription();
            if (StringUtils.isNotBlank(description)) {
                NotesDomain domain = new QNotesDomain().id.eq(dto.getId()).user.userName.eq(dto.getUserName()).findOne();
                if (domain != null) {
                    domain.setDescription(description);
                    domain.save();

                    response.setSuccessful(true);
                    response.setStatus(200);
                } else {
                    addErrorCodeToResponse(response, 400, "InvalidRequest", "Invalid id");
                }
            } else {
                addErrorCodeToResponse(response, 400, "MissingDetails", "Fill all required information");
            }
        }
        return response;
    }

    public Response deleteNote(RequestDto dto) {
        Response response = new Response();
        if (dto != null) {
            NotesDomain domain = new QNotesDomain().id.eq(dto.getId()).findOne();
            if (domain != null) {
                domain.delete();
                response.setStatus(200);
                response.setSuccessful(true);
            } else {
                addErrorCodeToResponse(response, 400, "InvalidRequest", "Invalid id");
            }
        }
        return response;
    }

    public Response shareNote(RequestDto dto) {
        Response response = new Response();
        if (dto != null) {
            String sharedUserName = dto.getSharedUserName();
            long id = dto.getId();
            if (StringUtils.isNotBlank(sharedUserName)) {
                NotesDomain notesDomain = new QNotesDomain().id.eq(id).findOne();
                if (notesDomain != null) {
                    UserDomain sharedUser = new QUserDomain().userName.eq(sharedUserName).findOne();
                    if (sharedUser != null) {
                        if (!new QSharedNotesDomain().note.id.eq(id).sharedUser.userName.eq(sharedUserName).exists()) {
                            SharedNotesDomain domain = new SharedNotesDomain();
                            domain.setNote(notesDomain);
                            domain.setSharedUser(sharedUser);
                            domain.save();

                            response.setSuccessful(true);
                            response.setStatus(200);
                        } else {
                            addErrorCodeToResponse(response, 400, "InvalidRequest", "Already shared the note " + dto.getId() + " with username " + sharedUserName);
                        }
                    } else {
                        addErrorCodeToResponse(response, 400, "InvalidRequest", "Invalid username");
                    }
                } else {
                    addErrorCodeToResponse(response, 400, "InvalidRequest", "Invalid id");
                }
            } else {
                addErrorCodeToResponse(response, 400, "MissingDetail", "Username to share with missing");
            }
        }

        return response;
    }
}
