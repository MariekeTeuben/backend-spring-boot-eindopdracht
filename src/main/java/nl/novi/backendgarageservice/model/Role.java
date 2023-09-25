package nl.novi.backendgarageservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.Collection;

@Entity
@Table(name = "roles")
public class Role {

    @Id
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
