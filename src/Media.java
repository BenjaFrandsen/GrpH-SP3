import java.util.ArrayList;
import java.util.Locale;

public abstract class Media {

    private String title;
    private int releaseYear;
    private double rating;
    private ArrayList<String> categories;

    public Media(String title, int releaseYear, double rating, ArrayList<String> categories) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public double getRating() {
        return rating;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public boolean hasCategory(String category) {
        for (String c : categories) {
            if (c.equalsIgnoreCase(category.trim())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Media" +
                "title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                ", rating=" + rating +
                ", categories=" + categories;

    }
}

