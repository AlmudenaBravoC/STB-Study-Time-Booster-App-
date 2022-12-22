package com.example.stb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Questions {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public long ID;

    @ColumnInfo(name = "CATEGORY")
    @NonNull
    public String category;

    @ColumnInfo(name = "STATEMENT")
    @NonNull
    public String statement;

    @ColumnInfo(name = "ANSWER 1")
    @NonNull
    public String ans1;

    @ColumnInfo(name = "ANSWER 2")
    @NonNull
    public String ans2;

    @ColumnInfo(name = "ANSWER 3")
    @NonNull
    public String ans3;

    @ColumnInfo(name = "ANSWER 4")
    @NonNull
    public String ans4;

    @ColumnInfo(name = "CORRECT")
    @NonNull
    public int correct;

    public Questions(long ID, String category, String statement, String ans1, String ans2, String ans3, String ans4, int correct) {
        this.ID = ID;
        this.category = category;
        this.statement= statement;
        this.ans1 = ans1;
        this.ans2 = ans2;
        this.ans3 = ans3;
        this.ans4 = ans4;
        this.correct = correct;
    }

    @Ignore
    public Questions(String category, String statement, String ans1, String ans2, String ans3, String ans4, int correct) {
        this.category = category;
        this.statement= statement;
        this.ans1 = ans1;
        this.ans2 = ans2;
        this.ans3 = ans3;
        this.ans4 = ans4;
        this.correct = correct;
    }

    public long getId() {
        return ID;
    }

    public void setId(long id) {
        this.ID = ID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category){
        this.category= category;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement){
        this.statement= statement;
    }

    public String getAns1() {
        return ans1;
    }

    public void setAns1(String ans1){
        this.ans1= ans1;
    }

    public String getAns2() {
        return ans2;
    }

    public void setAns2(String ans2){
        this.ans2= ans2;
    }

    public String getAns3() {
        return ans3;
    }

    public void setAns3(String ans3){
        this.ans3= ans3;
    }

    public String getAns4() {
        return ans4;
    }

    public void setAns4(String ans4){
        this.ans4= ans4;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct){
        this.correct= correct;
    }

}
