package com.alijan.textrecognition_mlkit.ui.text

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TextViewModel : ViewModel() {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    private lateinit var image: InputImage

    private var _text = MutableLiveData<String>()
    val text: LiveData<String> get() = _text

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun textRecognitionFromURI(context: Context, uri: Uri) {
        viewModelScope.launch(Dispatchers.Default) {
            image = InputImage.fromFilePath(context, uri)
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    _text.value = visionText.text
                }
                .addOnFailureListener { e ->
                    _text.value = e.localizedMessage?.toString()
                }
        }
    }

    fun textRecognitionFromBitmap(bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.Default) {
            image = InputImage.fromBitmap(bitmap, 0)
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    _text.value = visionText.text
                }
                .addOnFailureListener { e ->
                    _text.value = e.localizedMessage?.toString()
                }
        }
    }

}