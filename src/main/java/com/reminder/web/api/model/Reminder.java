package com.reminder.web.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reminder", schema = "public")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Reminder {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "remindersIdSeq", sequenceName = "reminders_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "remindersIdSeq")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "place_name")
    private String placeName;

    @Column(name = "cr_date")
    private LocalDate date;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;
}
