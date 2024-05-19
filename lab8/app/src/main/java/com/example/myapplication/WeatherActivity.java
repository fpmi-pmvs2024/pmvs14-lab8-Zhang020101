package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherActivity extends AppCompatActivity {
    private TextView weatherTextView;
    private LocationManager locationManager;
    private TextView timeText;
    private Handler timeHandler;
    private Runnable timeRunnable;
    private static final String OPEN_WEATHER_MAP_API_KEY = "b9d0b05cd6ecf600d360ee254cc909bd"; // 替换为您的OpenWeatherMap API密钥

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        weatherTextView = findViewById(R.id.welcomeText);
        timeText = findViewById(R.id.timeText);
        timeHandler = new Handler();
        updateTime();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        }
    }
    private void updateTime() {
        timeRunnable = new Runnable() {
            @Override
            public void run() {
                // 更新TextView的文本为当前时间
                String currentTime = new SimpleDateFormat("hh:mm:ss a", Locale.US).format(new Date());
                timeText.setText(currentTime);
                // 每秒钟更新一次时间
                timeHandler.postDelayed(this, 1000);
            }
        };
        // 立即执行Runnable
        timeHandler.post(timeRunnable);
    }
    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            getWeatherForLocation(location.getLatitude(), location.getLongitude());
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {}

        public void onProviderEnabled(String provider) {}

        public void onProviderDisabled(String provider) {}
    };

    private void getWeatherForLocation(double latitude, double longitude) {
        Thread thread = new Thread(() -> {
            try {
                URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&units=metric&appid=" + OPEN_WEATHER_MAP_API_KEY);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder json = new StringBuilder(1024);
                String tmp;
                while ((tmp = reader.readLine()) != null)
                    json.append(tmp).append("\n");
                reader.close();

                JSONObject data = new JSONObject(json.toString());
                if (data.getInt("cod") != 200) {
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Не удалось получить информацию о погоде", Toast.LENGTH_LONG).show());
                    return;
                }

                String weatherInfo = formatWeatherData(data);
                runOnUiThread(() -> weatherTextView.setText(weatherInfo));
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Ненормальность в получении информации о погоде：" + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        });
        thread.start();
    }

    private String formatWeatherData(JSONObject data) throws Exception {
        JSONObject main = data.getJSONObject("main");
        JSONObject weather = data.getJSONArray("weather").getJSONObject(0);
        return "Город: " + data.getString("name") + "\n" +
                "температура: " + main.getDouble("temp") + "°C\n" +
                "погода: " + weather.getString("description") + "\n" +
                "влажность: " + main.getString("humidity") + "%\n" +
                "давление воздуха: " + main.getString("pressure") + " hPa";
    }
}
