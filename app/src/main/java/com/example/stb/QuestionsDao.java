package com.example.stb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface QuestionsDao {

    @Query("SELECT * FROM Questions")
    List<Questions> getAllNotes();

    @Query("SELECT * FROM Questions WHERE id IN (:quesIds)")
    List<Questions> loadAllByIds(long[] quesIds);

    @Query("SELECT * FROM Questions WHERE id=:id")
    Questions findById(long id);

    @Query("SELECT id FROM Questions WHERE category=:catChoose")
    List<Integer> loadAllIDsByCategory(String catChoose);

    @Query("SELECT * FROM Questions WHERE category=:catChoose")
    List<Questions> loadAllBYCategory(String catChoose);

    @Insert
    long insert(Questions notes);

    @Insert
    long[] insertAll(Questions... notes);

    @Update
    public void update(Questions... notes);

    @Delete
    void delete(Questions notes);
}

