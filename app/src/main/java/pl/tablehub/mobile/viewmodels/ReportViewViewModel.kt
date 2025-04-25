package pl.tablehub.mobile.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.tablehub.mobile.services.interfaces.TablesService
import javax.inject.Inject

@HiltViewModel
class ReportViewViewModel @Inject constructor(
    private val application: Application,
    private val service: TablesService
) : AndroidViewModel(application) {
}