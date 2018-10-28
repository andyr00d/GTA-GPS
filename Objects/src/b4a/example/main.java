package b4a.example;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
            main mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.Timer _timer1 = null;
public static uk.co.martinpearman.b4a.fusedlocationprovider.FusedLocationProviderWrapper _fusedlocationprovider1 = null;
public static anywheresoftware.b4a.gps.LocationWrapper _lastlocation = null;
public static uk.co.martinpearman.b4a.location.GeocoderWrapper _geocoder1 = null;
public static String _strlocation = "";
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.oservice _oservice = null;
public b4a.example.starter _starter = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 40;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 42;BA.debugLine="If FirstTime=True Then";
if (_firsttime==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 44;BA.debugLine="FusedLocationProvider1.Initialize(\"FusedLocation";
_fusedlocationprovider1.Initialize(processBA,"FusedLocationProvider1");
 //BA.debugLineNum = 46;BA.debugLine="Geocoder1.Initialize3(\"Geocoder1\", \"en\", \"AU\")";
_geocoder1.Initialize3(processBA,"Geocoder1","en","AU");
 //BA.debugLineNum = 48;BA.debugLine="Starter.NUM_STARS=3";
mostCurrent._starter._num_stars = (int) (3);
 //BA.debugLineNum = 49;BA.debugLine="Starter.ADDITIONAL=0";
mostCurrent._starter._additional = (int) (0);
 //BA.debugLineNum = 50;BA.debugLine="Starter.SOUND=False";
mostCurrent._starter._sound = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 239;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 432;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 433;BA.debugLine="FusedLocationProvider1.Disconnect";
_fusedlocationprovider1.Disconnect();
 //BA.debugLineNum = 435;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 422;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 424;BA.debugLine="FusedLocationProvider1.Connect";
_fusedlocationprovider1.Connect();
 //BA.debugLineNum = 426;BA.debugLine="Starter.MYSERVICE=False";
mostCurrent._starter._myservice = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 427;BA.debugLine="StopService(oService)";
anywheresoftware.b4a.keywords.Common.StopService(processBA,(Object)(mostCurrent._oservice.getObject()));
 //BA.debugLineNum = 430;BA.debugLine="End Sub";
return "";
}
public static String  _chkmove_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 289;BA.debugLine="Sub  chkMove_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 290;BA.debugLine="Starter.MOVE = Checked";
mostCurrent._starter._move = _checked;
 //BA.debugLineNum = 291;BA.debugLine="End Sub";
return "";
}
public static String  _chksound_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 286;BA.debugLine="Sub  chkSound_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 287;BA.debugLine="Starter.SOUND = Checked";
mostCurrent._starter._sound = _checked;
 //BA.debugLineNum = 288;BA.debugLine="End Sub";
return "";
}
public static String  _finish_click() throws Exception{
 //BA.debugLineNum = 282;BA.debugLine="Sub FINISH_click";
 //BA.debugLineNum = 283;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 284;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_connectionfailed(int _connectionresult1) throws Exception{
 //BA.debugLineNum = 303;BA.debugLine="Sub FusedLocationProvider1_ConnectionFailed(Connec";
 //BA.debugLineNum = 304;BA.debugLine="Log(\"FusedLocationProvider1_ConnectionFailed\")";
anywheresoftware.b4a.keywords.Common.Log("FusedLocationProvider1_ConnectionFailed");
 //BA.debugLineNum = 308;BA.debugLine="Select ConnectionResult1";
switch (BA.switchObjectToInt(_connectionresult1,_fusedlocationprovider1.ConnectionResult.NETWORK_ERROR)) {
case 0: {
 //BA.debugLineNum = 312;BA.debugLine="FusedLocationProvider1.Connect";
_fusedlocationprovider1.Connect();
 break; }
default: {
 break; }
}
;
 //BA.debugLineNum = 316;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_connectionsuccess() throws Exception{
anywheresoftware.b4a.gps.LocationWrapper _lastknownlocation = null;
uk.co.martinpearman.b4a.fusedlocationprovider.LocationRequest _locationrequest1 = null;
 //BA.debugLineNum = 318;BA.debugLine="Sub FusedLocationProvider1_ConnectionSuccess";
 //BA.debugLineNum = 319;BA.debugLine="Log(\"FusedLocationProvider1_ConnectionSuccess\")";
anywheresoftware.b4a.keywords.Common.Log("FusedLocationProvider1_ConnectionSuccess");
 //BA.debugLineNum = 321;BA.debugLine="Dim LastKnownLocation As Location";
_lastknownlocation = new anywheresoftware.b4a.gps.LocationWrapper();
 //BA.debugLineNum = 322;BA.debugLine="LastKnownLocation=FusedLocationProvider1.GetLastK";
_lastknownlocation = _fusedlocationprovider1.GetLastKnownLocation();
 //BA.debugLineNum = 324;BA.debugLine="If LastKnownLocation.IsInitialized Then";
if (_lastknownlocation.IsInitialized()) { 
 //BA.debugLineNum = 325;BA.debugLine="GeocodeLocation(LastKnownLocation)";
_geocodelocation(_lastknownlocation);
 }else {
 //BA.debugLineNum = 327;BA.debugLine="Dim LocationRequest1 As LocationRequest";
_locationrequest1 = new uk.co.martinpearman.b4a.fusedlocationprovider.LocationRequest();
 //BA.debugLineNum = 328;BA.debugLine="LocationRequest1.Initialize";
_locationrequest1.Initialize();
 //BA.debugLineNum = 329;BA.debugLine="LocationRequest1.SetInterval(1000)    '    1000";
_locationrequest1.SetInterval((long) (1000));
 //BA.debugLineNum = 330;BA.debugLine="LocationRequest1.SetPriority(LocationRequest1.Pr";
_locationrequest1.SetPriority(_locationrequest1.Priority.PRIORITY_NO_POWER);
 //BA.debugLineNum = 331;BA.debugLine="LocationRequest1.SetSmallestDisplacement(1)    '";
_locationrequest1.SetSmallestDisplacement((float) (1));
 //BA.debugLineNum = 332;BA.debugLine="FusedLocationProvider1.RequestLocationUpdates(Lo";
_fusedlocationprovider1.RequestLocationUpdates((com.google.android.gms.location.LocationRequest)(_locationrequest1.getObject()));
 };
 //BA.debugLineNum = 334;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_connectionsuspended(int _suspendedcause1) throws Exception{
 //BA.debugLineNum = 336;BA.debugLine="Sub FusedLocationProvider1_ConnectionSuspended(Sus";
 //BA.debugLineNum = 337;BA.debugLine="Log(\"FusedLocationProvider1_ConnectionSuspended\")";
anywheresoftware.b4a.keywords.Common.Log("FusedLocationProvider1_ConnectionSuspended");
 //BA.debugLineNum = 341;BA.debugLine="Select SuspendedCause1";
switch (BA.switchObjectToInt(_suspendedcause1,_fusedlocationprovider1.SuspendedCause.CAUSE_NETWORK_LOST,_fusedlocationprovider1.SuspendedCause.CAUSE_SERVICE_DISCONNECTED)) {
case 0: {
 break; }
case 1: {
 break; }
}
;
 //BA.debugLineNum = 347;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_locationchanged(anywheresoftware.b4a.gps.LocationWrapper _location1) throws Exception{
 //BA.debugLineNum = 349;BA.debugLine="Sub FusedLocationProvider1_LocationChanged(Locatio";
 //BA.debugLineNum = 350;BA.debugLine="Log(\"FusedLocationProvider1_LocationChanged\")";
anywheresoftware.b4a.keywords.Common.Log("FusedLocationProvider1_LocationChanged");
 //BA.debugLineNum = 352;BA.debugLine="FusedLocationProvider1.RemoveLocationUpdates";
_fusedlocationprovider1.RemoveLocationUpdates();
 //BA.debugLineNum = 353;BA.debugLine="GeocodeLocation(Location1)";
_geocodelocation(_location1);
 //BA.debugLineNum = 354;BA.debugLine="End Sub";
return "";
}
public static String  _geocodelocation(anywheresoftware.b4a.gps.LocationWrapper _location1) throws Exception{
int _maxresults = 0;
 //BA.debugLineNum = 356;BA.debugLine="Sub GeocodeLocation(Location1 As Location)";
 //BA.debugLineNum = 357;BA.debugLine="Log(\"GeocodeLocation: \"&Location1.Latitude&\", \"&L";
anywheresoftware.b4a.keywords.Common.Log("GeocodeLocation: "+BA.NumberToString(_location1.getLatitude())+", "+BA.NumberToString(_location1.getLongitude()));
 //BA.debugLineNum = 361;BA.debugLine="Dim MaxResults As Int";
_maxresults = 0;
 //BA.debugLineNum = 362;BA.debugLine="MaxResults=2";
_maxresults = (int) (2);
 //BA.debugLineNum = 364;BA.debugLine="Geocoder1.GetFromLocation(Location1.Latitude, Loc";
_geocoder1.GetFromLocation(processBA,_location1.getLatitude(),_location1.getLongitude(),_maxresults,anywheresoftware.b4a.keywords.Common.Null);
 //BA.debugLineNum = 367;BA.debugLine="End Sub";
return "";
}
public static String  _geocoder1_geocodedone(uk.co.martinpearman.b4a.location.AddressWrapper[] _results,Object _tag) throws Exception{
uk.co.martinpearman.b4a.location.AddressWrapper _address1 = null;
int _i = 0;
int _j = 0;
 //BA.debugLineNum = 393;BA.debugLine="Sub Geocoder1_GeocodeDone(Results() As Address, Ta";
 //BA.debugLineNum = 394;BA.debugLine="If Results.Length>0 Then";
if (_results.length>0) { 
 //BA.debugLineNum = 395;BA.debugLine="Dim Address1 As Address";
_address1 = new uk.co.martinpearman.b4a.location.AddressWrapper();
 //BA.debugLineNum = 396;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 397;BA.debugLine="For i=0 To Results.Length-1";
{
final int step4 = 1;
final int limit4 = (int) (_results.length-1);
_i = (int) (0) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
 //BA.debugLineNum = 398;BA.debugLine="Log(\"**********\")";
anywheresoftware.b4a.keywords.Common.Log("**********");
 //BA.debugLineNum = 399;BA.debugLine="Log(\"Result(\"&i&\")\")";
anywheresoftware.b4a.keywords.Common.Log("Result("+BA.NumberToString(_i)+")");
 //BA.debugLineNum = 400;BA.debugLine="Log(\"AddressLines:\")";
anywheresoftware.b4a.keywords.Common.Log("AddressLines:");
 //BA.debugLineNum = 401;BA.debugLine="Address1=Results(i)";
_address1 = _results[_i];
 //BA.debugLineNum = 402;BA.debugLine="For j=0 To Address1.AddressLines.Size-1";
{
final int step9 = 1;
final int limit9 = (int) (_address1.getAddressLines().getSize()-1);
_j = (int) (0) ;
for (;_j <= limit9 ;_j = _j + step9 ) {
 //BA.debugLineNum = 403;BA.debugLine="Log(Address1.AddressLines.Get(j))";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(_address1.getAddressLines().Get(_j)));
 }
};
 //BA.debugLineNum = 406;BA.debugLine="Log(\"CountryCode: \"&Address1.CountryName)";
anywheresoftware.b4a.keywords.Common.Log("CountryCode: "+_address1.getCountryName());
 //BA.debugLineNum = 407;BA.debugLine="Log(\"Locality: \"&Address1.Locality)";
anywheresoftware.b4a.keywords.Common.Log("Locality: "+_address1.getLocality());
 }
};
 //BA.debugLineNum = 411;BA.debugLine="strLocation = Address1.Locality";
_strlocation = _address1.getLocality();
 //BA.debugLineNum = 412;BA.debugLine="Starter.MYSERVICE=True";
mostCurrent._starter._myservice = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 413;BA.debugLine="StartService(oService)";
anywheresoftware.b4a.keywords.Common.StartService(processBA,(Object)(mostCurrent._oservice.getObject()));
 //BA.debugLineNum = 415;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else {
 //BA.debugLineNum = 417;BA.debugLine="Log(\"**********\")";
anywheresoftware.b4a.keywords.Common.Log("**********");
 //BA.debugLineNum = 418;BA.debugLine="Log(\"No results found\")";
anywheresoftware.b4a.keywords.Common.Log("No results found");
 };
 //BA.debugLineNum = 420;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 32;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return "";
}
public static String  _ivsnow_click() throws Exception{
anywheresoftware.b4a.objects.ImageViewWrapper _i = null;
int _k = 0;
 //BA.debugLineNum = 241;BA.debugLine="Sub  IVSnow_Click";
 //BA.debugLineNum = 243;BA.debugLine="Dim I As ImageView = Sender";
_i = new anywheresoftware.b4a.objects.ImageViewWrapper();
_i.setObject((android.widget.ImageView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 244;BA.debugLine="Dim k As Int =I.Tag";
_k = (int)(BA.ObjectToNumber(_i.getTag()));
 //BA.debugLineNum = 245;BA.debugLine="Starter.NUM_STARS=k+1";
mostCurrent._starter._num_stars = (int) (_k+1);
 //BA.debugLineNum = 247;BA.debugLine="End Sub";
return "";
}
public static void  _latlontoplace(double _lat,double _lon) throws Exception{
ResumableSub_LatLonToPlace rsub = new ResumableSub_LatLonToPlace(null,_lat,_lon);
rsub.resume(processBA, null);
}
public static class ResumableSub_LatLonToPlace extends BA.ResumableSub {
public ResumableSub_LatLonToPlace(b4a.example.main parent,double _lat,double _lon) {
this.parent = parent;
this._lat = _lat;
this._lon = _lon;
}
b4a.example.main parent;
double _lat;
double _lon;
String _api_key = "";
String _res = "";
anywheresoftware.b4a.samples.httputils2.httpjob _getaddressjob = null;
anywheresoftware.b4a.objects.collections.JSONParser _jp = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
anywheresoftware.b4a.objects.collections.List _results = null;
anywheresoftware.b4a.objects.collections.Map _first = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 370;BA.debugLine="Dim API_KEY As String = \"AIzaSyAvz6aSaS-lG9kEbHHZ";
_api_key = "AIzaSyAvz6aSaS-lG9kEbHHZOX_BEGgGTx7RZjM";
 //BA.debugLineNum = 371;BA.debugLine="Dim res As String";
_res = "";
 //BA.debugLineNum = 372;BA.debugLine="Dim GetAddressJob As HttpJob";
_getaddressjob = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 373;BA.debugLine="GetAddressJob.Initialize(\"GetAddress\", Me)";
_getaddressjob._initialize(processBA,"GetAddress",main.getObject());
 //BA.debugLineNum = 374;BA.debugLine="GetAddressJob.Download2(\"https://maps.googleapis.";
_getaddressjob._download2("https://maps.googleapis.com/maps/api/geocode/json",new String[]{"latlng",BA.NumberToString(_lat)+","+BA.NumberToString(_lon),"key",_api_key});
 //BA.debugLineNum = 375;BA.debugLine="Wait For (GetAddressJob) JobDone(GetAddressJob As";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_getaddressjob));
this.state = 13;
return;
case 13:
//C
this.state = 1;
_getaddressjob = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
 //BA.debugLineNum = 376;BA.debugLine="If GetAddressJob.Success Then";
if (true) break;

case 1:
//if
this.state = 12;
if (_getaddressjob._success) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 377;BA.debugLine="Dim jp As JSONParser";
_jp = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 378;BA.debugLine="jp.Initialize(GetAddressJob.GetString)";
_jp.Initialize(_getaddressjob._getstring());
 //BA.debugLineNum = 379;BA.debugLine="Dim m As Map = jp.NextObject";
_m = new anywheresoftware.b4a.objects.collections.Map();
_m = _jp.NextObject();
 //BA.debugLineNum = 380;BA.debugLine="If m.Get(\"status\") = \"OK\" Then";
if (true) break;

case 4:
//if
this.state = 11;
if ((_m.Get((Object)("status"))).equals((Object)("OK"))) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 381;BA.debugLine="Dim results As List = m.Get(\"results\")";
_results = new anywheresoftware.b4a.objects.collections.List();
_results.setObject((java.util.List)(_m.Get((Object)("results"))));
 //BA.debugLineNum = 382;BA.debugLine="If results.Size > 0 Then";
if (true) break;

case 7:
//if
this.state = 10;
if (_results.getSize()>0) { 
this.state = 9;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 383;BA.debugLine="Dim first As Map = results.Get(0)";
_first = new anywheresoftware.b4a.objects.collections.Map();
_first.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_results.Get((int) (0))));
 //BA.debugLineNum = 384;BA.debugLine="res = first.Get(\"formatted_address\")";
_res = BA.ObjectToString(_first.Get((Object)("formatted_address")));
 //BA.debugLineNum = 385;BA.debugLine="Log(res)";
anywheresoftware.b4a.keywords.Common.Log(_res);
 if (true) break;

case 10:
//C
this.state = 11;
;
 if (true) break;

case 11:
//C
this.state = 12;
;
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 390;BA.debugLine="GetAddressJob.Release";
_getaddressjob._release();
 //BA.debugLineNum = 391;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _getaddressjob) throws Exception{
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        anywheresoftware.b4a.samples.httputils2.httputils2service._process_globals();
main._process_globals();
oservice._process_globals();
starter._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 18;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 22;BA.debugLine="Dim Timer1 As Timer";
_timer1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 24;BA.debugLine="Dim FusedLocationProvider1 As FusedLocationProvid";
_fusedlocationprovider1 = new uk.co.martinpearman.b4a.fusedlocationprovider.FusedLocationProviderWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private LastLocation As Location";
_lastlocation = new anywheresoftware.b4a.gps.LocationWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Dim Geocoder1 As Geocoder";
_geocoder1 = new uk.co.martinpearman.b4a.location.GeocoderWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Dim strLocation As String";
_strlocation = "";
 //BA.debugLineNum = 30;BA.debugLine="End Sub";
return "";
}
public static String  _radiobutton0_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 293;BA.debugLine="Sub  Radiobutton0_CheckedChange(Checked As Boolean";
 //BA.debugLineNum = 294;BA.debugLine="If Checked=True Then Starter.additional=0";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
mostCurrent._starter._additional = (int) (0);};
 //BA.debugLineNum = 295;BA.debugLine="End Sub";
return "";
}
public static String  _radiobutton1_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 296;BA.debugLine="Sub  Radiobutton1_CheckedChange(Checked As Boolean";
 //BA.debugLineNum = 297;BA.debugLine="If Checked=True Then Starter.additional=1";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
mostCurrent._starter._additional = (int) (1);};
 //BA.debugLineNum = 298;BA.debugLine="End Sub";
return "";
}
public static String  _radiobutton2_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 299;BA.debugLine="Sub  Radiobutton2_CheckedChange(Checked As Boolean";
 //BA.debugLineNum = 300;BA.debugLine="If Checked=True Then Starter.additional=2";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
mostCurrent._starter._additional = (int) (2);};
 //BA.debugLineNum = 301;BA.debugLine="End Sub";
return "";
}
public static String  _start_click() throws Exception{
 //BA.debugLineNum = 276;BA.debugLine="Sub START_click";
 //BA.debugLineNum = 277;BA.debugLine="Starter.MYSERVICE=True";
mostCurrent._starter._myservice = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 278;BA.debugLine="StartService(oService)";
anywheresoftware.b4a.keywords.Common.StartService(processBA,(Object)(mostCurrent._oservice.getObject()));
 //BA.debugLineNum = 279;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 280;BA.debugLine="End Sub";
return "";
}
public static String  _timer1_tick() throws Exception{
 //BA.debugLineNum = 250;BA.debugLine="Sub Timer1_Tick";
 //BA.debugLineNum = 251;BA.debugLine="Log(\"stopping oService\")";
anywheresoftware.b4a.keywords.Common.Log("stopping oService");
 //BA.debugLineNum = 252;BA.debugLine="StopService(oService)";
anywheresoftware.b4a.keywords.Common.StopService(processBA,(Object)(mostCurrent._oservice.getObject()));
 //BA.debugLineNum = 274;BA.debugLine="End Sub";
return "";
}
}
