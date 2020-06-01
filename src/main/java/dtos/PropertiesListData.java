package dtos;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PropertiesListData {
    private String fileName;
    private String path;
    private String fileComment;
    private Map<String, String> properties;

    public String getFileName() {
        return fileName;
    }

    public String getFileComment() {
        return fileComment;
    }

    public void setFileComment(String fileComment) {
        this.fileComment = fileComment;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public PropertiesListData(){}
    public PropertiesListData(String fileName, String path, String fileComment, Map<String, String> properties) {
        this.fileName = fileName;
        this.path = path;
        this.fileComment = fileComment;
        this.properties = properties;
    }

    
}