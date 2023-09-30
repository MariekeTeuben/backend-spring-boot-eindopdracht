package nl.novi.backendgarageservice.model;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column(nullable = false)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
