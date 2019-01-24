package itschool;

import java.util.Currency;
import java.util.Locale;

public class Item
{
    int id;
    String title;
    double price;
    String producer;
    String model;
    int volume;
    boolean isTester;
    boolean isForMen;

    public Item()
    {
        this.id = -1;
        this.title = "";
        this.price = -1;
        this.producer = "";
        this.model = "";
        this.volume = -1;
        this.isForMen = false;
        this.isTester = false;
    }

    public Item(int id, String title, double price)
    {
        this.id = id;
        this.title = title;
        this.price = price;
        this.producer = "";
        this.model = "";
        this.volume = -1;
        this.isForMen = false;
        this.isTester = false;
    }

    boolean tryToConvert()
    {
        boolean result = false;
        String tempTitle = this.title.trim();
        if (tempTitle.contains("   ") && tempTitle.contains("ml"))
        {
            this.model = tempTitle.substring(tempTitle.indexOf("   "), tempTitle.indexOf(" ",
                                                                                         tempTitle.indexOf(tempTitle.contains("edt")?"edt":
                                                                                                                   tempTitle.contains("edp") ? "edp" : "ml"))-3);
            //this.volume = Integer.parseInt(tempTitle.substring(tempTitle.indexOf("ml")-2, tempTitle.indexOf( "ml")));
        }

        return result;
    }

    @Override
    public String toString()
    {
        Locale locale=new Locale("uk_ua", "UA") ;//("en", "US");
        Currency currency=Currency.getInstance(locale);
        String symbol = currency.getSymbol();

        if (this.model == "")
            return String.format("%10d", id) + "\t" + String.format("%-60s", title) + "\t" + String.format("%20.1f", price) + ' ' + symbol;
        else
            return String.format("%10d", id) + "\t" + String.format("%-60s", this.producer + " " + this.model) + "\t" + this.volume + "ml\t" + String.format(
                    "%20.1f", price) + ' ' + symbol;
    }
}
