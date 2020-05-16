
// Neil Jain, RUID: 178001542, NetId: nj243
//Rohan Pahwa, RUID: 179005790, NetId: rp930 


package songlib.app;



import java.util.Comparator;

public class Song implements Comparable<Song>{
    String songName;
    String artist;
    String album;
    int year;

    public Song(String songName, String artist){
        this.songName = songName.trim();
        this.artist = artist.trim();
        this.album = "";
    }

    public Song(String songName, String artist, int year, String album){
        this.songName = songName.trim();
        this.artist = artist.trim();
        this.album = album.trim();
        this.year = year;
    }

    public Song(String songName, String artist, String album){
        this.songName = songName.trim();
        this.artist = artist.trim();
        this.album = album.trim();
    }

    public Song(String songName, String artist, int year){
        this.songName = songName.trim();
        this.artist = artist.trim();
        this.year = year;
        this.album = "";
    }

    @Override
    public int compareTo(Song one){
        if (one.songName.toLowerCase().equals(this.songName.toLowerCase()) && one.artist.toLowerCase().equals(this.artist.toLowerCase())) return 0;
        //if songs are the same (compareTo returns 0, then song should not be allowed and popup added)
        if (one.songName.toLowerCase().equals(this.songName.toLowerCase())){
            return this.artist.toLowerCase().compareTo(one.artist.toLowerCase());
        } else {
            return this.songName.toLowerCase().compareTo(one.songName.toLowerCase());
        }
    }

    @Override
    public boolean equals(Object one)
    {
        boolean isEqual= false;
        if (one != null && one instanceof Song)
            isEqual = ((this.songName.toLowerCase().equals(((Song) one).songName.toLowerCase()))
                    && (this.artist.toLowerCase().equals(((Song) one).artist.toLowerCase())));

        return isEqual;
    }


    @Override
    public String toString(){
        return ("Song Name: \"" + this.songName + "\", Artist: \"" + this.artist + "\"");
    }

    public String getSongName(){
        return this.songName;
    }
    public String getArtist() {
    	return this.artist;
    }
    public int getYear() {
    	return this.year;
    }
    public String getAlbum() {
    	return this.album;
    }


}

