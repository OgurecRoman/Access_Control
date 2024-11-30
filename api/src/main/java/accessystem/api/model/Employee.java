package accessystem.api.model;

public class Employee {
    private Integer uuid;
    private String firstName;
    private String lastName;
    private String photoPath;
    private Boolean status;

    public Integer getId() {
        return uuid;
    }

    public void setId(Integer id) {
        this.uuid = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String name) {
        this.lastName = name;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String path) {
        this.photoPath = path;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}