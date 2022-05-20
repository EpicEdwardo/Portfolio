package photo_renamer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;


public class Image extends FileNode implements Serializable{
	private ArrayList<String> imageTags = new ArrayList<String>();
	private String prevName;
	private String name; //name with tags included but no .jpg at the end
	private String nameWithNoTags;  //name used for serialize purposes
	private File f; // file Image is representing
	
	/**
	 * @param name
	 *            name of Image
	 * @param parent
	 * 			  the directory image is in
	 */
	public Image(File f,FileNode parent){
		
		// call FileNode constructor
		super(f.getName().substring(0,f.getName().length() - 4), parent, FileType.FILE);
		
		this.name = f.getName().substring(0,f.getName().length() - 4);
		this.prevName = name;
		this.f = f;
		this.nameWithNoTags = this.name.split("@")[0].trim();
	}
	
	
	/**
	 * deletes the tag from Image
	 * 
	 * @param tagName
	 *            name of tag to delete
	 * tag must be in tag for it to be deleted
	 */
	public void deleteTagForImage(String tagName){
		this.prevName = name;
		// remove the tag from the imageTags
		this.imageTags.remove(tagName);
		
		// change the name of image to not have tagName in it
		String[] splitName = name.split(" @" + tagName);
		if (splitName.length == 1){
			this.name = splitName[0];
		} else {
			this.name = splitName[0] + splitName[1];
		}
		
		changeFileName(this.name);
	}
	
	
	/**
	 * changes file name
	 *
	 * @param name
	 * 			the name that we want to change file to
	 */
	public void changeFileName(String name){
		
		// we need an absolute path for renameTo to work, so nameToChangeTo starts out as the file path of f
		String nameToChangeTo = f.getAbsolutePath().substring(0,(f.getAbsolutePath().length() - f.getName().length()));
		
		// then add desired name and jpg to nameToChange
		nameToChangeTo += name + ".jpg";
		
		//and rename it
		File f2 = new File(nameToChangeTo);
		f.renameTo(f2);
		
		//File is immutable, so we have to change the reference in order for f to refer to a new name
		this.f = f2;
		
		//Log this change to name histories
		Log.getInstance().writeHistory(this, name);
	}
	
	/**
	 * gets image of name
	 *
	 * @return name of Image
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * sets image name
	 *
	 * @param name
	 * 			name to change image to
	 */
	public void setName(String name) {
		this.name = name;
		changeFileName(this.name);
	}
	
	/**
	 * add tags to image
	 *
	 * @param tagName
	 * 			String to add to imageTags
	 */
	public void addTagForImage(String tagName){
		this.imageTags.add(tagName);
		this.name += (" @" + tagName);
		changeFileName(this.name);
		
	}
	
	/**
	 * sets tags to image
	 *
	 * @param tagName
	 * 			array of tags to add to imageTags
	 */
	public void setTag(String[] tagName){
		// erase all tags in TagManager
		
		// size changes after calling deleteTags so this is why we have size
		int size = this.imageTags.size();
		for (int i = 0; size > i; i++){
			// only delete the first element because once we delete the
			// tag, it removes the tag from imageTags
			// therefore shifting the position of imageTags
			TagManager.getInstance().deleteTags(imageTags.get(0), this);
		}
		
		// set tags to be nothing
		this.imageTags = new ArrayList<String>();

		for (String tag: tagName){
			this.addTagForImage(tag);
		}		
	}
	
	/**
	 * gets all tags from image
	 *
	 * @return an arraylist of Strings
	 */
	public ArrayList<String> getTag(){
		return this.imageTags;
	}
	
	// idk 
	public void undo(){
		setTag(getTagsFromName(this.prevName));
}
	
	private String[] getTagsFromName(String name) {
		String[] onlyTags = name.split(" @");
		onlyTags = Arrays.copyOfRange(onlyTags, 1, onlyTags.length);
		ArrayList<String> temp = new ArrayList<String>();
		for (String tag: onlyTags) {
			temp.add(tag);
		}
		
		return temp.toArray(new String[temp.size()]);
	}
	
	/**
	 * gets the files name without any tags
	 *
	 * @return files name without any tags
	 */
	public String getOriginalName(){
		return this.nameWithNoTags;
	}
	
	
}
