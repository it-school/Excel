package itschool;

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
        if (this.model == "")
            return String.format("%10d", id) + "\t" + String.format("%-100s", title) + "\t" + String.format("%20.1f", price);
        else
            return String.format("%10d", id) + "\t" + String.format("%-100s", this.producer + " " + this.model) + "\t" + this.volume + "ml\t" + String.format(
                    "%20.1f", price);
    }
}
