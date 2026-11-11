import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class StreamingService {

    private TextUI ui;
    private FileIO io;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Media> media = new ArrayList<>();
    private User currentUser;
    private final String USER_PATH = "data/users.csv";

    public StreamingService(TextUI ui, FileIO io) {
        this.ui = ui;
        this.io = io;
    }
    public void startSession() {
        loadMedia();
        loadUsers();

        boolean running = true;

        while(running) {
            if (currentUser == null) {
                ArrayList<String> options = new ArrayList<>();

                options.add("Start");
                options.add("Exit");

                int choice = ui.promptMenuChoice(options, "Velkommen til StreamingServices");

                switch (choice) {
                    case 1:
                        startMenu();
                        break;
                    case 2:
                        endSession();
                        running = false;
                        break;
                }
            } else {
                ArrayList<String> options = new ArrayList<>();
                options.add("Logout");
                options.add("Exit");

                int choice = ui.promptMenuChoice(options, " Welcome " + currentUser.getUsername());

                endSession();

                if (choice == 2) {
                    running = false;
                }
            }
        }
    }

    public void endSession() {
        saveUsers();

        if (currentUser != null) {
            ui.displayMessage("Farvel " + currentUser.getUsername());
            currentUser = null;
        } else {
            ui.displayMessage("Farvel");
        }
    }

    private void saveUsers() {
        ArrayList<String> data = new ArrayList<>();

        for (User user : users) {
            data.add("User: " + user.getUsername() + ":" + user.getPassword());

            for(Media m : user.getWatched()) {
                data.add("Watched;" + m.getTitle());
            }

            for(Media m : user.getWatched()) {
                data.add("Saved;" + m.getTitle());
            }
        }

        io.saveData(data, USER_PATH, "StreamingService");
    }

    private void loadUsers() {
        users.clear();

        ArrayList<String> data = io.readData(USER_PATH);
        User user = null;

        for (String line : data) {
            if (line.startsWith("USER;")) {
                String[] parts = line.split(";", 3);

                if (parts.length == 3) {
                    user = new User(parts[1], parts[2]);
                    users.add(user);
                }

            } else if (user != null && line.startsWith("WATCHED;")) {
                String title = line.substring(8);

                for (Media m : media) {
                    if (m.getTitle().equals(title)) {
                        user.addToWatched(m);
                        break;
                    }
                }
            } else if (user != null && line.startsWith("SAVED;")) {
                String title = line.substring(6);

                for (Media m : media) {
                    if (m.getTitle().equals(title)) {
                        user.addToSaved(m);
                        break;
                    }
                }
            }
        }
    }







    private void startMenu(){
        ArrayList<String> options = new ArrayList<>();
        options.add("1) Log ind");
        options.add("2) Opret bruger");

        int choice = ui.promptMenuChoice(options, "Velkommen! Vælg en mulighed");

        while(currentUser == null){
            switch (choice){
                case 1:
                    login();
                    break;
                case 2:
                    createUser();
                    break;
            }
        }
        mainMenu();
    }

    private void mainMenu(){
        ArrayList<String> menuOptions = new ArrayList<>();
        menuOptions.add("1) Søg efter bestemt film");
        menuOptions.add("2) Søg alle film i bestemt kategori");
        menuOptions.add("3) Se din liste over gemte film");
        menuOptions.add("4) Se din liste over sete film");
        menuOptions.add("5) Forlad program");

        while (currentUser != null){
            int choice = ui.promptMenuChoice(menuOptions, "Vælg en mulighed");

            switch (choice){
                case 1:
                    searchByTitle();
                    break;
                case 2:
                    searchByCategory();
                    break;
                case 3:
                    showSaved();
                    break;
                case 4:
                    showWatched();
                    break;
                case 5:
                    endSession();
                    break;
                default:

            }
        }

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

    private Movie parseMovie(String line) {
        String[] parts = line.split(";");
        String title = parts[0].trim();
        int year = Integer.parseInt(parts[1].trim());
        ArrayList<String> categories = parseCategories(parts[2]);
        double rating = parseRating(parts[3]);
        return new Movie(title,year,rating,categories);
    }


    private Series parseSeries(String line) {
        String[] parts = line.split(";");
        String title = parts[0].trim();
        String year = parts[1].trim();
        int releaseYear = 0;
        int endYear = 0;
        if (year.contains("-")) {
            String[] years = year.split("-");
            releaseYear = Integer.parseInt(years[0].trim());
            if (years.length > 1) {
                Integer.parseInt(years[1].trim());
            } else {
                endYear = -1;
            }
        }
        ArrayList<String> categories = parseCategories(parts[2]);
        double rating = parseRating(parts[3]);

        StringBuilder sb = new StringBuilder();
        for (int i = 4; i < parts.length; i++) {
            sb.append(parts[i]).append(",");
        }

        ArrayList<Season> seasons = new ArrayList<>();
        for (String s : sb.toString().split(",")) {
            s = s.trim();
            if (s.isEmpty()) {
                continue;
            }
            String[] ss = s.split("-");
            int seasonNumber = Integer.parseInt(ss[0].trim());
            int episodeCount = Integer.parseInt(ss[1].trim());

            ArrayList<Episode> episodes = new ArrayList<>();
            for (int i = 1; i <= episodeCount; i++) {
                episodes.add(new Episode(i));
            }
            seasons.add(new Season(seasonNumber, episodes));
        }
        return new Series(title, releaseYear, endYear, rating, categories, seasons);
    }

    private ArrayList<String> parseCategories(String text) {
        ArrayList<String> categories = new ArrayList<>();
        for (String c : text.split(",")) {
            categories.add(c.trim());
        }
        return categories;
    }

    private void showResults(ArrayList<Media> results) {
        if (results.isEmpty()) {
            ui.displayMessage("No results");
            return;
        }
        ArrayList<String> texts = new ArrayList<>();
        for (Media m: results) {
            texts.add(m.toString());
        }
        ui.displayMessage(texts, "Results - " + results.size());
    }

    public void loadMedia() {
        ArrayList<String> movieLines = io.readData("data/movies.csv");
        ArrayList<String> seriesLines = io.readData("data/series.csv");

        if (movieLines.isEmpty() && seriesLines.isEmpty()) {
            ui.displayMessage("Could not read movies & series from file");
            return;
        }

        for (String line: movieLines) {
            if (line.isBlank()) {
                continue;
            }
            try {
                media.add(parseMovie(line));
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Skipping current movie " + line);
            }
        }

        for (String line : seriesLines) {
            if (line.isBlank()) {
                continue;
            }
            try {
                media.add(parseSeries(line));
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Skipping current series " + line);
            }
        }

    }

    public ArrayList<Media> searchByTitle() {
        String search = ui.promptText("Enter title").trim();
        ArrayList<Media> results = new ArrayList<>();
        for (Media m: media) {
            if (m.getTitle().toLowerCase().contains(search.toLowerCase())) {
                results.add(m);
            }
        }
        showResults(results);
        return results;
    }

    public ArrayList<Media> searchByCategory() {
        String search = ui.promptText("Enter category ").trim();
        ArrayList<Media> results = new ArrayList<>();
        for (Media m: media) {
            if (m.hasCategory(search)) {
                results.add(m);
            }
        }
        showResults(results);
        return results;
    }

    public ArrayList<Media> searchByRating() {
        String text = ui.promptText("Type minimum rating").trim().replace(",", ".");
        ArrayList<Media> results = new ArrayList<>();
        try {
            double minRating = Double.parseDouble(text);
            for (Media m : media) {
                if (m.getRating() >= minRating) {
                    results.add(m);
                }
            }
        } catch (NumberFormatException e) {
            ui.displayMessage("Unavaillable rating");
            return results;
        }
        showResults(results);
        return results;
    }


    
    private double parseRating (String text) {
        return Double.parseDouble(text.trim().replace(",","."));
    }

    private void saveMedia(Media media) {
        try {
            currentUser.addToSaved(media);
            System.out.println(media.getTitle() + " Has been added to your saved media!");
        } catch (Exception e) {
            System.out.println("Unknown error when saving media");
        }

    }

    private void showSaved() {
        System.out.println("Here are your saved shows:");
        for(Media media : currentUser.getSaved()) {
            System.out.println(media.getTitle());
        }
    }

    private void showWatched() {
        System.out.println("Here are your watched shows:");
        for(Media media : currentUser.getWatched()) {
            System.out.println(media.getTitle());
        }
    }

    private void removeFromSaved(Media media) {
        currentUser.removeFromSaved(media);
    }




}