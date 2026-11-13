package com.hr_nexus.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "emails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmalLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recepientEmail;

    @Column(length = 500)
    private String subject;

    @Column(length = 5000)
    private String body;

    private boolean sentStatus;

    private LocalDateTime createdAt = LocalDateTime.now();

    public EmalLogs(
            String recepientEmail,
            String subject,
            String body,
            boolean sentStatus) {

        this.recepientEmail = recepientEmail;
        this.subject = subject;
        this.body = body;
        this.sentStatus = sentStatus;
    }
}
