package net.pop.taskemer.app.data.model.entities;

import jakarta.persistence.*;
import lombok.*;
import net.pop.taskemer.app.data.model.enums.ProjectPriority;
import net.pop.taskemer.app.data.model.enums.ProjectStatus;
import net.pop.taskemer.auth.entities.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    private ProjectPriority priority;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    // Assuming the User entity from the auth system is mapped here
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
