import java.util.ArrayList;

public class Main {

    public void main() {

        //Test af episode og klm
        /*
        ArrayList<Episode> episodes = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            episodes.add(new Episode(i));
        }

        Season s1 = new Season(1, episodes);

        System.out.println("Season " + s1.getSeasonNumber() + " has " +s1.getEpisodes().size() + " episodes");

        for (Episode e: s1.getEpisodes()) {
            System.out.println("Episode" + e.getEpisodeNumber());
        }

         */

        TextUI ui = new TextUI();
        StreamingService streamingService = new StreamingService(ui);

        ArrayList<Episode> episodes = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            episodes.add(new Episode(i));
        }

        Season season1 = new Season(1, episodes);

        ArrayList<Season> seasons = new ArrayList<>();
        seasons.add(season1);

        Series series = new Series("Breaking Bad", seasons);

        streamingService.playSeries(series);


    }
}
