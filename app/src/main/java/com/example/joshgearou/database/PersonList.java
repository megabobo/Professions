package com.example.joshgearou.database;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Josh Gearou on 4/16/2017.
 */

public class PersonList extends ArrayAdapter<Person> {

    private Activity context;
    private List<Person> personList;

    public PersonList(Activity context, List<Person> personList) {
        super(context, R.layout.list_layout, personList);
        this.context = context;
        this.personList = personList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewAge = (TextView) listViewItem.findViewById(R.id.textViewAge);
        TextView textViewProfession = (TextView) listViewItem.findViewById(R.id.textViewProfession);

        Person person = personList.get(position);

        textViewName.setText(person.getName());
        textViewAge.setText(person.getAge());
        textViewProfession.setText(person.getProfession());

        return listViewItem;

    }

    public List<Person> getPersonList() {
        return personList;
    }
}

