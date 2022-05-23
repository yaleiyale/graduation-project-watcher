package com.example.watcher.ui.person;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.watcher.data.passRecord.PassRecordRepository;
import com.example.watcher.data.person.Person;
import com.example.watcher.data.person.PersonRepository;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class PersonDetailViewModel extends ViewModel {
    private final Handler handler = new Handler(Looper.getMainLooper());
    PersonRepository personRepository;
    PassRecordRepository recordRepository;
    int personId;
    Person person;
    CompositeDisposable mDisposable = new CompositeDisposable();
    private Callback mCallback;

    @Inject
    public PersonDetailViewModel(@NotNull SavedStateHandle savedStateHandle, PersonRepository personRepository, PassRecordRepository recordRepository) {
        super();
        this.personRepository = personRepository;
        this.recordRepository = recordRepository;
        this.personId = PersonDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).getMyArg();
    }

    public void delete(View view) {
        personRepository.deletePersonNet(person);
        mDisposable.add(personRepository.delete(person)
                .subscribeOn(Schedulers.newThread())
                .subscribe(() -> handler.post(() -> mCallback.OnDelete(view)),
                        throwable -> Log.e("no detail", "Unable to get detail", throwable)));
        mDisposable.add(recordRepository.deleteByPersonId(personId)
                .subscribeOn(Schedulers.newThread())
                .subscribe(() -> {
                        },
                        throwable -> Log.e("no detail", "Unable to get detail", throwable)));
        mCallback.OnDelete(view);

    }

    public void adjust(String name) {
        person.personName = name;
        personRepository.updatePersonNet(person);
        mDisposable.add(personRepository.update(person)
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {
                        },
                        throwable -> Log.e("no detail", "Unable to get detail", throwable)));
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
        Load();
    }

    private void Load() {
        mDisposable.add(personRepository.getPersonById(personId)
                .subscribeOn(Schedulers.io())
                .subscribe(temp -> mCallback.OnLoad(temp),
                        throwable -> Log.e("no detail", "Unable to get detail", throwable)));
    }

    public interface Callback {
        void OnLoad(Person temp);

        void OnDelete(View view);
    }
}
