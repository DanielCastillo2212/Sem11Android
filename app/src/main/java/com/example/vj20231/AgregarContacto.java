package com.example.vj20231;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vj20231.R;
import com.example.vj20231.adapters.ContactAdapter;
import com.example.vj20231.entities.Contact;
import com.example.vj20231.services.ContactService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AgregarContacto extends AppCompatActivity {

    private EditText etNombre;
    private EditText etNumero;
    private EditText etImgContact;
    private Button btnAgregar;

    private ContactService contactService;

    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contacto);

        etNombre = findViewById(R.id.etNombre);
        etNumero = findViewById(R.id.etNumero);
        etImgContact = findViewById(R.id.etImgContact);
        btnAgregar = findViewById(R.id.btnAgregar);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://64779d129233e82dd53beed7.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        contactService = retrofit.create(ContactService.class);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString();
                String numero = etNumero.getText().toString();
                String imgContact = etImgContact.getText().toString();

                if (!nombre.isEmpty() && !numero.isEmpty()) {
                    // Crear un nuevo objeto Contact con los datos ingresados
                    Contact contact = new Contact();
                    contact.setNameContact(nombre);
                    contact.setNumberContact(numero);
                    contact.setImgContact(imgContact);

                    // Llamar al método create para guardar el nuevo contacto en MockAPI
                    Call<Contact> createCall = contactService.create(contact);
                    createCall.enqueue(new Callback<Contact>() {
                        @Override
                        public void onResponse(Call<Contact> call, Response<Contact> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(AgregarContacto.this, "Contacto agregado correctamente", Toast.LENGTH_SHORT).show();
                                finish(); // Cerrar la actividad después de agregar el contacto
                            } else {
                                Toast.makeText(AgregarContacto.this, "Error al agregar el contacto", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Contact> call, Throwable t) {
                            Toast.makeText(AgregarContacto.this, "Error al agregar el contacto", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(AgregarContacto.this, "Ingrese un nombre y número válidos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
