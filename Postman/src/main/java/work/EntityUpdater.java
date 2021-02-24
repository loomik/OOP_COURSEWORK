package work;

import database.*;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.List;

public class EntityUpdater
{
    public void updatePostman(int entityIdPostman,String entityLastName,String entityFirstName,String entityBirthdayDate) throws errorMail
    {
        if (entityLastName.isEmpty() || entityFirstName.isEmpty() || entityBirthdayDate.isEmpty())
        {
            throw new errorMail("Enter all field");
        }

        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        PostmanEntity de = em.find(PostmanEntity.class,entityIdPostman);
        de.setLastName(entityLastName);
        de.setFirstName(entityFirstName);
        de.setBirthdayDate(Date.valueOf(entityBirthdayDate));
        em.getTransaction().commit();
    }

    public void updateClient(int entityIdClient,String entityClientNumber,String entityLastname2,String entityFirstname2,String entityTelephonenumber,String entityDeliveryDate,String entityIdPostman) throws errorMail
    {
        if (entityClientNumber.isEmpty() || entityLastname2.isEmpty() || entityFirstname2.isEmpty() || entityTelephonenumber.isEmpty() || entityDeliveryDate.isEmpty() || entityIdPostman.isEmpty())
        {
            throw new errorMail("Enter all field");
        }

        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List <ClientEntity> client_list = em.createQuery("SELECT c FROM ClientEntity c WHERE c.clientNumber='" + entityClientNumber + "'").getResultList();
        em.getTransaction().commit();

        em.getTransaction().begin();
        List <PostmanEntity> postman_list = em.createQuery("SELECT d FROM PostmanEntity d WHERE d.idPostman='" + Integer.parseInt(entityIdPostman) + "'").getResultList();
        em.getTransaction().commit();

        if (!postman_list.isEmpty())
        {
            em.getTransaction().begin();
            ClientEntity ce = em.find(ClientEntity.class,entityIdClient);
            ce.setClientNumber(entityClientNumber);
            ce.setLastname2(entityLastname2);
            ce.setFirstname2(entityFirstname2);
            ce.setTelephonenumber(entityTelephonenumber);
            ce.setDeliveryDate(Date.valueOf(entityDeliveryDate));
            ce.setPostmanByIdPostman(postman_list.get(0));
            em.getTransaction().commit();
        }
        else
        {
            throw new errorMail("there are no such postmans");
        }
    }

    public void updateNewspaper(int entityIdNewspaper,String entityNewspaperName,String entityDate,String entityCount,String entityReleaseNumber,String entityIdClient) throws errorMail
    {
        if (entityNewspaperName.isEmpty() || entityDate.isEmpty() || entityCount.isEmpty() || entityIdClient.isEmpty())
        {
            throw new errorMail("Enter all field");
        }

        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List <ClientEntity> client_list = em.createQuery("SELECT c FROM ClientEntity c WHERE c.idClient='" + Integer.parseInt(entityIdClient) + "'").getResultList();
        em.getTransaction().commit();

        if (!client_list.isEmpty())
        {
            em.getTransaction().begin();
            NewspaperEntity ve = em.find(NewspaperEntity.class, entityIdNewspaper);
            ve.setNewspaperName(entityNewspaperName);
            ve.setDate(Date.valueOf(entityDate));
            ve.setCount(Integer.parseInt(entityCount));
            ve.setReleaseNumber(entityReleaseNumber);
            ve.setClientByIdClient(client_list.get(0));
            em.getTransaction().commit();
        }
        else
        {
            throw new errorMail("there are no such clients");
        }
    }
}