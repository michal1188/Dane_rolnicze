import com.sun.jdi.Value;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class Dane_rolnicze {
    String pattern = "MM/dd/yyyy HH:mm:ss";
    DateFormat df = new SimpleDateFormat(pattern);
    ArrayList<String> rodzaj_produktu = new ArrayList<>();


    public String Dzien_bierzacy() {
        Date date = new Date();
        return df.format(date);

    }

    public DefaultListModel Rodzaj_produktu() {
        rodzaj_produktu.add("ZBOŻA (CENA ZA TONĘ)");
        rodzaj_produktu.add("ŻYWIEC WOŁOWY (CENA ZA 1 KG)");
        rodzaj_produktu.add("ŻYWIEC WIEPRZOWY (CENA ZA 1 KG)");
        DefaultListModel model = new DefaultListModel();
        for (int i = 0; i < rodzaj_produktu.size(); i++) {
            model.addElement(rodzaj_produktu.get(i));
        }
        return model;

    }

    public String wybierz_kategorie(String s) {
        String zmienna = null;
        if (s == "ZBOŻA (CENA ZA TONĘ)") {
            zmienna = "zboza";
        } else if (s == "ŻYWIEC WOŁOWY (CENA ZA 1 KG)") {
            zmienna = "zywiec-wolowy";
        } else if (s == "ŻYWIEC WIEPRZOWY (CENA ZA 1 KG)") {
            zmienna = "zywiec-wieprzowy";
        }

        return zmienna;
    }

    public ArrayList<String> Pobierz_szegoly(String s) throws IOException {

        Connection connect = Jsoup.connect("http://wiescirolnicze.pl/ceny-rolnicze/" + wybierz_kategorie(s) + "/");
        Document document = connect.get();

        ArrayList<String> szczegoly_list = new ArrayList<>();
        Elements table = document.getElementsByClass("table table-condensed table-hover");
        Elements pojedynczy = table.first().select("tr").select("a");


        int j = 0;
        for (Element e : pojedynczy) {
            szczegoly_list.add(pojedynczy.get(j).text());
            j++;
        }


        return szczegoly_list;
    }

    public ArrayList<String> Pobierz_skupy(String s, String s2) throws IOException {


        Document document2 = polaczenie_szcegoly(s,s2).get();
        int j=0;
        ArrayList<String> skup_list = new ArrayList<>();

        Elements table2 = document2.getElementsByClass("table table-condensed table-hover table-no-bordered sortable");
        Elements pojedynczy2 = table2.first().select("tr").select("a");

        for (Element e : pojedynczy2) {
            skup_list.add(pojedynczy2.get(j).text());
            j++;
        }

        return skup_list;
    }

    public String skup_href_get(String s, String s2,String s3) throws IOException {

        Connection connect3 = Jsoup.connect("http://wiescirolnicze.pl/ceny-rolnicze/" + wybierz_kategorie(s) + "/");
        Document document = connect3.get();

        ArrayList<String> szegoly_list_href = new ArrayList<>();
        ArrayList<String> szczegoly_list = new ArrayList<>();
        Elements table = document.getElementsByClass("table table-condensed table-hover");
        Elements pojedynczy = table.first().select("tr").select("a");

        String href;
        int j = 0;
        for (Element e : pojedynczy) {
            href = pojedynczy.get(j).attr("href");
            szegoly_list_href.add(href);
            j++;
        }
        j = 0;
        for (Element e : pojedynczy) {
            szczegoly_list.add(pojedynczy.get(j).text());
            j++;
        }
        j = 0;
        int poszukiwany = 0;
        for (int i = 0; i < szczegoly_list.size(); i++) {
            if (szczegoly_list.get(i).equals(s2)) {
                poszukiwany = i;
            }
        }

        Connection connect2 = Jsoup.connect("http://wiescirolnicze.pl" + szegoly_list_href.get(poszukiwany));
        Document document2 = connect2.get();

        ArrayList<String> skup_list = new ArrayList<>();

        Elements table2 = document2.getElementsByClass("table table-condensed table-hover table-no-bordered sortable");
        Elements pojedynczy2 = table2.first().select("tr").select("a");

        ArrayList<String> skup_list_href = new ArrayList<>();
        String href2;
         j = 0;
        for (Element e : pojedynczy2) {
            href2 = pojedynczy2.get(j).attr("href");
            skup_list_href.add(href2);
            j++;
        }
        j = 0;
        for (Element e : pojedynczy2) {
            skup_list.add(pojedynczy2.get(j).text());
            j++;
        }
        int poszukiwany2 = 0;
        for (int i = 0; i < skup_list.size(); i++) {
            if (skup_list.get(i).equals(s3)) {
                poszukiwany2 = i;
            }
        }

        return skup_list_href.get(poszukiwany2);
    }

    public String get_cena(String s1,String s2,String s3) throws IOException {

        Connection connect = Jsoup.connect("http://wiescirolnicze.pl" +skup_href_get(s1,s2,s3));
        Document document = connect.get();

        ArrayList<String> cena_list = new ArrayList<>();
        Elements table = document.getElementsByClass("table table-condensed table-hover table-no-bordered");
        Elements pojedynczy = table.first().select(".price").select("td");
        int j = 0;
        String price;
        for (Element e : pojedynczy) {
          price= pojedynczy.get(j).attr("td");
           cena_list.add(pojedynczy.get(j).text());
            j++;
        }

        if (cena_list.get(0).equals("")) {
            for (int i = 0; i < cena_list.size(); i++) {
                if (cena_list.get(i).equals("")) {
                    cena_list.remove(i);
                }
            }
        }else {
            for (int i = 0; i < cena_list.size(); i++) {
                if (i%2==0) {
                    cena_list.remove(i);
                }
            }
        }
        System.out.println(cena_list.get(0));
        return cena_list.get(0);
    }

    public ArrayList<String> archi_notowan(String s, String s2) throws IOException {

        Document document2 = polaczenie_szcegoly(s,s2).get();
        int j=0;
        ArrayList<String> archi_list = new ArrayList<>();

        Elements table2 = document2.select("li.col-md-3");
       // Elements pojedynczy2 = table2.first().select(".col-md-3");

        for (Element e : table2) {
            archi_list.add(table2.get(j).text());
            //System.out.println(archi_list.get(j));
            j++;

        }

        return archi_list;

    }

    public  Connection polaczenie_szcegoly(String s, String s2) throws IOException {

        Connection connect = Jsoup.connect("http://wiescirolnicze.pl/ceny-rolnicze/" + wybierz_kategorie(s) + "/");
        Document document = connect.get();

        ArrayList<String> szegoly_list_href = new ArrayList<>();
        ArrayList<String> szczegoly_list = new ArrayList<>();
        Elements table = document.getElementsByClass("table table-condensed table-hover");
        Elements pojedynczy = table.first().select("tr").select("a");

        String href;
        int j = 0;
        for (Element e : pojedynczy) {
            href = pojedynczy.get(j).attr("href");
            szegoly_list_href.add(href);
            j++;
        }
        j = 0;
        for (Element e : pojedynczy) {
            szczegoly_list.add(pojedynczy.get(j).text());
            j++;
        }
        j = 0;
        int poszukiwany = 0;
        for (int i = 0; i < szczegoly_list.size(); i++) {
            if (szczegoly_list.get(i).equals(s2)) {
                poszukiwany = i;
            }
        }
        Connection connect2 = Jsoup.connect("http://wiescirolnicze.pl" + szegoly_list_href.get(poszukiwany));
        return  connect2;
    }

    public String polaczenie_szcegoly_Str(String s, String s2) throws IOException {

        Connection connect = Jsoup.connect("http://wiescirolnicze.pl/ceny-rolnicze/" + wybierz_kategorie(s) + "/");
        Document document = connect.get();

        ArrayList<String> szegoly_list_href = new ArrayList<>();
        ArrayList<String> szczegoly_list = new ArrayList<>();
        Elements table = document.getElementsByClass("table table-condensed table-hover");
        Elements pojedynczy = table.first().select("tr").select("a");

        String href;
        int j = 0;
        for (Element e : pojedynczy) {
            href = pojedynczy.get(j).attr("href");
            szegoly_list_href.add(href);
            j++;
        }
        j = 0;
        for (Element e : pojedynczy) {
            szczegoly_list.add(pojedynczy.get(j).text());
            j++;
        }
        j = 0;
        int poszukiwany = 0;
        for (int i = 0; i < szczegoly_list.size(); i++) {
            if (szczegoly_list.get(i).equals(s2)) {
                poszukiwany = i;
            }
        }

        return  "http://wiescirolnicze.pl" + szegoly_list_href.get(poszukiwany);
    }


    public  Double get_cena_archi(String s1,String s2,String s3, String s4) throws IOException, ParseException {
        Document document2 = polaczenie_szcegoly(s1,s2).get();

        int j=0;
        ArrayList<String> archi_list = new ArrayList<>();

        Elements table2 = document2.select("li.col-md-3");

        for (Element e : table2) {
            archi_list.add(table2.get(j).text());
            j++;
        }

        ArrayList<String> archi_list_href = new ArrayList<>();
        Elements adresy=table2.select("a");
        String href;
        j = 0;
        for (Element e : adresy) {
            href = adresy.get(j).attr("href");
            archi_list_href.add(href);
           //System.out.println(archi_list_href.get(j));
            j++;
        }

        int poszukiwany = 0;
        for (int i = 0; i < archi_list.size(); i++) {
            if (archi_list.get(i).equals(s4)) {
                poszukiwany = i;

            }
        }
        Connection connect3 = Jsoup.connect(polaczenie_szcegoly_Str(s1,s2)+archi_list_href.get(poszukiwany));
        Document document3 =connect3.get();

        //System.out.println(polaczenie_szcegoly_Str(s1,s2)+archi_list_href.get(poszukiwany));
        int poszukiwana_cena =0;
        ArrayList<String> lista_cen_archi = new ArrayList<>();
        ArrayList<String> lista_skupow_archi=new ArrayList<>();


        Elements skupy_archi = document3.getElementsByClass("table table-condensed table-hover table-no-bordered sortable");
        Elements skup_archi_td = skupy_archi.first().select("td").select("a");
        j=0;

        for (Element e : skup_archi_td) {
            lista_skupow_archi.add(skup_archi_td.get(j).text());
            System.out.println(lista_skupow_archi.get(j));
            j++;

        }

        //Elements cena_archi = document.getElementsByClass("table table-condensed table-hover table-no-bordered sortable");

        Elements cena_archi_td = skupy_archi.first().select(".price").select("td");
        j = 0;
        String price;
        for (Element e : cena_archi_td) {
            price= cena_archi_td.get(j).attr("td");
            lista_cen_archi.add(cena_archi_td.get(j).text());
          //  System.out.println(lista_cen_archi.get(j));
            j++;
        }

        ArrayList<String> lista_cen_archimax = new ArrayList<>();

        j=0;
        for (int i = 1; i < lista_cen_archi.size();  i =i+ 2 ) {
            lista_cen_archimax.add(lista_cen_archi.get(i));
         //   System.out.println(i+" lool"+lista_cen_archimax.get(j));
           // j++;
        }


        for (int i = 0; i < lista_skupow_archi.size(); i++) {
            if (lista_skupow_archi.get(i).equals(s3)) {
                poszukiwana_cena = i;
            }
        }
        System.out.println(poszukiwana_cena);
       // System.out.println();

        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
        Number number = format.parse(lista_cen_archimax.get( poszukiwana_cena));


        double cena = number.doubleValue();

        return Double.valueOf(Math.round(cena));
    }

public  String porownaj(double cena1, double cena2){
        double roznica=0;
        String roznica_str;
        if(cena1<cena2){
            roznica=cena2-cena1;
            roznica_str ="+"+String.valueOf(roznica)+" ->";
        }else{
            roznica=cena1-cena2;
            roznica_str ="<- "+String.valueOf(roznica)+"+";
        }
        return  roznica_str;
}
    public static void main(String[] args) throws IOException, ParseException {

      //  System.out.println(new Dane_rolnicze().Dzien_bierzacy());
    //  new Dane_rolnicze().get_cena("ZBOŻA","owies","NEOROL Sp. z o. o.");
         new Dane_rolnicze().get_cena_archi("ZBOŻA","owies","AGRITO Monika Świtała","21 października 2019");


    }
}
