package g01.it342.oauth2.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_users")
public class UserEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private int id;

    private String email;
    private String displayName;
    private String avatarUrl;
    private String bio;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserEntity()
    {
        super();
    }

    public UserEntity(int id, String email, String displayName, String avatarUrl, String bio, LocalDateTime createdAt, LocalDateTime updatedAt)
    {
        super();
        this.id = id;
        this.email = email;
        this.displayName = displayName;
        this.avatarUrl = avatarUrl;
        this.bio = bio;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    public void onCreate()
    {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate()
    {
        updatedAt = LocalDateTime.now();
    }

    public String getEmail() { return email; }

    public String getDisplayName() { return displayName; }

    public String getAvatarUrl() { return avatarUrl; }

    public String getBio() { return bio; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public void setAvatarUrl(String avatarUrl)
    {
        this.avatarUrl = avatarUrl;
    }

    public void setBio(String bio)
    {
        this.bio = bio;
    }

    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt)
    {
        this.updatedAt = updatedAt;
    }
}
