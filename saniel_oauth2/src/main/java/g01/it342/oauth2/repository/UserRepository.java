package g01.it342.oauth2.repository;

import g01.it342.oauth2.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>
{
    public Optional<UserEntity> findByEmail(String email);
    public Optional<UserEntity> findByDisplayName(String displayName);
    public Optional<UserEntity> findByAvatarUrl(String avatarUrl);
}
