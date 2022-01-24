package com.nafim.colorbizz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.dhaval2404.imagepicker.ImagePicker;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.common.FileUtil;
import org.tensorflow.lite.support.common.TensorOperator;
import org.tensorflow.lite.support.common.TensorProcessor;
import org.tensorflow.lite.support.common.ops.NormalizeOp;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp;
import org.tensorflow.lite.support.label.TensorLabel;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Ammo_fresh extends AppCompatActivity {

    ImageView img;
    Button btn;
    TextView tv;
    int imageSizeY,imageSizeX;


    Bitmap bitmap;
    float PROBABILITY_MEAN = 0.0f;
    float PROBABILITY_STD = 255.0f;
    float IMAGE_STD = 1.0f;
    float IMAGE_MEAN = 0.0f;
    List<String> labels;


    Interpreter tflite;
    TensorImage inputImageBuffer;
    TensorBuffer outputProbabilityBuffer;
    TensorProcessor probabilityProcessor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ammo_fresh);


        img=findViewById(R.id.img);
        btn=findViewById(R.id.btn);
        tv=findViewById(R.id.tv);

       /* Bitmap bm= Bitmap.createBitmap("imagebacground");

        img.setImageBitmap(bm);*/


        try
        {
            tflite=new Interpreter(loadmodelfile(this));

        }
        catch (Exception e)
        {

        }


    }


    public TensorImage loadImage(Bitmap bitmap)
    {
        inputImageBuffer.load(bitmap); ////////////problem
        int cropSize=Math.min(bitmap.getWidth(),bitmap.getHeight());

        ImageProcessor imageProcessor =
                new ImageProcessor.Builder()
                        .add(new ResizeWithCropOrPadOp(cropSize, cropSize))
                        .add(new ResizeOp(imageSizeX, imageSizeY, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
                        .add(getPreprocessNormalizeOp())
                        .build();
        return imageProcessor.process(inputImageBuffer);

    }



    public MappedByteBuffer loadmodelfile(Activity activity) throws IOException {
        FileChannel fileChannel = null;
        long startOffset = 0;
        long declearedLength=0;
        try {
            AssetFileDescriptor fileDescriptor=activity.getAssets().openFd("Am_fr_6_model.tflite");

            FileInputStream inputStream=new FileInputStream(fileDescriptor.getFileDescriptor());

            fileChannel=inputStream.getChannel();

            startOffset=fileDescriptor.getStartOffset();
            declearedLength=fileDescriptor.getDeclaredLength();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declearedLength);

    }

    public TensorOperator getPostprocessNormalizeOp()
    {
        TensorOperator tensorOperator=new NormalizeOp(PROBABILITY_MEAN, PROBABILITY_STD);
        return tensorOperator;                                                                 //new NormalizeOp(PROBABILITY_MEAN, PROBABILITY_STD);
    }

    private TensorOperator getPreprocessNormalizeOp() {
        return new NormalizeOp(IMAGE_MEAN, IMAGE_STD);
    }



    public void showresult()
    {
        try
        {
            labels= FileUtil.loadLabels(getApplicationContext(),"Am_fr_6_labels.txt");

        }
        catch(Exception e)
        {

            e.printStackTrace();
        }

        Map<String, Float> labeledProbability =
                new TensorLabel(labels, probabilityProcessor.process(outputProbabilityBuffer))
                        .getMapWithFloatValue();

        float maxValueInMap =(Collections.max(labeledProbability.values()));

        for (Map.Entry<String, Float> entry : labeledProbability.entrySet()) {
            if (entry.getValue()==maxValueInMap) {
                //tv.setText(entry.getKey());
                tv.setText(entry.getKey());
            }
        }

    }

    public void classify(View v)
    {

        int imageTensorIndex = 0;
        int[] imageShape=tflite.getInputTensor(imageTensorIndex).shape();

        imageSizeY=imageShape[1];
        imageSizeX=imageShape[2];

        DataType imageDataType=tflite.getInputTensor(imageTensorIndex).dataType();

        int probabilityTensorIndex= 0;
        int[] probabilityShape=tflite.getOutputTensor(probabilityTensorIndex).shape();

        DataType prbabilityDataType=tflite.getOutputTensor(probabilityTensorIndex).dataType();

        inputImageBuffer=new TensorImage(imageDataType);
        outputProbabilityBuffer=TensorBuffer.createFixedSize(probabilityShape,prbabilityDataType);

        probabilityProcessor=new TensorProcessor.Builder().add(getPostprocessNormalizeOp()).build();

        inputImageBuffer= loadImage(bitmap); ///////////// problem


        tflite.run(inputImageBuffer.getBuffer(),outputProbabilityBuffer.getBuffer().rewind());
        showresult();


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        img.setImageBitmap(null);

        Uri uri=data.getData();
        img.setImageURI(uri);
        btn.setVisibility(View.VISIBLE);

        try {




            bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(), uri);


            btn.setVisibility(View.VISIBLE);
            tv.setVisibility(View.VISIBLE);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void select_image(View v)
    {
        ImagePicker.with(this)
                .crop()
                .compress(720)
                .maxResultSize(1080,1080)
                .start();

    }

}