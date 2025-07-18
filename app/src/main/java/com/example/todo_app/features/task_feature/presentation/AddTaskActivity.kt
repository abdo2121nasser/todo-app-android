package com.example.todo_app.features.task_feature.presentation

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.todo_app.databinding.ActivityAddTaskBinding
import com.example.todo_app.utils.constants.ui
import com.github.dhaval2404.imagepicker.ImagePicker
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog

class AddTaskActivity : AppCompatActivity(){
    private lateinit var addBinding: ActivityAddTaskBinding
    private lateinit var imagePicker : ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBinding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(addBinding.root)
initVariables()


    }
   private   fun initVariables(){
       imagePicker = initImagePickerVariable()
       setPriorities()
       addBinding.datePickerContainer.setEndIconOnClickListener {
           pickDate()
       }
      }


    private fun setPriorities() {
        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, ui.priorities)
        addBinding.priorityDropdownMenu
            .setAdapter(adapter)
    }

    fun returnToHomeScreen(view: View) = finish()
    private fun initImagePickerVariable() =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri: Uri? = result.data?.data
                uri?.let {
                    addBinding.addText.visibility = View.GONE
                    addBinding.selectedImage.setImageURI(it)
                }
            }
        }

    fun selectImage(view: View) {
        ImagePicker.with(this)
            .createIntent {
                imagePicker.launch(it)
            }
    }
    private fun pickDate() {
        val dpd = DatePickerDialog.newInstance { _, year, monthOfYear, dayOfMonth ->
            val selectedDate =
                String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year)
            addBinding.dateInput.setText(selectedDate)
        }
        dpd.show(supportFragmentManager, "Datepickerdialog")
    }

}