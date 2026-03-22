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

    private String text;

    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    private Task task;


    @PrePersist
    public void prePersist()
    {
        completed = false;
    }
}
  