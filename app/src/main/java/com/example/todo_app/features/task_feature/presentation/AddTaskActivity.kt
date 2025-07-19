package com.example.todo_app.features.task_feature.presentation

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.todo_app.R
import com.example.todo_app.databinding.ActivityAddTaskBinding
import com.example.todo_app.features.authentication_feature.data_layer.entities.AuthResponseModel
import com.example.todo_app.features.task_feature.data.entities.CreateTodoItemRequestEntity
import com.example.todo_app.features.task_feature.data.repositories.TodoRepository
import com.example.todo_app.features.task_feature.presentation.view_models.AddTaskViewModel
import com.example.todo_app.utils.constants.nav
import com.example.todo_app.utils.constants.ui
import com.github.dhaval2404.imagepicker.ImagePicker
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.coroutines.launch

class AddTaskActivity : AppCompatActivity() {
    private lateinit var addBinding: ActivityAddTaskBinding
    private lateinit var addViewModel: AddTaskViewModel
    private val imagePicker: ActivityResultLauncher<Intent> = initImagePicker()
    private lateinit var authModel: AuthResponseModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBinding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(addBinding.root)
        initVariables()
    }

    private fun initVariables() {
        addViewModel =
            ViewModelProvider(
                this,
                AddTaskViewModel.provideFactory(application, TodoRepository(this))
            )[AddTaskViewModel::class.java]
        authModel = intent.getParcelableExtra<AuthResponseModel>(nav.AUTH)!!
        addViewModel.selectedDate?.let {
            addBinding.dateInput.setText(it)
        }
        addViewModel.selectedImageUri?.let {
            addBinding.addText.visibility = View.GONE
            addBinding.selectedImage.setImageURI(it)
        }

        addViewModel.selectedPriority?.let {
            addBinding.priorityDropdownMenu.setText(it, false)
        }
        setPriorities()
        addBinding.datePickerContainer.setEndIconOnClickListener {
            pickDate()
        }
    }

    private fun initImagePicker() =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri: Uri? = result.data?.data
                uri?.let {
                    addViewModel.selectedImageUri = it
                    addBinding.addText.visibility = View.GONE
                    addBinding.selectedImage.setImageURI(it)
                }
            }
        }

    private fun setPriorities() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ui.priorities)
        addBinding.priorityDropdownMenu.setAdapter(adapter)
        addBinding.priorityDropdownMenu.setOnItemClickListener { _, _, position, _ ->
            val selected = ui.priorities[position]
            addViewModel.selectedPriority = selected
        }
    }

    private fun pickDate() {
        val dpd = DatePickerDialog.newInstance { _, year, monthOfYear, dayOfMonth ->
            val selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year)
            addBinding.dateInput.setText(selectedDate)
            addViewModel.selectedDate = selectedDate
        }

        dpd.show(supportFragmentManager, "Datepickerdialog")
    }

    fun selectImage(view: View) {
        ImagePicker.with(this)
            .createIntent {
                imagePicker.launch(it)
            }
    }

    fun returnToHomeScreen(view: View) = finish()

    fun createTodoItem(view: View) {
        if (validate()) {
            lifecycleScope.launch {
                addBinding.addButton.visibility = View.GONE
                addBinding.circularProgressBar.visibility = View.VISIBLE

                addViewModel.createTodoItem(
                    getTodoItem(),
                    authModel.accessToken
                )
                finish()
            }
        }

    }

    private fun validate(): Boolean {
        var isValid = true

        val title = addBinding.myTaskTitle.text.toString().trim()
        val description = addBinding.myTaskDesc.text.toString().trim()
        val priority = addViewModel.selectedPriority
        val date = addViewModel.selectedDate
        addBinding.myTaskTitle.error = null
        addBinding.myTaskDescContainer.error = null
        addBinding.priorityLevelContainer.error = null
        addBinding.datePickerContainer.error = null

        if (title.isEmpty()) {
            addBinding.myTaskTitleContainer.error = "Title is required"
            isValid = false
        }

        if (description.isEmpty()) {
            addBinding.myTaskDescContainer.error = "Description is required"
            isValid = false
        }

        if (priority == null) {
            addBinding.priorityLevelContainer.error = "Please select a priority"
            isValid = false
        }

        if (date == null) {
            addBinding.datePickerContainer.helperText = "Please select a date"
            addBinding.datePickerContainer.setHelperTextColor(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this,
                        R.color.red
                    )
                )
            )
            isValid = false
        } else {
            addBinding.datePickerContainer.helperText = null
        }


        if (addViewModel.selectedImageUri == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }

    private fun getTodoItem() = CreateTodoItemRequestEntity(
        addViewModel.selectedImageUri.toString(),
        addBinding.myTaskTitle.text.toString(),
        addBinding.myTaskDesc.text.toString(),
        addViewModel.selectedPriority!!,
        addViewModel.selectedDate!!
    )
}
