package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

   private final NoteMapper noteMapper;

   public NoteService(NoteMapper noteMapper) {
      this.noteMapper = noteMapper;
   }

   public int createNote(Note note){
      return noteMapper.createUser(note);
   }

   public List <Note> getNote(){
      return noteMapper.getNote();
   }

   public List<Note> getNotesByUserId(Integer userid){
      List <Note> noteList= noteMapper.getNoteByUserId(userid);
      if (noteList == null){
         return new ArrayList <Note>();
      }
      return noteList;
   }

}
