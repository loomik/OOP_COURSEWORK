package database;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "newspaper", schema = "mail")
public class NewspaperEntity {
    private int idNewspaper;
    private String newspaperName;
    private Date date;
    private int count;
    private String releaseNumber;
    private ClientEntity clientByIdClient;

    @Id
    @Column(name = "idNewspaper")
    public int getIdNewspaper() {
        return idNewspaper;
    }

    public void setIdNewspaper(int idNewspaper) {
        this.idNewspaper = idNewspaper;
    }

    @Basic
    @Column(name = "NewspaperName")
    public String getNewspaperName() {
        return newspaperName;
    }

    public void setNewspaperName(String newspaperName) {
        this.newspaperName = newspaperName;
    }

    @Basic
    @Column(name = "Date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "Count")
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Basic
    @Column(name = "ReleaseNumber")
    public String getReleaseNumber() {
        return releaseNumber;
    }

    public void setReleaseNumber(String releaseNumber) {
        this.releaseNumber = releaseNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewspaperEntity that = (NewspaperEntity) o;

        if (idNewspaper != that.idNewspaper) return false;
        if (count != that.count) return false;
        if (newspaperName != null ? !newspaperName.equals(that.newspaperName) : that.newspaperName != null)
            return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (releaseNumber != null ? !releaseNumber.equals(that.releaseNumber) : that.releaseNumber != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idNewspaper;
        result = 31 * result + (newspaperName != null ? newspaperName.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + count;
        result = 31 * result + (releaseNumber != null ? releaseNumber.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idClient", referencedColumnName = "idClient", nullable = false)
    public ClientEntity getClientByIdClient() {
        return clientByIdClient;
    }

    public void setClientByIdClient(ClientEntity clientByIdClient) {
        this.clientByIdClient = clientByIdClient;
    }
}
