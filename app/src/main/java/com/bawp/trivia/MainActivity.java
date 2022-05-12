package com.bawp.trivia;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bawp.trivia.data.Repository;
import com.bawp.trivia.databinding.ActivityMainBinding;
import com.bawp.trivia.model.Question;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;
    private int point = 0, max = 0;
    List<Question> questionList;
    private static final String topPoints_id = "topPoints";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);



        questionList = new Repository().getQuestions(questionArrayList -> {
            binding.questionTextview.setText((CharSequence) questionArrayList.get(currentQuestionIndex).getAnswer());
            updateCounter(questionArrayList);
        });
        binding.buttonNext.setOnClickListener(v -> {
            currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
            updateQuestion();
        });
        binding.buttonTrue.setOnClickListener(v -> {
            checkAnswer(true);
            updateQuestion();


        });
        binding.buttonFalse.setOnClickListener(v -> {
            checkAnswer(false);
            updateQuestion();

        });
        binding.points.setText(("points: " + point));
        binding.highPoint.setText(("High_points: " + getPoints()));
    }


    private void checkAnswer(boolean b) {
        boolean answer = questionList.get(currentQuestionIndex).getAnswerTrue();
        int sankMessageId = 0;
        if (b == answer) {
            sankMessageId = R.string.correct_answer;
            fadeAnimation();
            if (point <= currentQuestionIndex) point++;
            binding.points.setText(("points: " + point));
            max = Math.max(max, point);
            if (max > Integer.parseInt(getPoints())) savePoints(max);
            binding.highPoint.setText(("High_points: " + getPoints()));
        } else {
            sankMessageId = R.string.incorrect;
            shakeAnimation();
        }
        Snackbar.make(binding.cardView, sankMessageId, Snackbar.LENGTH_SHORT).show();

    }

    private void savePoints(int point) {
        SharedPreferences sharedPreferences = getSharedPreferences(topPoints_id, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("points", String.valueOf(point));
        editor.apply();
    }

    private String getPoints() {
        SharedPreferences getShareData = getSharedPreferences(topPoints_id, MODE_PRIVATE);
        return getShareData.getString("points", "0");
    }

    private void updateCounter(ArrayList<Question> questionArrayList) {
        binding.textViewOutOf.setText(String.format(getString(R.string.text_formatted), currentQuestionIndex + 1, questionArrayList.size()));
    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        binding.questionTextview.setText(question);
        updateCounter((ArrayList<Question>) questionList);
    }

    private void fadeAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        binding.cardView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextview.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextview.setTextColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_animation);
        binding.cardView.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextview.setTextColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextview.setTextColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}