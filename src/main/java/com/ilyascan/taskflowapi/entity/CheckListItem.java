package com.ilyascan.taskflowapi.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID chechListId;

    @Column(nullable = false)
    private String text;

    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    private Integer position;

    @PrePersist
    public void prePersist()
    {
        completed = false;
    }
}
