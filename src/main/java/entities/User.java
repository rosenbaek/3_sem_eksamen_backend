package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "users")
@NamedQuery(name = "User.deleteAllRows", query = "DELETE from User")
public class User implements Serializable {

    @Size(max = 255)
    @Column(name = "user_pass")
    private String userPass;
    
    @OneToMany(mappedBy = "user")
    private List<Car> carsList = new ArrayList<>();

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_name", length = 25)
    private String userName;
    
    @JoinTable(name = "user_roles", joinColumns = {
      @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
      @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
    @ManyToMany
    private List<Role> roleList = new ArrayList<>();

    public List<String> getRolesAsStrings() {
      if (roleList.isEmpty()) {
        return null;
      }
      List<String> rolesAsStrings = new ArrayList<>();
      roleList.forEach((role) -> {
          rolesAsStrings.add(role.getRoleName());
        });
      return rolesAsStrings;
    }

    public User() {}

    //pw is plain password
    public boolean verifyPassword(String pw) {
        return (BCrypt.checkpw(pw, userPass));
    }

    public User(String userName, String userPass) {
        this.userName = userName;
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }

    public String getUserName() {
      return userName;
    }

    public void setUserName(String userName) {
      this.userName = userName;
    }

    public String getUserPass() {
      return this.userPass;
    }

    public void setUserPass(String userPass) {
      this.userPass = userPass;
    }

    public List<Role> getRoleList() {
      return roleList;
    }

    public void setRoleList(List<Role> roleList) {
      this.roleList = roleList;
    }

    public void addRole(Role userRole) {
      roleList.add(userRole);
    }

    
    public List<Car> getCarsList() {
        return carsList;
    }

    public void addCar(Car car) {
        this.carsList.add(car);
        car.setUser(this);
    }

}
