import java.util.ArrayList;

public class User {

    private String username;
    private String password;
    private ArrayList<Media> watched = new ArrayList<>();
    private ArrayList<Media> saved = new ArrayList<>();
    private TextUI ui = new TextUI();

    public User(){

    }

    public String getUsername(){
        return username;
    }

    public boolean checkPassword(String password){
        return password.equals(this.password);
    }

    public void addToWatched(Media m){
        if(! watched.contains(m)){
            watched.add(m);
            ui.displayMessage(m.getTitle + " blev tilføjet til din liste over sete medier");
        }
    }

    public boolean addToSaved(Media m){
        if(! saved.contains(m)){
            saved.add(m);
            ui.displayMessage(m.getTitle + " blev tilføjet til din liste over gemte medier");
            return true;
        }
        else{
            ui.displayMessage(m.getTitle + " er allerede på din liste over gemte medier");
            return false;
        }
    }



}
