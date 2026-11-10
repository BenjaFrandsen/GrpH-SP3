import java.util.ArrayList;

public class StreamingService {

    private TextUI ui;
    private ArrayList<User> users = new ArrayList<>();
    private User currentUser;

    public StreamingService(TextUI ui) {
        this.ui = ui;
    }

    public void createUser(){
        String username = ui.promptText("Indtast ønsket brugernavn");
        String password = ui.promptText("Indtast password");

        if(username.isEmpty() || password.isEmpty()){
            ui.displayMessage("Brugernavn og password må ikke være blankt");
            return;
        }
        if(username.contains(";") || password.contains(";")){
            ui.displayMessage("Du må ikke bruge semikolon");
            return;
        }

        User user = new User(username, password);
        users.add(user);
        currentUser = user;
        ui.displayMessage("Brugeren er oprettet, velkommen " + currentUser.getUsername() + "!");
    }

    public void playSeries(Series series) {

        ArrayList<String> seasonOptions = new ArrayList<>();

        for (Season season : series.getSeasons()) {
            seasonOptions.add("Season " + season.getSeasonNumber());
        }

        int seasonChoice = ui.promptMenuChoice(
                seasonOptions,
                "Choose a season for " + series.getTitle()
        );

        if (seasonChoice < 1 || seasonChoice > series.getSeasons().size()) {
            ui.displayMessage("Invalid season");
            return;
        }

        Season selectedSeason =
                series.getSeasons().get(seasonChoice - 1);

        playSeason(selectedSeason);
    }

    public void playSeason(Season season) {

        ArrayList<String> episodeOptions = new ArrayList<>();

        for (Episode episode : season.getEpisodes()) {
            episodeOptions.add("Episode " + episode.getEpisodeNumber());
        }

        int episodeChoice = ui.promptMenuChoice(
                episodeOptions,
                "Choose an episode"
        );

        if (episodeChoice < 1 || episodeChoice > season.getEpisodes().size()) {
            ui.displayMessage("Invalid episode");
            return;
        }

        Episode selectedEpisode =
                season.getEpisodes().get(episodeChoice - 1);

        playEpisode(selectedEpisode);
    }

    public void playEpisode(Episode episode) {

        ui.displayMessage(
                "Now playing episode " + episode.getEpisodeNumber()
        );
    }
}