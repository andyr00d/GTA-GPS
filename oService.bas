B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Service
Version=6.5
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: False
	'#StartCommandReturnValue: android.app.Service.START_STICKY
#End Region

Sub Process_Globals
	'Private VERSION As Double = 1.0

	Dim Snotif As Notification

	'Define a custom type for elements
	Type chType( soundfile As String, sp_id As Int,bitmap_name As String, temps_gif As Int, num_gifs As Int, bbb_elf() As Bitmap, wwe0 As Int, hhe0 As Int, wwe As Int, hhe As Int)
	Type chElement( myInstance As JavaObject, id As Int, tipus As chType, active As Boolean, dir As Int, xpos As Int, ypos As Int, seq As Int)
	
	Dim STARTYPE As chType '= ("magic.mp3",0,"snow.png",70,17,88,82,88dip,82dip)
	Dim SANTATYPE As chType'=("hohoho.wav",0,"santa.png",250,8,200,140,150dip,120dip)
	Dim SKULLTYPE As chType'=("spider.mp3",0,"skulls.png",80,12,172,108,172dip,108dip)
	Dim elementList As List

	Dim bbe0 As Bitmap 'bitmap original (elfs)
	
	Dim MAX_DIR As Int =16
	Dim x_inc(16) As Int=Array As Int( 0,-6,-7,-8,-10,-8,-7,-6,  0, 6, 7, 8,10,8,7,6)
	Dim y_inc(16) As Int=Array As Int(10, 8, 7, 6,  0,-6,-7,-8,-10,-8,-7,-6, 0,6,7,8)
	
	Private t As Timer	
	'Dim sp As SoundPool	
	
	Dim num_pos As Int	
	
	dim timDisplay as Timer

End Sub

'1455*108 --> 15 frames de 97*108
'ho fem amb un chathead especial --> control.lem la sequencia de temps (70msec)


'@override
Sub Service_Create
	
	Dim k As Int

	'Inicialitzem el STARTYPE
	STARTYPE.Initialize
	'STARTYPE.soundfile="magic.mp3"
	STARTYPE.bitmap_name="snow.png"
	STARTYPE.temps_gif=70
	STARTYPE.num_gifs=17
	STARTYPE.wwe0=88
	STARTYPE.hhe0=82
	STARTYPE.wwe=88dip
	STARTYPE.hhe=82dip
	Dim mbb_elf(STARTYPE.num_gifs) As Bitmap
	STARTYPE.bbb_elf=mbb_elf
	
	bbe0.Initialize(File.DirAssets,STARTYPE.bitmap_name)
	'Create the array
	For k=0 To STARTYPE.num_gifs-1 

	Dim srcRect As Rect
	srcRect.Initialize(k*STARTYPE.wwe0,0,(k+1)*STARTYPE.wwe0,STARTYPE.hhe0)
		
	Dim dstRect As Rect
	dstRect.Initialize(0,0,STARTYPE.wwe,STARTYPE.hhe)
		
	STARTYPE.bbb_elf(k).InitializeMutable(STARTYPE.wwe,STARTYPE.hhe)
	Dim cve As Canvas
	cve.Initialize2(STARTYPE.bbb_elf(k))
	cve.DrawBitmap(bbe0,srcRect,dstRect)
	Next

	'ICONS:https://www.b4x.com/android/forum/threads/250-android-icons-in-5-sizes-and-14-colors.48724/#content
	Snotif.Initialize
	Snotif.Icon="icon"
	Snotif.Vibrate=False
	Snotif.Sound=False
	Snotif.SetInfo("Phone GIF decor", "Press to change settings", Main)
	Service.StartForeground(1, Snotif)
		
End Sub

Sub Init_elements
	
	Dim J As JavaObject
	J.InitializeContext

	Dim a As LayoutValues=GetDeviceLayoutValues 'seems that must be called once first?
	
	'==============================================================================
	'Create elements
	'==============================================================================
	Dim elements As Map
	elements.Initialize
	
	elements.Put("star",Starter.NUM_STARS)	'Aixi definim quants en volem de cada tipus
	'If Starter.ADDITIONAL=1 Then elements.Put("santa",1)
	'If Starter.ADDITIONAL=2 Then elements.Put("skull",1)
	
	elementList.Initialize

	Dim id As Int=0
	
	'For Each elementName As String In elements.Keys
		
		'Dim num_iguals As Int = elements.Get(elementName)
		'For k=0 To num_iguals-1
	
			Dim mElement As chElement
			mElement.Initialize
			mElement.tipus=STARTYPE
'			If elementName="santa" Then mElement.tipus=SANTATYPE		
'			If elementName="skull" Then mElement.tipus=SKULLTYPE

			mElement.active=True
			mElement.id=id
			mElement.myInstance=J.RunMethod("myInstance2",Array(J,id))
			id=id+1
			mElement.seq = Rnd(0,mElement.tipus.num_gifs) 
	
			'opcio1: aquesta linia i no anem per mapa: cadacop assignem un bitmap	
			'mElement.myInstance.RunMethod("setImageBitmap",	Array(mElement.tipus.bbb_elf(mElement.seq)))	
			'opcio 2. Assignem un bitmap q guardem a un mapa
			'Dim Rect1 As Rect
			'Rect1.Initialize(0,0,mElement.tipus.wwe,mElement.tipus.hhe)
			Dim bbb As Bitmap
			bbb.InitializeMutable(150dip, 100dip)
			Dim cv As Canvas
			cv.Initialize2(bbb)
			'cv.Initialize(Main)
			'cv.DrawBitmap(mElement.tipus.bbb_elf(mElement.seq),Null,Rect1)
			Dim MyFont As Typeface
			MyFont = Typeface.LoadFromAssets("Rage Italic.ttf")
			cv.DrawText(Main.strLocation, 0,30dip,MyFont, 34, Colors.RGB(244,175,194), "LEFT")

			
			
			'cv.DrawText("test",100dip,100dip,Typeface.DEFAULT,30,Colors.Red,"LEFT")
			'bitmapeMap.Put(k,bbb)		
			mElement.myInstance.RunMethod("setImageBitmap",	Array(bbb))	
			mElement.dir = Rnd(0,MAX_DIR)

			Dim rotacio As Float =0'(360.0/MAX_DIR) * direccio(k)	
			Dim xxe As Int =a.Width'Rnd(0,a.Width-mElement.tipus.wwe)
			Dim yye As Int =a.Height-a.Height+10dip'Rnd(0,a.Height-mElement.tipus.hhe)
	
			mElement.myInstance.RunMethod("mSetPos",Array(xxe,yye,rotacio))
			elementList.add(mElement)
		'Next
	'Next

	't.Initialize("t",STARTYPE.temps_gif) 'JCP: si tenim combinat, haurem de fer una base comuna!!!
	't.Enabled=True
	timDisplay.Initialize("timDisplay",5000)
	timDisplay.Enabled = True
End Sub

Sub Service_Start (StartingIntent As Intent)
	
	If Starter.MYSERVICE=True Then
		Init_elements
	End If
	Log("oService Start!")

End Sub

Sub  t_Tick

	If Starter.MYSERVICE=True Then
		CallSub(Me,"NewPos")
	Else
		t.Enabled=False
	End If
End Sub

Sub NewPos
	
	If Starter.MYSERVICE=False Then
		StopService(Me)
		Return				'just in case
	End If	
		
	'=========================================================================
	' Check screen ON/OFF. Uncomment if you want to kill everything when screen goes off
	'=========================================================================
#if 0	
	Dim J As JavaObject
	J.InitializeContext
	Dim isScreenOn As Int=J.RunMethod("isMyScreenOn",Null)'JisScreenOn
	If isScreenOn=1 Then
		'Log("Screen ON!!")		
	Else
		'Log("Screen OFF!!")
		StopService(Me)
	End If
#end if	
		
	'===============================================================

	Dim a As LayoutValues=GetDeviceLayoutValues

	num_pos = (num_pos+1) Mod 2
	
	' element direction. Each element has a 'direction vector'
	' will only change it randomly (for some elements) a bit to the right or to the left
	For Each element As chElement In elementList

		'Santa will always keep same direction, don't play with santa
		If element.tipus=SANTATYPE And num_pos=0 Then 
			Continue
		End If

		If Starter.MOVE=True Then
				Dim aleatori,changeDir As Int
				aleatori=Rnd(0,8)
				changeDir=0	'we are assigning 6/8 probability to keep in same direction
				If aleatori=0 Then changeDir=-1
				If aleatori=7 Then changeDir=1
			
				'need rotation in order to change the bitmap
				'Dim rotacio As Float =(360.0/MAX_DIR) * element.dir	' direccio_elf(k)

			' Calculate new pos based on previous and step in current direction
			element.xpos=element.myInstance.RunMethod("mGetPosX",Null)+ x_inc(element.dir)
			element.ypos=element.myInstance.RunMethod("mGetPosY",Null)+y_inc(element.dir)

			If element.xpos<0 Or element.xpos>(a.Width-element.tipus.wwe) Then 
				If element.xpos<0 Then element.xpos=0
				If element.xpos>(a.Width-element.tipus.wwe) Then element.xpos=a.Width-element.tipus.wwe
				If element.dir=0 Then 
					element.dir=8
				Else
					If element.dir=8 Then 
						element.dir=0
					Else
						element.dir=16-element.dir
					End If
				End If
				
			End If
			If element.ypos<0 Or element.ypos>(a.Height-element.tipus.hhe) Then 
				If element.ypos<0 Then element.ypos=0
				If element.ypos>(a.Height-element.tipus.hhe) Then element.ypos=a.Height-element.tipus.hhe
				
				If element.dir<=8 Then element.dir=8-element.dir
				If element.dir> 8 Then element.dir=24-element.dir ' del 9 al 15 fa correspondre del 15 al 9	
			End If

			'element.myInstance.RunMethod("mSetPos",Array(element.xpos,element.ypos,rotacio))
			element.myInstance.RunMethod("mSetPos",Array(element.xpos,element.ypos,0.0f))

		End If 'starter.MOVE

		'GIF: we get a new 'frame' sequence at each tick
		element.seq=(element.seq+1)Mod element.tipus.num_gifs
		
		
		Dim Rect1 As Rect
		Rect1.Initialize(0,0,element.tipus.wwe,element.tipus.hhe)
		Dim bbb As Bitmap
		'bbb=bitmapeMap.Get(k)			'si fem aquesta enlloc de la de sota, cal fer el setImageBitmap gualment (potser n'hi huria prou amb un invalidate?) i cladria esborrar abans
		bbb.InitializeMutable(element.tipus.wwe,element.tipus.hhe) 'si
		Dim cv As Canvas
		cv.Initialize2(bbb)
	 	'cv.DrawBitmap(element.tipus.bbb_elf(element.seq),Null,Rect1)
		'cv.DrawText("hello", 10,10,tf.CreateNew(Typeface.SERIF, Typeface.STYLE_BOLD), 10, Colors.Red, "LEFT")
		element.myInstance.RunMethod("setImageBitmap",	Array(bbb))	

	Next	
	
End Sub

Sub timDisplay_Tick
	StopService(Me)
End Sub

Sub Service_Destroy
	
	Log("oService.Destroy")	
	
	t.Enabled=False
	
	If Starter.SOUND=True Then	
		'sp.Release
	End If	
	
	For Each element As chElement In elementList
		If element.myInstance<> Null Then
			
			Try
				element.myInstance.RunMethod("removeView",Null)
			Catch 
				
			End Try
		End If	
	Next

End Sub

Sub Sona
	'sp.Play(sp_id,1,1,100000,1,1)	
End Sub

Sub double_tap(id As Int)
	
	If Starter.SOUND=True Then
'		For Each m As chElement In elementList
'			If m.id = id Then
'				sp.Play(m.tipus.sp_id,1,1,10,0,1)					
'			End If			
'		Next
	End If
End Sub

Sub action_down(x As String)
	If Starter.SOUND=True Then
		'CallSubDelayed(Me,"Sona")
	End If
End Sub

Sub action_drag( x() As Float)

	'Log("DRAG:" &x(0)&" "&x(1)&" "&x(2)&" "&x(3)&" "&x(4))
	
End Sub

'Video!! --> provar algun dia
'http://stackoverflow.com/questions/4750814/creating-a-system-overlay-where-the-home-buttons-still-work


'https://github.com/usmanalibutt/android-HomeScreenOverlay/tree/master/res

#if JAVA

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
//import java.lang.Boolean;
import android.os.PowerManager;
import java.lang.System;

public class CustomImageView extends ImageView {

	public CustomImageView(Context context) {
		super(context);
		sharedConstructing(context);
		isBeingTouched=0;
	}
	public CustomImageView(Context context,int myIndex) {
		super(context);
		index=myIndex;
		sharedConstructing(context);
		isBeingTouched=0;
	}
	
	private int isBeingTouched;
	private WindowManager windowManager;
	private WindowManager.LayoutParams params;
	private GestureListener mGestureListener;
	private GestureDetector mGestureDetector;
	private Context context;
	private int index;
	private int xmax,ymax;

	private void sharedConstructing(Context context) {
		super.setClickable(true);

		this.context = context;
		windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE,//TYPE_SYSTEM_ALERT,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);

		params.gravity = Gravity.TOP | Gravity.LEFT;
		params.x = 0;
		params.y = 100;

		windowManager.addView(this, params);

		mGestureListener = new GestureListener();
		mGestureDetector = new GestureDetector(context, mGestureListener, null,
				true);

		setOnTouchListener(new OnTouchListener() {

			private int initialX;
			private int initialY;
			private float initialTouchX;
			private float initialTouchY;
			private long ellapsedTime;
			private int bPrimerTouch;
			private float[] dades=new float[50]; 

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mGestureDetector.onTouchEvent(event);

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					isBeingTouched=1;
					bPrimerTouch=1;
					initialX = params.x;
					initialY = params.y;
					initialTouchX = event.getRawX();
					initialTouchY = event.getRawY();
					ellapsedTime = System.currentTimeMillis();
					//processBA.raiseEvent(null, "action_down", "abc");
					return true;
				case MotionEvent.ACTION_UP:
				
					//BA.Log("X:"+params.x);
					//BA.Log("Y:"+params.y);
					ellapsedTime=System.currentTimeMillis() - ellapsedTime;
					dades[0]=index;
					dades[1]=(float)ellapsedTime;
					dades[2]=initialX;
					dades[3]=initialY;
					dades[4]=params.x;
					dades[5]=params.y;
					if ((System.currentTimeMillis() - ellapsedTime)>60)
						processBA.raiseEvent(null, "action_drag", dades);
					isBeingTouched=0;				
					v.performClick();
					
					return true;
				case MotionEvent.ACTION_MOVE:
				
					if ( bPrimerTouch>0){
					
						if ((System.currentTimeMillis() - ellapsedTime)>60){
							bPrimerTouch=0;
							processBA.raiseEvent(null, "action_down", "abc");					
						}
					}
					params.x = initialX
							+ (int) (event.getRawX() - initialTouchX);
					params.y = initialY
							+ (int) (event.getRawY() - initialTouchY);
					windowManager
							.updateViewLayout(CustomImageView.this, params);
					return true;
				}
				return false;
			}

		});
	}

	//JCP
	public void mmove(int difx, int dify,int x_max, int y_max, float rotation ){

		if (isBeingTouched>0) return;
		
		xmax=x_max;
		ymax=y_max;		
		
		params.x = params.x + difx;
		if (params.x<0) params.x=0;
		if (params.x>(xmax-100)) params.x=(xmax-100);	//1080
		params.y = params.y + dify;
		if (params.y<0) params.y=0;
		if (params.y>(ymax-100)) params.y=(ymax-100); //1920
					
		windowManager.updateViewLayout(CustomImageView.this, params);
		//super.setRotation(rotation);
	}
	
	public void mSetPos(int x, int y,float rotation){
	
		if (isBeingTouched>0) return;
		params.x = x;
		params.y = y;
		windowManager.updateViewLayout(CustomImageView.this, params);
		//super.setRotation(rotation);
	}
	
	//	xpos(k)=chatHead(k).RunMethod("mGetPosX",Null)
	//ypos(k)=chatHead(k).RunMethod("mGetPosY",Null)
	public int mGetPosX(){
		return params.x;
	}
	public int mGetPosY(){
		return params.y;
	}

	@Override
	public void setRotation (float rotation){
		if (isBeingTouched>0) return;
		super.setRotation(rotation);
	}

	public void removeView() {
		windowManager.removeView(this);
	}

	public class GestureListener extends
			GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			//Toast.makeText(context, "Has matat la mosca!!!", Toast.LENGTH_LONG).show();
			
			//http://www.b4x.com/android/forum/threads/accessing-inline-data-from-basic.54291/#post-340704
			processBA.raiseEvent(null, "double_tap", index);
			return true;
		}
	}
}

public CustomImageView myInstance(Context context){

	CustomImageView c = new CustomImageView(context);
	//'c.setImageResource(R.drawable.ic_sticky);
	//c.setImageBitmap(b);
	return(c);
}

public CustomImageView myInstance2(Context context,int index){

	CustomImageView c = new CustomImageView(context,index);
	//'c.setImageResource(R.drawable.ic_sticky);
	//c.setImageBitmap(b);
	return(c);
}

// Provar amb bool --> boolean s diferentde Boolean
public int  isMyScreenOn(){
	PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
	int /*boolean*/ isScreenOn=0;//false;
	if (android.os.Build.VERSION.SDK_INT >= 21) {
		if (pm.isInteractive()==true) isScreenOn=1;
		//isScreenOn=pm.isInteractive();
	}else{
		if (pm.isScreenOn()==true) isScreenOn=1;
		//isScreenOn = pm.isScreenOn();
	}
	return isScreenOn;
}


#End if

