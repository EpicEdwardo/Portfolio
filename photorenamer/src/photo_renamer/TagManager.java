package photo_renamer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TagManager implements Serializable{
	 
	 // make a singleton class
	 private static final TagManager instance = new TagManager();
	 
	 HashMap<String, ArrayList<Image>> Tags;
	 
	 private TagManager(){
		  Tags = new HashMap<>();
	 }

	 
	 public static TagManager getInstance(){
		 return instance;
	 }
	 
	 
	/**
	* Adds a tag to Tags and to a specific image
	* 
	* @param tagName
	*          name of tag
	* @param image
	*          image to add tag to
	*/
	public void addTags(String tagName, Image image){
			if (Tags.containsKey(tagName)){
				// if tags already contains the key, add to the key the image
				for (Image i: Tags.get(tagName)) {
					System.out.println(i.getOriginalName()+ " image: " + image.getOriginalName());
					if (i.getOriginalName().equals(image.getOriginalName())){
						System.out.println("original name found in tag manager already");
						return;
					}
				}
				Tags.get(tagName).add(image);
				
			} else {
				// create a new key combo
				ArrayList<Image> temp = new ArrayList<Image>();
				temp.add(image);
				Tags.put(tagName, temp);
			}
			image.addTagForImage(tagName);
			Log.getInstance().updateConfig();
			Log.getInstance().updateImageLog();
	}
	
	/**
	* Adds multiple tags to Tags and to a specific image
	* 
	* @param tagNames
	*          array of tagNames to add
	* @param image
	*          image to add tag to
	*/
	public void addTags(String[] tagNames, Image image){
		 
		for (String tag : tagNames) {
			addTags(tag, image);
		}
				
	}
	
	/**
	* get all images which have a specific tag
	* 
	* @param tagName
	*          name of tag
	* @return an arraylist of all images with tagName
	*/
	public ArrayList<Image> getImages(String tagName){
		return Tags.get(tagName);
	}
	 
	/**
	* deletes all instances of tagName in Tags and the corresponding 
	* tag from each image
	* 
	* @param tagName
	*          name of tag to delete
	*/
	public void deleteTags(String tagName){	
		// remove tag from each image
		for (Image i : Tags.get(tagName)){
			i.deleteTagForImage(tagName);
	    }
		// remove tag from Tags
		Tags.remove(tagName);
	 }
	
	/**
	* deletes a tag from a image
	* 
	* @param tagName
	*          name of tag to delete
	* @param image
	*          image to delete tag from
	*/
	public void deleteTags(String tagName, Image image){
		// remove image from the tag in Tags
		Tags.get(tagName).remove(image);
		// remove tag from the image
		image.deleteTagForImage(tagName);
	 }
	 
	

}
