package javaguru.model;

public class User {
    private String name;
    private String surname;
    private String mobileNumber;
    private String email;
    private Comment comment;

    public User(String name, String surname, String mobileNumber, String email, Comment comment) {
        this.name = name;
        this.surname = surname;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
