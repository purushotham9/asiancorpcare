package nnk.com.asiancorpcare;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class TripExpenses extends AppCompatActivity implements View.OnClickListener {

    EditText empId,startKm,endKm,fuelLiters,expenses;
    ImageView startImage,endImage;
    Button save_expenses;
    private static final int SELECT_PHOTO = 1;
    private static final int CAPTURE_PHOTO = 2;

    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();
    private boolean hasImageChanged = false;
    SQLiteDatabase dbHelper;

    Context context = this;
    Bitmap thumbnail;
    private SQLiteDatabase sqLiteDatabase;
    Button text_start,text_end;
    private static final String IMAGE_DIRECTORY = "/demonuts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_expenses);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        text_start = (Button)findViewById(R.id.start_bt);
        text_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        text_end = (Button)findViewById(R.id.end_bt);
        text_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        empId =(EditText)findViewById(R.id.emp_id);
        startKm =(EditText)findViewById(R.id.start_km);
        endKm =(EditText)findViewById(R.id.end_km);
        fuelLiters =(EditText)findViewById(R.id.fuel_liters);
        expenses =(EditText)findViewById(R.id.expenses);

        startImage =(ImageView)findViewById(R.id.start_image);
        endImage =(ImageView)findViewById(R.id.end_image);
        save_expenses =(Button)findViewById(R.id.save_expenses);
        save_expenses.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(TripExpenses.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            startImage.setEnabled(false);
            ActivityCompat.requestPermissions(TripExpenses.this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        } else {
            startImage.setEnabled(true);
        }
        SaveLoginDatabase saveLoginDatabase = new SaveLoginDatabase(this);
        sqLiteDatabase = saveLoginDatabase.getWritableDatabase();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_expenses:
                String idEmp =empId.getText().toString();
                int empValue=Integer.parseInt(idEmp);
                String startkm =startKm.getText().toString();
                int startValue = Integer.parseInt(startkm);
                String endkm =endKm.getText().toString();
                int endValue = Integer.parseInt(endkm);
                String fuel =fuelLiters.getText().toString();
                int fuelValue = Integer.parseInt(fuel);
                String expence =expenses.getText().toString();
                int expences = Integer.parseInt(expence);
//                String validCredentials = new SaveLoginDatabase(getApplicationContext()).getExpenses(dbHelper,empValue, startValue,addToDb(),endValue,addEndImage(),fuelValue,expences);
//                if (validCredentials.equalsIgnoreCase("nullnull")) {
//                    new SaveLoginDatabase(getApplicationContext()).addExpenses(dbHelper,empValue, startValue,addToDb(),endValue,addEndImage(),fuelValue,expences);
//                    Toast.makeText(TripExpenses.this, "user data saved successfully ", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Toast.makeText(TripExpenses.this, "user login exists ", Toast.LENGTH_SHORT).show();
//                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                startImage.setEnabled(true);
            }
        }
    }


    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, SELECT_PHOTO);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_PHOTO);
    }
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery", "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == SELECT_PHOTO) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    Toast.makeText(TripExpenses.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    startImage.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(TripExpenses.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAPTURE_PHOTO) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            startImage.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(TripExpenses.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                f = new File(wallpaperDirectory, Calendar.getInstance()
                        .getTimeInMillis() + ".png");
            }
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/png"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


//    public String addToDb(){
//        startImage.setDrawingCacheEnabled(true);
//        startImage.buildDrawingCache();
//        Bitmap bitmap = startImage.getDrawingCache();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] data = baos.toByteArray();
//        Toast.makeText(this, "Image saved to DB successfully", Toast.LENGTH_SHORT).show();
//        return String.valueOf(data);
//    }
//    public String addEndImage(){
//
//        endImage.setDrawingCacheEnabled(true);
//        endImage.buildDrawingCache();
//        Bitmap bitmap = endImage.getDrawingCache();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] data = baos.toByteArray();
//        Toast.makeText(this, "Image saved to DB successfully", Toast.LENGTH_SHORT).show();
//
//        return String.valueOf(data);
//    }
}


