package g01.it342.oauth2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_authproviders")
public class AuthProviderEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "providerid")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    public enum Provider { GOOGLE, GITHUB }

    public AuthProviderEntity()
    {
        super();
    }

    private String providerUserId;
    private String providerEmail;

    public AuthProviderEntity(int id, UserEntity user, Provider provider, String providerUserId,String providerEmail)
    {
        super();
        this.id = id;
        this.user = user;
        this.provider = provider;
        this.providerUserId = providerUserId;
        this.providerEmail = providerEmail;
    }

    public UserEntity getUser() { return user; }

    public Provider getProvider() { return provider; }

    public String getProviderUserId() { return providerUserId; }

    public String getProviderEmail() { return providerEmail; }

    public void setUser(UserEntity user)
    {
        this.user = user;
    }

    public void setProvider(Provider provider)
    {
        this.provider = provider;
    }

    public void setProviderUserId(String providerUserId)
    {
        this.providerUserId = providerUserId;
    }

    public void setProviderEmail(String providerEmail)
    {
        this.providerEmail = providerEmail;
    }
}
