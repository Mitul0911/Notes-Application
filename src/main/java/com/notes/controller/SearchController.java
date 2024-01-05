package com.notes.controller;

import com.notes.dto.RequestDto;
import com.notes.response.Response;
import com.notes.service.SearchService;
import com.notes.tools.JwtTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final String TOKEN = "verify_token";
    private final String USERNAME = "username";

    @Autowired
    SearchService searchService;

    @Autowired
    JwtTools jwtTools;

    @PostMapping
    public Response searchNotes(@RequestHeader(name = TOKEN) String token,
                                @RequestHeader(name = USERNAME) String userName,
                                @RequestParam(name = "q") String query) {
        Response response = new Response();
        if (jwtTools.validateToken(token, userName, response)) {
            response = searchService.searchNotes(userName, query);
        }

        return response;
    }
}
