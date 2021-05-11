package com.amanbatra.cointoss;

/*
 ***************Developed by AMAN BATRA*************************************
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    public static final Random RANDOM = new Random();
    private ImageView coin;
    private Button toss;

    private static int[] loosing_count = new int[]{0};
    private static int current_winning_count = 0;
    private static int maxwinning_count = 10;
    private static String currentuseaccount = "REAL";
    private double Demo_account = 1000;
    private double Real_account = 1000;
    private double Winning_account = 0;
    private double Bitamt= 0;
    private ArrayList<Double> prevBit = new ArrayList<Double>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coin = (ImageView) findViewById(R.id.coin);
        toss = (Button) findViewById(R.id.toss);

        loosing_count = new int[]{RANDOM.nextInt((10 - 3)) + 3,RANDOM.nextInt((10 - 4)) + 3,RANDOM.nextInt((10 - 5)) + 3};

        toss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checkbal = CheckBalance();
                reset_ratio();
                if(checkbal){
                    flipCoin("Heads",currentuseaccount);
                }
                else{
                    function_Alert_Balance();
                }
            }
        });
    }

    private boolean CheckBalance(){
        boolean result = false;
        if(currentuseaccount.equals("DEMO")){
            if(Demo_account > 0 && Demo_account >= Bitamt){
                result = true;
            }
            else{
                result = false;
            }
        }
        else if(currentuseaccount.equals("REAL")){
            double main_account = Real_account + Winning_account;
            if(main_account > 0 && main_account >= Bitamt){
                result = true;
            }
            else{
                result = false;
            }
        }
        return result;
    }

    private void reset_ratio(){
        if(maxwinning_count == current_winning_count){
            current_winning_count = 0;
            prevBit = new ArrayList<Double>();
            loosing_count = new int[]{RANDOM.nextInt((10 - 3)) + 3,RANDOM.nextInt((10 - 4)) + 3,RANDOM.nextInt((10 - 5)) + 3};
        }
    }

    private void function_Alert_Balance(){
        Toast.makeText(MainActivity.this,currentuseaccount+" Balance Empty",Toast.LENGTH_SHORT).show();
    }

    private void filpcoingif(final String setstring,final String currentuseacc){
        boolean winningresult = false;
        if(setstring.equals("Heads")){
            if(currentuseacc.equals("DEMO")){
                winningresult =  Demo_toss_Heads();
            }
            else if(currentuseacc.equals("REAL")){
                winningresult =  Real_Toss_Heads();
            }
        }
        else{
            if(currentuseacc.equals("DEMO")){
                winningresult =  Demo_toss_Tails();
            }
            else if(currentuseacc.equals("REAL")){
                winningresult =  Real_Toss_Tails();
            }
        }
        if(winningresult){
            Winning();
        }
        else{
            Loosing();
        }
    }

    private void flipCoin(final String setstring,final String currentuseacc) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(1000);
        fadeOut.setFillAfter(true);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setInterpolator(new DecelerateInterpolator());
                fadeIn.setDuration(3000);
                fadeIn.setFillAfter(true);

                coin.setImageResource(R.drawable.coin);
                coin.startAnimation(fadeIn);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        filpcoingif(setstring,currentuseacc);
                    }
                }, 2000);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        coin.startAnimation(fadeOut);
    }

    private boolean Demo_toss_Heads(){
        boolean result = false;
        if(loosing_count[0] != current_winning_count && loosing_count[1] != current_winning_count && loosing_count[2] != current_winning_count){
            coin.setImageResource(R.drawable.heads);
            result = true;
        }
        else{
            coin.setImageResource(R.drawable.tails);
            result = false;
        }
        current_winning_count++;
        return result;
    }

    private boolean Demo_toss_Tails() {
        boolean result = false;
        if (loosing_count[0] != current_winning_count && loosing_count[1] != current_winning_count && loosing_count[2] != current_winning_count) {
            coin.setImageResource(R.drawable.tails);
            result = true;
        } else {
            coin.setImageResource(R.drawable.heads);
            result = false;
        }
        current_winning_count++;
        return result;
    }

    private boolean Real_Toss_Heads(){
        boolean result,winning_heads;

        winning_heads = checklossingcount_sum();

        if(loosing_count[0] == current_winning_count && winning_heads || loosing_count[1] == current_winning_count && winning_heads || loosing_count[2] == current_winning_count && winning_heads){
            remove_bit_amount();
            coin.setImageResource(R.drawable.heads);
            result = true;
        }
        else{
            prevBit.add(Bitamt);
            coin.setImageResource(R.drawable.tails);
            result = false;
        }

        current_winning_count++;
        return result;
    }

    private boolean Real_Toss_Tails(){
        boolean result,winning_tails;
        winning_tails = checklossingcount_sum();
        if(loosing_count[0] == current_winning_count && winning_tails || loosing_count[1] == current_winning_count && winning_tails || loosing_count[2] == current_winning_count && winning_tails){
            remove_bit_amount();
            coin.setImageResource(R.drawable.heads);
            result = true;
        }
        else{
            prevBit.add(Bitamt);
            coin.setImageResource(R.drawable.tails);
            result = false;
        }

        current_winning_count++;
        return result;
    }

    private boolean checklossingcount_sum(){
        boolean result;
        int sumlossing = 0;
        for(int i = 0; i<prevBit.size();i++){
            sumlossing +=  prevBit.get(i);
        }
        int sum_ratio = 0;

        if(sumlossing != 0){
            sum_ratio  = sumlossing/4;
            if(sum_ratio >= Bitamt){
                result = true;
            }
            else{
                result = false;
            }
        }
        else{
            result = false;
        }
        return result;
    }

    private void remove_bit_amount(){
        for(int i = 0; i < prevBit.size() ;i++){
            if(prevBit.get(i) == Bitamt){
                prevBit.remove(i);
                break;
            }
        }
    }

    private void Winning(){
        if(currentuseaccount.equals("DEMO")){
            Demo_account += Bitamt;
            Toast.makeText(MainActivity.this,"You Win "+ current_winning_count +" ___" + currentuseaccount +"Amount " + Demo_account,Toast.LENGTH_SHORT).show();
        }
        else if(currentuseaccount.equals("REAL")){
            Winning_account += Bitamt * 2;
            Real_account -= Bitamt;
            Toast.makeText(MainActivity.this,"You Win "+ current_winning_count +" ___" + currentuseaccount +"Amount " + Real_account + "___ Wiining Amount "+ Winning_account,Toast.LENGTH_SHORT).show();
        }
    }

    private void Loosing(){
        if(currentuseaccount.equals("DEMO")){
            Demo_account -= Bitamt;
            Toast.makeText(MainActivity.this,"You win ",Toast.LENGTH_SHORT).show();
        }
        else if(currentuseaccount.equals("REAL")){
            double decreseamt =  wiining_decrease_amount();
            Real_account -= decreseamt;
            Toast.makeText(MainActivity.this,"You win ",Toast.LENGTH_SHORT).show();
        }
    }

    private double wiining_decrease_amount(){
        double amount_decrease = 0;
        if(Winning_account > 0){
            if(Winning_account >= Bitamt){
                Winning_account = Winning_account - Bitamt;
            }
            else{
                amount_decrease = Bitamt - Winning_account;
                Winning_account = Winning_account + amount_decrease - Bitamt;
            }
        }
        else{
            amount_decrease = Bitamt;
        }
        return amount_decrease;
    }


}