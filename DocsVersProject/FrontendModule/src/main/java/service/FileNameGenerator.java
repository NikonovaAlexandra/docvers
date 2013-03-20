package service;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 06.03.13
 * Time: 9:59
 * To change this template use File | Settings | File Templates.
 */
public class FileNameGenerator {

    public static String generateUploadVersionName(long l) {
        return "Version_" + l;
    }

    public static String getType(String fileName) {
        if (fileName.contains(".")) {
            String[] arr = fileName.split("\\.");
            String type = arr[arr.length - 1];
            return type;
        } else return null;
    }

    public static String generateDownloadVersionName(String documentName, long version) {
        return documentName + "_" + "v." + "_" + version;
    }

    public static String generateDownloadVersionName(String documentName, long version, String versionType) {
        return documentName + "_" + "v." + "_" + version + "." + versionType;
    }
}
