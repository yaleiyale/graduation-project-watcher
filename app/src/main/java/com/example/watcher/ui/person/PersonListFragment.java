package com.example.watcher.ui.person;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.watcher.adapters.PersonListAdapter;
import com.example.watcher.data.person.Person;
import com.example.watcher.databinding.FragmentPersonListBinding;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class PersonListFragment extends Fragment {

    private final CompositeDisposable disposable = new CompositeDisposable();
    private PersonListViewModel personListViewModel;
    private FragmentPersonListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        personListViewModel =
                new ViewModelProvider(this).get(PersonListViewModel.class);
        personListViewModel.personRepository.refreshLocal();
        binding = FragmentPersonListBinding.inflate(inflater, container, false);
        PersonListAdapter adapter = new PersonListAdapter();
        binding.personList.setAdapter(adapter);
        subscribeUi(adapter);

        setHasOptionsMenu(false);
        return binding.getRoot();
    }

    private void toInsert(View view) {
//        NavDirections action = DeviceListFragmentDirections.actionNavigationDeviceToAddDeviceFragment();
//        Navigation.findNavController(view).navigate(action);
//        Intent i  = new Intent(getActivity(), LoginActivity.class);
//        startActivity(i);
        disposable.add(personListViewModel.insert("李四")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                        },
                        throwable -> Log.e("unable", "Unable to add device", throwable)));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void subscribeUi(PersonListAdapter adapter) {
        PersonListObserver observer = new PersonListObserver(adapter);
        personListViewModel.people.observe(getViewLifecycleOwner(), observer);
    }
}

class PersonListObserver implements Observer<List<Person>> {
    final PersonListAdapter personListAdapter;

    PersonListObserver(PersonListAdapter personListAdapter) {
        this.personListAdapter = personListAdapter;
    }

    @Override
    public void onChanged(List<Person> people) {
        this.personListAdapter.submitList(people);
    }
}