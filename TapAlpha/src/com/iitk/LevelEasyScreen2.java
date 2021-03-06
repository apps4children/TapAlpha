package com.iitk;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.TreeSet;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
public class LevelEasyScreen2 extends Activity implements AdapterView.OnItemClickListener 
{
	StringBuilder sb = new StringBuilder();
	String wrongQuestion=null,wrong=null;
	
	int counter=0,truecounter=0,level=2,wrongCounter=0;
	TextView selection,dailogView,blinkText;
	String[] alphabet,sortedAlphabet;
	GridView gv ;
	final Context context = this;
	Dialog dialog;
	//sound
	SoundManager snd;
	int i,j,k,l,m,n,o,p,nana,verygood;
	@Override
	public void onCreate(Bundle icicle) 
	{
		super.onCreate(icicle);requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.game);
		alphabet=randomCharacter();
		selection = (TextView) findViewById(R.id.selection);
		gv= (GridView) findViewById(R.id.grid);
		gv.setAdapter(new ImageAdapter(this));
		gv.setOnItemClickListener(this);
		Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fly_in_from_top_corner);
        gv.setAnimation(anim);
        anim.start();
		
		
		snd = new SoundManager(getApplicationContext());
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        i=snd.load(R.raw.i);
        j=snd.load(R.raw.j);
        k=snd.load(R.raw.k);
        l=snd.load(R.raw.l);
        m=snd.load(R.raw.m);
        n=snd.load(R.raw.n);
        o=snd.load(R.raw.o);
        p=snd.load(R.raw.p);
        nana=snd.load(R.raw.nana);
        verygood=snd.load(R.raw.verygood);      
        
        Bundle extras = getIntent().getExtras();  
        String wrong= extras.getString("wrong"); 
        sb.append(wrong);
        
        Button back=(Button)findViewById(R.id.back);
        back.setOnClickListener(new Button.OnClickListener()
        {
        	public void onClick(View v) 
        	{
        			finish();
        }});
        
	}
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
	{
		selection.setText(alphabet[position]);	
		selection.setTextColor(Color.parseColor(LevelEasyScreen1.AllGradients().get(position)));
		if(!alphabet[position].equals(sortedAlphabet[counter]))
		{
		++wrongCounter;
		snd.play(nana);
		//v.setBackgroundColor(Color.RED);
		//Drawable background = this.getResources().getDrawable(R.drawable.wrong_border); 
		//v.setBackgroundDrawable(background);
		alert();
		   //
		   if(wrongQuestion==null){
			  sb.append("\n");
			  wrongQuestion=sortedAlphabet[counter];
			  sb.append(sortedAlphabet[counter]+"-"+alphabet[position]);
		   }
		   else if(wrongQuestion.equalsIgnoreCase(sortedAlphabet[counter])){
			   sb.append(","+alphabet[position]);
		   }
		   else if(!wrongQuestion.equalsIgnoreCase(sortedAlphabet[counter])){
			   sb.append("\n");
			   wrongQuestion=sortedAlphabet[counter];
			   sb.append(sortedAlphabet[counter]+"-"+alphabet[position]);  
		   }
		  //
		}
		else if(alphabet[position].equals(sortedAlphabet[counter]))
		{
			if(alphabet[position].equalsIgnoreCase((String) blinkText.getText()))               //
			{                  //Clear animation
			v.clearAnimation();//
			}                  //
			//v.setBackgroundColor(0x30FF0000);
			play(position);
			v.setBackgroundColor(Color.TRANSPARENT);
			v.setOnClickListener(null);
			++truecounter;
			if(truecounter==8)
				++level;
			if(level==3)
			{
				newScreen();
			}counter++;
		}
	}
	public String[] randomCharacter()
	{
		String [] arr;
		SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String alphaType=sharedPreferences.getString("alpha","capital");
        if(alphaType.equals("capital"))
        {
	    arr=new String[]{"I","J","K","L","M","N","O","P"};
        }
        else 
        arr=new String[]{"i","j","k","l","m","n","o","p"};
        Random random = new Random();
        LinkedHashSet<String> str=new LinkedHashSet<String>();
        while(str.size()<8)
        {
        	int select = random.nextInt(arr.length); 
            str.add(arr[select]);
	    }
        String[] alphabet = str.toArray(new String[0]);
        TreeSet<String> sorted=new TreeSet<String>();
        sorted.addAll(str);
        sortedAlphabet=sorted.toArray(new String[0]);
		return alphabet;
	}
	public void alert()
	{
		dialog = new Dialog(LevelEasyScreen2.this);
		dialog.setContentView(R.layout.dailog);
		dailogView= (TextView) dialog.findViewById(R.id.dialogView);
		dialog.setTitle("Click Below");
		dialog.setCancelable(true);
		dailogView.setText(sortedAlphabet[counter]);
		
		Button ok =(Button) dialog.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() 
        {
        public void onClick(View v) 
        {
        	dialog.dismiss();
        	selection.setText("");
        }
        });
		dialog.show();
	}
	public void play(int id)
	{
		if(alphabet[id].equalsIgnoreCase("I"))
			snd.play(i);
		else if(alphabet[id].equalsIgnoreCase("J"))
			snd.play(j);
		else if(alphabet[id].equalsIgnoreCase("K"))
			snd.play(k);
		else if(alphabet[id].equalsIgnoreCase("L"))
			snd.play(l);
		else if(alphabet[id].equalsIgnoreCase("M"))
			snd.play(m);
		else if(alphabet[id].equalsIgnoreCase("N"))
			snd.play(n);
		else if(alphabet[id].equalsIgnoreCase("O"))
			snd.play(o);
		else if(alphabet[id].equalsIgnoreCase("P"))
			snd.play(p);
	}
	public void newScreen()
	{		
		int DELAY = 1000;
	    Handler handler = new Handler();
	    handler.postDelayed(new Runnable() 
	    {            
	        public void run() 
	        {
	        	snd.play(verygood);
	            Intent intent = new Intent(LevelEasyScreen2.this, LevelEasyScreen3.class);
	            intent.putExtra("wrong", sb.toString());
	            startActivity(intent); 
	            overridePendingTransition(R.anim.bottom_in,R.anim.top_out);
	            finish();
	        }
	    }, DELAY);
	}
	
	
	//gradient class and methods below
		public class ImageAdapter extends BaseAdapter
		{
			Context MyContext;
			public ImageAdapter(Context _MyContext){
				MyContext = _MyContext;
			}
			public int getCount(){
				return 8;
			}
			public View getView(int position, View convertView, ViewGroup parent){
				View MyView = convertView;
				if ( convertView == null )
				{
					LayoutInflater li = getLayoutInflater();
					MyView = li.inflate(R.layout.cell, null);
				}
				TextView tv = (TextView)MyView.findViewById(R.id.gridcell);
				tv.setText(alphabet[position]);
	            tv.setTextColor(Color.parseColor(LevelEasyScreen1.AllGradients().get(position)));
	            
	            String str=(String) tv.getText();
	            if(str.equalsIgnoreCase(sortedAlphabet[0]))
	            {
	            blink(tv);
	            }
				return MyView;
			}
			public Object getItem(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			public long getItemId(int arg0) {
				// TODO Auto-generated method stub
				return 0;
			}
		}
		public static ArrayList<String> AllGradients(){
	        ArrayList<String> list = new ArrayList<String>();
	        list.add("#006400");
	        list.add("#DA70D6");
	        list.add("#9400D3");
	        list.add("#FE0388");
	        
	        list.add("#0099CC");
	        list.add("#D9007E");
	        list.add("#2F4F4F");
	        list.add("#731134");
	               
	        return list;
	    }
		public void blink(TextView tv)
		{
			blinkText=tv;
			Animation animBlink= new AlphaAnimation(0.0f, 1.0f);
	        animBlink.setDuration(500); //You can manage the time of the blink with this parameter
	        animBlink.setStartOffset(100);
	        animBlink.setRepeatMode(Animation.REVERSE);
	        animBlink.setRepeatCount(Animation.INFINITE);
			blinkText.startAnimation(animBlink);
		}
	}