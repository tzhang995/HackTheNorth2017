package hackers.hackthenorth2017;

/**
 * Created by tony on 16/09/17.
 */

public class BasicMessage {
    private String name;
    private String photoUrl;
    private String imageUrl;

    public BasicMessage(){

    }

    public BasicMessage(String name, String photoUrl, String imageUrl){
        this.name = name;
        this.photoUrl = photoUrl;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
