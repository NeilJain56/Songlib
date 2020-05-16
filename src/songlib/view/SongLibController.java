
// Neil Jain, RUID: 178001542, NetId: nj243
//Rohan Pahwa, RUID: 179005790, NetId: rp930

package songlib.view;

import javafx.collections.FXCollections;


import javafx.collections.ObservableList;
import songlib.app.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Scanner;



public class SongLibController {
      ObservableList<Song> observableList;
	 ArrayList<Song> allSongs = new ArrayList<>();


	@FXML TextField song_name;
	@FXML TextField artist_name;
	@FXML TextField year;
	@FXML TextField album;
	@FXML Button addButton;
	@FXML Button editButton;
	@FXML ListView<Song> listView;
	
	@FXML Label songnamelbl;
	@FXML Label artistnamelbl;
	@FXML Label yearlbl;
	@FXML Label albumlbl;
	
	boolean editCall = false;
	public void edit() throws IOException {
		Song s = listView.getSelectionModel().getSelectedItem();
		addButton.setDisable(false);
		song_name.setText(s.getSongName());
		artist_name.setText(s.getArtist());
		if(s.getYear() != 0) year.setText("" +s.getYear());
		else year.setText("");
		if(s.getAlbum() != "0") album.setText(s.getAlbum());
		else album.setText("");
		
		addButton.setOnAction(event -> {
			
		    try {
		    	delete(s);
		    	if(!add()) {
		    		add(s);
		    	}
		    		song_name.setText("");
					artist_name.setText("");
					year.setText("");
					album.setText("");
					addButton.setDisable(true);
						
		    	
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			});
		
		
	}
	
	
	
	public boolean add(Song s) throws FileNotFoundException {
	
				allSongs.add(s);
				Collections.sort(allSongs);
				song_name.setText("");
				artist_name.setText("");
				year.setText("");
				album.setText("");
				
				listview(s, 0);
				
				try {
					addToTextFile(s);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			
		
		
	}
	
	
	
	public void delete(Song m) throws IOException {
		
		
		
		allSongs.remove(m);
	
		String basePath = new File("").getAbsolutePath();
		File file = new File(basePath + "/src/songlib/view/list.txt"); 
		FileWriter overWriter = new FileWriter(file, false); 
	
		for (Song s: allSongs) {
			if(s.getYear() == 0) {
				overWriter.write( "\n"+ s.getSongName() + "~" + s.getArtist() + "~" + s.getAlbum() );
			}
			else {
			overWriter.write( "\n"+ s.getSongName() + "~" + s.getArtist() + "~" + s.getYear() + "~" + s.getAlbum() );
			}
		}
		overWriter.close();
		
		listview(null,0);
		
	}
	
	
	
	
	

	public boolean add() throws FileNotFoundException {
		boolean error_free = true;
		Song new_song;
		if(song_name.getText().trim().equals("") || artist_name.getText().trim().equals("")) {
			if(song_name.getText().trim().equals("")) {
				song_name.setStyle("-fx-control-inner-background: red");
				error_free = false;
			}
			if(artist_name.getText().trim().equals("")) {
				artist_name.setStyle("-fx-control-inner-background: red");
				error_free = false;
			}
		}


		if (!year.getText().trim().equals("")) {
			try {
				Integer.parseInt(year.getText());
				year.setStyle("-fx-control-inner-background: white");
			}
			catch (NumberFormatException e){
				year.setStyle("-fx-control-inner-background: red");
				error_free = false;
			}
		}
		
		if (error_free) {
			if(popup("ARE YOU SURE?","CONFIRMATION")) {
			song_name.setStyle("-fx-control-inner-background: white");
			artist_name.setStyle("-fx-control-inner-background: white");
			year.setStyle("-fx-control-inner-background: white");
		    
		    if (year.getText().equals("") && album.getText().equals("")){
			    new_song = new Song(song_name.getText(), artist_name.getText());
		    } else if (year.getText().equals("")){
			    new_song = new Song(song_name.getText(), artist_name.getText(),album.getText());
		    } else if (album.getText().equals("")){
			    new_song = new Song(song_name.getText(), artist_name.getText(), Integer.valueOf(year.getText()));
		    } else {
			    new_song = new Song(song_name.getText(), artist_name.getText(), Integer.valueOf(year.getText()), album.getText());
		    }

		    if (!allSongs.contains(new_song)){
				allSongs.add(new_song);
				Collections.sort(allSongs);
				song_name.setText("");
				artist_name.setText("");
				year.setText("");
				album.setText("");
				
				listview(new_song, 0);
				
				try {
					addToTextFile(new_song);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
		    else {
		    	popup("THE SONG YOU ARE TRYING TO ENTER ALREADY EXISTS","ERROR");
		    }
			}
		    
		}
		
		return false;
	}

	public boolean popup(String message, String title) {
		if(title.equals("ERROR")) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText(message);
		alert.setTitle(title);
		
		alert.showAndWait();
		return true;
		
		}
		
		if(title.equals("CONFIRMATION")) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle(title);
			alert.setHeaderText(message);
			Optional<ButtonType> result = alert.showAndWait();
			return result.isPresent() && result.get() == ButtonType.OK;
			
		}
		return true;
		
	}
	public void addToTextFile(Song song) throws IOException {
		String nextLine = formatText(song);
		String basePath = new File("").getAbsolutePath();
			File file = new File(basePath + "/src/songlib/view/list.txt");
			FileWriter fr = new FileWriter(file, true);
			fr.write(nextLine);
			fr.close();

	}

	public String formatText(Song song) {
		String song_name = song.getSongName();
		String artist = song.getArtist();
		int year = song.getYear();
		String album = song.getAlbum();
		if(year == 0) {
			return "\n" +song_name + "~" + artist + "~" + "" + "~" + album;
		}
		return "\n" +song_name + "~" + artist + "~" + year + "~" + album;
	}
	

	
	public void selection(Song s) {
		if(s != null) {
		songnamelbl.setText(s.getSongName());
		
		artistnamelbl.setText(s.getArtist());
		if(s.getYear() != 0) yearlbl.setText("" +s.getYear());
		else yearlbl.setText("");
		
		if(!s.getAlbum().equals("0")) albumlbl.setText(s.getAlbum());
		else albumlbl.setText("");
		
		}
		else {
			songnamelbl.setText("");
			artistnamelbl.setText("");
			yearlbl.setText("");
			albumlbl.setText("");
			
		}
	}
	
	
	
	public void listview(Song s, int x) throws FileNotFoundException {
		addButton.setDisable(true);
		String basePath = new File("").getAbsolutePath();
		File file = new File(basePath + "/src/songlib/view/list.txt");
		file.getAbsolutePath();
		Scanner sc = new Scanner(file);
		while (sc.hasNextLine()){
			String[] song = sc.nextLine().split("~");
			if(song.length == 2) {
				Song new_song = new Song(song[0], song[1]);
				if (!allSongs.contains(new_song)) allSongs.add(new_song);
			}
			if(song.length == 3 ) {
				Song new_song;
				try {
				 new_song = new Song(song[0], song[1], Integer.valueOf(song[2]));
				}
				catch (Exception e) {
					new_song = new Song(song[0], song[1], song[2]);
				}
				if (!allSongs.contains(new_song)) allSongs.add(new_song);
			}
			if(song.length == 4) {
				Song new_song;
				try {
					 new_song = new Song(song[0], song[1], Integer.valueOf(song[2]), song[3]);
				}
				catch (Exception e) {
					new_song = new Song(song[0], song[1], 0, song[3]);
				}
				if (!allSongs.contains(new_song)) allSongs.add(new_song);
			}
			
		}
		Collections.sort(allSongs);
		observableList = FXCollections.observableArrayList(allSongs);
		listView.setItems(observableList);
		if(s == null) {
			listView.getSelectionModel().select(x);
			selection(listView.getSelectionModel().getSelectedItem());
		}
		else {
		listView.getSelectionModel().select(s);
		selection(listView.getSelectionModel().getSelectedItem());
		}
		
		listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>() {
		    @Override
		    public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue) {
		    	if(newValue != null) {
		        selection(newValue);
		    	}
		    }
		});
	sc.close();
	}
	
	public void delete() throws IOException {
		Song deleteThis = listView.getSelectionModel().getSelectedItem();
		int x = listView.getSelectionModel().getSelectedIndex();
		
		int z = allSongs.size();

		if(z == 1) {
			x = 0;
		}
		else if(x+1 == z) {
			x = x -1;
		}
		
		
		
		if(popup("ARE YOU SURE?", "CONFIRMATION")) {
			allSongs.remove(deleteThis);
			if(allSongs.size() == 0) {
				selection(null);
			}
		String basePath = new File("").getAbsolutePath();
		File file = new File(basePath + "/src/songlib/view/list.txt"); 
		FileWriter overWriter = new FileWriter(file, false); 
	
		for (Song s: allSongs) {
			if(s.getYear() == 0) {
				overWriter.write( "\n"+ s.getSongName() + "~" + s.getArtist() + "~" + s.getAlbum() );
			}
			else {
			overWriter.write( "\n"+ s.getSongName() + "~" + s.getArtist() + "~" + s.getYear() + "~" + s.getAlbum() );
			}
		}
		overWriter.close();
		
		listview(null,x);
		}
	}
	

	
}
