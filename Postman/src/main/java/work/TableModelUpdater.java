package work;

import database.*;

import javax.persistence.EntityManager;
import javax.swing.table.DefaultTableModel;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class TableModelUpdater
{
    public static String[] columns;
    public static DefaultTableModel model;

    public void updatePostman()
    {
        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List<PostmanEntity> postman_list = em.createQuery("SELECT d FROM PostmanEntity d ORDER BY d.lastName, d.firstName").getResultList();
        em.getTransaction().commit();

        columns = new String[] {"idPostman","Last name","First name","Birthday date"};
        model = new DefaultTableModel(columns, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        for (int i=0;i<postman_list.size();i++)
        {
            PostmanEntity de = postman_list.get(i);
            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            String[] row = {Integer.toString(de.getIdPostman()),de.getLastName(),de.getFirstName(),date.format(de.getBirthdayDate())};
            model.addRow(row);
        }

        form_GUI.table_model = model;
    }

    public void updateClient()
    {
        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List<ClientEntity> client_list = em.createQuery("SELECT c FROM ClientEntity c ORDER BY c.idClient").getResultList();
        em.getTransaction().commit();

        columns = new String[] {"idClient","Client number","Lastname2","Firstname2","Telephonenumber","Delivery date","idPostman"};
        model = new DefaultTableModel(columns, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        for (int i=0;i<client_list.size();i++)
        {
            ClientEntity ce = client_list.get(i);
            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            String[] row = {Integer.toString(ce.getIdClient()),ce.getClientNumber(),ce.getLastname2(),ce.getFirstname2(),ce.getTelephonenumber(),date.format(ce.getDeliveryDate()),Integer.toString(ce.getPostmanByIdPostman().getIdPostman())};
            model.addRow(row);
        }

        form_GUI.table_model = model;
    }

    public void updateNewspaper()
    {
        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List<NewspaperEntity> newspaper_list = em.createQuery("SELECT v FROM NewspaperEntity v ORDER BY v.newspaperName").getResultList();
        em.getTransaction().commit();

        columns = new String[] {"idNewspaper","Newspaper name","Date","Count","Release number","idClient"};
        model = new DefaultTableModel(columns, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        for (int i=0;i<newspaper_list.size();i++)
        {
            NewspaperEntity ve = newspaper_list.get(i);
            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            String[] row = {Integer.toString(ve.getIdNewspaper()),ve.getNewspaperName(),date.format(ve.getDate()),Integer.toString(ve.getCount()),ve.getReleaseNumber(),Integer.toString(ve.getClientByIdClient().getIdClient())};
            model.addRow(row);
        }

        form_GUI.table_model = model;
    }

    public void updateFindNewspapers(String entityIdPostman) throws errorMail
    {
        if (entityIdPostman.isEmpty())
        {
            throw new errorMail("Enter idPostman");
        }
        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List <PostmanEntity> postman_list = em.createQuery("SELECT d FROM PostmanEntity d WHERE d.idPostman='" + Integer.parseInt(entityIdPostman) + "'").getResultList();
        em.getTransaction().commit();

        if (!postman_list.isEmpty())
        {
            columns = new String[] {"idPostman","Last name","First name","Client number","Last name 2","First name 2","Delivery date","Newspaper name"};
            model = new DefaultTableModel(columns, 0)
            {
                @Override
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            };
            PostmanEntity de = postman_list.get(0);

            em.getTransaction().begin();
            List <ClientEntity> client_list = em.createQuery("SELECT c FROM ClientEntity c WHERE c.postmanByIdPostman.idPostman ='" + de.getIdPostman() + "'").getResultList();
            em.getTransaction().commit();

            if (!client_list.isEmpty())
            {
                for (int i=0; i<client_list.size(); i++)
                {
                    em.getTransaction().begin();
                    List <NewspaperEntity> newspaper_list = em.createQuery("SELECT v FROM NewspaperEntity v WHERE v.clientByIdClient.idClient = '" + client_list.get(i).getIdClient() + "'").getResultList();
                    em.getTransaction().commit();

                    if (!newspaper_list.isEmpty())
                    {
                        for (int j=0; j<newspaper_list.size(); j++)
                        {
                            String[] Row = new String[8];
                            Row[0]=Integer.toString(de.getIdPostman());
                            Row[1]=de.getLastName();
                            Row[2]=de.getFirstName();
                            Row[3]=client_list.get(i).getClientNumber();
                            Row[4]=client_list.get(i).getLastname2();
                            Row[5]=client_list.get(i).getFirstname2();
                            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                            Row[6]=date.format(client_list.get(j).getDeliveryDate());
                            Row[7]=newspaper_list.get(j).getNewspaperName();
                            model.addRow(Row);
                        }
                    }
                    else
                    {
                        for (int j = 0; j < client_list.size(); j++) {
                            String[] Row = new String[8];
                            Row[0] = Integer.toString(de.getIdPostman());
                            Row[1] = de.getLastName();
                            Row[2] = de.getFirstName();
                            Row[3] = client_list.get(i).getClientNumber();
                            Row[4] = client_list.get(i).getLastname2();
                            Row[5] = client_list.get(i).getFirstname2();
                            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                            Row[6] = date.format(client_list.get(j).getDeliveryDate());
                            Row[7] = "";
                            model.addRow(Row);
                        }
                    }
                }
            }
            else
            {
                String[] row = new String[8];
                row[0]=Integer.toString(de.getIdPostman());
                row[1]=de.getLastName();
                row[2]= de.getFirstName();
                row[3]="";
                row[4]="";
                row[5]="";
                row[6]="";
                row[7]="";
                model.addRow(row);
            }
            form_GUI.table_model = model;
        }
        else
        {
            throw new errorMail("there are no such postmans");
        }
    }

    public void updateFindPostman(String idClient) throws errorMail
    {
        if (idClient.isEmpty())
        {
            throw new errorMail("Enter idClient");
        }
        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List <ClientEntity> client_list = em.createQuery("SELECT c FROM ClientEntity c WHERE c.idClient ='" + Integer.parseInt(idClient) + "'").getResultList();
        em.getTransaction().commit();

        if (!client_list.isEmpty())
        {
            columns = new String[]{"Last name", "First name", "Client number","Lastname2","Firstname2","Telephonenumber","Delivery date"};
            model = new DefaultTableModel(columns, 0)
            {
                @Override
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            };
            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            String[] row = {
                    client_list.get(0).getPostmanByIdPostman().getLastName(),
                    client_list.get(0).getPostmanByIdPostman().getFirstName(),
                    client_list.get(0).getClientNumber(),
                    client_list.get(0).getLastname2(),
                    client_list.get(0).getFirstname2(),
                    client_list.get(0).getTelephonenumber(),
                    date.format(client_list.get(0).getDeliveryDate())
            };
            model.addRow(row);
            form_GUI.table_model = model;
        }
        else
        {
            throw new errorMail("there are no such clients");
        }
    }

    public void updateFindClient(String idNewspaper) throws errorMail
    {
        if (idNewspaper.isEmpty())
        {
            throw new errorMail("Enter Newspaper name");
        }
        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List <NewspaperEntity> newspaper_list = em.createQuery("SELECT c FROM NewspaperEntity c WHERE c.newspaperName ='" + idNewspaper + "'").getResultList();
        em.getTransaction().commit();

        if (!newspaper_list.isEmpty())
        {


                columns = new String[]{"Id Client", "Last name 2", "First name 2", "Newspaper name", "Date", "Count", "Release number"};
                model = new DefaultTableModel(columns, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
            for (int j=0; j<newspaper_list.size(); j++) {
                String[] Row = new String[7];
                Row[0] = String.valueOf(newspaper_list.get(j).getClientByIdClient().getIdClient());
                Row[1] = String.valueOf(newspaper_list.get(j).getClientByIdClient().getLastname2());
                Row[2] = String.valueOf(newspaper_list.get(j).getClientByIdClient().getFirstname2());
                Row[3] = newspaper_list.get(j).getNewspaperName();
                DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                Row[4] = date.format(newspaper_list.get(j).getDate());
                Row[5] = String.valueOf(newspaper_list.get(j).getCount());
                Row[6] = newspaper_list.get(j).getReleaseNumber();
                model.addRow(Row);
            }
            form_GUI.table_model = model;
        }
        else
        {
            throw new errorMail("there are no such newspapers");
        }
    }

    public void updateReport(String startDate, String endDate) throws errorMail
    {
        if (startDate.isEmpty() || endDate.isEmpty())
        {
            throw new errorMail("Enter Start/End dates");
        }
        EntityManager em = form_GUI.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List <NewspaperEntity> newspaper_list = em.createQuery("SELECT v FROM NewspaperEntity v WHERE v.date >= '" + Date.valueOf(startDate) + "' AND v.date <= '" + Date.valueOf(endDate) + "'").getResultList();
        em.getTransaction().commit();

        if (!newspaper_list.isEmpty())
        {
            columns = new String[] {"Last name","First name","Client number","Newspaper name","Date","Count","Release number"};
            model = new DefaultTableModel(columns, 0)
            {
                @Override
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            };
            for (int i=0; i<newspaper_list.size(); i++) {
                DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                String[] row = {
                        newspaper_list.get(i).getClientByIdClient().getPostmanByIdPostman().getLastName(),
                        newspaper_list.get(i).getClientByIdClient().getPostmanByIdPostman().getFirstName(),
                        newspaper_list.get(i).getClientByIdClient().getClientNumber(),
                        newspaper_list.get(i).getNewspaperName(),
                        date.format(newspaper_list.get(i).getDate()),
                        Integer.toString(newspaper_list.get(i).getCount()),
                        newspaper_list.get(i).getReleaseNumber()};
                model.addRow(row);
            }
            form_GUI.table_model=model;
        }
        else
        {
            throw new errorMail("no violations were found");
        }
    }
}
