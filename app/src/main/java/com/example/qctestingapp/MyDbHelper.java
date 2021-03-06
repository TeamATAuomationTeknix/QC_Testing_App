package com.example.qctestingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MyDbHelper extends SQLiteOpenHelper {
    String tb_question="question_data";
    String tb_part="part_table";
    String tb_answer="answer_table";
    String tb_temp_ans="temp_table_ans";
    String tb_remaining_parts="remaining_parts";
    String tb_master="master_images";
    String tb_appName="app_names";
    String tb_employee="emp_table";
    String tb_tmp_battery="tmp_battery";
    String tb_ip_adress="ip_adress_tbl";
    String tb_remark="remark_tbl";
    ArrayList<String> pnames;
    ArrayList<Questions_main> questionsList;
    SQLiteDatabase mydatabase;

    public static final String DB_NAME="my_database";

    public MyDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {

        super(context, name, factory, version);
    }
    public MyDbHelper(Context context){
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+tb_answer+" (id Integer , question text,answer Integer, Highlight varchar, partname varchar, qr varchar, user varchar,TimeStamp datetime,remark varchar,remarkImage blob)");
        // id,qid,partname,qrcode,operator,answer,partTime,TimeStamp,fullTime,qr_code

        db.execSQL("create table "+tb_temp_ans+"(id Integer ,qid Integer, partname text,qrcode Text, operator Text, answer varchar, partTime varchar, TimeStamp datetime,remark varchar,remarkImage blob)");
        db.execSQL("create table "+tb_part+"(id Integer primary key autoincrement,part_name varchar,app_name varchar)");
        db.execSQL("create table "+tb_question+"(id Integer primary key,question varchar, Highlight varchar, part_name varchar, model_name varchar)");
        db.execSQL("create table "+tb_remaining_parts+"(id Integer,part_name varchar,qr_code varchar, fullTime Integer)");
        db.execSQL("create table "+tb_master+" (id integer primary key autoincrement,part_name text, model_name text, image blob)");
        db.execSQL("create table "+tb_appName+"(id Integer,app_name varchar)");
        db.execSQL("create table "+tb_employee+"(id Integer primary key autoincrement,token_no Integer, name varchar)");
        db.execSQL("create table "+tb_tmp_battery+"(id Integer primary key autoincrement,mainqr varchar, batteryqr varchar, status varchar)");
        db.execSQL("create table "+tb_ip_adress+"(ip_address varchar)");
        db.execSQL("insert into "+tb_ip_adress+"(ip_address) values ('192.168.0.13')");
        db.execSQL("create table "+tb_remark+"(id Integer primary key autoincrement,question_id Integer,remark varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert_data( ArrayList<Questions_main> questions,String partname,String qr,String user){

        mydatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        for(Questions_main q:questions) {
            Cursor cursor = mydatabase.query(tb_answer, new String[]{"question", "qr"}, "question=? and qr=?", new String[]{q.getQuestion(), qr}, null, null, null);
            if (cursor.moveToFirst()) {
                String where = "question=? and qr=?";
                values.put("id", q.getId());
                values.put("question", q.getQuestion());
                values.put("answer", q.getAnswer());
                values.put("Highlight", q.isHighlighted());
                values.put("qr", qr);
                values.put("user", user);
                values.put("partname", partname);
                values.put("remark",q.getRemark());
                if(q.getAnswer().equals(Questions_main.NOT_OK) && q.getRemarkImage()!=null)
                values.put("remarkImage",q.getRemarkImage());
                Date date=new Date();
                SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                values.put("timestamp",formatter.format(date));
                // mydatabase.insert(tb_answer,null,values);
                mydatabase.update(tb_answer, values, where, new String[]{q.getQuestion(), qr});
            }
            else{
                values.put("id", q.getId());
                values.put("question", q.getQuestion());
                values.put("answer", q.getAnswer());
                values.put("Highlight", q.isHighlighted());
                values.put("qr", qr);
                values.put("user", user);
                values.put("partname", partname);
                values.put("remark",q.getRemark());
                Date date=new Date();
                SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                values.put("timestamp",formatter.format(date));
                if(q.getAnswer().equals(Questions_main.NOT_OK) && q.getRemarkImage()!=null)
                    values.put("remarkImage",q.getRemarkImage());
                mydatabase.insert(tb_answer,null,values);
            }
        }

    }
    //todo get all answers******************************
    public ArrayList<Questions_main> getAllAnswers() {
        // (id Integer , question text,answer Integer, Highlight Integer, partname varchar, qr varchar, user varchar)
        questionsList=new ArrayList<>();
        String[] resultColumns = {"id", "question", "answer","Highlight","partname","qr","user"};
        SQLiteDatabase mydatabase = this.getReadableDatabase();
        Cursor cursor = mydatabase.query(false, tb_answer, resultColumns, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String question = cursor.getString(1);
                String answer = cursor.getString(2);
                String highlight=cursor.getString(3);
                Questions_main q=new Questions_main(id,question,highlight);
                q.setAnswer(answer);
                questionsList.add(q);
                Log.e("from database", id + " " + question);
                Log.e("from database", answer);

            } while (cursor.moveToNext());
        }
        return questionsList;
    }
    public ArrayList<Questions_main> getAnswersBydata(String qr_res, String partname) {
        Log.e(qr_res,partname);
        questionsList=new ArrayList<>();
        SQLiteDatabase mydatabase = this.getReadableDatabase();
        String[] resultColumns = {"id", "question", "answer","Highlight","partname","qr","user","remark","remarkImage"};
        String where="qr=? and partname=?";
        Cursor cursor= mydatabase.query(tb_answer, resultColumns, where, new String[]{qr_res, partname}, null, null, null, null);
        if (cursor.moveToFirst()) {
            Log.e("tag","update operation");
            do {
                int id = cursor.getInt(0);
                String question = cursor.getString(1);
                String answer = cursor.getString(2);
                String highlight=cursor.getString(3);

                Questions_main q=new Questions_main(id,question,highlight);
                q.setAnswer(answer);
                q.setRemark(cursor.getString(7));
                q.setRemarkImage(cursor.getBlob(8));
                questionsList.add(q);
                Log.e("from database", id + " " + question);
                Log.e("from database", answer);

            } while (cursor.moveToNext());
        }
        return questionsList;
    }
    //todo add partnames***************************
    public void addPartNames(List<MyDbHelper.Parts> partnames){
        Log.e("tag","insert");
        SQLiteDatabase mydatabase=this.getWritableDatabase();
        mydatabase.execSQL("delete from " +tb_part);
        ContentValues values=new ContentValues();
        for(Parts q:partnames) {
            values.put("part_name",q.getPartname());
            values.put("app_name",q.getAppname());
            mydatabase.insert(tb_part,null,values);

        }

    }
    //todo get Partnames*****************************
    public ArrayList<String> getPartnames(){
        pnames=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String[] cols={"id","part_name"};
        Cursor cursor=db.query(tb_part,cols,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                pnames.add(cursor.getString(1));

            } while (cursor.moveToNext());
        }
        return pnames;
    }

    public ArrayList<String> getPartnamesByApp(String appname){
        pnames=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String[] cols={"id","part_name"};
        String where="app_name=?";
        Cursor cursor=db.query(tb_part,cols,where,new String[]{appname},null,null,null);
        if(cursor.moveToFirst()){
            do {
                pnames.add(cursor.getString(1));

            } while (cursor.moveToNext());
        }
        return pnames;
    }
    //********************** todo add questions
    public void addQuestions(ArrayList<Questions_main> questions, String partname, String model_name){
        Log.e("MyDbHelper","size:  "+questions.size()+" "+partname);
        SQLiteDatabase mydatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        for(Questions_main q:questions) {
            Log.e("MyDbHelper","adding "+q.getQuestion());
            values.put("id",q.getId());
            values.put("question",q.getQuestion());
            String h=q.highlight;
            values.put("Highlight",h);
            values.put("part_name",partname);
            values.put("model_name",model_name);
            mydatabase.insert(tb_question,null,values);

        }
        Log.e("MyDbHelper","questions added to local db");
    }
    //todo get Qeestions***********************************************
    public ArrayList<Questions_main> getQuestions(String partname, String model_name){
        if(partname==null)partname="";
        Log.e("Mydbhelper","reading partnames");
        questionsList=new ArrayList<>();
        String[] resultColumns = {"id", "question", "Highlight"};
        SQLiteDatabase mydatabase = this.getReadableDatabase();
        String where="part_name=? and model_name=?";

        Cursor cursor = mydatabase.query( tb_question, resultColumns, where, new String[]{partname,model_name}, null, null, null, null);
        Log.e("Mydbhelper","reading questions");
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String question = cursor.getString(1);
                String highlight = cursor.getString(2);

                questionsList.add(new Questions_main(id,question,highlight));
                Log.e("from database", id + " " + question);


            } while (cursor.moveToNext());
        }
        return questionsList;
    }

    public void submitTempAnswers( int qid, String partname, String qrcode, String operator,int answer,String partTime, String TimeStamp, String remark) {
        // (id Integer ,qid Text, question text,answer Integer, Highlight Integer, partname varchar, qr varchar, user varchar)
        SQLiteDatabase mydatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        //id,qid,partname,qrcode,operator,answer,partTime,TimeStamp
        //  values.put("id",id);
        values.put("qid",qid);
        values.put("partname",partname);
        values.put("qrcode",qrcode);
        values.put("operator",operator);
        values.put("answer",answer);
        values.put("partTime",partTime);
        values.put("remark",remark);
        values.put("TimeStamp", TimeStamp);
        mydatabase.insert(tb_temp_ans,null,values);
    }
    public  JSONArray getTempAnswers(){
        ArrayList<Questions_main> q=new ArrayList<>();
        SQLiteDatabase mydatabase=this.getWritableDatabase();
        //id,qid,partname,qrcode,operator,answer,partTime,TimeStamp
        String[] resultColumns = { "qid","partname","qrcode","operator","answer","partTime","TimeStamp","remark"};
        Cursor cursor = mydatabase.query(tb_temp_ans,resultColumns,null,null,null,null,null);
        JSONObject jsonObjet=new JSONObject();
        JSONArray jsonArray=null;
        if(cursor.moveToFirst()){
            jsonArray=new JSONArray();
            do{
                try {
                    jsonObjet= new JSONObject();
                    jsonObjet.put("id_fk_lhs_all_prt_que_tbl", cursor.getInt(0));
                    jsonObjet.put("partname", cursor.getString(1));
                    String qr_res=cursor.getString(2);
                    if(qr_res!=null)
                        jsonObjet.put("qr_code", qr_res);
                    else
                        jsonObjet.put("qr_code", "100");
                    jsonObjet.put("operator", cursor.getString(3));

                    jsonObjet.put("answer", cursor.getInt(4));
                    jsonObjet.put("partTime",cursor.getString(5));
                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    jsonObjet.put("currentTime",formatter.format(new Date()));
                    jsonObjet.put("remark",cursor.getString(7));
                    jsonArray.put(jsonObjet);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }while(cursor.moveToNext());
        }
        return jsonArray;
    }

    public void deleteTempAnswers(){
        SQLiteDatabase mydatabase=this.getWritableDatabase();
        mydatabase.execSQL("delete from " +tb_temp_ans);
        mydatabase.close();
    }

    public void setRemainingParts(LinkedList<String> parts, long fullTime, String qr_code){
        int id=1;
        SQLiteDatabase mydatabase=this.getWritableDatabase();
        mydatabase.execSQL("delete from " +tb_remaining_parts);
        for(String partname:parts) {
            ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("part_name", partname);
            values.put("fullTime", fullTime);
            values.put("qr_code", qr_code);
            mydatabase.insert(tb_remaining_parts,null,values);
            id++;
        }
    }
    public void deletAllParts() {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from " +tb_part);
    }

    public Cursor getRemainingParts(){
        String[] resultColumns = { "part_name","fullTime","qr_code"};
        SQLiteDatabase mydatabase = this.getReadableDatabase();
        //ArrayList<Questions_main> pnames=new ArrayList<>();
        Cursor cursor = mydatabase.query(tb_remaining_parts,resultColumns,null,null,null,null,null);

        return cursor;
    }
    public void deleteRemainingParts(){
        SQLiteDatabase mydatabase = this.getReadableDatabase();
        mydatabase.execSQL("delete from " +tb_remaining_parts);
    }

    public long addImage(String partname, String model_nm , byte[] img)
    {
//(id integer primary key, model_name text, image blob)

        SQLiteDatabase mydatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("model_name", model_nm);
        contentValues.put("part_name",partname);
        contentValues.put("image", img);
        Log.e("tag","add image");
        //Toast.makeText(context,"Image Inserted",Toast.LENGTH_SHORT).show();
        return mydatabase.insert(tb_master, null, contentValues);
    }
    public Cursor getMaxImageRegResult(){
        SQLiteDatabase mydatabase = this.getWritableDatabase();
        return mydatabase.query(tb_master, new String[] {"id"}, null, null, null, null, null);
    }

    public Cursor getAllImagesOfSpecificModel(String model_name,String part_name) {
        SQLiteDatabase mydatabase = this.getReadableDatabase();
        String where="model_name=? and part_name=?";
        Cursor cursor = mydatabase.query(tb_master, new String[] {"id", "image"}, where, new String[]{model_name,part_name}, null, null, null);;
        //mydatabase.close();
        return cursor;
    }
    public Cursor getAllImagesOfSpecificModel(String part_name) {
        Log.e("geting model",part_name);
        SQLiteDatabase mydatabase = this.getReadableDatabase();
        String where="part_name=?";
        Cursor cursor = mydatabase.query(tb_master, new String[] {"id", "image","model_name"}, where, new String[]{part_name}, null, null, null);;
        //mydatabase.close();
        return cursor;
    }

    public void deleteAllImages(){
        SQLiteDatabase mydatabase=this.getWritableDatabase();
        mydatabase.execSQL("delete from "+tb_master);
        mydatabase.close();
    }
    public void deleteAllQuestions(){
        SQLiteDatabase mydatabase=this.getWritableDatabase();
        mydatabase.execSQL("delete from " +tb_question);
        mydatabase.close();
    }



    public void deletePrimaryData() {
        SQLiteDatabase mydatabase=this.getWritableDatabase();
        mydatabase.execSQL("delete from "+tb_master);
        mydatabase.execSQL("delete from " +tb_question);
        mydatabase.execSQL("delete from " +tb_part);
        mydatabase.execSQL("delete from " +tb_appName);
//        deleteAllImages();
//        deletAllParts();
//        deleteAllQuestions();
    }

    public void addAppNames(ArrayList<String> appNames) {
        SQLiteDatabase mydatabase=this.getWritableDatabase();
        mydatabase.execSQL("delete from " +tb_appName);
        ContentValues values=new ContentValues();
        for(String q:appNames) {
            values.put("app_name",q);
            mydatabase.insert(tb_appName,null,values);

        }
    }
    public ArrayList<String> getAppNames(){
        pnames=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String[] cols={"id","app_name"};
        Cursor cursor=db.query(tb_appName,cols,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                pnames.add(cursor.getString(1));

            } while (cursor.moveToNext());
        }
        return pnames;
    }
    //****************** Insert Employee******************
    public void addEmployee(int token,String name){
        SQLiteDatabase mydatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("token_no", token);
        contentValues.put("name", name);
        mydatabase.insert(tb_employee,null,contentValues);
        Log.e("insert emp","employee added");
    }

    public String getEmployee(String token){
        String empname="";
        SQLiteDatabase db=this.getReadableDatabase();
        String[] cols={"token_no","name"};
        String where="token_no=?";
        Cursor cursor=db.query(tb_employee,cols,where,new String[]{token},null,null,null);
        if(cursor.moveToFirst()){
              empname= cursor.getString(1);
        }
        return empname;
    }

    // TODO: 22-06-2021 change ip adress
    public void changeIp(String ip_adress) {
        SQLiteDatabase mydatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ip_address", ip_adress);
        mydatabase.update(tb_ip_adress,contentValues,null,null);
    }

    // TODO: 23-06-2021 get ip adress 
    public String getIpAdress() {
        String ip="";
        SQLiteDatabase mydatabase = this.getWritableDatabase();
        Cursor cursor=mydatabase.query(tb_ip_adress,new String[]{"ip_address"},null,null,null,null,null);
        if(cursor.moveToFirst())
        ip=cursor.getString(0);
        Log.e("ip: ",ip);
        return ip;
    }

    public static class Parts{
        String partname;
        String appname;

        public Parts(String partname, String appname) {
            this.partname = partname;
            this.appname = appname;
        }

        public String getPartname() {
            return partname;
        }

        public void setPartname(String partname) {
            this.partname = partname;
        }

        public String getAppname() {
            return appname;
        }

        public void setAppname(String appname) {
            this.appname = appname;
        }
    }
    public void batteryStatusTemp(HashMap<String, String> batteryInfo){
        SQLiteDatabase mydatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mainqr", batteryInfo.get("mainqr"));
        contentValues.put("batteryqr", batteryInfo.get("batteryqr"));
        contentValues.put("status", batteryInfo.get("status"));
        mydatabase.insert(tb_tmp_battery,null,contentValues);
    }
    public List<HashMap> getBatteryTemp(){

        ArrayList<HashMap> batteryinfo=new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        String[] cols={"mainqr","batteryqr","status"};
        Cursor cursor=db.query(tb_tmp_battery,cols,null,null,null,null,null);

       if(cursor.moveToFirst())
        do{
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("mainqr",cursor.getString(0));
            hashMap.put("batteryqr",cursor.getString(1));
            hashMap.put("status",cursor.getString(2));
            batteryinfo.add(hashMap);
            Log.e("battery info",hashMap.toString());
        }while(cursor.moveToNext());
        db.execSQL("delete from " +tb_tmp_battery);

        return batteryinfo;
    }

    // TODO: 12-07-2021 add remarks
    public void insertRemarks(List<Bundle> remarkList){
        //id Integer,question_id Integer,remark varchar
        SQLiteDatabase mydatabase = this.getWritableDatabase();
        for(Bundle b:remarkList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("question_id", b.getInt("question_id"));
            contentValues.put("remark", b.getString("remark"));
            mydatabase.insert(tb_remark, null, contentValues);
        }
    }
    public Cursor getRemarks(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursorRemark=db.query(tb_remark,new String[]{"question_id,remark"},null,null,null,null,null);
        return cursorRemark;
    }
    public ArrayList<String> getRemarksByQID(int question_id){
        ArrayList<String> remarks=new ArrayList<>();
        SQLiteDatabase mydatabase = this.getWritableDatabase();
        String where="question_id=?";
        Cursor cursor=mydatabase.query(tb_remark,new String[]{"remark"},where,new String[]{question_id+""},null,null,null);
        if(cursor.moveToFirst()){
            while (cursor.moveToNext()){
                remarks.add(cursor.getString(0));
            }
        }
        return remarks;
    }
}
