package com.scau.fixqrcode;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private String strFilePath="";
    private Button openBtn;
    private Button shotBtn;
    private Button preBtn;
    private Button reBtn;
    private ImageView qrView;
    private TextView resultTv;
    private Bitmap qrBitmap; //原图
    private Mat iMat;
    private Button workBtn;
    private Button findBtn,recBtn;
    private Point[] abcPoint=new Point[4];
    private Bitmap topBitmap; //当前图
    private Button oneBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		qrView=(ImageView)findViewById(R.id.imageView1);
		openBtn=(Button)findViewById(R.id.openBtn);
		shotBtn=(Button)findViewById(R.id.shotBtn);
		preBtn=(Button)findViewById(R.id.preBtn);
		reBtn=(Button)findViewById(R.id.reBtn);
		findBtn=(Button)findViewById(R.id.findBtn);
		workBtn=(Button)findViewById(R.id.workBtn);
		oneBtn=(Button)findViewById(R.id.oneBtn);
		resultTv=(TextView)findViewById(R.id.resultTv);
	    //qrView.setAdjustViewBounds(true);
		//qrView.setLayoutParams(new LayoutParams(432,576));
		qrBitmap=BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath()+"/1tp.jpg");
		qrView.setImageBitmap(qrBitmap);
		topBitmap=qrBitmap.copy(Bitmap.Config.ARGB_8888, true);
		recBtn=(Button)findViewById(R.id.recBtn);
		openBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			   Intent intent=new Intent(MainActivity.this,OpenImageActivity.class);
			   startActivityForResult(intent, 1);
			}
		});
		
		shotBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,ScanActivity.class);
				startActivityForResult(intent,2);
			}
		});
		reBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(qrBitmap!=null)
				qrView.setImageBitmap(qrBitmap);
			}
		});
		
		recBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LuminanceSource source;
				
					source = new RGBLuminanceSource(topBitmap);
					System.out.println("out123"+source.getHeight());
					BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
						Result result;
						Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>(); 
						//解码设置编码方式为：utf-8
						hints.put(DecodeHintType.CHARACTER_SET, "utf-8");  
						try {
							result = new MultiFormatReader().decode(bitmap, hints);
							String resultStr = result.getText();
							
							resultTv.setText("识别结果:"+resultStr);
						} catch (NotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
			}
		});
		
		preBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    //Bitmap rBitmap=qrView.getDrawingCache();
			    topBitmap=qrBitmap.copy(Bitmap.Config.RGB_565, true);
			    iMat=new Mat(new Size(topBitmap.getWidth(),topBitmap.getHeight()), CvType.CV_8UC1);
			    System.out.println("topbitmap:"+topBitmap.getWidth()+","+topBitmap.getHeight());
				Utils.bitmapToMat(topBitmap,iMat);
				iMat.convertTo(iMat,  CvType.CV_8UC1);
				Imgproc.cvtColor(iMat, iMat, Imgproc.COLOR_RGB2GRAY);
				//System.out.println("i0:"+iMat.height());
			    Core.bitwise_not(iMat, iMat);
			    //System.out.println("i1:"+iMat.height());
				Mat element=Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(66,66));
				Mat e=Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(4,4));
				//Mat ee=Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1,1));
				Imgproc.morphologyEx(iMat, iMat, Imgproc.MORPH_TOPHAT, element);
				//System.out.println("i2:"+iMat.height());
				//Bitmap topBitmap =Bitmap.createBitmap(iMat.width(),iMat.height(),Bitmap.Config.RGB_565);				
				Imgproc.threshold(iMat, iMat, 0, 255, Imgproc.THRESH_OTSU);
				//System.out.println("i3:"+iMat.height());
				Core.bitwise_not(iMat, iMat);
				Imgproc.morphologyEx(iMat, iMat, Imgproc.MORPH_OPEN, e);
				//Imgproc.morphologyEx(iMat, iMat, Imgproc.MORPH_CLOSE, ee);
				Utils.matToBitmap(iMat, topBitmap);
				qrView.setImageBitmap(topBitmap);
			}
		});
		
		findBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				long startMili=System.currentTimeMillis();
				FindPoint fp=new FindPoint();
				abcPoint=fp.findPoint(iMat);
				Core.circle(iMat, abcPoint[0], 5, new Scalar(100,100,100),-1);
			    Core.circle(iMat, abcPoint[1], 5, new Scalar(100,100,100),-1);
			    Core.circle(iMat, abcPoint[2], 5, new Scalar(100,100,100),-1);
			    
			    Utils.matToBitmap(iMat, topBitmap);
				qrView.setImageBitmap(topBitmap);
				long endMili=System.currentTimeMillis();
				System.out.println("寻址耗时为："+(endMili-startMili)+"毫秒");
			}
		});
		
		
		workBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    Point[] dst=new Point[4];
			    dst[0]=new Point(80,80);
			    dst[1]=new Point(390,80);
			    dst[2]=new Point(80,390);
			    dst[3]=new Point(390,390);
			    Mat t=new Mat();
			    t=Imgproc.getPerspectiveTransform(new MatOfPoint2f(abcPoint),new MatOfPoint2f(dst));
			    Size size = new Size(iMat.width(), iMat.height());
			    Mat rotate=new Mat(size,CvType.CV_8UC1);
			    Imgproc.warpPerspective(iMat, rotate, t, size, Imgproc.INTER_LINEAR);
			    //Bitmap topBitmap=Bitmap.createBitmap(iMat.width(),iMat.height(),Bitmap.Config.RGB_565);
			    Mat idst=new Mat(rotate.size(),rotate.type(),new Scalar(255));
				Mat mask = Mat.zeros(rotate.size(), CvType.CV_8UC1);
				Core.rectangle(mask, new Point(70,70), new Point(400,400),new Scalar(255),-1);
				rotate.copyTo(idst, mask);
			    Utils.matToBitmap(idst, topBitmap);
				qrView.setImageBitmap(topBitmap);
			}
		});
		
		oneBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				long startMili=System.currentTimeMillis();
				preBtn.performClick();
				findBtn.performClick();
				workBtn.performClick();
				recBtn.performClick();
				long endMili=System.currentTimeMillis();
				System.out.println("总耗时为："+(endMili-startMili)+"毫秒");
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (RESULT_OK==resultCode){
		switch (requestCode){
		case 1:		
			strFilePath=data.getExtras().getString("selectFile");
			if (strFilePath!=""){
				qrBitmap=BitmapFactory.decodeFile(strFilePath);
				qrView.setImageBitmap(qrBitmap);
				Log.v("strfilepath", strFilePath);
				System.out.println("qb:"+qrBitmap.getHeight()+","+qrBitmap.getWidth());
				System.out.println("qv:"+qrView.getHeight()+","+qrView.getWidth());
			}
			break;
		case 2:
			if (ScanActivity.msBitmap!=null){
				qrBitmap=ScanActivity.msBitmap;
				qrView.setImageBitmap(ScanActivity.msBitmap);
			}
			Log.v("shot","success");
			break;
		default:
		}
		}
		}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, getApplicationContext(), iLoaderCallback);

	}

	private BaseLoaderCallback iLoaderCallback = new BaseLoaderCallback(this) {

        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                   
                   
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };
    
}
