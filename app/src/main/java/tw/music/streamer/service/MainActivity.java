public class MainActivity extends AppCompatActivity {
	
	private void initializeLogic() {
		listview1.setAdapter(new Listview1Adapter(logcat));
		webview1.loadUrl("abc");
		webview1.setVisibility(View.GONE);
		zz = new ZryteZeneAdaptor(this);
		
		zz.requestAction("request-media");
		
	}
	
	private ZryteZeneAdaptor zz;
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
			if (requestCode == 1) {
					if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
							_startZZ();
					} else {
							showMessage("Please allow the notification permission");
					}
			}
	}
	
	private BroadcastReceiver listenerReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
					if (intent != null && intent.getAction() != null) {
							if (intent.getAction().equals(ZryteZenePlay.ACTION_UPDATE)) {
									String m = intent.getStringExtra("update");
									_log(m);
					                _handleEvents(m, intent);
							}
					}
			}
	};
	
	@Override
	public void onResume() {
		super.onResume();
		super.onResume();
		IntentFilter filter = new IntentFilter(ZryteZenePlay.ACTION_UPDATE);
		registerReceiver(listenerReceiver, filter);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		unregisterReceiver(listenerReceiver);
	}
	public void _startZZ() {
		Intent siop = new Intent(getApplicationContext(), ZryteZenePlay.class);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				startForegroundService(siop);
		} else {
				startService(siop);
		}
	}
	
	
	public void _log(final String _a) {
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("text", _a);
			logcat.add((int)0, _item);
		}
		
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
	}
	
	
	public void _handleEvents(final String _a, final Intent _b) {
		if (_a.equals("on-prepared")) {
			int duration = _b.getIntExtra("data",0);
			int minutes = (duration / 1000) / 60;
			int seconds = (duration / 1000) % 60;
			
			seekbar1.setProgress(0);
			seekbar1.setMax(duration/1000);
			
			textview4.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
			_log("music duration updated");
		}
		else {
			if (_a.equals("on-tick")) {
				int duration = _b.getIntExtra("data",0);
				int minutes = (duration / 1000) / 60;
				int seconds = (duration / 1000) % 60;
				
				seekbar1.setProgress(duration/1000);
				
				textview3.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
			}
			else {
				if (_a.equals("on-reqmedia")) {
					int status = _b.getIntExtra("status",0);
					
					if (status == 2 || status == 1) {
							int d = _b.getIntExtra("duration",0);
							int m = (d / 1000) / 60;
							int s = (d / 1000) % 60;
							seekbar1.setMax(d/1000);
							textview4.setText(String.valueOf(m) + ":" + String.valueOf(s));
							int cd = _b.getIntExtra("currentDuration",0);
							int cm = (cd / 1000) / 60;
							int cs = (cd / 1000) % 60;
							seekbar1.setProgress(cd/1000);
							textview3.setText(String.valueOf(cm) + ":" + String.valueOf(cs));
							_log("Previous data loaded");
					} else {
							_log("No data to load: " + status);
					}
					
				}
				else {
					
				}
			}
		}
	}
	
	public class Listview1Adapter extends BaseAdapter {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = getLayoutInflater();
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.logcat_view, null);
			}
			
			final TextView textview1 = _view.findViewById(R.id.textview1);
			
			textview1.setText(logcat.get((int)_position).get("text").toString());
			
			return _view;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}
