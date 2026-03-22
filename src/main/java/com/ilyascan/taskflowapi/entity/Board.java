package com.ilyascan.taskflowapi.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID boardId;

    private String boardName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "board_owner_id",
            referencedColumnName = "userId",
            nullable = false
    )
    private User owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "board_members",
            joinColumns = @JoinColumn(name = "board_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> members = new ArrayList<>();

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ListColumn> listColumns = new ArrayList<>();

    private String boardDescription;

    private Date createdAt;


    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
    }
}
