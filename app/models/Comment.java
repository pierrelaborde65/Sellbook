package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

/**
 * Created by Fati on 15/11/2016.
 */
@Entity
public class Comment extends com.avaje.ebean.Model {
    public static Model.Finder<Long, Comment> find = new Model.Finder<Long, Comment>(Comment.class);

    @Id @GeneratedValue
    public long idComment;

    public long idUser;

    public long idDiary;

    public String messageComment;

    public String dateComment;

    public Comment(){}

    public Comment(long idComment, long idUser, long idDiary,  String messageComment, String dateComment){
        this.idComment = idComment;
        this.idUser = idUser;
        this.idDiary = idDiary;
        this.messageComment = messageComment;
        this.dateComment = dateComment;
        this.save();
    }

    public static Finder<Long,Comment> getFind() {
        return find;
    }

    public static void setFind(Finder<Long, Comment> find) {
        Comment.find = find;
    }

    public Long getIdComment() {
        return idComment;
    }

    public void setId(Long id) {
        this.idComment = id;
    }

    public String getMessageComment() {
        return messageComment;
    }

    public void setMessageComment(String msg) {this.messageComment = msg;}

    public String getDateComment(){ return dateComment;}
}
