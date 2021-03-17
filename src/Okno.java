import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;

public class Okno extends JFrame implements ActionListener {
    JPanel p1 = new JPanel();
    JPanel p = new JPanel();
    JSplitPane panel;
// pola panelu nr 1
    JLabel label_rodzaj;
    JList lista_rodzaj;

    JLabel label_szczeg;
    JComboBox lista_szczeg;

    JLabel label_skup;
    JComboBox skup;

    JLabel label_cena;
    JTextArea cena;

    JLabel label_data;
    JTextArea data;

    JLabel label_sprawdz;
    JButton sprawdz;
    // pola panelu 2
    JLabel label_por_dat1;
    JTextArea por_dat1;

    JLabel label_por_dat2;
    JTextArea por_dat2;

    JLabel label_porownaj;
    JButton porownaj;

    JLabel label_cena2;
    JTextArea cena2;

    JLabel label_cena3;
    JTextArea cena3;
    public  Okno(String s){
        super(s);
        label_rodzaj = new JLabel("Rodzaj Produktu");
        lista_rodzaj=new JList();

        label_szczeg = new JLabel("Produkty");
        lista_szczeg = new JComboBox();

        label_skup  = new JLabel("Skup");
        skup=new JComboBox();

        label_cena  = new JLabel("Cena");
        cena =new JTextArea( 2, 5);;

        label_data  = new JLabel("Data");
        data =new JTextArea( 2, 5);;

        //label_sprawdz  = new JLabel("Sprawdź Cene");
        sprawdz=new JButton("Sprawdź Cene");

        p.add(label_rodzaj);
        p.add(lista_rodzaj);
        p.add(label_szczeg);
        p.add(lista_szczeg);
        p.add(label_skup);
        p.add(skup);
        p.add(label_cena);
        p.add(cena);
        p.add(label_data);
        p.add(data);
        p.add(sprawdz);
        //panel 2

        label_por_dat1  = new JLabel("Data notowania numer 1");
        por_dat1=new JTextArea( 2, 5);

        label_por_dat2  = new JLabel("Data notowania numer 2");
        por_dat2=new JTextArea( 2, 5);;

       // label_porownaj  new JLabel("Porownaj");
        porownaj=new JButton("Porownaj");

         label_cena2= label_cena  = new JLabel("Cena");
         cena2 =new JTextArea( 2, 5);

         label_cena3= label_cena  = new JLabel("Cena");
         cena3=new JTextArea( 2, 5);

        p1.add(label_por_dat1);
        p1.add(por_dat1);
        p1.add(label_por_dat2);
        p1.add(por_dat2);
        p1.add(label_cena2);
        p1.add(cena2);
        p1.add(label_cena3);
        p1.add(cena3);
        p1.add(porownaj);
        panel = new JSplitPane(SwingConstants.HORIZONTAL, p, p1);
        add(panel, BorderLayout.CENTER);
        setSize(600,700);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {


    }
}
