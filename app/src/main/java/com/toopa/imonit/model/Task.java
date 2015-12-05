package com.toopa.imonit.model;

import java.util.UUID;

/**
 * Created by Toopa on 24/02/2015.
 */
public class Task {

    private UUID id;

    private String description;

    private TaskStatus status;

    public Task(final String description) {
        this(UUID.randomUUID(), description);
    }

    public Task(final UUID id, final String description) {
        this(id, description, TaskStatus.POSTED);

    }

    public Task(final UUID id, final String description, final TaskStatus status) {
        this.id = id;
        this.description = description;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(final TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.description + "\t[" + this.status.toString() + "]";
    }
}
