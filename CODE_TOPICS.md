```java
// Topic 1: Authentication — SignupActivity.java
package com.example.madjava;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignupActivity extends AppCompatActivity {

    Button loginBtn, registerBtn;
    EditText user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginBtn = findViewById(R.id.loginbtn);
        registerBtn = findViewById(R.id.registerbtn);
        user = findViewById(R.id.userInput);
        pass = findViewById(R.id.passInput);

        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u1 = user.getText().toString();
                String p1 = pass.getText().toString();
                if (u1.isEmpty() || p1.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please fil the above fields", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("username:", u1);
                    edit.putString("password:", p1);
                    edit.apply();
                    Toast.makeText(SignupActivity.this, "User has been registered", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                }
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }
}
```

```java
// Topic 1: Authentication — LoginActivity.java
package com.example.madjava;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    Button loginBtn, registerBtn;
    EditText user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginBtn = findViewById(R.id.loginbtn);
        registerBtn = findViewById(R.id.registerbtn);
        user = findViewById(R.id.userInput);
        pass = findViewById(R.id.passInput);

        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        Boolean isLoggedIn;
        isLoggedIn = sp.getBoolean("IsLoggedIn", false);
        if (isLoggedIn) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u1 = user.getText().toString();
                String p1 = pass.getText().toString();
                String username = sp.getString("username:", "");
                String password = sp.getString("password:", "");
                if (u1.equals(username) && p1.equals(password)) {
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putBoolean("IsLoggedIn", true);
                    ed.apply();
                    Toast.makeText(LoginActivity.this, "login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid User name or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
```

```java
// Topic 2: Theme toggle — MainActivity (theme selection + switch listener)
package com.example.madjava;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {
    Button btn1, btn2, btn3;
    ConstraintLayout layout;
    Switch sw;
    Boolean isDarkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        isDarkMode = sp.getBoolean("isDarkMode", false);
        if(isDarkMode){
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.main);
        if(isDarkMode){
            layout.setBackgroundColor(Color.parseColor("black"));
        }

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        sw = findViewById(R.id.switch1);
        sw.setChecked(isDarkMode);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setBackgroundColor(Color.parseColor("Green"));
            }
        });

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                isDarkMode = isChecked;
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("isDarkMode", isChecked);
                edit.apply();
                recreate();
            }
        });
    }
}
```

```java
// Topic 3: Activity lifecycle toasts — MainActivity methods
@Override
protected void onStart() {
    super.onStart();
    Toast.makeText(this, "this is onStart Function", Toast.LENGTH_SHORT).show();
}

@Override
protected void onResume() {
    super.onResume();
    Toast.makeText(this, "this is onResume Function", Toast.LENGTH_SHORT).show();
}

@Override
protected void onPause() {
    super.onPause();
    Toast.makeText(this, "this is onPause Function", Toast.LENGTH_SHORT).show();
}

@Override
protected void onStop() {
    Toast.makeText(this, "this is onStop Function", Toast.LENGTH_SHORT).show();
    super.onStop();
}

@Override
protected void onDestroy() {
    Toast.makeText(this, "this is onDestroy Function", Toast.LENGTH_SHORT).show();
    super.onDestroy();
}
```

```java
// Topic 4: Explicit intents — Navigation between activities
// MainActivity → MainActivity2
private void startActivity() {
    Intent myintent = new Intent(this, MainActivity2.class);
    startActivity(myintent);
}

// MainActivity2 list click → ActivityLifecycle / IntentLifecycle / Notification
contentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        switch (position)
        {
            case 0:
                Intent myintent1 = new Intent(MainActivity2.this, ActivityLifecycle.class);
                startActivity(myintent1);
                break;
            case 1:
                Intent myintent2 = new Intent(MainActivity2.this, IntentLifecycle.class);
                startActivity(myintent2);
                break;
            case 2:
                Send_Notification();
                break;
        }
    }
});

// Logout (MainActivity) → LoginActivity
btn3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("IsLoggedIn", false);
        edit.apply();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
});
```

```java
// Topic 5: Implicit intents — IntentLifecycle.java (full)
package com.example.madjava;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class IntentLifecycle extends AppCompatActivity {
    Button btn1, btn2, btn3, btn4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_lifecycle);
        btn1 = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: 0331 1234567"));
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String thePlace = "University of Gujrat, Gujrat";
                Intent intent =new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q=(" + thePlace + ")"));
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto: 0331 1234567"));
                intent.putExtra("sms_body",
                        "Hello how are you?");
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY,"Assasin's Creed");
                startActivity(intent);
            }
        });
    }
}
```

```java
// Topic 6: Notifications — MainActivity2 snippets
// Channel creation (Android O+)
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    ch = new NotificationChannel("ch_1000","chat_channel", NotificationManager.IMPORTANCE_DEFAULT);
}

// Posting a notification with PendingIntent
Intent intent = new Intent(this, MainActivity.class);
intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

PendingIntent pendingIntent = PendingIntent.getActivity(
        this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

NotificationManager nm = getSystemService(NotificationManager.class);
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    nm.createNotificationChannel(ch);
}
NotificationCompat.Builder NB = new NotificationCompat.Builder(this, "ch_1000")
        .setContentTitle("BS_7B")
        .setSmallIcon(R.drawable.tree)
        .setContentText("this is your new message")
        .setContentIntent(pendingIntent)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
nm.notify(1000,NB.build());
```

```xml
<!-- Topic 7: Layouts — activity_login.xml -->
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".LoginActivity">

    <TextView
        android:id="@+id/txt1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello Welcome to the login Activity"
        android:textColor="#ff00"
        android:textSize="18dp"
        android:textStyle="bold"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/usertxt"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="UserName"
        android:textSize="24dp"
        android:textStyle="bold"
        android:padding="20dp"
        app:layout_constraintTop_toBottomOf="@id/txt1"
        app:layout_constraintStart_toStartOf="parent"></TextView>
    <TextView
        android:id="@+id/passtxt"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="20dp"
        android:text="Password"
        android:textSize="24dp"
        android:textStyle="bold"
        tools:layout_editor_absoluteX="74dp"
        tools:layout_editor_absoluteY="28dp"
        app:layout_constraintTop_toBottomOf="@id/usertxt"
        app:layout_constraintStart_toStartOf="parent"></TextView>
    <EditText
        android:id="@+id/userInput"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="20dp"
        android:hint="Enter user name here"
        app:layout_constraintTop_toBottomOf="@id/txt1"
        app:layout_constraintStart_toEndOf="@+id/usertxt">

    </EditText>
    <EditText
        android:id="@+id/passInput"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="20dp"
        android:hint=".................."
        app:layout_constraintTop_toBottomOf="@id/userInput"
        app:layout_constraintStart_toEndOf="@+id/passtxt">

    </EditText>

    <Button
        android:id="@+id/loginbtn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Login"
        app:layout_constraintTop_toBottomOf="@+id/passInput"
        app:layout_constraintLeft_toLeftOf="parent"/>

    <Button
        android:id="@+id/registerbtn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Register"
        app:layout_constraintTop_toBottomOf="@id/passInput"
        app:layout_constraintStart_toEndOf="@+id/loginbtn"/>


</androidx.constraintlayout.widget.ConstraintLayout>
```

```xml
<!-- Topic 7: Layouts — activity_signup.xml -->
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".SignupActivity">

    <TextView
        android:id="@+id/txt1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello Welcome to the signup Activity"
        android:textColor="#ff00"
        android:textSize="18dp"
        android:textStyle="bold"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/usertxt"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="20dp"
        android:text="UserName"
        android:textSize="24dp"
        android:textStyle="bold"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/txt1"></TextView>

    <TextView
        android:id="@+id/passtxt"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="20dp"
        android:text="Password"
        android:textSize="24dp"
        android:textStyle="bold"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/usertxt"
        tools:layout_editor_absoluteX="74dp"
        tools:layout_editor_absoluteY="28dp"></TextView>

    <EditText
        android:id="@+id/userInput"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:hint="Enter user name here"
        android:padding="20dp"
        app:layout_constraintStart_toEndOf="@+id/usertxt"
        app:layout_constraintTop_toBottomOf="@id/txt1">

    </EditText>

    <EditText
        android:id="@+id/passInput"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:hint=".................."
        android:padding="20dp"
        app:layout_constraintStart_toEndOf="@+id/passtxt"
        app:layout_constraintTop_toBottomOf="@id/userInput">

    </EditText>

    <Button
        android:id="@+id/loginbtn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Login"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/passInput" />

    <Button
        android:id="@+id/registerbtn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Register"
        app:layout_constraintStart_toEndOf="@+id/loginbtn"
        app:layout_constraintTop_toBottomOf="@id/passInput" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

```xml
<!-- Topic 7: Layouts — activity_main.xml -->
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    tools:context=".MainActivity">

    <Button
        android:id="@+id/btn1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Change color"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.501"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.348" />

    <Button
        android:id="@+id/btn2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Topics"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.498"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.45" />

    <Button
        android:id="@+id/btn3"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Logout"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.498"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.544" />

    <TextView
        android:id="@+id/home_title"
        android:layout_width="69dp"
        android:layout_height="23dp"
        android:text="068-Sami"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        tools:layout_editor_absoluteY="29dp" />

    <Switch
        android:id="@+id/switch1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="196dp"
        android:text="Switch mode"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.501"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />


</androidx.constraintlayout.widget.ConstraintLayout>
```

```xml
<!-- Topic 7: Layouts — activity_main2.xml -->
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    tools:context=".MainActivity2">
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">
        <ListView
            android:id="@+id/list1"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
        </ListView>
    </LinearLayout>

</androidx.constraintlayout.widget.ConstraintLayout>
```

```xml
<!-- Topic 7: Layouts — activity_lifecycle.xml -->
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ActivityLifecycle">

    <Spinner
        android:id="@+id/sp1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content">

    </Spinner>


</androidx.constraintlayout.widget.ConstraintLayout>
```

```xml
<!-- Topic 7: Layouts — activity_intent_lifecycle.xml -->
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    tools:context=".IntentLifecycle">
    <TextView
        android:id="@+id/textView2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Welcome to Intent Activity"
        tools:layout_editor_absoluteX="141dp"
        tools:layout_editor_absoluteY="59dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        />

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="call Button"
        tools:layout_editor_absoluteX="141dp"
        tools:layout_editor_absoluteY="140dp"
        app:layout_constraintTop_toBottomOf="@id/textView2"
        app:layout_constraintStart_toStartOf="parent"/>

    <Button
        android:id="@+id/button2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Geo Button"
        tools:layout_editor_absoluteX="139dp"
        tools:layout_editor_absoluteY="248dp"
        app:layout_constraintTop_toBottomOf="@id/button"
        app:layout_constraintStart_toStartOf="parent"/>

    <Button
        android:id="@+id/button3"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Sms Button"
        tools:layout_editor_absoluteX="147dp"
        tools:layout_editor_absoluteY="356dp"
        app:layout_constraintTop_toBottomOf="@id/button2"
        app:layout_constraintStart_toStartOf="parent"/>

    <Button
        android:id="@+id/button4"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Web SearchButton"
        tools:layout_editor_absoluteX="151dp"
        tools:layout_editor_absoluteY="448dp"
        app:layout_constraintTop_toBottomOf="@id/button3"
        app:layout_constraintStart_toStartOf="parent"/>


</androidx.constraintlayout.widget.ConstraintLayout>
```

```xml
<!-- Topic 8: Manifest and permissions — AndroidManifest.xml -->
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/s_app_icon"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/s_app_icon"
        android:supportsRtl="true"
        android:theme="@style/Theme.MADJava">
        <activity
            android:name=".SignupActivity"
            android:exported="false" />
        <activity
            android:name=".LoginActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity
            android:name=".IntentLifecycle"
            android:exported="false" />
        <activity
            android:name=".ActivityLifecycle"
            android:exported="false" />
        <activity
            android:name=".MainActivity2"
            android:exported="true" />
        <activity
            android:name=".MainActivity"
            android:exported="false" />

    </application>

</manifest>
```

```xml
<!-- Topic 9: Resources — values/themes.xml -->
<resources xmlns:tools="http://schemas.android.com/tools">
    <style name="Base.Theme.MADJava" parent="Theme.Material3.DayNight.NoActionBar">
    </style>
    <style name="Theme.MADJava" parent="Base.Theme.MADJava" />
</resources>
```

```xml
<!-- Topic 9: Resources — values/modes.xml -->
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="LightTheme" parent="Theme.AppCompat.Light.NoActionBar">
        <item name="colorPrimary">#FF0000</item>
        <item name="android:windowBackground">#FFFFFF</item>
        <item name="android:textColor">#000000</item>
    </style>
    <style name="DarkTheme" parent="Theme.AppCompat.DayNight.NoActionBar">
        <item name="colorPrimary">#00255D</item>
        <item name="android:windowBackground">#000000</item>
        <item name="android:textColor">#FFFFFF</item>
    </style>
</resources>
```

```xml
<!-- Topic 9: Resources — values/strings.xml -->
<resources>
    <string name="app_name">MAD 068</string>
</resources>
```

```xml
<!-- Topic 9: Resources — values/colors.xml -->
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="black">#FF000000</color>
    <color name="white">#FFFFFFFF</color>
</resources>
```

```java
// Topic 10: SharedPreferences usage — key reads/writes (aggregated)
// Write on Signup
SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
SharedPreferences.Editor edit = sp.edit();
edit.putString("username:", u1);
edit.putString("password:", p1);
edit.apply();

// Read on Login
String username = sp.getString("username:", "");
String password = sp.getString("password:", "");

// Session flag
sp.edit().putBoolean("IsLoggedIn", true).apply();
sp.edit().putBoolean("IsLoggedIn", false).apply();

// Theme flag
sp.edit().putBoolean("isDarkMode", isChecked).apply();
boolean isDarkMode = sp.getBoolean("isDarkMode", false);
```
