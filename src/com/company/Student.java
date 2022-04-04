package com.company;

public class Student {
    private final String sid;
    private final String sname;
    private final String birthDate;

    public Student(String sid, String sname, String birthDate) {
        this.sid = sid;
        this.sname = sname;
        this.birthDate = birthDate;
    }

    public String getSid() {
        return sid.replaceAll("\\s+","");
    }

    public String getSname() {
        return sname;
    }

    public String getBirthDate() {
        return birthDate.replaceAll("\\s+","");
    }

    @Override
    public String toString() {
        return "Student{" +
                "sid='" + sid.replaceAll("\\s+","") + '\'' +
                ", sname='" + sname + '\'' +
                ", birthDate='" + birthDate.replaceAll("\\s+","") + '\'' +
                '}';
    }
}
