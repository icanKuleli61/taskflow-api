package com.ilyascan.taskflowapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private ListColumn listColumn;

    @OneToMany(mappedBy = "task",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<CheckListItem> checklistItem;

    @PrePersist
    public void prePersist() {
        taskStartTime = new Date();
    }

}
