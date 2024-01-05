package com.notes.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.ebean.annotation.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "notes")
public class NotesDomain extends BaseDomain {

    @ManyToOne(optional = false)
    @NotNull
    @JsonBackReference()
    UserDomain user;

    String description;

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    @JsonManagedReference()
    List<SharedNotesDomain> sharedNotes;
}
