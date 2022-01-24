package com.nafim.qawmiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;
import java.util.Locale;

public class Read extends AppCompatActivity {

    String text;

    TextToSpeech t1;
    //TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        text= getIntent().getStringExtra("txt");


        t1=new TextToSpeech(getApplicationContext(),new TextToSpeech.OnInitListener()
        {

            @Override
            public void onInit(int status) {
                t1.setLanguage(Locale.forLanguageTag("ar"));
            }
        }
        );

       // txt=findViewById(R.id.tv);
        //txt.setText(text);

        init(text);


    }


    private void init(String definition) {
        //definition = "كن لا بد أن أوضح لك أن كل هذه الأفكار المغلوطة حول استنكار  النشوة وتمجيد الألم نشأت بالفعل، وسأعرض لك التفاصيل لتكتشف حقيقة وأساس تلك السعادة البشرية، فلا أحد يرفض أو يكره أو يتجنب الشعور بالسعادة، ولكن بفضل هؤلاء الأشخاص الذين لا يدركون بأن السعادة لا بد أن نستشعرها بصورة أكثر عقلانية ومنطقية فيعرضهم هذا لمواجهة الظروف الأليمة، وأكرر بأنه لا يوجد من يرغب في الحب ونيل المنال ويتلذذ بالآلام، الألم هو ال".trim();
        TextView definitionView = (TextView) findViewById(R.id.tv);

        //String definition=definitionView.getText().toString();

        definitionView.setMovementMethod(LinkMovementMethod.getInstance());
        definitionView.setText(definition, TextView.BufferType.SPANNABLE);
        Spannable spans = (Spannable) definitionView.getText();
        BreakIterator iterator = BreakIterator.getWordInstance(Locale.US);
        iterator.setText(definition);
        int start = iterator.first();
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator
                .next()) {
            String possibleWord = definition.substring(start, end);
            if (Character.isLetterOrDigit(possibleWord.charAt(0))) {
                ClickableSpan clickSpan = getClickableSpan(possibleWord);
                spans.setSpan(clickSpan, start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }


    private ClickableSpan getClickableSpan(final String word) {
        return new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {

                Toast.makeText(widget.getContext(), mWord, Toast.LENGTH_SHORT)
                        .show();

                t1.speak(mWord, TextToSpeech.QUEUE_FLUSH, null);

                //takeSpeak(mWord);
            }

            final String mWord;
            {
                mWord = word;
            }

           /* @Override
            public void onClick(View widget) {

            }*/

            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
            }
        };
    }

    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    public void takeSpeak(String toSpeak)
    {
        //String toSpeak = editText.getText().toString();
        // Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

    }





}