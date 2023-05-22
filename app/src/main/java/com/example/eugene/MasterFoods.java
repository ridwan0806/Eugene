package com.example.eugene;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eugene.Common.MoneyTextWatcher;
import com.example.eugene.Model.Foods;
import com.example.eugene.ViewHolder.MasterFoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

public class MasterFoods extends AppCompatActivity {
    RecyclerView recyclerViewMasterFoods;
    RecyclerView.LayoutManager layoutManager;
    ProgressBar progressBar;

    TextView btnAddFood;

    FirebaseDatabase database;
    DatabaseReference foodList;
    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseRecyclerAdapter<Foods, MasterFoodViewHolder>adapter;

    ProgressDialog progressDialog;

    EditText addFoodName,addFoodPrice;
    String price = "";
//    double price;
    Button selectImg,uploadImg;
    ImageView cekFoto;
    TextView cekFotoKet;
    Spinner spinner;
    private ArrayList<String> categoryName;
    int categoryId = 0;

    Foods newFood;
    Uri saveUri;
    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_foods);

        initView();

        progressDialog = new ProgressDialog(MasterFoods.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        
        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFoodShowDialog();
            }
        });
    }

    private void addFoodShowDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MasterFoods.this);

        LayoutInflater inflater = this.getLayoutInflater();
        View master_add_food_layout = inflater.inflate(R.layout.master_add_food,null);

        alertDialog.setView(master_add_food_layout);

        addFoodName = master_add_food_layout.findViewById(R.id.addFoodName);

        addFoodPrice = master_add_food_layout.findViewById(R.id.addFoodPrice);
        addFoodPrice.addTextChangedListener(new MoneyTextWatcher(addFoodPrice));
        addFoodPrice.setText("0");

        selectImg = master_add_food_layout.findViewById(R.id.btnSelectImagefood);
        uploadImg = master_add_food_layout.findViewById(R.id.btnUploadImagefood);
//        spinner = master_add_food_layout.findViewById(R.id.addFoodCategory);
        cekFoto = master_add_food_layout.findViewById(R.id.ImgCheckFoto);
        cekFotoKet = master_add_food_layout.findViewById(R.id.txtInfoImage);

//        categoryName = new ArrayList<>();
//        categoryName.add("Nasi");
//        categoryName.add("Daging");
//        categoryName.add("Ikan");
//        categoryName.add("Tambahan");
//
//        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item,categoryName);
//        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_item);
//        spinner.setAdapter(adapterCategory);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (adapterCategory.getItem(i).equals("Nasi")){
//                    categoryId = 1;
//                } else if (adapterCategory.getItem(i).equals("Daging")){
//                    categoryId = 2;
//                } else if (adapterCategory.getItem(i).equals("Ikan")){
//                    categoryId = 3;
//                } else if (adapterCategory.getItem(i).equals("Tambahan")){
//                    categoryId = 4;
//                } else {
//                    categoryId = 0;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                Toast.makeText(MasterFoods.this, "", Toast.LENGTH_SHORT).show();
//            }
//        });

        selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        
        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
        
        // Submit to Firebase
        alertDialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (uploadImg.getText().equals("Successfully Uploaded")){
                    // Insert to DB Foods
                    if (newFood != null) {
                        foodList.push().setValue(newFood);
                        Toast.makeText(MasterFoods.this, newFood.getName()+" berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MasterFoods.this, "Error ! Ada Kesalahan Input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.rdnasi:
                if (checked){
                    categoryId = 1;
                }
                break;
            case R.id.rdDaging:
                if (checked){
                    categoryId = 2;
                }
                break;
            case R.id.rdIkan:
                if (checked){
                    categoryId = 3;
                }
                break;
            case R.id.rdTambahan:
                if (checked){
                    categoryId = 4;
                }
                break;
        }
    }

    private void uploadImage() {

        BigDecimal value = MoneyTextWatcher.parseCurrencyValue(addFoodPrice.getText().toString());
        price = String.valueOf(value);
//        System.out.println(price);

        if (addFoodName.getText().toString().length() == 0) {
            Toast.makeText(this, "Nama Makanan Belum Diisi", Toast.LENGTH_SHORT).show();
        } else if (Integer.parseInt(price) == 0){
            Toast.makeText(this, "Harga Belum Di Setting", Toast.LENGTH_SHORT).show();
        } else if (categoryId == 0){
            Toast.makeText(this, "Kategori Belum Di Setting", Toast.LENGTH_SHORT).show();
        } else if (selectImg.getText().equals("Choose Image")){
            Toast.makeText(this, "Foto Belum Dipilih", Toast.LENGTH_SHORT).show();
        } else {
            if (saveUri != null){
                ProgressDialog mDialog = new ProgressDialog(this);
                mDialog.setMessage("Please Wait..");
                mDialog.show();

                String imageName = UUID.randomUUID().toString();
                StorageReference imageFolder = storageReference.child("images/foods/"+imageName);
                imageFolder.putFile(saveUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                mDialog.dismiss();
                                Toast.makeText(MasterFoods.this, "berhasil diupload..", Toast.LENGTH_SHORT).show();
                                selectImg.setText("Image Selected");
                                uploadImg.setText("Successfully Uploaded");
                                imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        newFood = new Foods(addFoodName.getText().toString(),uri.toString(),(Integer.parseInt(price)),categoryId,1,0,1);
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                mDialog.dismiss();
                                Toast.makeText(MasterFoods.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                double progress = (100 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                mDialog.setMessage("Uploaded "+progress+"%");
                            }
                        });
            } else {
                Toast.makeText(this, "Failed..", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            saveUri = data.getData();
            selectImg.setText("Image Selected");
            cekFoto.setVisibility(View.VISIBLE);
            cekFotoKet.setVisibility(View.VISIBLE);
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select picture"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadListMasterFoods();
    }

    private void initView() {
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Foods");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        progressBar = findViewById(R.id.progressBarMasterFood);

        // GET LIST FOODS
        recyclerViewMasterFoods = findViewById(R.id.rvMasterFoods);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewMasterFoods.setLayoutManager(layoutManager);
//        progressDialog.dismiss();
        loadListMasterFoods();

        // ADD NEW FOOD GROUP
        btnAddFood = findViewById(R.id.masterFoodAddFood);
    }

    private void loadListMasterFoods() {
        FirebaseRecyclerOptions<Foods> options =
                new FirebaseRecyclerOptions.Builder<Foods>()
                        .setQuery(foodList.orderByChild("categoryId"), Foods.class)
                        .build();
        adapter = new FirebaseRecyclerAdapter<Foods, MasterFoodViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MasterFoodViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Foods model) {
                Glide.with(getBaseContext()).load(model.getImage()).into(holder.food_image);
                holder.food_name.setText(model.getName());

                NumberFormat formatter = new DecimalFormat("#,###");
                double price = model.getPrice();

                holder.food_price.setText(formatter.format(price));
                holder.food_category.setText(String.valueOf(model.getCategoryId()));

                holder.btn_menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupMenu popup = new PopupMenu(MasterFoods.this, holder.btn_menu);
                        popup.getMenuInflater().inflate(R.menu.recycler_master_food, popup.getMenu());

                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                int itemId = item.getItemId();
                                if (itemId == R.id.foods_add_cart) {
                                    Intent i = new Intent(getApplicationContext(), FoodDetail.class);
                                    i.putExtra("FoodId",adapter.getRef(position).getKey());
                                    startActivity(i);
                                } else if (itemId == R.id.foods_edit){
                                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
//                                    i.putExtra("FoodId",adapter.getRef(position).getKey());
                                    startActivity(i);
                                } // FOOD DELETE...
                                return true;
                            }
                        });
                        popup.show();
                    }
                });

            }

            @NonNull
            @Override
            public MasterFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.viewholder_masterfoods, parent, false);

                return new MasterFoodViewHolder(view);
            }

            @Override
            public void onDataChanged() {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }

        };
        adapter.startListening();
        recyclerViewMasterFoods.setAdapter(adapter);
    }
}