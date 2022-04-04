package com.company;

public class Course {
    private final String cid;
    private final String cname;
    private final String credit;

    public Course(String cid, String cname, String credit) {
        this.cid = cid;
        this.cname = cname;
        this.credit = credit;
    }

    public String getCid() {
        return cid.replaceAll("\\s+","");
    }

    public String getCname() {
        return cname;
    }

    public String getCredit() {
        return credit.replaceAll("\\s+","");
    }

    @Override
    public String toString() {
        return "Course{" +
                "cid='" + cid.replaceAll("\\s+","") + '\'' +
                ", cname='" + cname + '\'' +
                ", credit='" + credit.replaceAll("\\s+","") + '\'' +
                '}';
    }
}
