package itschool;

import java.util.Comparator;
import java.util.Currency;
import java.util.Locale;

public class Item
{
	public static Comparator<Item> byPriceAsc = (o1, o2) -> (int) (o1.rosnichPrice < o2.rosnichPrice ? -1 : o1.rosnichPrice > o2.rosnichPrice ? 1 : 0);
	public static Comparator<Item> byPriceDesc = (o1, o2) -> (int) (o1.rosnichPrice > o2.rosnichPrice ? -1 : o1.rosnichPrice < o2.rosnichPrice ? 1 : 0);
	int id;
	String title;
	String sklad;
	double rosnichPrice;
	double optPrice;
	double dilPrice;
	double gar;

	public Item()
	{
		this.id = -1;
		this.gar = -1;
		this.dilPrice = -1;
		this.optPrice = -1;
		this.rosnichPrice = -1;
		this.title = "";
		this.sklad = "";
	}

	public Item(int id, String title, String sklad, double rosnichPrice, double optPrice, double dilPrice, double gar)
	{
		this.id = id;
		this.sklad = sklad;
		this.rosnichPrice = rosnichPrice;
		this.optPrice = optPrice;
		this.gar = gar;
		this.dilPrice = dilPrice;
		this.title = title;
	}

	boolean tryToConvert()
	{
		boolean result = false;
		String tempTitle = this.title.trim();
        /*if (tempTitle.contains("   ") && tempTitle.contains("ml"))
        {
            this.model = tempTitle.substring(tempTitle.indexOf("   "), tempTitle.indexOf(" ",
                                                                                         tempTitle.indexOf(tempTitle.contains("edt")?"edt":
                                                                                                                   tempTitle.contains("edp") ? "edp" : "ml"))-3);
            //this.volume = Integer.parseInt(tempTitle.substring(tempTitle.indexOf("ml")-2, tempTitle.indexOf( "ml")));
        }
        */

		return result;
	}

	@Override
	public String toString()
	{
        /*
        for (Locale locale : Locale.getAvailableLocales()) {
            try {
                Currency currency = Currency.getInstance(locale);
                System.out.println(locale + " / " + currency);
            } catch (Exception e) {
            }
        }
*/
		Locale locale = new Locale("uk_ua", "UA"); //("en", "US");
		// Locale locale=Locale.getDefault();
		Currency currency = Currency.getInstance(locale);
		String symbol = currency.getSymbol();

		return String.format("%10d", id) + "\t" + String.format("%-100s", this.title) + "\t" + this.sklad + "\t" + String.format(
				"%20.1f", rosnichPrice) + ' ' + symbol + "\t" + String.format(
				"%20.1f", optPrice) + ' ' + symbol + "\t" + String.format(
				"%20.1f", dilPrice) + ' ' + symbol + "\t" + String.format(
				"%10.0f", gar);
	}
}