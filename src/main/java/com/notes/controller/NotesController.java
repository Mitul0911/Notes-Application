package com.notes.controller;

import com.notes.dto.RequestDto;
import com.notes.response.Response;
import com.notes.service.NotesService;
import com.notes.tools.JwtTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

    private final String ID_URL = "/{id}";
    private final String TOKEN = "verify_token";
    private final String USERNAME = "username";

    @Autowired
    NotesService notesService;

    @Autowired
    JwtTools jwtTools;

    @GetMapping
    public Response fetchAllNotes(@RequestHeader(name = TOKEN) String token,
                                  @RequestHeader(name = USERNAME) String userName) {
        Response response = new Response();
        if (jwtTools.validateToken(token, userName, response)) {
            response = notesService.fetchAllNotes(userName);
        }
        return response;
    }

    @GetMapping(ID_URL)
    public Response fetchNote(@RequestHeader(name = TOKEN) String token,
                              @RequestHeader(name = USERNAME) String userName,
                              @PathVariable long id) {
        Response response = new Response();
        if (jwtTools.validateToken(token, userName, response)) {
            response = notesService.fetchNote(id, userName);
        }
        return response;
    }

    @PostMapping
    public Response createNote(@RequestHeader(name = TOKEN) String token,
                               @RequestHeader(name = USERNAME) String userName,
                               @RequestBody RequestDto dto) {
        Response response = new Response();
        if (jwtTools.validateToken(token, userName, response)) {
            dto.setUserName(userName);
            response = notesService.createNote(dto);
        }
        return response;
    }

    @PutMapping(ID_URL)
    public Response editNote(@RequestHeader(name = TOKEN) String token,
                             @RequestHeader(name = USERNAME) String userName,
                             @PathVariable long id,
                             @RequestBody RequestDto dto) {
        Response response = new Response();
        if (jwtTools.validateToken(token, userName, response)) {
            dto.setUserName(userName);
            dto.setId(id);
            response = notesService.updateNote(dto);
        }
        return response;
    }

    @DeleteMapping
    public Response deleteNote(@RequestHeader(name = TOKEN) String token,
                               @RequestHeader(name = USERNAME) String userName,
                               @RequestBody RequestDto dto) {
        Response response = new Response();
        if (jwtTools.validateToken(token, userName, response)) {
            dto.setUserName(userName);
            response = notesService.deleteNote(dto);
        }
        return response;
    }

    @PostMapping(ID_URL + "/share")
    public Response shareNote(@RequestHeader(name = TOKEN) String token,
                              @RequestHeader(name = USERNAME) String userName,
                              @RequestBody RequestDto dto,
                              @PathVariable long id) {
        Response response = new Response();
        if (jwtTools.validateToken(token, userName, response)) {
            dto.setUserName(userName);
            dto.setId(id);
            response = notesService.shareNote(dto);
        }

        return response;
    }

}
