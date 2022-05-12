package com.bawp.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bawp.trivia.controller.AppController;
import com.bawp.trivia.model.Question;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    ArrayList<Question> questionArrayList=new ArrayList<>();
    String url="https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
    public  List<Question> getQuestions(final AnswerListAsyncResponse callBack){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,url,null, response ->{
            for (int i = 0; i <response.length(); i++) {
                try {
                    questionArrayList.add(new Question(response.getJSONArray(i).getString(0),
                            response.getJSONArray(i).getBoolean(1)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (null!=callBack)callBack.processFinished(questionArrayList);
        } ,error -> {
            Log.d("devil","error");
        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        return questionArrayList;
    }
}
