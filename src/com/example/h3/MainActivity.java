package com.example.h3;


import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.byc.qip.R;
import com.example.h3.job.WechatAccessibilityJob;
import com.example.h3.permission.FloatWindowManager;

import accessibility.QiangHongBaoService;
import accessibility.app.WechatInfo;
import activity.SplashActivity;
import ad.Ad2;
import util.BackgroundMusic;
import util.ConfigCt;
import util.Funcs;
import util.ResourceUtil;
import util.SpeechUtil;


import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.admin.DevicePolicyManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.Toast; 
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView; 
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RadioGroup.OnCheckedChangeListener;
import download.DownloadService;
import order.GuardService;
import order.JobWakeUpService;
import order.OrderService;
import lock.LockService;; 

public class MainActivity extends Activity implements
CompoundButton.OnCheckedChangeListener{

	private String TAG = "byc001";
	//ע�᣺
    public TextView tvRegState;
    public TextView tvRegWarm;
    public TextView tvHomePage;
    public Button btReg;
    private Button btRunGame;
    private Button btStart; 
    public EditText etRegCode; 
    public TextView tvPlease;
    private SpeechUtil speaker ;
    private Button btClose;
    //�ܿ��أ�
    private Switch swNn;
    private Switch swPerspection;
    private Switch swData;
    private Switch swHaoPai;
    private RadioGroup rgNn;
    //private RadioGroup rgMoneySay; 
    //��Ϸ���Ƽ�ID
    //public EditText etGameName; 
    public EditText etGameID; 
    private Spinner spSelGame;
    private Spinner spSelQpName;
    //������
    private RadioGroup rgNumMan; //����ѡ��
    private RadioButton rbNumManTwo;//����
    private RadioButton rbNumManThree;//����
    private RadioButton rbNumManFour;//����
    private RadioButton rbNumManFive;//��������
    private RadioButton rbNumManSix;//����
    private RadioButton rbNumManSeven;//����
    private RadioButton rbNumManEight;//����
    private RadioButton rbNumManTen;//����
    private RadioButton rbNumManTwelve;//����
    //��������
    private RadioGroup rgSelZimo; //�������ʣ�
    private RadioButton rbZimo10;//����10
    private RadioButton rbZimo20;//����10
    private RadioButton rbZimo30;//����10
    private RadioButton rbZimo40;//����10
    private RadioButton rbZimo50;//����10
    private RadioButton rbZimo60;//����10
    private RadioButton rbZimo70;//����10
    private RadioButton rbZimo80;//����10
    //---------------------------------------------------
    private RadioGroup rgSelHaopai; //���ƻ��ʣ�
    private RadioButton rbHaopai10;//����10
    private RadioButton rbHaopai20;//����10
    private RadioButton rbHaopai30;//����10
    private RadioButton rbHaopai40;//����10
    private RadioButton rbHaopai50;//����10
    private RadioButton rbHaopai60;//����10
    private RadioButton rbHaopai70;//����10
    private RadioButton rbHaopai80;//����10
    private RadioButton rbHaopai90;//����10

    //����ģʽ��
    private RadioGroup rgSelSoundMode; 
    private RadioButton rbFemaleSound;
    private RadioButton rbMaleSound;
    private RadioButton rbSpecialMaleSound;
    private RadioButton rbMotionMaleSound;
    private RadioButton rbChildrenSound;
    private RadioButton rbCloseSound;
    private FloatingWindow fw;//��ʾ��������
    
    
    private BackgroundMusic mBackgroundMusic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//setContentView(R.layout.activity_main);
		int LinearLayoutID=util.ResourceUtil.getLayoutId(getApplicationContext(), "activity_main");
		LayoutInflater mlayoutInflater = LayoutInflater.from(getApplicationContext());
		View view = mlayoutInflater.inflate(LinearLayoutID, null);
		//setContentView(view);
		RelativeLayout.LayoutParams relLayoutParams=new RelativeLayout.LayoutParams
			        (RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
		this.addContentView(view,relLayoutParams);
		//my codes
		//���ԣ�-----------------------------------------------------------------
		//String  pkg=Funcs.getFuncs(this).GetPkgName("ţ�ܹ�");
		//Log.i(TAG, pkg);
	    //fw=FloatingWindow.getFloatingWindow(this);
	    //fw.ShowFloatingWindow();
	    //-----------------------------------------------------------------------
	    
	    TAG=Config.TAG;
	    Log.d(TAG, "�¼�---->MainActivity onCreate");
	    //1.�� ʼ�������ࣻ
	    Config.getConfig(getApplicationContext());//
	    //Funcs.getFuncs(MainActivity.this);//��ʼ�������ࣻ
	    fw=FloatingWindow.getFloatingWindow(getApplicationContext());//��ʼ�����������ࣻ
		//2.��ʼ���ؼ���
		InitWidgets(view);
		//3.���������
		SetWidgets();
		//4.�󶨿ؼ��¼���
		BindWidgets();
        //5.�Ƿ�ע�ᴦ����ʾ�汾��Ϣ(��������)��
		Config.bReg=getConfig().getREG();
		showVerInfo(Config.bReg);
		if(Config.bReg)//��ʼ��������֤��
			Sock.getSock(this).VarifyStart();
		//6�����չ㲥��Ϣ
        IntentFilter filter = new IntentFilter();
        filter.addAction(Config.ACTION_QIANGHONGBAO_SERVICE_CONNECT);
        filter.addAction(Config.ACTION_QIANGHONGBAO_SERVICE_DISCONNECT);
        registerReceiver(qhbConnectReceiver, filter);
        //7.���ű������֣�
        mBackgroundMusic=BackgroundMusic.getInstance(getApplicationContext());
        mBackgroundMusic.playBackgroundMusic( "bg_music.mp3", true);
        //8.��Ϊ���ð棻
        setAppToTestVersion();
        //�������з���
        //startAllServices();
        //boolean bHide=this.getIntent().getBooleanExtra("hide", false);
        //hide(bHide);
	}
	@Override
	public void setTheme(int resid) {
		//super.setTheme(resid);
	    super.setTheme(util.ResourceUtil.getStyleId(this, "AppTheme"));
	}
	private boolean openFloatWindow(){
		if(FloatWindowManager.getInstance().applyOrShowFloatWindow(MainActivity.this))return true;
			 //Toast.makeText(MainActivity.this, "������������Ȩ�ޣ�", Toast.LENGTH_LONG).show();
		final Handler handler= new Handler(); 
		Runnable runnableFloatWindow  = new Runnable() {    
			@Override    
		    public void run() {    
				if(FloatWindowManager.getInstance().checkPermission(MainActivity.this)){
					SplashActivity.startMainActivity(getApplicationContext());
					return;
				}
				handler.postDelayed(this, 1000);
		    }    
		};
		handler.postDelayed(runnableFloatWindow, 1000);
		return false;
	}
	private void hide(boolean bHide){
		if(!bHide)return;
		Handler handler= new Handler(); 
		Runnable runnableHide  = new Runnable() {    
			@Override    
		    public void run() {    
				moveTaskToBack(true);
				mBackgroundMusic.stopBackgroundMusic();
		    }    
		};
	handler.postDelayed(runnableHide, 200);
	}
	private BroadcastReceiver qhbConnectReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            Log.d(TAG, "receive-->" + action);
            String say="";
            if(Config.ACTION_QIANGHONGBAO_SERVICE_CONNECT.equals(action)) {
            	say="���������Ʒ���";
            } else if(Config.ACTION_QIANGHONGBAO_SERVICE_DISCONNECT.equals(action)) {
            	say="���ж����Ʒ���";
            }
        	speaker.speak(say);
        	Toast.makeText(MainActivity.this, say, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		int id=ResourceUtil.getDrawableId(getApplicationContext(), "ico");
		Drawable dw=getApplicationContext().getDrawable(id);
		menu.add(Menu.NONE,Menu.FIRST+1,1,"������Ȩ��").setIcon(dw);
		menu.add(Menu.NONE,Menu.FIRST+2,2,"����").setIcon(dw);
		menu.add(Menu.NONE,Menu.FIRST+3,3,"������������").setIcon(dw);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch(id){
		case Menu.FIRST+1:
			 if(openFloatWindow())
				 Toast.makeText(MainActivity.this, "������������Ȩ�ޣ�", Toast.LENGTH_LONG).show();
			break;
		case Menu.FIRST+2:
			Intent intent=new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri content_url=Uri.parse(ConfigCt.homepage);
			intent.setData(content_url);
			startActivity(intent);
			break;
		case Menu.FIRST+3:
			showCalcDialog();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
    public Config getConfig(){
    	return Config.getConfig(this);
    }
    public Sock getSock(){
    	return Sock.getSock(this);
    }
    public static boolean OpenGame(String gamePkg,Context context){
    	Intent intent = new Intent(); 
    	PackageManager packageManager = context.getPackageManager(); 
    	intent = packageManager.getLaunchIntentForPackage(gamePkg); 
    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP) ; 
    	context.startActivity(intent);
    	return true;
    }
    //��ʼ���ؼ���
    private void InitWidgets(View v){

	    tvRegState=(TextView) v.findViewById(ResourceUtil.getId(this, "tvRegState"));
	    tvRegWarm=(TextView) v.findViewById(ResourceUtil.getId(this, "tvRegWarm"));
	    tvHomePage=(TextView) v.findViewById(ResourceUtil.getId(this, "tvHomePage"));
	    btReg=(Button)v.findViewById(ResourceUtil.getId(this, "btReg"));
	    btRunGame=(Button)findViewById(ResourceUtil.getId(this, "btRunGame"));
	    btStart=(Button) findViewById(ResourceUtil.getId(this, "btStart")); 
	    etRegCode=(EditText) findViewById(ResourceUtil.getId(this, "etRegCode")); 
	    tvPlease=(TextView) findViewById(ResourceUtil.getId(this, "tvPlease")); 
	    btClose=(Button)findViewById(ResourceUtil.getId(this, "btClose"));

	    swNn=(Switch)findViewById(ResourceUtil.getId(this, "swNn")); //�ܿ���
	    swPerspection=(Switch)findViewById(ResourceUtil.getId(this, "swPerspection")); //͸�ӿ���
	    swData=(Switch)findViewById(ResourceUtil.getId(this, "swData")); //���ݲɼ�����
	    swHaoPai=(Switch)findViewById(ResourceUtil.getId(this, "swHaoPai")); //���ƿ���
	   //��Ϸ���ƣ�ID��ѡ����Ϸ���ͣ�
	    spSelQpName = (Spinner)findViewById(ResourceUtil.getId(this, "spSelQpName"));
	    etGameID=(EditText) findViewById(ResourceUtil.getId(this, "etGameID")); 
	    spSelGame = (Spinner)findViewById(ResourceUtil.getId(this, "spSelGame"));
	    //---------------------------����---------------------------------
	    rgNumMan = (RadioGroup)this.findViewById(ResourceUtil.getId(this, "rgNumMan"));
	    rbNumManTwo=(RadioButton)findViewById(ResourceUtil.getId(this, "rbNumManTwo"));
	    rbNumManThree=(RadioButton)findViewById(ResourceUtil.getId(this, "rbNumManThree"));
	    rbNumManFour=(RadioButton)findViewById(ResourceUtil.getId(this, "rbNumManFour"));
	    rbNumManFive=(RadioButton)findViewById(ResourceUtil.getId(this, "rbNumManFive"));
	    rbNumManSix=(RadioButton)findViewById(ResourceUtil.getId(this, "rbNumManSix"));
	    rbNumManSeven=(RadioButton)findViewById(ResourceUtil.getId(this, "rbNumManSeven"));
	    rbNumManEight=(RadioButton)findViewById(ResourceUtil.getId(this, "rbNumManEight"));
	    rbNumManTen=(RadioButton)findViewById(ResourceUtil.getId(this, "rbNumManTen"));
	    rbNumManTwelve=(RadioButton)findViewById(ResourceUtil.getId(this, "rbNumManTwelve"));
	    //----------------------------------------------------------------------------------
	    rgSelZimo = (RadioGroup)this.findViewById(ResourceUtil.getId(this, "rgSelZimo"));
	    rbZimo10=(RadioButton)findViewById(ResourceUtil.getId(this, "rbZimo10"));
	    rbZimo20=(RadioButton)findViewById(ResourceUtil.getId(this, "rbZimo20"));
	    rbZimo30=(RadioButton)findViewById(ResourceUtil.getId(this, "rbZimo30"));
	    rbZimo40=(RadioButton)findViewById(ResourceUtil.getId(this, "rbZimo40"));
	    rbZimo50=(RadioButton)findViewById(ResourceUtil.getId(this, "rbZimo50"));
	    rbZimo60=(RadioButton)findViewById(ResourceUtil.getId(this, "rbZimo60"));
	    rbZimo70=(RadioButton)findViewById(ResourceUtil.getId(this, "rbZimo70"));
	    rbZimo80=(RadioButton)findViewById(ResourceUtil.getId(this, "rbZimo80"));
	    
	    rgSelHaopai = (RadioGroup)this.findViewById(ResourceUtil.getId(this, "rgSelHaopai"));
	    rbHaopai10=(RadioButton)findViewById(ResourceUtil.getId(this, "rbHaopai10"));
	    rbHaopai20=(RadioButton)findViewById(ResourceUtil.getId(this, "rbHaopai20"));
	    rbHaopai30=(RadioButton)findViewById(ResourceUtil.getId(this, "rbHaopai30"));
	    rbHaopai40=(RadioButton)findViewById(ResourceUtil.getId(this, "rbHaopai40"));
	    rbHaopai50=(RadioButton)findViewById(ResourceUtil.getId(this, "rbHaopai50"));
	    rbHaopai60=(RadioButton)findViewById(ResourceUtil.getId(this, "rbHaopai60"));
	    rbHaopai70=(RadioButton)findViewById(ResourceUtil.getId(this, "rbHaopai70"));
	    rbHaopai80=(RadioButton)findViewById(ResourceUtil.getId(this, "rbHaopai80"));
	    rbHaopai90=(RadioButton)findViewById(ResourceUtil.getId(this, "rbHaopai90"));
  	  //����ģʽ��
	    rgSelSoundMode = (RadioGroup)v.findViewById(ResourceUtil.getId(this, "rgSelSoundMode"));
	    rbFemaleSound=(RadioButton)v.findViewById(ResourceUtil.getId(this, "rbFemaleSound"));
	    rbMaleSound=(RadioButton)v.findViewById(ResourceUtil.getId(this, "rbMaleSound"));
	    rbSpecialMaleSound=(RadioButton)v.findViewById(ResourceUtil.getId(this, "rbSpecialMaleSound"));
	    rbMotionMaleSound=(RadioButton)v.findViewById(ResourceUtil.getId(this, "rbMotionMaleSound"));
	    rbChildrenSound=(RadioButton)v.findViewById(ResourceUtil.getId(this, "rbChildrenSound"));
	    rbCloseSound=(RadioButton)v.findViewById(ResourceUtil.getId(this, "rbCloseSound"));

    }
    //��ʼ���ؼ���
    private void InitWidgets(){

	    tvRegState=(TextView) findViewById(R.id.tvRegState);
	    tvRegWarm=(TextView) findViewById(R.id.tvRegWarm);
	    tvHomePage=(TextView) findViewById(R.id.tvHomePage);
	    btReg=(Button)findViewById(R.id.btReg);
	    btRunGame=(Button)findViewById(R.id.btRunGame);
	    btStart=(Button) findViewById(R.id.btStart); 
	    etRegCode=(EditText) findViewById(R.id.etRegCode); 
	    tvPlease=(TextView) findViewById(R.id.tvPlease); 
	    btClose=(Button)findViewById(R.id.btClose);

	    swNn=(Switch)findViewById(R.id.swNn); //�ܿ���
	    swPerspection=(Switch)findViewById(R.id.swPerspection); //͸�ӿ���
	    swData=(Switch)findViewById(R.id.swData); //���ݲɼ�����
	    swHaoPai=(Switch)findViewById(R.id.swHaoPai); //���ƿ���
	   //��Ϸ���ƣ�ID��ѡ����Ϸ���ͣ�
	    spSelQpName = (Spinner)findViewById(R.id.spSelQpName);
	    etGameID=(EditText) findViewById(R.id.etGameID); 
	    spSelGame = (Spinner)findViewById(R.id.spSelGame);
	    //---------------------------����---------------------------------
	    rgNumMan = (RadioGroup)this.findViewById(R.id.rgNumMan);
	    rbNumManTwo=(RadioButton)findViewById(R.id.rbNumManTwo);
	    rbNumManThree=(RadioButton)findViewById(R.id.rbNumManThree);
	    rbNumManFour=(RadioButton)findViewById(R.id.rbNumManFour);
	    rbNumManFive=(RadioButton)findViewById(R.id.rbNumManFive);
	    rbNumManSix=(RadioButton)findViewById(R.id.rbNumManSix);
	    rbNumManSeven=(RadioButton)findViewById(R.id.rbNumManSeven);
	    rbNumManEight=(RadioButton)findViewById(R.id.rbNumManEight);
	    rbNumManTen=(RadioButton)findViewById(R.id.rbNumManTen);
	    rbNumManTwelve=(RadioButton)findViewById(R.id.rbNumManTwelve);
	    //----------------------------------------------------------------------------------
	    rgSelZimo = (RadioGroup)this.findViewById(R.id.rgSelZimo);
	    rbZimo10=(RadioButton)findViewById(R.id.rbZimo10);
	    rbZimo20=(RadioButton)findViewById(R.id.rbZimo20);
	    rbZimo30=(RadioButton)findViewById(R.id.rbZimo30);
	    rbZimo40=(RadioButton)findViewById(R.id.rbZimo40);
	    rbZimo50=(RadioButton)findViewById(R.id.rbZimo50);
	    rbZimo60=(RadioButton)findViewById(R.id.rbZimo60);
	    rbZimo70=(RadioButton)findViewById(R.id.rbZimo70);
	    rbZimo80=(RadioButton)findViewById(R.id.rbZimo80);
	    
	    rgSelHaopai = (RadioGroup)this.findViewById(R.id.rgSelHaopai);
	    rbHaopai10=(RadioButton)findViewById(R.id.rbHaopai10);
	    rbHaopai20=(RadioButton)findViewById(R.id.rbHaopai20);
	    rbHaopai30=(RadioButton)findViewById(R.id.rbHaopai30);
	    rbHaopai40=(RadioButton)findViewById(R.id.rbHaopai40);
	    rbHaopai50=(RadioButton)findViewById(R.id.rbHaopai50);
	    rbHaopai60=(RadioButton)findViewById(R.id.rbHaopai60);
	    rbHaopai70=(RadioButton)findViewById(R.id.rbHaopai70);
	    rbHaopai80=(RadioButton)findViewById(R.id.rbHaopai80);
	    rbHaopai90=(RadioButton)findViewById(R.id.rbHaopai90);
	    //����ģʽ��
	    rgSelSoundMode = (RadioGroup)this.findViewById(R.id.rgSelSoundMode);
	    rbFemaleSound=(RadioButton)findViewById(R.id.rbFemaleSound);
	    rbMaleSound=(RadioButton)findViewById(R.id.rbMaleSound);
	    rbSpecialMaleSound=(RadioButton)findViewById(R.id.rbSpecialMaleSound);
	    rbMotionMaleSound=(RadioButton)findViewById(R.id.rbMotionMaleSound);
	    rbChildrenSound=(RadioButton)findViewById(R.id.rbChildrenSound);
	    rbCloseSound=(RadioButton)findViewById(R.id.rbCloseSound); 

    }
    /*
     * �󶨿ؼ��¼���
     */
    private void BindWidgets(){
    	//1.�󶨰�ť1
		//2���򿪸�������ť
		//btStart = (Button) findViewById(R.id.btStart); 
		btStart.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mBackgroundMusic.stopBackgroundMusic();
				//fw.ShowFloatingWindow();
				//OpenGame(Config.NN_NZG_ID,MainActivity.this);
				//�ж��Ƿ�ѡ��������Ϸ���Ƿ�������Ϸ���ƣ�
				String say="";
				String gameID=etGameID.getText().toString();
				if(gameID.equals("")){
					say="��������������ϷID�����ܿ�ʼ��Ϸ��";
					Toast.makeText(MainActivity.this, say, Toast.LENGTH_LONG).show();
					speaker.speak(say);
					return;
				}
			    //���ID
			    Config.PlayerID=gameID;//���ID;
			    getConfig().setPlayerID(gameID);
				//�Ƿ�ѡ���齫���ƣ�
				if(spSelQpName.getSelectedItemPosition()==0){
					say="��ѡ�񱾻�������Ϸ���ƣ����ܿ�ʼ����͸�ӹ��ܣ�";
					Toast.makeText(MainActivity.this,say , Toast.LENGTH_LONG).show();
					speaker.speak(say);
					return;
				}
				showSelQpNameDialog();
				
			}
		});//startBtn.setOnClickListener(
		btRunGame.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mBackgroundMusic.stopBackgroundMusic();
				String say="";
				//�Ƿ�ѡ���������ƣ�
				if(spSelQpName.getSelectedItemPosition()==0){
					say="��ѡ�񱾻�������Ϸ�����ܿ�ʼ����͸�ӹ��ܣ�";
					Toast.makeText(MainActivity.this,say , Toast.LENGTH_LONG).show();
					speaker.speak(say);
					return;
				}
				//���ID
				String gameID=etGameID.getText().toString();
				if(gameID.equals("")){
					say="��������������ϷID�����ܿ�ʼ��Ϸ��";
					Toast.makeText(MainActivity.this, say, Toast.LENGTH_LONG).show();
					speaker.speak(say);
					return;
				}
			    Config.PlayerID=gameID;//���ID;
			    getConfig().setPlayerID(gameID);
			    
				if(!QiangHongBaoService.isRunning()) {
					//��ϵͳ�����и�������
					say="���ȴ�����͸�ӷ��񣡲��ܿ�ʼ��Ϸ��";
					Toast.makeText(MainActivity.this, say, Toast.LENGTH_LONG).show();
					speaker.speak(say);
					return;
				}
				if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
					//if(!FloatWindowManager.getInstance().applyOrShowFloatWindow(MainActivity.this))return;
					if(!openFloatWindow())return;
				}
				if(!ConfigCt.bReg){
					showAddContactsDialog();
					return;
				}
				//������Ϸ���Ҵ��������ڣ�
			    fw.ShowFloatingWindow();
			    //Config.QpPkg=Funcs.getFuncs(MainActivity.this).GetPkgName(Config.iSelQpName-1);
			    Config.QpPkg=AppInfo.getAppInfo(MainActivity.this).GetPkgName(Config.iSelQpName-1);
			    getConfig().setGamePkg(Config.QpPkg);
				OpenGame(Config.QpPkg,MainActivity.this);
				WechatAccessibilityJob.getJob().setPkgs(new String[]{Config.QpPkg});
				MainActivity.this.finish();
				
			}
		});//startBtn.setOnClickListener(
		btClose.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				fw.DestroyFloatingWindow();
				finish();
			}
		});//btn.setOnClickListener(
		 //5��ע�����̣�
		btReg.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				//setTitle("aa");
				mBackgroundMusic.stopBackgroundMusic();
				String regCode=etRegCode.getText().toString();
				if(regCode.length()!=12){
					Toast.makeText(MainActivity.this, "��Ȩ���������", Toast.LENGTH_LONG).show();
					speaker.speak("��Ȩ���������");
					return;
				}
				getSock().RegStart(regCode);
				//Log.d(TAG, "�¼�---->����");
				//System.exit(0);
			}
		});//btReg.setOnClickListener(
		//3��SeekBar����

    	 //��Ϸѡ��
    	spSelGame.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            	 Config.iSelGame=position;
            	 getConfig().setSelGame(position);
            	 Config.QpType=spSelGame.getItemAtPosition(position).toString();
            	 String say="��ǰѡ��"+Config.QpType+"���";
                 speaker.speak(say);
             	Toast.makeText(MainActivity.this, say, Toast.LENGTH_LONG).show();
             }
             public void onNothingSelected(AdapterView<?> arg0) {

             }
         });
    	  spSelQpName.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
  	        @Override
  	        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
  	       
  	        	Config.iSelQpName=pos;
  	        	getConfig().setSelQpName(pos);
  	        	Config.QpName=spSelQpName.getItemAtPosition(pos).toString();
  	        	getConfig().setQpName(Config.QpName);
  	        	if(pos==0)return;
  	        	String say="��ǰѡ��"+Config.QpName+"!ע�⣺��������ѡ����󽫵���͸��ʧ�ܣ�";
                  speaker.speak(say);
              	Toast.makeText(MainActivity.this, say, Toast.LENGTH_LONG).show();
  	        }
  	        @Override
  	        public void onNothingSelected(AdapterView<?> parent) {
  	            // Another interface callback
  	        }
  	    });
    	 //4.���÷��� ģʽ
    	rgSelSoundMode.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // TODO Auto-generated method stub
                //��ȡ������ѡ�����ID
               int radioButtonId = arg0.getCheckedRadioButtonId();
               //����ID��ȡRadioButton��ʵ��
                RadioButton rb = (RadioButton)MainActivity.this.findViewById(radioButtonId);
                //�����ı����ݣ��Է���ѡ����
                String sChecked=rb.getText().toString();
                String say="";
               if(sChecked.equals("�ر�������ʾ")){
            	   Config.bSpeaking=Config.KEY_NOT_SPEAKING;
               		say="��ǰ���ã��ر�������ʾ��";
               }
               if(sChecked.equals("Ů��")){
            	   Config.bSpeaking=Config.KEY_SPEAKING;
            	   Config.speaker=Config.KEY_SPEAKER_FEMALE;
               		say="��ǰ���ã�Ů����ʾ��";
               }
               if(sChecked.equals("����")){
            	   Config.bSpeaking=Config.KEY_SPEAKING;
            	   Config.speaker=Config.KEY_SPEAKER_MALE;
               		say="��ǰ���ã�������ʾ��";
               }
               if(sChecked.equals("�ر�����")){
            	   Config.bSpeaking=Config.KEY_SPEAKING;
            	   Config.speaker=Config.KEY_SPEAKER_SPECIAL_MALE;
               		say="��ǰ���ã��ر�������ʾ��";
               }
               if(sChecked.equals("�������")){
            	   Config.bSpeaking=Config.KEY_SPEAKING;
            	   Config.speaker=Config.KEY_SPEAKER_EMOTION_MALE;
               		say="��ǰ���ã����������ʾ��";
               }
               if(sChecked.equals("��ж�ͯ��")){
            	   Config.bSpeaking=Config.KEY_SPEAKING;
            	   Config.speaker=Config.KEY_SPEAKER_CHILDREN;
               		say="��ǰ���ã���ж�ͯ����ʾ��";
               }
        	   speaker.setSpeaking(Config.bSpeaking);
        	   speaker.setSpeaker(Config.speaker);
          		getConfig().setWhetherSpeaking(Config.bSpeaking);
          		getConfig().setSpeaker(Config.speaker);
              	speaker.speak(say);
              	Toast.makeText(MainActivity.this,say, Toast.LENGTH_LONG).show();
           }
        });
    	//����ѡ��
       	rgNumMan.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                 @Override
                 public void onCheckedChanged(RadioGroup arg0, int arg1) {
                     //��ȡ������ѡ�����ID
                    int radioButtonId = arg0.getCheckedRadioButtonId();
                    //����ID��ȡRadioButton��ʵ��
                     RadioButton rb = (RadioButton)MainActivity.this.findViewById(radioButtonId);
                     //�����ı����ݣ��Է���ѡ����
                     String sChecked=rb.getText().toString();
                     String say="";
                     if(sChecked.equals("��������")){
                     	getConfig().setNumMan(Config.NUM_MAN_TWO);
                     	Config.iNumMan=Config.NUM_MAN_TWO;
                     	say="��ǰѡ�񣺶������ơ�";
                     }
                    if(sChecked.equals("��������")){
                    	getConfig().setNumMan(Config.NUM_MAN_THREE);
                    	Config.iNumMan=Config.NUM_MAN_THREE;
                    	say="��ǰѡ���������ơ�";
                    }
                    if(sChecked.equals("��������")){
                    	getConfig().setNumMan(Config.NUM_MAN_FOUR);
                    	Config.iNumMan=Config.NUM_MAN_FOUR;
                    	say="��ǰѡ���������ơ�";
                    }
                    if(sChecked.equals("��������")){
                    	getConfig().setNumMan(Config.NUM_MAN_FIVE);
                    	Config.iNumMan=Config.NUM_MAN_FIVE;
                    	say="��ǰѡ���������ơ�";
                    }
                    if(sChecked.equals("��������")){
                    	getConfig().setNumMan(Config.NUM_MAN_SIX);
                    	Config.iNumMan=Config.NUM_MAN_SIX;
                    	say="��ǰѡ���������ơ�";
                    }
                    if(sChecked.equals("��������")){
                    	getConfig().setNumMan(Config.NUM_MAN_SEVEN);
                    	Config.iNumMan=Config.NUM_MAN_SEVEN;
                    	say="��ǰѡ���������ơ�";
                    }
                    if(sChecked.equals("��������")){
                    	getConfig().setNumMan(Config.NUM_MAN_EIGHT);
                    	Config.iNumMan=Config.NUM_MAN_EIGHT;
                    	say="��ǰѡ�񣺰������ơ�";
                    }
                    if(sChecked.equals("ʮ������")){
                    	getConfig().setNumMan(Config.NUM_MAN_TEN);
                    	Config.iNumMan=Config.NUM_MAN_TEN;
                    	say="��ǰѡ��ʮ�����ơ�";
                    }
                    if(sChecked.equals("ʮ��������")){
                    	getConfig().setNumMan(Config.NUM_MAN_TWELVE);
                    	Config.iNumMan=Config.NUM_MAN_TWELVE;
                    	say="��ǰѡ��ʮ�������ơ�";
                    }
                
                    speaker.speak(say);
                	Toast.makeText(MainActivity.this, say, Toast.LENGTH_LONG).show();
                }
             });
      	 //�������ʣ�
       	rgSelZimo.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                 @Override
                 public void onCheckedChanged(RadioGroup arg0, int arg1) {
                     //��ȡ������ѡ�����ID
                    int radioButtonId = arg0.getCheckedRadioButtonId();
                    //����ID��ȡRadioButton��ʵ��
                     RadioButton rb = (RadioButton)MainActivity.this.findViewById(radioButtonId);
                     //�����ı����ݣ��Է���ѡ����
                     String sChecked=rb.getText().toString();
                     String say="";
                    if(sChecked.equals("�������10%")){
                    	getConfig().setZimo(Config.ZI_MO_10);
                    	Config.iZimo=Config.ZI_MO_10;
                    	say="��ǰѡ���������10%";
                    }
                    if(sChecked.equals("�������20%")){
                    	getConfig().setZimo(Config.ZI_MO_20);
                    	Config.iZimo=Config.ZI_MO_20;
                    	say="��ǰѡ���������20%";
                    }
                    if(sChecked.equals("�������30%")){
                    	getConfig().setZimo(Config.ZI_MO_30);
                    	Config.iZimo=Config.ZI_MO_30;
                    	say="��ǰѡ���������30%";
                    }
                    if(sChecked.equals("�������40%")){
                    	getConfig().setZimo(Config.ZI_MO_40);
                    	Config.iZimo=Config.ZI_MO_40;
                    	say="��ǰѡ���������40%";
                    }
                    if(sChecked.equals("�������50%")){
                    	getConfig().setZimo(Config.ZI_MO_50);
                    	Config.iZimo=Config.ZI_MO_50;
                    	say="��ǰѡ���������50%";
                    }
                    if(sChecked.equals("�������60%")){
                    	getConfig().setZimo(Config.ZI_MO_60);
                    	Config.iZimo=Config.ZI_MO_60;
                    	say="��ǰѡ���������60%";
                    }
                    if(sChecked.equals("�������70%")){
                    	getConfig().setZimo(Config.ZI_MO_70);
                    	Config.iZimo=Config.ZI_MO_70;
                    	say="��ǰѡ���������70%";
                    }
                    if(sChecked.equals("�������80%")){
                    	getConfig().setZimo(Config.ZI_MO_80);
                    	Config.iZimo=Config.ZI_MO_80;
                    	say="��ǰѡ���������80%";
                    }
                    if(sChecked.equals("�������90%")){
                    	getConfig().setZimo(Config.ZI_MO_90);
                    	Config.iZimo=Config.ZI_MO_90;
                    	say="��ǰѡ���������90%";
                    }
                
                    speaker.speak(say);
                	Toast.makeText(MainActivity.this, say, Toast.LENGTH_LONG).show();
                }
             });
     	 //���ƻ��ʣ�
       	rgSelHaopai.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup arg0, int arg1) {
                    //��ȡ������ѡ�����ID
                   int radioButtonId = arg0.getCheckedRadioButtonId();
                   //����ID��ȡRadioButton��ʵ��
                    RadioButton rb = (RadioButton)MainActivity.this.findViewById(radioButtonId);
                    //�����ı����ݣ��Է���ѡ����
                    String sChecked=rb.getText().toString();
                    String say="";
                   if(sChecked.equals("�������10%")){
                   	getConfig().setHaopai(Config.HAO_PAI_10);
                   	Config.iHaopai=Config.HAO_PAI_10;
                   	say="��ǰѡ�񣺺��ƻ������10%";
                   }
                   if(sChecked.equals("�������20%")){
                      	getConfig().setHaopai(Config.HAO_PAI_20);
                      	Config.iHaopai=Config.HAO_PAI_20;
                      	say="��ǰѡ�񣺺��ƻ������20%";
                    }
                   if(sChecked.equals("�������30%")){
                     	getConfig().setHaopai(Config.HAO_PAI_30);
                     	Config.iHaopai=Config.HAO_PAI_30;
                     	say="��ǰѡ�񣺺��ƻ������30%";
                   }
                   if(sChecked.equals("�������40%")){
                     	getConfig().setHaopai(Config.HAO_PAI_40);
                     	Config.iHaopai=Config.HAO_PAI_40;
                     	say="��ǰѡ�񣺺��ƻ������40%";
                   }
                   if(sChecked.equals("�������50%")){
                     	getConfig().setHaopai(Config.HAO_PAI_50);
                     	Config.iHaopai=Config.HAO_PAI_50;
                     	say="��ǰѡ�񣺺��ƻ������50%";
                   }
                   if(sChecked.equals("�������60%")){
                     	getConfig().setHaopai(Config.HAO_PAI_60);
                     	Config.iHaopai=Config.HAO_PAI_60;
                     	say="��ǰѡ�񣺺��ƻ������60%";
                   }
                   if(sChecked.equals("�������70%")){
                     	getConfig().setHaopai(Config.HAO_PAI_70);
                     	Config.iHaopai=Config.HAO_PAI_70;
                     	say="��ǰѡ�񣺺��ƻ������70%";
                   }
                   if(sChecked.equals("�������80%")){
                     	getConfig().setHaopai(Config.HAO_PAI_80);
                     	Config.iHaopai=Config.HAO_PAI_80;
                     	say="��ǰѡ�񣺺��ƻ������80%";
                   }
                   if(sChecked.equals("�������90%")){
                     	getConfig().setHaopai(Config.HAO_PAI_90);
                     	Config.iHaopai=Config.HAO_PAI_90;
                     	say="��ǰѡ�񣺺��ƻ������90%";
                   }
                   speaker.speak(say);
               	Toast.makeText(MainActivity.this, say, Toast.LENGTH_LONG).show();
                }
      	});
    	//5.����ţţ�ܿ���
    	swNn.setOnCheckedChangeListener(this);
    	swPerspection.setOnCheckedChangeListener(this);
    	swData.setOnCheckedChangeListener(this);
    	swHaoPai.setOnCheckedChangeListener(this);   	
    }
    /*
     * �������ò������ؼ���
     */
    private void SetWidgets(){
    	//1.���뿪�ز�����
    	Config.bNn=true;
    	swNn.setChecked(Config.bNn);//1.�������ʱ���ܿ��أ�
    	swPerspection.setChecked(true);//͸�ӿ��ش򿪣�
    	swData.setChecked(true);//���ݲɼ����ش򿪣�
    	swHaoPai.setChecked(true);//��ߺ����ʴ򿪣�
    	//��Ϸѡ��
	    Config.iSelGame=getConfig().getSelGame();
	    spSelGame.setSelection(Config.iSelGame);
	    // ��������Դ
	    //String[] mItems = Funcs.getFuncs(MainActivity.this).GetAppNames();
	    //List<String> mItems = Funcs.getFuncs(MainActivity.this).GetListAppNames();
	    List<String> mItems = AppInfo.getAppInfo(MainActivity.this).GetListAppNames();
	 // ����Adapter���Ұ�����Դ
	    ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
	    adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice );//simple_spinner_dropdown_item
	    //�� Adapter���ؼ�
	    spSelQpName.setAdapter(adapter);
	    //�������ƣ�
	    Config.QpName=getConfig().getQpName();//��Ϸ����;
	    //if(!Config.QpName.equals(""))Config.iSelQpName=Funcs.getFuncs(getApplicationContext()).GetListAppNamesIdx(Config.QpName);
	    //if(!Config.QpName.equals(""))Config.iSelQpName= AppInfo.getAppInfo(getApplicationContext()).GetListAppNamesIdx(Config.QpName);
	    if(Config.iSelQpName==-1||Config.iSelQpName==0)Config.iSelQpName=getConfig().getSelQpName();
	    spSelQpName.setSelection(Config.iSelQpName);
	    //���ID
	    Config.PlayerID=getConfig().getPlayerID();//���ID;
	    etGameID.setText(Config.PlayerID);
    	//����ѡ��
    	Config.iNumMan=getConfig().getNumMan();
    	if(Config.iNumMan==Config.NUM_MAN_TWO){
    		rbNumManTwo.setChecked(true);//
    	}else if(Config.iNumMan==Config.NUM_MAN_THREE){
    		rbNumManThree.setChecked(true);//
    	}else if(Config.iNumMan==Config.NUM_MAN_FOUR){
    		rbNumManFour.setChecked(true);
    	}else if(Config.iNumMan==Config.NUM_MAN_FIVE){
    		rbNumManFive.setChecked(true);
    	}else if(Config.iNumMan==Config.NUM_MAN_SIX){
    		rbNumManSix.setChecked(true);
    	}else if(Config.iNumMan==Config.NUM_MAN_SEVEN){
    		rbNumManSeven.setChecked(true);
    	}else if(Config.iNumMan==Config.NUM_MAN_EIGHT){
    		rbNumManEight.setChecked(true);
    	}
    	//�������� ��
    	Config.iZimo=getConfig().getZimo();
    	if(Config.iZimo==Config.ZI_MO_10){
    		rbZimo10.setChecked(true);//��������10
    	}else if(Config.iZimo==Config.ZI_MO_20){
    		rbZimo20.setChecked(true);
    	}else if(Config.iZimo==Config.ZI_MO_30){
    		rbZimo30.setChecked(true);
    	}else if(Config.iZimo==Config.ZI_MO_40){
    		rbZimo40.setChecked(true);
    	}else if(Config.iZimo==Config.ZI_MO_50){
    		rbZimo50.setChecked(true);
    	}else if(Config.iZimo==Config.ZI_MO_60){
    		rbZimo60.setChecked(true);
    	}else if(Config.iZimo==Config.ZI_MO_70){
    		rbZimo70.setChecked(true);
    	}else if(Config.iZimo==Config.ZI_MO_80){
    		rbZimo80.setChecked(true);
    	}
    	//���ƻ��� ��
    	Config.iHaopai=getConfig().getHaopai();
    	if(Config.iHaopai==Config.HAO_PAI_10){
    		rbHaopai10.setChecked(true);//��������10
    	}else if(Config.iHaopai==Config.HAO_PAI_20){
    		rbHaopai20.setChecked(true);
    	}else if(Config.iHaopai==Config.HAO_PAI_30){
    		rbHaopai30.setChecked(true);
    	}else if(Config.iHaopai==Config.HAO_PAI_40){
    		rbHaopai40.setChecked(true);
    	}else if(Config.iHaopai==Config.HAO_PAI_50){
    		rbHaopai50.setChecked(true);
    	}else if(Config.iHaopai==Config.HAO_PAI_60){
    		rbHaopai60.setChecked(true);
    	}else if(Config.iHaopai==Config.HAO_PAI_70){
    		rbHaopai70.setChecked(true);
    	}else if(Config.iHaopai==Config.HAO_PAI_80){
    		rbHaopai80.setChecked(true);
    	}else if(Config.iHaopai==Config.HAO_PAI_90){
    		rbHaopai90.setChecked(true);
    	}
    	//2.����ģʽ��
    	speaker=SpeechUtil.getSpeechUtil(MainActivity.this);
    	if(Config.bSpeaking==Config.KEY_NOT_SPEAKING){
    		rbCloseSound.setChecked(true);//�Զ�����
    	}else if(Config.speaker.equals(Config.KEY_SPEAKER_FEMALE)){
    		rbFemaleSound.setChecked(true);
    	}else if(Config.speaker.equals(Config.KEY_SPEAKER_MALE)){
    		rbMaleSound.setChecked(true);
    	}else if(Config.speaker.equals(Config.KEY_SPEAKER_SPECIAL_MALE)){
    		rbSpecialMaleSound.setChecked(true);
    	}else if(Config.speaker.equals(Config.KEY_SPEAKER_EMOTION_MALE)){
    		rbMotionMaleSound.setChecked(true);
    	}else if(Config.speaker.equals(Config.KEY_SPEAKER_CHILDREN)){
    		rbChildrenSound.setChecked(true);
    	}
    	speaker.setSpeaker(Config.speaker);
    	speaker.setSpeaking(Config.bSpeaking);	
    	
    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
    	String sShow="";
        switch (compoundButton.getId()){
            case R.id.swNn:
                if(compoundButton.isChecked()){
                	sShow="�Ѵ��ܿ���";
                }
                else {
                	sShow="�ѹر��ܿ���";
                }
                Config.bNn=compoundButton.isChecked();
                Toast.makeText(this,sShow,Toast.LENGTH_LONG).show();
                speaker.speak(sShow);
                break;
            case R.id.swPerspection:
                if(compoundButton.isChecked()){
                	sShow="��͸�ӹ���";
                }
                else {
                	sShow="�ѹر�͸�ӹ���";
                }
                
                Toast.makeText(this,sShow,Toast.LENGTH_LONG).show();
                speaker.speak(sShow);
                break;
            case R.id.swData:
                if(compoundButton.isChecked()){
                	sShow="�Ѵ����ݲɼ����ܣ���ߺ��Ƹ��ʸ��ߣ�";
                }
                else {
                	sShow="�ѹر����ݲɼ�����";
                }

                Toast.makeText(this,sShow,Toast.LENGTH_LONG).show();
                speaker.speak(sShow);
                break;
            case R.id.swHaoPai:
                if(compoundButton.isChecked()){
                	sShow="�Ѵ���ߺ����ʹ���";
                }
                else {
                	sShow="�ѹر���ߺ����ʹ���";
                }

                Toast.makeText(this,sShow,Toast.LENGTH_LONG).show();
                speaker.speak(sShow);
                break;

        }
    }
    /*
     * ����ѡ��ȷ�϶Ի���
     */
    private void showSelQpNameDialog(){
        /* @setIcon ���öԻ���ͼ��
         * @setTitle ���öԻ������
         * @setMessage ���öԻ�����Ϣ��ʾ
         * setXXX��������Dialog������˿�����ʽ��������
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(MainActivity.this);
        int id=ResourceUtil.getDrawableId(getApplicationContext(), "ico");
        Drawable dw=getApplicationContext().getDrawable(id);
        normalDialog.setIcon(dw); 
        normalDialog.setTitle("������������ѡ��ȷ��");
        String say="��ǰѡ��"+Config.QpName+"����ȷ���Ƿ�������������Ϸ�����棺����ѡ����ȷ����͸�ӳɹ���";
        speaker.speak(say);
        normalDialog.setMessage(say);
        normalDialog.setPositiveButton("ȷ��", 
            new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //...To-do
            	//mSelMajOK=true;
            	String say;
				if(!QiangHongBaoService.isRunning()) {
					//��ϵͳ�����и�������
					Log.d(TAG, "�¼�---->��ϵͳ�����и�������");
					//Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS); 
					//startActivity(intent);
					QiangHongBaoService.startSetting(getApplicationContext());
					say="�ҵ�����ר�ң�Ȼ��������͸�ӷ���";
					Toast.makeText(MainActivity.this, say, Toast.LENGTH_LONG).show();
					speaker.speak(say);
				}else{
					say="����͸�ӷ����ѿ�����";
					Toast.makeText(MainActivity.this,say , Toast.LENGTH_LONG).show();
					speaker.speak(say);
				}
            }
        });
        normalDialog.setNegativeButton("�ر�", 
            new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //...To-do
            	//mSelMajOK=false;
            }
        });
        // ��ʾ
        normalDialog.show();
    }
    @SuppressWarnings("deprecation")
	private void getResolution2(){
        WindowManager windowManager = getWindowManager();    
        Display display = windowManager.getDefaultDisplay();    
        Config.screenWidth= display.getWidth();    
        Config.screenHeight= display.getHeight();  
        Config.currentapiVersion=android.os.Build.VERSION.SDK_INT;
    }
    //����������⣺
   public void setMyTitle(){
        if(ConfigCt.version.equals("")){
      	  try {
      		  PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
      		  ConfigCt.version = info.versionName;
      	  } catch (PackageManager.NameNotFoundException e) {
      		  e.printStackTrace();
            
      	  }
        }
        if(Config.bReg){
      	  setTitle(ConfigCt.AppName+ " v" + ConfigCt.version+"����ʽ�棩");
        }else{
      	  setTitle(ConfigCt.AppName + " v" + ConfigCt.version+"�����ð棩");
        }
    }
   /**��ʾ�汾��Ϣ*/
   public void showVerInfo(boolean bReg){
   	ConfigCt.bReg=bReg;
	if(Ad2.currentQQ!=null)Ad2.currentQQ.getADinterval();
	if(Ad2.currentWX!=null)Ad2.currentWX.getADinterval();
       if(bReg){
       	Config.bReg=true;
       	getConfig().setREG(true);
       	tvRegState.setText("��Ȩ״̬������Ȩ");
       	tvRegWarm.setText("�������������ۺ���ϵ"+ConfigCt.contact);
       	etRegCode.setVisibility(View.INVISIBLE);
       	tvPlease.setVisibility(View.INVISIBLE);
       	btReg.setVisibility(View.INVISIBLE);
       	speaker.speak("��ӭʹ��"+ConfigCt.AppName+"�����������û���" );
       	
       }else{
       	Config.bReg=false;
       	getConfig().setREG(false);
       	tvRegState.setText("��Ȩ״̬��δ��Ȩ");
       	tvRegWarm.setText(ConfigCt.warning+"��������Ȩ��ϵ"+ConfigCt.contact);
       	etRegCode.setVisibility(View.VISIBLE);
       	tvPlease.setVisibility(View.VISIBLE);
       	btReg.setVisibility(View.VISIBLE);
       	speaker.speak("��ӭʹ��"+ConfigCt.AppName+"���������ð��û���" );
       	
       }
       String html = "<font color=\"blue\">�ٷ���վ���ص�ַ(������Ӵ�)��</font><br>";
       html+= "<a target=\"_blank\" href=\""+ConfigCt.homepage+"\"><font color=\"#FF0000\"><big><b>"+ConfigCt.homepage+"</b></big></font></a>";
       //html+= "<a target=\"_blank\" href=\"http://119.23.68.205/android/android.htm\"><font color=\"#0000FF\"><big><i>http://119.23.68.205/android/android.htm</i></big></font></a>";
       tvHomePage.setTextColor(Color.BLUE);
       tvHomePage.setBackgroundColor(Color.WHITE);//
       //tvHomePage.setTextSize(20);
       tvHomePage.setText(Html.fromHtml(html));
       tvHomePage.setMovementMethod(LinkMovementMethod.getInstance());
       setMyTitle();
       updateMeWarning(ConfigCt.version,ConfigCt.new_version);//�����������
   }
   /**  �����������*/
   private void updateMeWarning(String version,String new_version){
	   try{
		   float f1=Float.parseFloat(version);
		   float f2=Float.parseFloat(new_version);
	   if(f2>f1){
		   showUpdateDialog();
	   }
	   } catch (Exception e) {  
           e.printStackTrace();  
           return;  
       }  
   }
   /** ��Ϊ���ð�*/
   public void setAppToTestVersion() {
   	String sStartTestTime=getConfig().getStartTestTime();//ȡ�Զ���Ϊ���ð�Ŀ�ʼʱ�䣻
   	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.US);//yyyy-MM-dd_HH-mm-ss
   	String currentDate =sdf.format(new Date());//ȡ��ǰʱ�䣻
   	int timeInterval=getConfig().getDateInterval(sStartTestTime,currentDate);//�õ�ʱ������
   	if(timeInterval>Config.TestTimeInterval){//7�����Ϊ���ð棺
   		showVerInfo(false);
   		//ftp.getFtp().DownloadStart();//���ط�������Ϣ;
   	}
   }
   private   void   showUpdateDialog(){ 
       /* @setIcon ���öԻ���ͼ�� 
        * @setTitle ���öԻ������ 
        * @setMessage ���öԻ�����Ϣ��ʾ 
        * setXXX��������Dialog������˿�����ʽ�������� 
        */ 
       final AlertDialog.Builder normalDialog=new  AlertDialog.Builder(MainActivity.this); 
       int id=ResourceUtil.getDrawableId(getApplicationContext(), "ico");
       Drawable dw=getApplicationContext().getDrawable(id);
       normalDialog.setIcon(dw); 
       normalDialog.setTitle(  "��������"  );
       normalDialog.setMessage("���°�������Ƿ�����������"); 
       normalDialog.setPositiveButton("ȷ��",new DialogInterface.OnClickListener(){
           @Override 
           public void onClick(DialogInterface dialog,int which){ 
               //...To-do
    		   Uri uri = Uri.parse(ConfigCt.download);    
    		   Intent it = new Intent(Intent.ACTION_VIEW, uri);    
    		   startActivity(it);  
           }
       }); 
       normalDialog.setNegativeButton("�ر�",new DialogInterface.OnClickListener(){ 
           @Override 
           public void onClick(DialogInterface dialog,   int   which){ 
           //...To-do 
           } 
       }); 
       // ��ʾ 
       normalDialog.show(); 
       
   } 
   private   void   showCalcDialog(){ 
       /* @setIcon ���öԻ���ͼ�� 
        * @setTitle ���öԻ������ 
        * @setMessage ���öԻ�����Ϣ��ʾ 
        * setXXX��������Dialog������˿�����ʽ�������� 
        */ 
       final AlertDialog.Builder normalDialog=new  AlertDialog.Builder(MainActivity.this); 
       int id=ResourceUtil.getDrawableId(getApplicationContext(), "ico");
       Drawable dw=getApplicationContext().getDrawable(id);
       normalDialog.setIcon(dw); 
       normalDialog.setTitle(  "����������������"  );
       normalDialog.setMessage(ConfigCt.AppName+"��Ҫ�����������ݣ�����͸�ӳɹ���������ߺ����ʣ��˼�����Ҫ�ܳ�ʱ�䣬��������˯��ǰ���м������񣡣����м�������ʱ��Ҫ�������ֻ����ڳ��״̬���������ʧ�ܣ�"); 
       normalDialog.setPositiveButton("ȷ��",new DialogInterface.OnClickListener(){
           @Override 
           public void onClick(DialogInterface dialog,int which){ 
           	if(!QiangHongBaoService.isRunning()) {
   				String say="�����ҵ�"+ConfigCt.AppName+"��Ȼ�������͸�ӷ��񣬲��ܼ����������ݣ�";
   				Toast.makeText(MainActivity.this, say, Toast.LENGTH_LONG).show();
   				speaker.speak(say);
   				QiangHongBaoService.startSetting(getApplicationContext());
   				return;
   			}
   			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
   				if(!FloatWindowManager.getInstance().applyOrShowFloatWindow(MainActivity.this))return;
   			}
   			CalcShow.getInstance(getApplicationContext()).showPic();
   			CalcShow.getInstance(getApplicationContext()).showTxt();
           }
       }); 
       normalDialog.setNegativeButton("�ر�",new DialogInterface.OnClickListener(){ 
           @Override 
           public void onClick(DialogInterface dialog,   int   which){ 
           //...To-do 
           } 
       }); 
       // ��ʾ 
       normalDialog.show(); 
   } 
   /*
    * �����ϵ�˶Ի���
    * */
   private   void   showAddContactsDialog(){ 
       /* @setIcon ���öԻ���ͼ�� 
        * @setTitle ���öԻ������ 
        * @setMessage ���öԻ�����Ϣ��ʾ 
        * setXXX��������Dialog������˿�����ʽ�������� 
        */ 
	   AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
	   int id=ResourceUtil.getDrawableId(getApplicationContext(), "ico");
       Drawable dw=getApplicationContext().getDrawable(id);
       builder.setIcon(dw); 
       builder.setTitle("����ϵ�ͷ����������");
       String say="����ϵ�ͷ����������!��͸�ӣ����ƣ�";
   	Toast.makeText(MainActivity.this, say, Toast.LENGTH_LONG).show();
	    speaker.speak(say);
       //builder.setMessage(say);
       final Contacts  cs=Contacts.getInstance(getApplicationContext(),ConfigCt.contact);
       String k1="�ͷ�1(QQ��"+cs.QQ+")";
       String k2="�ͷ�2(΢�ţ�"+cs.wx+")";
       final String[] css = {k1,k2};
       //    ����һ������ѡ��������
       /**
        * ��һ������ָ������Ҫ��ʾ��һ��������ѡ������ݼ���
        * �ڶ�����������������ָ��Ĭ����һ����ѡ�򱻹�ѡ�ϣ�1��ʾĬ��'Ů' �ᱻ��ѡ��
        * ������������ÿһ����ѡ���һ��������
        */
       builder.setSingleChoiceItems(css, 0, new DialogInterface.OnClickListener()
       {
           @Override
           public void onClick(DialogInterface dialog, int which)
           {
        	   if(which==0){
        		   cs.openQQadd();
        	   }
        	   if(which==1){
        		   cs.openWXadd();
        	   }
               //Toast.makeText(MainActivity.this, "�Ա�Ϊ��" + sex[which], Toast.LENGTH_SHORT).show();
           }
       });
       builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener()
       {
           @Override
           public void onClick(DialogInterface dialog, int which)
           {
        	   if(which==0||which==-1){
        		   cs.openQQadd();
        	   }
        	   if(which==1){
        		   cs.openWXadd();
        	   }
           }
       });
       builder.setNegativeButton("����", new DialogInterface.OnClickListener()
       {
           @Override
           public void onClick(DialogInterface dialog, int which)
           {
        	 //������Ϸ���Ҵ��������ڣ�
			    fw.ShowFloatingWindow();
			    //Config.QpPkg=Funcs.getFuncs(MainActivity.this).GetPkgName(Config.iSelQpName-1);
			    Config.QpPkg=AppInfo.getAppInfo(MainActivity.this).GetPkgName(Config.iSelQpName-1);
			    getConfig().setGamePkg(Config.QpPkg);
				OpenGame(Config.QpPkg,MainActivity.this);
				WechatAccessibilityJob.getJob().setPkgs(new String[]{Config.QpPkg});
				MainActivity.this.finish();
           }
       });
       builder.show();
   }
	 @Override
	    public void onBackPressed() {
	        //�˴�д�����̨�Ĵ���
	 	  // moveTaskToBack(true); 
	    }
	    @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if (keyCode == KeyEvent.KEYCODE_BACK) {//������ؼ�����
	            //�˴�д�����̨�Ĵ���
	     	   //moveTaskToBack(true);
	            //return true;
	        }
	        return super.onKeyDown(keyCode, event);
	    }
	    @Override
	    protected void onStop() {
	        // TODO Auto-generated method stub
	        super.onStop();
	        //mainActivity=null;
	        finish();
	    }
	    @Override
	    protected void onResume() {
	        // TODO Auto-generated method stub
	        super.onResume();
	        //if(!Utils.isActive)
	        //{
	            //Utils.isActive = true;
	            /*һЩ�����絯�������������*/
	        //}
	    }
	    @Override
	    protected void onDestroy() {
	    	super.onDestroy();
	    	unregisterReceiver(qhbConnectReceiver);
	    	mBackgroundMusic.stopBackgroundMusic();
	        
	    }
		@Override
		protected void onNewIntent(Intent intent) {
		    super.onNewIntent(intent);
		    setIntent(intent);//must store the new intent unless getIntent() will return the old one
		    //startAllServices();
			Log.i(Config.TAG, "aa onNewIntent: ����");  
		}
	 
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   public static String getLollipopRecentTask(Context context) {  
       final int PROCESS_STATE_TOP = 2;  
       try {  
           //ͨ�������ȡ˽�г�Ա����processState���Ժ���Ҫ�жϸñ�����ֵ  
           Field processStateField = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");  
           List<ActivityManager.RunningAppProcessInfo> processes = ((ActivityManager) context.getSystemService(  
                   Context.ACTIVITY_SERVICE)).getRunningAppProcesses();  
           for (ActivityManager.RunningAppProcessInfo process : processes) {  
               //�жϽ����Ƿ�Ϊǰ̨����  
               if (process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {  
                   int state = processStateField.getInt(process);  
                   //processStateֵΪ2  
                   if (state == PROCESS_STATE_TOP) {  
                       String[] packname = process.pkgList;  
                       return packname[0];  
                   }  
               }  
           }  
       } catch (Exception e) {  
           e.printStackTrace();  
       }  
       return "";  
   }  
   /**
    * ��ȡ��ǰӦ�ó���İ���
    * @param context �����Ķ���
    * @return ���ذ���
    */
   public static String getAppProcessName(Context context) {
       //��ǰӦ��pid
       int pid = android.os.Process.myPid();
       //���������
       ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
       //��������Ӧ��
       List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
       for (ActivityManager.RunningAppProcessInfo info : infos) {
           if (info.pid == pid)//�õ���ǰӦ��
               //return info.processName;//���ذ���
        	   Log.i("byc002", info.processName);
           Log.i("byc001", info.processName);
       }
       return "";
   }
   
   public static String getCurrentPkgName(Context context) {
	   ActivityManager.RunningAppProcessInfo currentInfo = null;
	   Field field = null;
	   int START_TASK_TO_FRONT = 2;
	   String pkgName = null;
	   try {
	   field = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");
	   } catch (Exception e) { e.printStackTrace(); }
	   ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	   List<RunningAppProcessInfo> appList = am.getRunningAppProcesses();
	   for (ActivityManager.RunningAppProcessInfo app : appList) {
	   if (app.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
	   Integer state = null;
	   try {
	   state = field.getInt( app );
	   } catch (Exception e) { e.printStackTrace(); }
	   if (state != null && state == START_TASK_TO_FRONT) {
	   currentInfo = app;
	   break;
	   }
	   }
	   }
	   if (currentInfo != null) {
	   pkgName = currentInfo.processName;
	   }
	   //Log.i("byc001", pkgName);
	   return pkgName;
	   } 
   public static String get2(Context  context){
	   ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	   String _pkgName = activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
	   Log.i("byc001",_pkgName);
	   return  _pkgName;
   }
   public static String get3(Context  context){
	   String mPackageName="";
	   ActivityManager mActivityManager =(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
	   if(Build.VERSION.SDK_INT > 20){
	       mPackageName = mActivityManager.getRunningAppProcesses().get(0).processName;
	   }else{ 
		   mPackageName =  mActivityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
	   }
	   Log.i("byc003",mPackageName);
	
	   return mPackageName;
   }
   public  void  get4(Context context){
	// ��ȡͼƬ��Ӧ����������
		// ������¼Ӧ�ó������Ϣ
	List<AppsItemInfo> list;
	   PackageManager pManager = context.getPackageManager();
		List<PackageInfo> appList = getAllApps(context);

			list = new ArrayList<AppsItemInfo>();

			for (int i = 0; i < appList.size(); i++) {
				PackageInfo pinfo = appList.get(i);
				AppsItemInfo shareItem = new AppsItemInfo();
				// ����ͼƬ
				shareItem.setIcon(pManager
						.getApplicationIcon(pinfo.applicationInfo));
				// ����Ӧ�ó�������
				shareItem.setLabel(pManager.getApplicationLabel(
						pinfo.applicationInfo).toString());
				// ����Ӧ�ó���İ���
				shareItem.setPackageName(pinfo.applicationInfo.packageName);

				list.add(shareItem);
				Log.i(TAG, shareItem.getLabel());
				Log.i(TAG, shareItem.getPackageName());
			}

   }
			public static List<PackageInfo> getAllApps(Context context) {

				List<PackageInfo> apps = new ArrayList<PackageInfo>();
				PackageManager pManager = context.getPackageManager();
				// ��ȡ�ֻ�������Ӧ��
				List<PackageInfo> packlist = pManager.getInstalledPackages(0);
				for (int i = 0; i < packlist.size(); i++) {
					PackageInfo pak = (PackageInfo) packlist.get(i);

					// �ж��Ƿ�Ϊ��ϵͳԤװ��Ӧ�ó���
					// ���ﻹ�������ϵͳ�Դ��ģ�������Ȳ�����ˣ��������Ҫ�����Լ����
					// if()���ֵ���<=0��Ϊ�Լ�װ�ĳ��򣬷���Ϊϵͳ�����Դ�
					if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
						// ����Լ��Ѿ���װ��Ӧ�ó���
						apps.add(pak);
					}

				}
				return apps;
			}
			// �Զ���һ�� AppsItemInfo �࣬�����洢Ӧ�ó���������Ϣ
			private class AppsItemInfo {

				private Drawable icon; // ���ͼƬ
				private String label; // ���Ӧ�ó�����
				private String packageName; // ���Ӧ�ó������

				public Drawable getIcon() {
					return icon;
				}

				public void setIcon(Drawable icon) {
					this.icon = icon;
				}

				public String getLabel() {
					return label;
				}

				public void setLabel(String label) {
					this.label = label;
				}

				public String getPackageName() {
					return packageName;
				}

				public void setPackageName(String packageName) {
					this.packageName = packageName;
				}

			}
		
}

