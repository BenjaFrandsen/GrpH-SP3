import java.util.ArrayList;

public class Series extends Media {

    private int endYear;
    private ArrayList<Season> seasons;


    public Series(String title, int releaseYear, int endYear, double rating, ArrayList<String> categories, ArrayList<Season> seasons) {
        super(title, releaseYear, rating, categories);
        this.endYear = endYear;
        this.seasons = seasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }


    @Override
    public String toString(){
        String stillAiring;
        if(endYear ==-1) {
            stillAiring = "Kører stadig";
        } else {
            stillAiring = "sluttede " + endYear;
        }
        return super.toString() + " - " + stillAiring + " - " + seasons.size() + " sæsoner ";
    }



}