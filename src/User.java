import java.util.ArrayList;

public class User {

    private String username;
    private String password;
    private ArrayList<Media> watched;
    private ArrayList<Media> saved;

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
        }
    }

    public boolean addToSaved(Media m){
        if(! saved.contains(m)){
            saved.add(m);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean removeFromSaved(Media m){
        if(saved.contains(m)){
            saved.remove(m);
            return true;
        }
        else{
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
