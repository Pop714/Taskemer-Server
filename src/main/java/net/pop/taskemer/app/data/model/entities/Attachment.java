package net.pop.taskemer.app.data.model.entities;

import jakarta.persistence.*;
import lombok.*;
import net.pop.taskemer.app.data.model.enums.AttachmentType;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attachments")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private AttachmentType type;

    private String url;

    @Column(name = "file_path")
    private String filePath;

    @CreationTimestamp
    @Column(name = "added_in", updatable = false)
    private LocalDateTime addedIn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    // Lifecycle hook to strictly enforce that it is either a file or a URL, but not both.
    @PrePersist
    @PreUpdate
    private void validateAttachmentType() {
        if (type == AttachmentType.FILE) {
            if (filePath == null || filePath.trim().isEmpty()) {
                throw new IllegalStateException("File attachments must contain a file_path.");
            }
            this.url = null; // Ensure URL is wiped if type is FILE
        } else if (type == AttachmentType.URL) {
            if (url == null || url.trim().isEmpty()) {
                throw new IllegalStateException("URL attachments must contain a url.");
            }
            this.filePath = null; // Ensure file_path is wiped if type is URL
        }
    }
}
