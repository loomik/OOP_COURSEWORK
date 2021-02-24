package database;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "client", schema = "mail")
public class ClientEntity {
    private int idClient;
    private String clientNumber;
    private String lastname2;
    private String firstname2;
    private String telephonenumber;
    private Date deliveryDate;
    private PostmanEntity postmanByIdPostman;

    @Id
    @Column(name = "idClient")
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @Basic
    @Column(name = "ClientNumber")
    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    @Basic
    @Column(name = "Lastname2")
    public String getLastname2() {
        return lastname2;
    }

    public void setLastname2(String lastname2) {
        this.lastname2 = lastname2;
    }

    @Basic
    @Column(name = "Firstname2")
    public String getFirstname2() {
        return firstname2;
    }

    public void setFirstname2(String firstname2) {
        this.firstname2 = firstname2;
    }

    @Basic
    @Column(name = "Telephonenumber")
    public String getTelephonenumber() {
        return telephonenumber;
    }

    public void setTelephonenumber(String telephonenumber) {
        this.telephonenumber = telephonenumber;
    }

    @Basic
    @Column(name = "DeliveryDate")
    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientEntity clientEntity = (ClientEntity) o;

        if (idClient != clientEntity.idClient) return false;
        if (clientNumber != null ? !clientNumber.equals(clientEntity.clientNumber) : clientEntity.clientNumber != null)
            return false;
        if (lastname2 != null ? !lastname2.equals(clientEntity.lastname2) : clientEntity.lastname2 != null)
            return false;
        if (firstname2 != null ? !firstname2.equals(clientEntity.firstname2) : clientEntity.firstname2 != null)
            return false;
        if (telephonenumber != null ? !telephonenumber.equals(clientEntity.telephonenumber) : clientEntity.telephonenumber != null)
            return false;
        if (deliveryDate != null ? !deliveryDate.equals(clientEntity.deliveryDate) : clientEntity.deliveryDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idClient;
        result = 31 * result + (clientNumber != null ? clientNumber.hashCode() : 0);
        result = 31 * result + (lastname2 != null ? lastname2.hashCode() : 0);
        result = 31 * result + (firstname2 != null ? firstname2.hashCode() : 0);
        result = 31 * result + (telephonenumber != null ? telephonenumber.hashCode() : 0);
        result = 31 * result + (deliveryDate != null ? deliveryDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idPostman", referencedColumnName = "idPostman", nullable = false)
    public PostmanEntity getPostmanByIdPostman() {
        return postmanByIdPostman;
    }

    public void setPostmanByIdPostman(PostmanEntity postmanByIdPostman) {
        this.postmanByIdPostman = postmanByIdPostman;
    }
}

   