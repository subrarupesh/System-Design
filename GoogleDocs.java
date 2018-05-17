import java.util.*;
interface FileComponent {
  public void printName();
}
class leafFile implements FileComponent {
  private String file;
  leafFile(String file){
    this.file = file;
  }
  public String getName(){
    return this.file;
  }
  public void printName() {
    System.out.println(this.file+" ");
  }
}
class Directory implements FileComponent {
  private String directory;
  private LinkedList<leafFile> Files = new LinkedList<leafFile>();
  Directory(String directory){
    this.directory = directory;
  }
  public LinkedList<leafFile> getFiles(){
    return Files;
  }
  public void addFile(FileComponent fObj){
      Files.add((leafFile)fObj);
      Collections.sort(Files, new Comparator<leafFile>(){
          public int compare(leafFile f1, leafFile f2){
            return f1.getName().compareTo(f2.getName());
          }
      });
  }
  public void removeFile(int i){
    Files.remove(i);
  }
  public String getName(){
    return this.directory;
  }
  public void printName() {
    System.out.println(this.directory+" ");
    for(int i = 0; i < Files.size(); i++){
      leafFile f1 = Files.get(i);
      f1.printName();
    }
  }
}
class OnlineDocs {
  private LinkedList<leafFile> Files = new LinkedList<leafFile>();
  private LinkedList<Directory> Directories = new LinkedList<Directory>();
  public void addDir(String dirName){
      Directories.add(new Directory(dirName));
      Collections.sort(Directories, new Comparator<Directory>(){
          public int compare(Directory f1, Directory f2){
            return f1.getName().compareTo(f2.getName());
          }
      });
  }
  public void addFileToDir(String dirName, String file){
    int dirIndex = searchDirectory(Directories,dirName);
    if(dirIndex == -1){
      return;
    }
    if(searchFile(Directories.get(dirIndex).getFiles(),file) != -1){
      return;
    }
    dirIndex = searchDirectory(Directories,dirName);
    Directory currentDir = Directories.get(dirIndex);
    currentDir.addFile(new leafFile(file));
  }
  public void moveFileToDir(String dirName, String destDir, String file){
    int dirIndex = searchDirectory(Directories,dirName);
    if(dirIndex == -1){
      return;
    }
    int fileIndex = searchFile(Directories.get(dirIndex).getFiles(),file);
    if(fileIndex == -1){
      return;
    }
    int destIndex = searchDirectory(Directories,destDir);
    if(destIndex == -1){
      return;
    }
    Directory currentDir = Directories.get(dirIndex);
    currentDir.removeFile(fileIndex);
    Directory destDirObj = Directories.get(destIndex);
    leafFile fileComp = Files.get(fileIndex);
    destDirObj.addFile(fileComp);
  }
  public void removeFileFromDir(String dirName, String file){
    int dirIndex = searchDirectory(Directories,dirName);
    if(dirIndex == -1){
      return;
    }
    int fileIndex = searchFile(Directories.get(dirIndex).getFiles(),file);
    if(fileIndex == -1){
      return;
    }
    Directory currentDir = Directories.get(dirIndex);
    currentDir.removeFile(fileIndex);
  }
  public void removeFile(String file){
    int fileIndex = searchFile(Files,file);
    if(fileIndex == -1){
      return;
    }
    Files.remove(fileIndex);
  }
  public void addFile(String file){
      Files.add(new leafFile(file));
      Collections.sort(Files, new Comparator<leafFile>(){
          public int compare(leafFile f1, leafFile f2){
            return f1.getName().compareTo(f2.getName());
          }
      });
  }
  public int searchFile(LinkedList<leafFile> Files, String fileName){
    if(Files.size() == 0){
      return -1;
    }
    return searchFile(Files,fileName,0,Files.size()-1);
  }
  public int searchFile(LinkedList<leafFile> Files, String fileName, int i, int end){
    if(i > end){
      return -1;
    }
    int mid = (i)+(end - i)/2;
    String fName = fileName;
    String fMid = Files.get(mid).getName();
    if(fMid.compareTo(fName) == 0){
      return mid;
    }
    if(fMid.compareTo(fName) < 0){
      return searchFile(Files, fileName, mid+1, end);
    }
    return searchFile(Files, fileName, i, mid-1);
  }
  public int searchDirectory(LinkedList<Directory> Directories, String dir){
    if(Directories.size() == 0){
      return -1;
    }
    return searchDirectory(Directories, dir,0,Directories.size()-1);
  }
  public int searchDirectory(LinkedList<Directory> Directories, String dir, int i, int end){
    if(i > end){
      return -1;
    }
    int mid = (i)+(end - i)/2;
    String dName = dir;
    String dMid = Directories.get(mid).getName();
    if(dMid.compareTo(dName) == 0){
      return mid;
    }
    if(dMid.compareTo(dName) < 0){
      return searchDirectory(Directories, dir, mid+1, end);
    }
    return searchDirectory(Directories, dir, i, mid-1);
  }
  public void printFiles(){
    for(int i = 0; i < Files.size(); i++){
      System.out.println(Files.get(i).getName()+" ");
    }
    System.out.println(" --- ");
  }
  public void printDirectories(){
    for(int i = 0; i < Directories.size(); i++){
      System.out.println(Directories.get(i).getName()+" ");
    }
    System.out.println(" --- ");
  }
  public void printFilesFromDir(String dirName){
    int dirIndex = searchDirectory(Directories,dirName);
    if(dirIndex == -1){
      return;
    }
    Directories.get(dirIndex).printName();
  }
}
public class GoogleDocs {
  public static void main(String args[]){
    OnlineDocs doc = new OnlineDocs();
    doc.addFile("f3.txt");
    doc.addFile("f1.txt");
    doc.addFile("f2.txt");
    doc.addFile("f6.txt");
    doc.addFile("f5.txt");
    doc.addFile("f9.txt");
    doc.addFile("f8.txt");
    doc.addFile("f10.txt");
    doc.addDir("D3");
    doc.addDir("D1");
    doc.addDir("D2");
    doc.addDir("D6");
    doc.addDir("D5");
    doc.addDir("D9");
    doc.addDir("D8");
    doc.addDir("D10");
    doc.printFiles();
    doc.printDirectories();
    doc.addFileToDir("D1","f1.txt");
    doc.addFileToDir("D1","f2.txt");
    doc.addFileToDir("D1","f3.txt");
    doc.addFileToDir("D2","f1.txt");
    doc.addFileToDir("D2","f2.txt");
    doc.addFileToDir("D2","f3.txt");
    doc.removeFileFromDir("D1","f1.txt");
    doc.moveFileToDir("D2","D1","f1.txt");
    doc.printFilesFromDir("D1");
    doc.printFilesFromDir("D2");
  }
}
