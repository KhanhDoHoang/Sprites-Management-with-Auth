package cst8218.assignment2.entity;

import java.io.Serializable;
import java.util.HashMap;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.validation.constraints.NotNull;

/**
 * This is a app user model, used for security authentication
 * It contains 3 requirements fields userId, password and group
 * @author Hoang Do, Minh Duc
 * @version 1.0
 */

@Entity
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return "";
    }

    public void setPassword(String password) {
        if(password.equals("")){
            return ;
        }
        // initialize a PasswordHash object which will generate password hashes
        Instance<? extends PasswordHash> instance = CDI.current().select(Pbkdf2PasswordHash.class);
        PasswordHash passwordHash = instance.get();
        passwordHash.initialize(new HashMap<String, String>());  // todo: are the defaults good enough?
        // now we can generate a password entry for a given password
        String passwordEntry = password;
        passwordEntry = passwordHash.generate(passwordEntry.toCharArray());
        //at this point, passwordEntry refers to a salted/hashed password entry corresponding to mySecretPassword
        this.password = passwordEntry;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
    @NotNull
    private String password;
    @NotNull
    private String groupname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppUser)) {
            return false;
        }
        AppUser other = (AppUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cst8218.assignment2.entity.AppUser[ id=" + id + " ]";
    }
    
}
