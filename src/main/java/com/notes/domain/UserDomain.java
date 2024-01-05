package com.notes.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.ebean.annotation.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class UserDomain extends BaseDomain{

    @NotNull
    String firstName;

    String lastName;

    @Column(unique = true, nullable = false)
    String userName;

    @NotNull
    String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference()
    List<NotesDomain> notes;

    @OneToMany(mappedBy = "sharedUser", cascade = CascadeType.ALL)
    @JsonManagedReference()
    List<SharedNotesDomain> sharedNotes;
}
