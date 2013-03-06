package service;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 06.03.13
 * Time: 9:59
 * To change this template use File | Settings | File Templates.
 */
public class FileNameGenerator {

    public static String generateName(long l){
        return "Version_" + l;
    }

    public static String getType(String fileName) {
        if (fileName.contains(".")) {
           String [] arr = fileName.split("\\.");
            String type = arr[arr.length - 1];
            return type;
        } else return null;
    }
}
