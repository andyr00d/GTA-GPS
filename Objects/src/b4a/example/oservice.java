package b4a.example;

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
import android.os.PowerManager;
import java.lang.System;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class oservice extends  android.app.Service{
	public static class oservice_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
            BA.LogInfo("** Receiver (oservice) OnReceive **");
			android.content.Intent in = new android.content.Intent(context, oservice.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
            ServiceHelper.StarterHelper.startServiceFromReceiver (context, in, false, BA.class);
		}

	}
    static oservice mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return oservice.class;
	}
	@Override
	public void onCreate() {
        super.onCreate();
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "b4a.example", "b4a.example.oservice");
            if (BA.isShellModeRuntimeCheck(processBA)) {
                processBA.raiseEvent2(null, true, "SHELL", false);
		    }
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            processBA.loadHtSubs(this.getClass());
            ServiceHelper.init();
        }
        _service = new ServiceHelper(this);
        processBA.service = this;
        
        if (BA.isShellModeRuntimeCheck(processBA)) {
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.oservice", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
		}
        if (!false && ServiceHelper.StarterHelper.startFromServiceCreate(processBA, false) == false) {
				
		}
		else {
            processBA.setActivityPaused(false);
            BA.LogInfo("*** Service (oservice) Create ***");
            processBA.raiseEvent(null, "service_create");
        }
        processBA.runHook("oncreate", this, null);
        if (false) {
			ServiceHelper.StarterHelper.runWaitForLayouts();
		}
    }
		@Override
	public void onStart(android.content.Intent intent, int startId) {
		onStartCommand(intent, 0, 0);
    }
    @Override
    public int onStartCommand(final android.content.Intent intent, int flags, int startId) {
    	if (ServiceHelper.StarterHelper.onStartCommand(processBA, new Runnable() {
            public void run() {
                handleStart(intent);
            }}))
			;
		else {
			ServiceHelper.StarterHelper.addWaitForLayout (new Runnable() {
				public void run() {
                    processBA.setActivityPaused(false);
                    BA.LogInfo("** Service (oservice) Create **");
                    processBA.raiseEvent(null, "service_create");
					handleStart(intent);
                    ServiceHelper.StarterHelper.removeWaitForLayout();
				}
			});
		}
        processBA.runHook("onstartcommand", this, new Object[] {intent, flags, startId});
		return android.app.Service.START_NOT_STICKY;
    }
    public void onTaskRemoved(android.content.Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        if (false)
            processBA.raiseEvent(null, "service_taskremoved");
            
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (oservice) Start **");
    	java.lang.reflect.Method startEvent = processBA.htSubs.get("service_start");
    	if (startEvent != null) {
    		if (startEvent.getParameterTypes().length > 0) {
    			anywheresoftware.b4a.objects.IntentWrapper iw = ServiceHelper.StarterHelper.handleStartIntent(intent, _service, processBA);
    			processBA.raiseEvent(null, "service_start", iw);
    		}
    		else {
    			processBA.raiseEvent(null, "service_start");
    		}
    	}
    }
	
	@Override
	public void onDestroy() {
        super.onDestroy();
        if (false) {
            BA.LogInfo("** Service (oservice) Destroy (ignored)**");
        }
        else {
            BA.LogInfo("** Service (oservice) Destroy **");
		    processBA.raiseEvent(null, "service_destroy");
            processBA.service = null;
		    mostCurrent = null;
		    processBA.setActivityPaused(true);
            processBA.runHook("ondestroy", this, null);
        }
	}

@Override
	public android.os.IBinder onBind(android.content.Intent intent) {
		return null;
	}public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.NotificationWrapper _snotif = null;
public static b4a.example.oservice._chtype _startype = null;
public static b4a.example.oservice._chtype _santatype = null;
public static b4a.example.oservice._chtype _skulltype = null;
public static anywheresoftware.b4a.objects.collections.List _elementlist = null;
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bbe0 = null;
public static int _max_dir = 0;
public static int[] _x_inc = null;
public static int[] _y_inc = null;
public static anywheresoftware.b4a.objects.Timer _t = null;
public static int _num_pos = 0;
public static anywheresoftware.b4a.objects.Timer _timdisplay = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public static class _chtype{
public boolean IsInitialized;
public String soundfile;
public int sp_id;
public String bitmap_name;
public int temps_gif;
public int num_gifs;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper[] bbb_elf;
public int wwe0;
public int hhe0;
public int wwe;
public int hhe;
public void Initialize() {
IsInitialized = true;
soundfile = "";
sp_id = 0;
bitmap_name = "";
temps_gif = 0;
num_gifs = 0;
bbb_elf = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper[0];
{
int d0 = bbb_elf.length;
for (int i0 = 0;i0 < d0;i0++) {
bbb_elf[i0] = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
}
}
;
wwe0 = 0;
hhe0 = 0;
wwe = 0;
hhe = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _chelement{
public boolean IsInitialized;
public anywheresoftware.b4j.object.JavaObject myInstance;
public int id;
public b4a.example.oservice._chtype tipus;
public boolean active;
public int dir;
public int xpos;
public int ypos;
public int seq;
public void Initialize() {
IsInitialized = true;
myInstance = new anywheresoftware.b4j.object.JavaObject();
id = 0;
tipus = new b4a.example.oservice._chtype();
active = false;
dir = 0;
xpos = 0;
ypos = 0;
seq = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static String  _action_down(String _x) throws Exception{
 //BA.debugLineNum = 312;BA.debugLine="Sub action_down(x As String)";
 //BA.debugLineNum = 313;BA.debugLine="If Starter.SOUND=True Then";
if (mostCurrent._starter._sound==anywheresoftware.b4a.keywords.Common.True) { 
 };
 //BA.debugLineNum = 316;BA.debugLine="End Sub";
return "";
}
public static String  _action_drag(float[] _x) throws Exception{
 //BA.debugLineNum = 318;BA.debugLine="Sub action_drag( x() As Float)";
 //BA.debugLineNum = 322;BA.debugLine="End Sub";
return "";
}
public static String  _double_tap(int _id) throws Exception{
 //BA.debugLineNum = 301;BA.debugLine="Sub double_tap(id As Int)";
 //BA.debugLineNum = 303;BA.debugLine="If Starter.SOUND=True Then";
if (mostCurrent._starter._sound==anywheresoftware.b4a.keywords.Common.True) { 
 };
 //BA.debugLineNum = 310;BA.debugLine="End Sub";
return "";
}
public static String  _init_elements() throws Exception{
anywheresoftware.b4j.object.JavaObject _j = null;
anywheresoftware.b4a.keywords.LayoutValues _a = null;
anywheresoftware.b4a.objects.collections.Map _elements = null;
int _id = 0;
b4a.example.oservice._chelement _melement = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bbb = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _cv = null;
anywheresoftware.b4a.keywords.constants.TypefaceWrapper _myfont = null;
float _rotacio = 0f;
int _xxe = 0;
int _yye = 0;
 //BA.debugLineNum = 83;BA.debugLine="Sub Init_elements";
 //BA.debugLineNum = 85;BA.debugLine="Dim J As JavaObject";
_j = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 86;BA.debugLine="J.InitializeContext";
_j.InitializeContext(processBA);
 //BA.debugLineNum = 88;BA.debugLine="Dim a As LayoutValues=GetDeviceLayoutValues 'seem";
_a = anywheresoftware.b4a.keywords.Common.GetDeviceLayoutValues(processBA);
 //BA.debugLineNum = 93;BA.debugLine="Dim elements As Map";
_elements = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 94;BA.debugLine="elements.Initialize";
_elements.Initialize();
 //BA.debugLineNum = 96;BA.debugLine="elements.Put(\"star\",Starter.NUM_STARS)	'Aixi defi";
_elements.Put((Object)("star"),(Object)(mostCurrent._starter._num_stars));
 //BA.debugLineNum = 100;BA.debugLine="elementList.Initialize";
_elementlist.Initialize();
 //BA.debugLineNum = 102;BA.debugLine="Dim id As Int=0";
_id = (int) (0);
 //BA.debugLineNum = 109;BA.debugLine="Dim mElement As chElement";
_melement = new b4a.example.oservice._chelement();
 //BA.debugLineNum = 110;BA.debugLine="mElement.Initialize";
_melement.Initialize();
 //BA.debugLineNum = 111;BA.debugLine="mElement.tipus=STARTYPE";
_melement.tipus = _startype;
 //BA.debugLineNum = 115;BA.debugLine="mElement.active=True";
_melement.active = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 116;BA.debugLine="mElement.id=id";
_melement.id = _id;
 //BA.debugLineNum = 117;BA.debugLine="mElement.myInstance=J.RunMethod(\"myInstance2\",A";
_melement.myInstance.setObject((java.lang.Object)(_j.RunMethod("myInstance2",new Object[]{(Object)(_j.getObject()),(Object)(_id)})));
 //BA.debugLineNum = 118;BA.debugLine="id=id+1";
_id = (int) (_id+1);
 //BA.debugLineNum = 119;BA.debugLine="mElement.seq = Rnd(0,mElement.tipus.num_gifs)";
_melement.seq = anywheresoftware.b4a.keywords.Common.Rnd((int) (0),_melement.tipus.num_gifs);
 //BA.debugLineNum = 126;BA.debugLine="Dim bbb As Bitmap";
_bbb = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 127;BA.debugLine="bbb.InitializeMutable(250dip, 100dip)";
_bbb.InitializeMutable(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (250)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)));
 //BA.debugLineNum = 128;BA.debugLine="Dim cv As Canvas";
_cv = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 129;BA.debugLine="cv.Initialize2(bbb)";
_cv.Initialize2((android.graphics.Bitmap)(_bbb.getObject()));
 //BA.debugLineNum = 132;BA.debugLine="Dim MyFont As Typeface";
_myfont = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
 //BA.debugLineNum = 133;BA.debugLine="MyFont = Typeface.LoadFromAssets(\"Rage Italic.t";
_myfont.setObject((android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("Rage Italic.ttf")));
 //BA.debugLineNum = 134;BA.debugLine="cv.DrawText(Main.strLocation, 0,30dip,MyFont, 3";
_cv.DrawText(processBA,mostCurrent._main._strlocation,(float) (0),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),(android.graphics.Typeface)(_myfont.getObject()),(float) (34),anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (244),(int) (175),(int) (194)),BA.getEnumFromString(android.graphics.Paint.Align.class,"LEFT"));
 //BA.debugLineNum = 137;BA.debugLine="mElement.myInstance.RunMethod(\"setImageBitmap\",";
_melement.myInstance.RunMethod("setImageBitmap",new Object[]{(Object)(_bbb.getObject())});
 //BA.debugLineNum = 138;BA.debugLine="mElement.dir = Rnd(0,MAX_DIR)";
_melement.dir = anywheresoftware.b4a.keywords.Common.Rnd((int) (0),_max_dir);
 //BA.debugLineNum = 140;BA.debugLine="Dim rotacio As Float =0'(360.0/MAX_DIR) * direc";
_rotacio = (float) (0);
 //BA.debugLineNum = 141;BA.debugLine="Dim xxe As Int =a.Width'Rnd(0,a.Width-mElement.";
_xxe = _a.Width;
 //BA.debugLineNum = 142;BA.debugLine="Dim yye As Int =a.Height-a.Height+10dip'Rnd(0,a";
_yye = (int) (_a.Height-_a.Height+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)));
 //BA.debugLineNum = 145;BA.debugLine="elementList.add(mElement)";
_elementlist.Add((Object)(_melement));
 //BA.debugLineNum = 151;BA.debugLine="timDisplay.Initialize(\"timDisplay\",5000)";
_timdisplay.Initialize(processBA,"timDisplay",(long) (5000));
 //BA.debugLineNum = 152;BA.debugLine="timDisplay.Enabled = True";
_timdisplay.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 153;BA.debugLine="End Sub";
return "";
}
public static String  _newpos() throws Exception{
 //BA.debugLineNum = 173;BA.debugLine="Sub NewPos";
 //BA.debugLineNum = 175;BA.debugLine="If Starter.MYSERVICE=False Then";
if (mostCurrent._starter._myservice==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 176;BA.debugLine="StopService(Me)";
anywheresoftware.b4a.keywords.Common.StopService(processBA,oservice.getObject());
 //BA.debugLineNum = 177;BA.debugLine="Return				'just in case";
if (true) return "";
 };
 //BA.debugLineNum = 268;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim Snotif As Notification";
_snotif = new anywheresoftware.b4a.objects.NotificationWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Type chType( soundfile As String, sp_id As Int,bi";
;
 //BA.debugLineNum = 13;BA.debugLine="Type chElement( myInstance As JavaObject, id As I";
;
 //BA.debugLineNum = 15;BA.debugLine="Dim STARTYPE As chType '= (\"magic.mp3\",0,\"snow.pn";
_startype = new b4a.example.oservice._chtype();
 //BA.debugLineNum = 16;BA.debugLine="Dim SANTATYPE As chType'=(\"hohoho.wav\",0,\"santa.p";
_santatype = new b4a.example.oservice._chtype();
 //BA.debugLineNum = 17;BA.debugLine="Dim SKULLTYPE As chType'=(\"spider.mp3\",0,\"skulls.";
_skulltype = new b4a.example.oservice._chtype();
 //BA.debugLineNum = 18;BA.debugLine="Dim elementList As List";
_elementlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 20;BA.debugLine="Dim bbe0 As Bitmap 'bitmap original (elfs)";
_bbe0 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim MAX_DIR As Int =16";
_max_dir = (int) (16);
 //BA.debugLineNum = 23;BA.debugLine="Dim x_inc(16) As Int=Array As Int( 0,-6,-7,-8,-10";
_x_inc = new int[]{(int) (0),(int) (-6),(int) (-7),(int) (-8),(int) (-10),(int) (-8),(int) (-7),(int) (-6),(int) (0),(int) (6),(int) (7),(int) (8),(int) (10),(int) (8),(int) (7),(int) (6)};
 //BA.debugLineNum = 24;BA.debugLine="Dim y_inc(16) As Int=Array As Int(10, 8, 7, 6,  0";
_y_inc = new int[]{(int) (10),(int) (8),(int) (7),(int) (6),(int) (0),(int) (-6),(int) (-7),(int) (-8),(int) (-10),(int) (-8),(int) (-7),(int) (-6),(int) (0),(int) (6),(int) (7),(int) (8)};
 //BA.debugLineNum = 26;BA.debugLine="Private t As Timer";
_t = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 29;BA.debugLine="Dim num_pos As Int";
_num_pos = 0;
 //BA.debugLineNum = 31;BA.debugLine="Dim timDisplay As Timer";
_timdisplay = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
int _k = 0;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper[] _mbb_elf = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _srcrect = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _dstrect = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _cve = null;
 //BA.debugLineNum = 40;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 42;BA.debugLine="Dim k As Int";
_k = 0;
 //BA.debugLineNum = 45;BA.debugLine="STARTYPE.Initialize";
_startype.Initialize();
 //BA.debugLineNum = 47;BA.debugLine="STARTYPE.bitmap_name=\"snow.png\"";
_startype.bitmap_name = "snow.png";
 //BA.debugLineNum = 48;BA.debugLine="STARTYPE.temps_gif=70";
_startype.temps_gif = (int) (70);
 //BA.debugLineNum = 49;BA.debugLine="STARTYPE.num_gifs=17";
_startype.num_gifs = (int) (17);
 //BA.debugLineNum = 50;BA.debugLine="STARTYPE.wwe0=88";
_startype.wwe0 = (int) (88);
 //BA.debugLineNum = 51;BA.debugLine="STARTYPE.hhe0=82";
_startype.hhe0 = (int) (82);
 //BA.debugLineNum = 52;BA.debugLine="STARTYPE.wwe=88dip";
_startype.wwe = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (88));
 //BA.debugLineNum = 53;BA.debugLine="STARTYPE.hhe=82dip";
_startype.hhe = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (82));
 //BA.debugLineNum = 54;BA.debugLine="Dim mbb_elf(STARTYPE.num_gifs) As Bitmap";
_mbb_elf = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper[_startype.num_gifs];
{
int d0 = _mbb_elf.length;
for (int i0 = 0;i0 < d0;i0++) {
_mbb_elf[i0] = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
}
}
;
 //BA.debugLineNum = 55;BA.debugLine="STARTYPE.bbb_elf=mbb_elf";
_startype.bbb_elf = _mbb_elf;
 //BA.debugLineNum = 57;BA.debugLine="bbe0.Initialize(File.DirAssets,STARTYPE.bitmap_na";
_bbe0.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_startype.bitmap_name);
 //BA.debugLineNum = 59;BA.debugLine="For k=0 To STARTYPE.num_gifs-1";
{
final int step13 = 1;
final int limit13 = (int) (_startype.num_gifs-1);
_k = (int) (0) ;
for (;_k <= limit13 ;_k = _k + step13 ) {
 //BA.debugLineNum = 61;BA.debugLine="Dim srcRect As Rect";
_srcrect = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 62;BA.debugLine="srcRect.Initialize(k*STARTYPE.wwe0,0,(k+1)*STARTY";
_srcrect.Initialize((int) (_k*_startype.wwe0),(int) (0),(int) ((_k+1)*_startype.wwe0),_startype.hhe0);
 //BA.debugLineNum = 64;BA.debugLine="Dim dstRect As Rect";
_dstrect = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 65;BA.debugLine="dstRect.Initialize(0,0,STARTYPE.wwe,STARTYPE.hhe)";
_dstrect.Initialize((int) (0),(int) (0),_startype.wwe,_startype.hhe);
 //BA.debugLineNum = 67;BA.debugLine="STARTYPE.bbb_elf(k).InitializeMutable(STARTYPE.ww";
_startype.bbb_elf[_k].InitializeMutable(_startype.wwe,_startype.hhe);
 //BA.debugLineNum = 68;BA.debugLine="Dim cve As Canvas";
_cve = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 69;BA.debugLine="cve.Initialize2(STARTYPE.bbb_elf(k))";
_cve.Initialize2((android.graphics.Bitmap)(_startype.bbb_elf[_k].getObject()));
 //BA.debugLineNum = 70;BA.debugLine="cve.DrawBitmap(bbe0,srcRect,dstRect)";
_cve.DrawBitmap((android.graphics.Bitmap)(_bbe0.getObject()),(android.graphics.Rect)(_srcrect.getObject()),(android.graphics.Rect)(_dstrect.getObject()));
 }
};
 //BA.debugLineNum = 74;BA.debugLine="Snotif.Initialize";
_snotif.Initialize();
 //BA.debugLineNum = 75;BA.debugLine="Snotif.Icon=\"icon\"";
_snotif.setIcon("icon");
 //BA.debugLineNum = 76;BA.debugLine="Snotif.Vibrate=False";
_snotif.setVibrate(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 77;BA.debugLine="Snotif.Sound=False";
_snotif.setSound(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 78;BA.debugLine="Snotif.SetInfo(\"Phone GIF decor\", \"Press to chang";
_snotif.SetInfoNew(processBA,BA.ObjectToCharSequence("Phone GIF decor"),BA.ObjectToCharSequence("Press to change settings"),(Object)(mostCurrent._main.getObject()));
 //BA.debugLineNum = 79;BA.debugLine="Service.StartForeground(1, Snotif)";
mostCurrent._service.StartForeground((int) (1),(android.app.Notification)(_snotif.getObject()));
 //BA.debugLineNum = 81;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
b4a.example.oservice._chelement _element = null;
 //BA.debugLineNum = 274;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 276;BA.debugLine="Log(\"oService.Destroy\")";
anywheresoftware.b4a.keywords.Common.Log("oService.Destroy");
 //BA.debugLineNum = 278;BA.debugLine="t.Enabled=False";
_t.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 280;BA.debugLine="If Starter.SOUND=True Then";
if (mostCurrent._starter._sound==anywheresoftware.b4a.keywords.Common.True) { 
 };
 //BA.debugLineNum = 284;BA.debugLine="For Each element As chElement In elementList";
{
final anywheresoftware.b4a.BA.IterableList group5 = _elementlist;
final int groupLen5 = group5.getSize()
;int index5 = 0;
;
for (; index5 < groupLen5;index5++){
_element = (b4a.example.oservice._chelement)(group5.Get(index5));
 //BA.debugLineNum = 285;BA.debugLine="If element.myInstance<> Null Then";
if (_element.myInstance!= null) { 
 //BA.debugLineNum = 287;BA.debugLine="Try";
try { //BA.debugLineNum = 288;BA.debugLine="element.myInstance.RunMethod(\"removeView\",Null";
_element.myInstance.RunMethod("removeView",(Object[])(anywheresoftware.b4a.keywords.Common.Null));
 } 
       catch (Exception e10) {
			processBA.setLastException(e10); };
 };
 }
};
 //BA.debugLineNum = 295;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 155;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 157;BA.debugLine="If Starter.MYSERVICE=True Then";
if (mostCurrent._starter._myservice==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 158;BA.debugLine="Init_elements";
_init_elements();
 };
 //BA.debugLineNum = 160;BA.debugLine="Log(\"oService Start!\")";
anywheresoftware.b4a.keywords.Common.Log("oService Start!");
 //BA.debugLineNum = 162;BA.debugLine="End Sub";
return "";
}
public static String  _sona() throws Exception{
 //BA.debugLineNum = 297;BA.debugLine="Sub Sona";
 //BA.debugLineNum = 299;BA.debugLine="End Sub";
return "";
}
public static String  _t_tick() throws Exception{
 //BA.debugLineNum = 164;BA.debugLine="Sub  t_Tick";
 //BA.debugLineNum = 166;BA.debugLine="If Starter.MYSERVICE=True Then";
if (mostCurrent._starter._myservice==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 167;BA.debugLine="CallSub(Me,\"NewPos\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,oservice.getObject(),"NewPos");
 }else {
 //BA.debugLineNum = 169;BA.debugLine="t.Enabled=False";
_t.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 171;BA.debugLine="End Sub";
return "";
}
public static String  _timdisplay_tick() throws Exception{
 //BA.debugLineNum = 270;BA.debugLine="Sub timDisplay_Tick";
 //BA.debugLineNum = 271;BA.debugLine="StopService(Me)";
anywheresoftware.b4a.keywords.Common.StopService(processBA,oservice.getObject());
 //BA.debugLineNum = 272;BA.debugLine="End Sub";
return "";
}

//import java.lang.Boolean;

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
		
		params.gravity = Gravity.TOP | Gravity.RIGHT;
		params.x = 0;
		params.y = 0;
		params.windowAnimations = android.R.style.Animation_Toast;

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


}
