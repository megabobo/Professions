package com.example.joshgearou.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextAge;
    Button buttonAdd;
    Button buttonProf;
    Button buttonAge;
    Spinner spinnerProfessions;

    DatabaseReference myref;

    ListView listViewPeople;

    List<Person> personList;
    List<Person> investorList, contractorList, engineerList, studentList, ageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myref = FirebaseDatabase.getInstance().getReference("People");
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        buttonAdd = (Button) findViewById(R.id.buttonAddPerson);
        buttonProf = (Button) findViewById(R.id.buttonFilterP);
        buttonAge = (Button) findViewById(R.id.buttonFilterA);
        spinnerProfessions = (Spinner) findViewById(R.id.spinnerProfessions);

        listViewPeople = (ListView) findViewById(R.id.listViewPeople);

        personList = new ArrayList<>();
        investorList = new ArrayList<>();
        contractorList = new ArrayList<>();
        engineerList = new ArrayList<>();
        studentList = new ArrayList<>();
        ageList = new ArrayList<>();
        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addPerson();
            }

    });
        buttonProf.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick (View v) {
                filterProfession();
            }
        });

        buttonAge.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View v) {
                filterAge();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                personList.clear();
                for (DataSnapshot personSnapshot : dataSnapshot.getChildren()) {
                    Person person = personSnapshot.getValue(Person.class);

                    personList.add(person);
                }

               PersonList adapter = new PersonList(MainActivity.this, personList);
               listViewPeople.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addPerson() {
        String name = editTextName.getText().toString().trim();
        String profession = spinnerProfessions.getSelectedItem().toString();
        System.out.println(profession);

        String age = editTextAge.getText().toString().trim();

        if (!TextUtils.isEmpty(name)) {
            if(!TextUtils.isEmpty(age)) {
                String id = myref.push().getKey();
                Person person = new Person(id, name, profession, age);
                myref.child(id).setValue(person);

                Toast.makeText(this, profession + " added", Toast.LENGTH_LONG).show();
           }
            else {
                Toast.makeText(this, "Please enter an age", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }

    private void filterProfession() {
        ageList.clear();
        investorList.clear();
        contractorList.clear();
        engineerList.clear();
        studentList.clear();
        listViewPeople.setAdapter(null);
        String profession = spinnerProfessions.getSelectedItem().toString();
        if (profession.equals("Investor")) {
            for (int i=0; i<personList.size(); i++) {
                if (personList.get(i).getProfession().equals("Investor")) {
                    investorList.add(personList.get(i));
                }
            }
            PersonList adapter = new PersonList(MainActivity.this, investorList);
            listViewPeople.setAdapter(adapter);

        }
       else if (profession.equals("Contractor")) {
            for (int i=0; i<personList.size(); i++) {
                if (personList.get(i).getProfession().equals("Contractor")) {
                    contractorList.add(personList.get(i));
                }
            }
            PersonList adapter = new PersonList(MainActivity.this, contractorList);
            listViewPeople.setAdapter(adapter);

        }
        else if (profession.equals("Engineer")) {
            for (int i=0; i<personList.size(); i++) {
                if (personList.get(i).getProfession().equals("Engineer")) {
                    engineerList.add(personList.get(i));
                }
            }
            PersonList adapter = new PersonList(MainActivity.this, engineerList);
            listViewPeople.setAdapter(adapter);

        }
        else if (profession.equals("Student")) {
            for (int i=0; i<personList.size(); i++) {
                if (personList.get(i).getProfession().equals("Student")) {
                    studentList.add(personList.get(i));
                }
            }
            PersonList adapter = new PersonList(MainActivity.this, studentList);
            listViewPeople.setAdapter(adapter);

        }
    }

    private void filterAge() {
        ageList.clear();
        String ages = editTextAge.getText().toString().trim();

        if(!TextUtils.isEmpty(ages)) {
            listViewPeople.setAdapter(null);
            StringTokenizer st = new StringTokenizer(ages);
            int low = Integer.parseInt(st.nextToken());
            int high = Integer.parseInt(st.nextToken());
            for (int i=0; i<personList.size(); i++) {
                if (Integer.parseInt(personList.get(i).getAge()) >= low && Integer.parseInt(personList.get(i).getAge()) <= high) {
                    ageList.add(personList.get(i));
                }
            }
            PersonList adapter = new PersonList(MainActivity.this, ageList);
            listViewPeople.setAdapter(adapter);
        }
        else {
            Toast.makeText(this, "Please enter two space separated numbers into the " +
                    "Enter Age/Age Range Box", Toast.LENGTH_LONG).show();
        }
    }
}

