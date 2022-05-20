package photo_renamer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ImageManager implements Serializable{
	private static final ImageManager instance = new ImageManager();
	HashMap<String, ArrayList<Image>> images;
	
	// create a Singleton class
	private ImageManager() {
		images = new HashMap<String, ArrayList<Image>>();
		
	}
	
	public static ImageManager getInstance() {
		return instance;
	}
	
	/**
	 * Adds Image i to the ArrayList and key dir
	 * @param dir
	 * 			name of directory
	 * @param i
	 * 			the image to be added
	 */			
	public void addImage(String dir, Image i) {
		
		// adds dir to images if it is not there
		addDirectory(dir);
		
		// if images is not empty, then go through it and make sure
		// the image being added is not already there
		// this is tracked using getOriginalName()
		if (!images.get(dir).isEmpty()){
			for (Image x: images.get(dir)){
				if (i.getOriginalName().equals(x.getOriginalName())){
					return;
				}
			}
		}
		images.get(dir).add(i);
	}
	
	
	/**
	 * gets all tags and their corresponding images in ImageManager
	 * @return HashMap<String, ArrayList<Image>> of all tags and
	 *  their corresponding images
	 */			
	public HashMap<String, ArrayList<Image>> getAllImages() {
		return images;
	}
	
	/**
	 * Returns the ArrayList at key dir
	 * @param dir
	 * 			name of directory
	 * @return an ArrayList<Image> of all the images in the directory
	 */
	public ArrayList<Image> getImages(String dir) {
		if (images.containsKey(dir)) {
			System.out.println("old directory" + images.get(dir));
			return images.get(dir);
		}
		else {
			return new ArrayList<Image>();
		}
		
	}
	
	/**
	 * Returns the Image at key dir by a specific name
	 * @param dir
	 * 			name of directory
	 * @param OriginalName
	 * 			OriginalName of Image you want to find
	 * @return image that you want to find
	 */
	public Image getImage(String dir, String OriginalName){
		
		// if the directory does not exist, then create it and return null
		if(!images.containsKey(dir)){
			addDirectory(dir);
			return null;
		}
		
		// search for image by getOriginalName
		for (Image x: images.get(dir)){
			if (OriginalName.equals(x.getOriginalName())){
				return x;
			}
		}
		
		// return null if there is no image in the given directory
		return null;
	}
	
	/**
	 * Creates a dir in images that has an empty ArrayList<Image>
	 * @param dir
	 * 			name of directory
	 */
	public void addDirectory(String dir){
		
		// if you are trying to create a Directory already there, then return
		if (images.containsKey(dir)){
			return;
		}
		
		images.put(dir, new ArrayList<Image>());
	}
	
	public String toString() {
		return images.toString();
	}
}
	
