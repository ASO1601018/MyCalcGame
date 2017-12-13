package com.example.masahiro.mycalcgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private int result = 0;
    private int before_operator = -1;
    private int value = 0;
    private boolean wait = false;

    private int[] Numbers = { R.id.zero, R.id.one, R.id.two,
            R.id.three, R.id.four, R.id.five, R.id.six,
            R.id.seven, R.id.eight, R.id.nine };
    private int[] Operators = {R.id.plus, R.id.minus, R.id.multiple, R.id.division};

    private void calculate(){
        switch(before_operator){
            case R.id.plus:
                result = value + result;
                break;

            case R.id.minus:
                result = value - result;
                break;

            case R.id.multiple:
                result = value * result;
                break;

            case R.id.division:
                result = value / result;
                break;
        }
        value = 0;
        before_operator = -1;
        wait = true;
    }


    private void makeFormula(){
        Random r = new Random();
        int first = r.nextInt(100);
        int second = r.nextInt(100);
        int f = r.nextInt(4);
        String operator;

        switch(f){
            case 0:
                operator = "+";
                break;
            case 1:
                operator = "-";
                break;
            case 2:
                operator = "×";
                break;
            case 3:
                operator = "÷";
                break;
        }
        result = first + operator + second + "=?";
        show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeFormula();

        final Intent intent = new Intent(this,GameActivity.class);

        for(int i=0;i<10;i++){
            Button button = (Button)findViewById(Numbers[i]);
            button.setOnClickListener(this);
        }

        for(int i=0;i<4;i++){
            Button button = (Button)findViewById(Operators[i]);
            button.setOnClickListener(this);
        }

        Button clear = (Button)findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                result = 0;
                before_operator = -1;
                value = 0;
                show();
            }
        });

        Button equal = (Button)findViewById(R.id.equal);
        equal.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                calculate();
                show();
            }
        });

        Button game = (Button)findViewById(R.id.game_button);
        game.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(intent);
            }
        });
    }

    private void show(){
        TextView textview = (TextView)findViewById(R.id.answer);
        textview.setText(Integer.toString(result));
    }

    @Override
    public void onClick(View v) {
        for(int i=0;i<10;i++){
            if(v.getId() == Numbers[i]){
                if(wait){
                    wait = false;
                    result = 0;
                }
                result = result*10 + i;
                show();
                return ;
            }
        }

        if(before_operator != -1){
            calculate();
            show();
        }
        before_operator = v.getId();
        value = result;
        wait = true;
    }

}
