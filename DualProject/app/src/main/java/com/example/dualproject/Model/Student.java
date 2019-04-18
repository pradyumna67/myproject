package com.example.dualproject.Model;



import java.io.Serializable;

public class Student implements Serializable {

    int id;

    String first;

    String second;

    String email;

    String phone;

    byte[] image;

    String designation;
    String experience;
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id=id;
    }
    public String getFirst()
    {
        return first;
    }
    public void setFirst(String first)
    {
        this.first=first;
    }
    public String getSecond()
    {
        return second;
    }
    public void setSecond(String second)
    {
        this.second=second;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email=email;
    }
    public String getPhone()
    {
        return phone;
    }
    public void setPhone(String phone)
    {
        this.phone=phone;
    }

    public byte[] getImage()
    {
        return image;
    }
    public void setImage(byte[] image)
    {
        this.image=image;
    }

    public String getBranch()
    {
        return designation;
    }
    public void setBranch(String branch)
    {
        this.designation=designation;
    }

    public String getExperience()
    {
        return experience;
    }
    public void setExperience(String experience)
    {
        this.experience=experience;
    }
}
