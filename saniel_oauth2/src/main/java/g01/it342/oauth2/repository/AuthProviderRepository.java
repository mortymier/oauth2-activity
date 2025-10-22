package g01.it342.oauth2.repository;

import g01.it342.oauth2.entity.AuthProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AuthProviderRepository extends JpaRepository<AuthProviderEntity, Integer>
{
    public Optional<AuthProviderEntity> findByProviderUserId(String providerUserId);
    public Optional<AuthProviderEntity> findByProviderEmail(String providerEmail);
    Optional<AuthProviderEntity> findByProviderAndProviderUserId(AuthProviderEntity.Provider provider, String providerUserId);
}
