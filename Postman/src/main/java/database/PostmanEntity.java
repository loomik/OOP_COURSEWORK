package database;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "postman", schema = "mail")
public class PostmanEntity {
    private int idPostman;
    private String lastName;
    private String firstName;
    private Date birthdayDate;

    @Id
    @Column(name = "idPostman")
    public int getIdPostman() {
        return idPostman;
    }

    public void setIdPostman(int idPostman) {
        this.idPostman = idPostman;
    }

    @Basic
    @Column(name = "LastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "FirstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "BirthdayDate")
    public Date getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(Date birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostmanEntity that = (PostmanEntity) o;

        if (idPostman != that.idPostman) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (birthdayDate != null ? !birthdayDate.equals(that.birthdayDate) : that.birthdayDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPostman;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (birthdayDate != null ? birthdayDate.hashCode() : 0);
        return result;
    }
}