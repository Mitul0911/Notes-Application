package com.notes.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.ebean.annotation.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "shared_notes")
public class SharedNotesDomain extends BaseDomain {
    @ManyToOne(optional = false)
    @NotNull
    @JsonBackReference
    NotesDomain note;

    @ManyToOne(optional = false)
    @NotNull
    @JsonBackReference
    UserDomain sharedUser;


}
