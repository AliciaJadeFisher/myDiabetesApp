package uk.ac.tees.s6040531.mydiabetesapplication.MainActivities.ui.forum;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ForumViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ForumViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the forum fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}