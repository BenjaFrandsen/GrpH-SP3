import java.util.ArrayList;

public class User {

    private String username;
    private String password;
    private ArrayList<Media> watched;
    private ArrayList<Media> saved;
    private TextUI ui = new TextUI();

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.watched = new ArrayList<>();
        this.saved = new ArrayList<>();
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
            ui.displayMessage(m.getTitle() + " blev tilføjet til din liste over sete medier");
        }
    }

    public boolean addToSaved(Media m){
        if(! saved.contains(m)){
            saved.add(m);
            ui.displayMessage(m.getTitle() + " blev tilføjet til din liste over gemte medier");
            return true;
        }
        else{
            ui.displayMessage(m.getTitle() + " er allerede på din liste over gemte medier");
            return false;
        }
    }

    public boolean removeFromSaved(Media m){
        if(saved.contains(m)){
            saved.remove(m);
            ui.displayMessage(m.getTitle() + " blev fjernet fra din liste over gemte medier");
            return true;
        }
        else{
            ui.displayMessage(m.getTitle() + " kunne ikke fjernes, da den ikke er på din liste over gemte medier");
            return false;
        }
    }

    public ArrayList<Media> getWatched(){
        return watched;
    }

    public ArrayList<Media> getSaved(){
        return saved;
    }

}
