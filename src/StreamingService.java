import java.util.ArrayList;

public class StreamingService {

    private TextUI ui;
    private ArrayList<User> users = new ArrayList<>();
    private User currentUser;

    public StreamingService(TextUI ui) {
        this.ui = ui;
    }

    private void createUser(){
        String username = ui.promptText("Indtast ønsket brugernavn");
        String password = ui.promptText("Indtast password");

        if(username.isBlank() || password.isBlank()){
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

    private void login(){
        String username = ui.promptText("Indtast dit brugernavn");
        String password = ui.promptText("Indtast dit password");

        User user = findUser(username);
        if(user != null && user.getPassword().equals(password)){
            currentUser = user;
            ui.displayMessage("Velkommen tilbage, " + username + "!");
        }
        else{
            ui.displayMessage("Forkert brugernavn eller passwordd");
        }

    }

    private User findUser(String username){
        for (User u : users){
            if (u.getUsername().equals(username)){
                return u;
            }
        }
        return null;
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