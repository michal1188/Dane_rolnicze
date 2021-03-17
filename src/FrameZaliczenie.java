import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;

public class FrameZaliczenie extends JFrame implements ActionListener {
    private JPanel Panel_main;

    private JPanel Panel_1;

    private JButton Sprwdz;
    private JList Lista_produktow;
    private JComboBox Lista_szczegolow;
    private JComboBox Lista_skup;
    private JLabel Ceny_rolnicze;
    private JLabel Rodzaj_produktu;
    private JLabel Produkt;
    private JLabel Skup;
    private JTextPane Cena;
    private JLabel Cena_lable;
    private JLabel Dzien;
    private JTextPane Dzien_bierzacy;

    private JPanel Panel_2;

    private JLabel Porownaj_Cene;
    private JButton Porownaj;
    private JTextPane roznica;
    private JComboBox Dni_archi2;
    private JTextPane show_cena_arch2;
    private JComboBox Dni_archi1;
    private JTextPane show_cena_arch1;
    private JLabel Dzien_1;
    private JLabel Cena_archi1;
    private JLabel Cena_archi2;
    private JPanel JPanel;
    private JLabel Dzien_2;

    Dane_rolnicze dane = new Dane_rolnicze();

    public FrameZaliczenie() {

        setContentPane(Panel_main);


        setResizable(true);
       pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        Lista_produktow.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Lista_produktow.setModel(dane.Rodzaj_produktu());
        Lista_produktow.addListSelectionListener(sl);
        Lista_szczegolow.addItemListener(s2);

        //Dni_archi1.addItemListener(cena_archiwalna);
        //Lista_szczegolow.addItemListener(notowania_archi);

        Sprwdz.addActionListener(sprawdz_listener);
        Porownaj.addActionListener(porownaj_listener);
        Dzien_bierzacy.setText(dane.Dzien_bierzacy());

    }
    ActionListener sprawdz_listener= new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                e.getSource();
                String s = (String) Lista_szczegolow.getSelectedItem();
                String s2 = (String) Lista_produktow.getSelectedValue();
                String s3 = (String) Lista_skup.getSelectedItem();
                try {
                    Cena.setText(dane.get_cena(s2,s,s3));
                } catch (IOException ex) {

                }
            }
        };
    ActionListener porownaj_listener= new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            e.getSource();
            String s1 = (String) Lista_produktow.getSelectedValue();
            String s2 = (String) Lista_szczegolow.getSelectedItem();
            String s3 = (String) Lista_skup.getSelectedItem();
            String s4 = (String) Dni_archi1.getSelectedItem();

            String s5= (String) Dni_archi2.getSelectedItem();

            try {
               show_cena_arch1.setText(String.valueOf(dane.get_cena_archi(s1,s2,s3,s4)));
               show_cena_arch2.setText(String.valueOf(dane.get_cena_archi(s1,s2,s3,s5)));
               roznica.setText(dane.porownaj(dane.get_cena_archi(s1,s2,s3,s4),dane.get_cena_archi(s1,s2,s3,s5)));
            } catch (IOException | ParseException ex) {

            }
        }
    };

    ListSelectionListener sl = new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent e) {
            e.getSource();
            String s = (String) Lista_produktow.getSelectedValue();

            try {
                Lista_szczegolow.setModel(dcbm(dane.Pobierz_szegoly(s)));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

    };

    ItemListener s2 = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            e.getSource();
            String s = (String) Lista_szczegolow.getSelectedItem();
            String s2 = (String) Lista_produktow.getSelectedValue();
            try {
                Lista_skup.setModel(dcbm(dane.Pobierz_skupy(s2, s)));
                Dni_archi1.setModel(dcbm(dane.archi_notowan(s2,s)));
                Dni_archi2.setModel(dcbm(dane.archi_notowan(s2,s)));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    };




     DefaultComboBoxModel dcbm(ArrayList<String> list) {
        DefaultComboBoxModel dml = new DefaultComboBoxModel();
        for (int i = 0; i < list.size(); i++) {
            dml.addElement(list.get(i));

        }
        return dml;
    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
