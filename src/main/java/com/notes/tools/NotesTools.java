package com.notes.tools;

import com.notes.domain.NotesDomain;
import com.notes.domain.SharedNotesDomain;
import com.notes.dto.NotesDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NotesTools {

    public NotesDto convertNotesDomainToDto(NotesDomain domain) {
        NotesDto dto = new NotesDto();
        dto.setId(domain.getId());
        dto.setDescription(domain.getDescription());
        return dto;
    }

    public List<NotesDto> convertSharedNotesDomainToDto(List<SharedNotesDomain> sharedNotesDomainList) {
        List<NotesDto> notes = new ArrayList<>();
        for (SharedNotesDomain note: sharedNotesDomainList) {
            notes.add(convertNotesDomainToDto(note.getNote()));
        }
        return notes;
    }

    public List<NotesDto> convertNotesDomainListToList(List<NotesDomain> notesDomainList) {
        List<NotesDto> notes = new ArrayList<>();
        for (NotesDomain note : notesDomainList) {
            notes.add(convertNotesDomainToDto(note));
        }

        return notes;
    }
}
