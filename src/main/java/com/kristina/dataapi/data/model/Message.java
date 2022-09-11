package com.kristina.dataapi.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kristina.dataapi.dialog.model.Dialog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Language language;

    private String text;

    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime createDateTime;

    @ManyToOne
    @JoinColumn(name = "dialog_id")
    @JsonIgnore
    Dialog dialog;
}
