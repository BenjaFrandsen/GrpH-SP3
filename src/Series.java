import java.util.ArrayList;

public class Series {

    private String title;
    private ArrayList<Season> seasons;

    public Series(String title, ArrayList<Season> seasons) {
        this.title = title;
        this.seasons = seasons;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }
}