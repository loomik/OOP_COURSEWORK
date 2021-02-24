package work;

import database.*;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import database.PostmanEntity;

import javax.persistence.EntityManager;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class form_GUI extends JDialog
{
    private JPanel ExternalPanel;
    private JTabbedPane TabbedPane;
    private JPanel postmanPanel;
    private JButton AddUpdateButton;
    private JTextField postmanLastnameTextField;
    private JTextField postmanFirstnameTextField;
    private JTextField postmanBirthdaydateTextField;
    private JButton deleteButton;
    private JPanel clientPanel;
    private JButton renewButton;
    private JTextField postmanClientsTextField;
    private JButton postmanOkButton;
    //private JTextField clientFinddirverTextField;
    private JTextField newspaperFinddirverTextField;
    private JButton newspaperOkButton;
    private JPanel newspaperPanel;
    //private JButton exportReportButton;
    private JPanel FreeTablePanel;
    private JTable table;
    private JTextField clientNumberTextField;
    private JTextField clientLastname2TextField;
    private JTextField clientFirstname2TextField;
    private JTextField clientTelephonenumberTextField;
    private JTextField clientDeliverydateTextField;
    private JTextField clientidPostmanTextField;
    private JTextField newspaperNameTextField;
    private JTextField newspaperDateTextField;
    private JTextField newspaperCountTextField;
    private JTextField newspaperReleaseNumberTextField;
    private JTextField newspaperidClientTextField;
    private JTextField newspaperStartdateTextField;
    private JTextField newspaperEnddateTextField;
    private JPanel TablePanel;
    private JScrollPane TableScrollPane;
    private JTextField countTextField;
   // private JButton reportOkButton;

    public static DefaultTableModel table_model;

    public static final SessionFactory ourSessionFactory;
    static
    {
        Configuration configuration = new Configuration();
        configuration.configure();
        ourSessionFactory = configuration.buildSessionFactory();
    }
    public static Session getSession() throws HibernateException
    {
        return ourSessionFactory.openSession();
    }

    TableModelUpdater tmUpdater = new TableModelUpdater();
    EntityAdder entityAdder = new EntityAdder();
    EntityUpdater entityUpdater = new EntityUpdater();

    public static int count_of_postmans()
    {
        EntityManager em = ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List count = em.createQuery("select d from PostmanEntity d").getResultList();
        em.getTransaction().commit();

        return(count.size());
    }

    public static int count_of_clients()
    {
        EntityManager em = ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List count = em.createQuery("select c.idClient from ClientEntity c").getResultList();
        em.getTransaction().commit();

        return(count.size());
    }

    public static int count_of_newspapers()
    {
        EntityManager em = ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List count = em.createQuery("select v.idNewspaper from NewspaperEntity v").getResultList();
        em.getTransaction().commit();

        return(count.size());
    }

    public void clear_GUI()
    {
        AddUpdateButton.setText("Add new record");
        postmanLastnameTextField.setText("");
        postmanFirstnameTextField.setText("");
        postmanBirthdaydateTextField.setText("");
        postmanClientsTextField.setText("");

        newspaperFinddirverTextField.setText("");
        //clientFinddirverTextField.setText("");
        clientNumberTextField.setText("");
        clientLastname2TextField.setText("");
        clientFirstname2TextField.setText("");
        clientTelephonenumberTextField.setText("");
        clientDeliverydateTextField.setText("");
        clientidPostmanTextField.setText("");

        newspaperNameTextField.setText("");
        newspaperDateTextField.setText("");
        newspaperCountTextField.setText("");
        newspaperReleaseNumberTextField.setText("");
        newspaperidClientTextField.setText("");
        //newspaperStartdateTextField.setText("");
        //newspaperEnddateTextField.setText("");

        int tabIndex = TabbedPane.getSelectedIndex();
        switch (tabIndex)
        {
            case 0:
                tmUpdater.updatePostman();
                countTextField.setText(Integer.toString(count_of_postmans()));
                break;
            case 1:
                tmUpdater.updateClient();
                countTextField.setText(Integer.toString(count_of_clients()));
                break;
            case 2:
                tmUpdater.updateNewspaper();
                countTextField.setText(Integer.toString(count_of_newspapers()));
                break;
        }
        table.setModel(table_model);
    }

    public form_GUI()
    {
        clear_GUI();
        setContentPane(ExternalPanel);
        //setModal(true);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                exit();
            }
        });

        TabbedPane.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                clear_GUI();
            }
        });

        table.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                AddUpdateButton.setText("Update record");
                int tabIndex = TabbedPane.getSelectedIndex();

                switch (tabIndex) {
                    case 0:
                        String postmanLastName = table_model.getValueAt(table.getSelectedRow(), 1).toString();
                        String postmanFirstName = table_model.getValueAt(table.getSelectedRow(), 2).toString();
                        String postmanBirthdayDate = table_model.getValueAt(table.getSelectedRow(), 3).toString();
                        postmanLastnameTextField.setText(postmanLastName);
                        postmanFirstnameTextField.setText(postmanFirstName);
                        postmanBirthdaydateTextField.setText(postmanBirthdayDate);
                        break;
                    case 1:
                        String clientNumber = table_model.getValueAt(table.getSelectedRow(), 1).toString();
                        String clientLastname2 = table_model.getValueAt(table.getSelectedRow(), 2).toString();
                        String clientFirstname2 = table_model.getValueAt(table.getSelectedRow(), 3).toString();
                        String clientTelephonenumber = table_model.getValueAt(table.getSelectedRow(), 4).toString();
                        String clientDeliveryDate = table_model.getValueAt(table.getSelectedRow(), 5).toString();
                        String clientIdPostman = table_model.getValueAt(table.getSelectedRow(), 6).toString();
                        clientNumberTextField.setText(clientNumber);
                        clientLastname2TextField.setText(clientLastname2);
                        clientFirstname2TextField.setText(clientFirstname2);
                        clientTelephonenumberTextField.setText(clientTelephonenumber);
                        clientDeliverydateTextField.setText(clientDeliveryDate);
                        clientidPostmanTextField.setText(clientIdPostman);
                        break;
                    case 2:
                        String newspaperName = table_model.getValueAt(table.getSelectedRow(), 1).toString();
                        String newspaperDate = table_model.getValueAt(table.getSelectedRow(), 2).toString();
                        String newspaperCount = table_model.getValueAt(table.getSelectedRow(), 3).toString();
                        String newspaperReleaseNumber = table_model.getValueAt(table.getSelectedRow(), 4).toString();
                        String newspaperIdClient = table_model.getValueAt(table.getSelectedRow(), 5).toString();
                        newspaperNameTextField.setText(newspaperName);
                        newspaperDateTextField.setText(newspaperDate);
                        newspaperCountTextField.setText(newspaperCount);
                        newspaperidClientTextField.setText(newspaperIdClient);
                        newspaperReleaseNumberTextField.setText(newspaperReleaseNumber);
                        break;
                }
            }
        });

        AddUpdateButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int tabIndex = TabbedPane.getSelectedIndex();
                int rowIndex = table.getSelectedRow();

                if (rowIndex==-1)
                {
                    switch (tabIndex)
                    {
                        case 0:
                            String postmanLastName = postmanLastnameTextField.getText();
                            String postmanFirstName = postmanFirstnameTextField.getText();
                            String postmanBirthdayDate = postmanBirthdaydateTextField.getText();

                            try
                            {
                                entityAdder.addPostman(postmanLastName, postmanFirstName, postmanBirthdayDate);
                                tmUpdater.updatePostman();
                            }
                            catch (errorMail error)
                            {
                                JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case 1:
                            String clientNumber = clientNumberTextField.getText();
                            String clientLastname2 = clientLastname2TextField.getText();
                            String clientFirstname2 = clientFirstname2TextField.getText();
                            String clientTelephonenumber = clientTelephonenumberTextField.getText();
                            String clientDeliveryDate = clientDeliverydateTextField.getText();
                            String clientIdPostman = clientidPostmanTextField.getText();

                            try
                            {
                                entityAdder.addClient(clientNumber, clientLastname2, clientFirstname2, clientTelephonenumber, clientDeliveryDate, clientIdPostman);
                                tmUpdater.updateClient();
                            }
                            catch (errorMail error)
                            {
                                JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case 2:
                            String newspaperName = newspaperNameTextField.getText();
                            String newspaperDate = newspaperDateTextField.getText();
                            String newspaperCount = newspaperCountTextField.getText();
                            String newspaperReleaseNumber = newspaperReleaseNumberTextField.getText();
                            String newspaperIdClient = newspaperidClientTextField.getText();

                            try
                            {
                                entityAdder.addNewspaper(newspaperName, newspaperDate, newspaperCount, newspaperReleaseNumber, newspaperIdClient);
                                tmUpdater.updateNewspaper();
                            }
                            catch (errorMail error)
                            {
                                JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                    }
                }
                else
                {
                    EntityManager em = ourSessionFactory.createEntityManager();
                    switch (tabIndex)
                    {
                        case 0:
                            em.getTransaction().begin();
                            PostmanEntity de = em.find(PostmanEntity.class,Integer.parseInt(table.getValueAt(rowIndex,0).toString()));
                            em.getTransaction().commit();

                            String postmanLastName = postmanLastnameTextField.getText();
                            String postmanFirstName = postmanFirstnameTextField.getText();
                            String postmanBirthdayDate = postmanBirthdaydateTextField.getText();

                            try
                            {
                                entityUpdater.updatePostman(de.getIdPostman(),postmanLastName, postmanFirstName, postmanBirthdayDate);
                                tmUpdater.updatePostman();
                            }
                            catch (errorMail error)
                            {
                                JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case 1:
                            em.getTransaction().begin();
                            ClientEntity ce = em.find(ClientEntity.class,Integer.parseInt(table.getValueAt(rowIndex,0).toString()));
                            em.getTransaction().commit();

                            String clientNumber = clientNumberTextField.getText();
                            String clientLastname2 = clientLastname2TextField.getText();
                            String clientFirstname2 = clientFirstname2TextField.getText();
                            String clientTelephonenumber = clientTelephonenumberTextField.getText();
                            String clientDeliveryDate = clientDeliverydateTextField.getText();
                            String clientIdPostman = clientidPostmanTextField.getText();

                            try
                            {
                                entityUpdater.updateClient(ce.getIdClient(),clientNumber, clientLastname2, clientFirstname2, clientTelephonenumber, clientDeliveryDate, clientIdPostman);
                                tmUpdater.updateClient();
                            }
                            catch (errorMail error)
                            {
                                JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case 2:
                            em.getTransaction().begin();
                            NewspaperEntity ve = em.find(NewspaperEntity.class,Integer.parseInt(table.getValueAt(rowIndex,0).toString()));
                            em.getTransaction().commit();

                            String newspaperName = newspaperNameTextField.getText();
                            String newspaperDate = newspaperDateTextField.getText();
                            String newspaperCount = newspaperCountTextField.getText();
                            String newspaperReleaseNumber = newspaperReleaseNumberTextField.getText();
                            String newspaperIdClient = newspaperidClientTextField.getText();

                            try
                            {
                                entityUpdater.updateNewspaper(ve.getIdNewspaper(),newspaperName, newspaperDate, newspaperCount, newspaperReleaseNumber, newspaperIdClient);
                                tmUpdater.updateNewspaper();
                            }
                            catch (errorMail error)
                            {
                                JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                    }
                }

                table.setModel(table_model);
                clear_GUI();
            }
        });

        deleteButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int tabIndex = TabbedPane.getSelectedIndex();
                int rowIndex = table.getSelectedRow();
                EntityManager em = ourSessionFactory.createEntityManager();
                em.getTransaction().begin();

                switch (tabIndex)
                {
                    case 0:
                        PostmanEntity de = em.find(PostmanEntity.class,Integer.parseInt(table.getValueAt(rowIndex,0).toString()));
                        em.remove(de);
                        em.getTransaction().commit();
                        tmUpdater.updatePostman();
                        break;
                    case 1:
                        ClientEntity ce = em.find(ClientEntity.class,Integer.parseInt(table.getValueAt(rowIndex,0).toString()));
                        em.remove(ce);
                        em.getTransaction().commit();
                        tmUpdater.updateClient();
                        break;
                    case 2:
                        NewspaperEntity ve = em.find(NewspaperEntity.class,Integer.parseInt(table.getValueAt(rowIndex,0).toString()));
                        em.remove(ve);
                        em.getTransaction().commit();
                        tmUpdater.updateNewspaper();
                        break;
                }
                table.setModel(table_model);
                clear_GUI();
            }
        });

        renewButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                clear_GUI();
            }
        });

        postmanOkButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String postmanIdClient = postmanClientsTextField.getText();
                try
                {
                    tmUpdater.updateFindNewspapers(postmanIdClient);
                    table.setModel(table_model);
                    countTextField.setText(Integer.toString(table.getRowCount()));
                }
                catch (errorMail error)
                {
                    JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

     /*   clientOkButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String idClient = clientFinddirverTextField.getText();
                try
                {
                    tmUpdater.updateFindPostman(idClient);
                    table.setModel(table_model);
                    countTextField.setText(Integer.toString(table.getRowCount()));
                }
                catch (errorMail error)
                {
                    JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });*/

        newspaperOkButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String idNewspaper = newspaperFinddirverTextField.getText();
                try
                {
                    tmUpdater.updateFindClient(idNewspaper);
                    table.setModel(table_model);
                    countTextField.setText(Integer.toString(table.getRowCount()));
                }
                catch (errorMail error)
                {
                    JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

       /* reportOkButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String startDate = newspaperStartdateTextField.getText();
                String endDate = newspaperEnddateTextField.getText();
                try
                {
                    tmUpdater.updateReport(startDate,endDate);
                    table.setModel(table_model);
                    countTextField.setText(Integer.toString(table.getRowCount()));
                }
                catch (errorMail error)
                {
                    JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });*/

       /* exportReportButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String startDate = newspaperStartdateTextField.getText();
                String endDate = newspaperEnddateTextField.getText();
                try
                {
                    tmUpdater.updateReport(startDate,endDate);
                    table.setModel(table_model);
                    //ExportPdf.createPdf("Reports/period1.pdf", new String[]{"Last name","First name","Client number","Newspaper name","Date","Count","Other punishment"}, table_model);
                }
                catch (errorMail error)
                {
                    JOptionPane.showMessageDialog(ExternalPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception exception)
                {
                    JOptionPane.showMessageDialog(ExternalPanel, "Cant export: " + exception.getClass() + ':' + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });*/
    }

    private void exit()
    {
        dispose();
    }

    public static void main(String[] args)
    {
        form_GUI dialog = new form_GUI();
        dialog.setTitle("MAIL");
        dialog.setSize(1000,380);
        dialog.setLocation(300,150);
        dialog.setVisible(true);
    }
}