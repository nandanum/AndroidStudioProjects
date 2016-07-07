package com.mnandanuri.mytodoapp.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

import java.util.List;

@Table(name = "DBListItems")
public class ItemsList extends Model {
    // If name is omitted, then the field name is used.
    @Column(name = "TaskName")
    public String taskName;

    @Column(name = "DateCreated")
    public String dateCreated;

    @Column(name = "TaskNotes")
    public String taskNotes;

    @Column(name = "Status")
    public String status;


    public ItemsList() {
        super();

    }

    public ItemsList(String taskName, String dateCreated,String taskNotes,String status) {
        super();
        this.taskName= taskName;
        this.dateCreated=dateCreated;
        this.taskNotes=taskNotes;
        this.status = status;

    }
    public void setTaskName(String name){
        this.taskName = name;
    }

    public void setTaskDate(String date){
        this.dateCreated = date;
    }

    public void setNotes(String notes){
        this.taskNotes= notes;
    }

    public void setStatus(String status){
        this.status= status;
    }

    public static List<ItemsList> getAll() {
        return new Select()
                .from(ItemsList.class)
                //.where("dateCreated = ?", category.getId())
                .orderBy("Id ASC")
                .execute();
    }

    public static List<ItemsList> getItemById(long id) {
        return new Select()
                .from(ItemsList.class)
                .where("Id = ?", id)
                .execute();
    }
    public static List<ItemsList> getItemIdByTaskName(String name) {
        return new Select("rowid")
                .from(ItemsList.class)
                .where("TaskName = ?", name)
                .execute();
    }

    public static List<ItemsList> removeItem(long id) {
        return new Delete()
                .from(ItemsList.class)
                .where("Id = ?", id)
                .execute();
    }
    public static void updateItem(ItemsList items,  Long id) {
        String subSet="TaskName = ?,DateCreated = ?,TaskNotes = ?,Status = ?";
        String args[]={items.taskName,items.dateCreated,items.taskNotes,items.status};
         new Update(ItemsList.class)
                 .set(subSet,args)
                .where("Id = ?", id)
                .execute();
    }

}
