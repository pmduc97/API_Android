package ducpham.ducpham;

public class UserBEAN {
    private String _id;
    private String userName;
    private String passWord;
    private String fullName;
    private String email;
    private String phone;
    private String birthDay;
    private String homeTown;
    private boolean quyen;

    public UserBEAN() {
    }

    public UserBEAN(String _id, String userName, String passWord, String fullName, String email, String phone, String birthDay, String homeTown, boolean quyen) {
        this._id = _id;
        this.userName = userName;
        this.passWord = passWord;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.birthDay = birthDay;
        this.homeTown = homeTown;
        this.quyen = quyen;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public boolean isQuyen() {
        return quyen;
    }

    public void setQuyen(boolean quyen) {
        this.quyen = quyen;
    }

    @Override
    public String toString() {
        return "UserBEAN{" +
                "_id='" + _id + '\'' +
                ", userName=" + userName + '\'' +
                ", passWord=" +passWord + '\'' +
                ", fullName=" + fullName + '\'' +
                ", email=" + email + '\'' + ", phone=" + phone + '\'' + ", birthDay=" + birthDay + '\'' +
                ", homeTown=" + homeTown + '\'' + ", quyen=" + quyen + '\'' +
                '}';
    }
}
