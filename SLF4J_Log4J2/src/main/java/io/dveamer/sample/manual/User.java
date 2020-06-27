package io.dveamer.sample.manual;

public class User {

    private final String email;
    private final String name;
    private final String job;

    public User(String email, String name, String job) {
        this.email = email;
        this.name = name;
        this.job = job;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", job='" + job + '\'' +
                '}';
    }
}
