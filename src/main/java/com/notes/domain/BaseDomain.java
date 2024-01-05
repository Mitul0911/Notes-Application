package com.notes.domain;

import io.ebean.Model;
import io.ebean.annotation.SoftDelete;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.Instant;

@MappedSuperclass
@Data
public abstract class BaseDomain extends Model {
    @Id
    Long id;
    @Version
    long version;

    @WhenCreated
    Instant whenCreated;

    @WhenModified
    Instant lastModified;

    @SoftDelete
    boolean deleted;
}
