package com.ilyascan.taskflowapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID taskId;

    private String taskTitle;

    private String taskDescription;

    private Date taskStartTime;

    private Date taskEndTime;

    private boolean  completed;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "userId")
    private User user;

    @PrePersist
    public void prePersist() {
        completed = false;
        taskStartTime = new Date();
    }

}
