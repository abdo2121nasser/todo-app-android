package com.example.todo_app.features.task_feature.presentation

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.todo_app.R
import com.example.todo_app.databinding.ActivityTaskActionBinding
import com.example.todo_app.features.authentication_feature.data_layer.entities.AuthResponseModel
import com.example.todo_app.features.task_feature.data.entities.CreateTodoItemRequestEntity
import com.example.todo_app.features.task_feature.data.entities.TodoItemEntity
import com.example.todo_app.features.task_feature.data.entities.UpdateTodoItemRequestModel
import com.example.todo_app.features.task_feature.data.repositories.TodoRepository
import com.example.todo_app.features.task_feature.presentation.adaptors.formatDate
import com.example.todo_app.features.task_feature.presentation.view_models.TaskViewModel
import com.example.todo_app.utils.constants.nav
import com.example.todo_app.utils.constants.ui
import com.github.dhaval2404.imagepicker.ImagePicker
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.coroutines.launch

class TaskActionActivity : AppCompatActivity() {
    private lateinit var taskBinding: ActivityTaskActionBinding
    private lateinit var taskViewModel: TaskViewModel
    private val imagePicker: ActivityResultLauncher<Intent> = initImagePicker()
    private lateinit var authModel: AuthResponseModel
    private  var updateItemEntity: TodoItemEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskBinding = ActivityTaskActionBinding.inflate(layoutInflater)
        setContentView(taskBinding.root)
        initVariables()
    }
    private fun initVariables() {
        taskViewModel =
            ViewModelProvider(
                this,
                TaskViewModel.provideFactory(application, TodoRepository(this))
            )[TaskViewModel::class.java]
        receiveVariables()
        updatedItemEntitySetups()
        taskViewModel.selectedDate?.let {
            taskBinding.dateInput.setText(it)
        }
        taskViewModel.selectedImage?.let {
            Glide.with(this)
                .load(Uri.parse(it))
                .placeholder(R.drawable.round_square_place_holder_icon)
                .error(R.drawable.error_icon)
                .into(taskBinding.selectedImage)
        }

        taskViewModel.selectedPriority?.let {
            taskBinding.priorityDropdownMenu.setText(it, false)
        }
        setPriorities()
        taskBinding.datePickerContainer.setEndIconOnClickListener {
            pickDate()
        }
    }

    private fun updatedItemEntitySetups() {
        updateItemEntity?.let {
            taskBinding.title.text = "Edit Task"
            taskBinding.actionButton.text = "Edit"
            taskBinding.statusContainer.visibility = View.VISIBLE
            taskBinding.statusTitle.visibility = View.VISIBLE
            taskBinding.datePickerContainer.visibility = View.GONE
            taskBinding.dateTitle.visibility = View.GONE
            taskBinding.statusDropdownMenu.setText(it.status)
            setStatus()
            taskBinding.myTaskTitle.setText(it.title)
            taskBinding.myTaskDesc.setText(it.subTitle)
            taskBinding.priorityDropdownMenu.setText(it.priority)
            taskBinding.dateInput.setText(it.date.formatDate())
            Glide.with(this)
                .load(Uri.parse(it.imageLink))
                .placeholder(R.drawable.round_square_place_holder_icon)
                .error(R.drawable.error_icon)
                .into(taskBinding.selectedImage)
            taskViewModel.selectedDate = it.date.formatDate()
            taskViewModel.selectedImage = it.imageLink
            taskViewModel.selectedPriority = it.priority
            taskViewModel.selectedStatus = it.status

        }
    }

    private  fun receiveVariables(){
        authModel = intent.getParcelableExtra(nav.AUTH)!!
        updateItemEntity = intent.getParcelableExtra(nav.UPDATE_TODO_ENTITY)
    }

    private fun initImagePicker() =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri: Uri? = result.data?.data
                uri?.let {
                    taskViewModel.selectedImage = it.toString()
                    taskBinding.selectedImage.setImageURI(it)
                }
            }
        }

    private fun setPriorities() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ui.priorities)
        taskBinding.priorityDropdownMenu.setAdapter(adapter)
        taskBinding.priorityDropdownMenu.setOnItemClickListener { _, _, position, _ ->
            val selected = ui.priorities[position]
            taskViewModel.selectedPriority = selected
        }
    }

    private fun setStatus() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ui.statuses)
        taskBinding.statusDropdownMenu.setAdapter(adapter)
        taskBinding.statusDropdownMenu.setOnItemClickListener { _, _, position, _ ->
            val selected = ui.statuses[position]
            taskViewModel.selectedStatus = selected
        }
    }

    private fun pickDate() {
        val dpd = DatePickerDialog.newInstance { _, year, monthOfYear, dayOfMonth ->
            val selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year)
            taskBinding.dateInput.setText(selectedDate)
            taskViewModel.selectedDate = selectedDate
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

    private fun validate(): Boolean {
        var isValid = true

        val title = taskBinding.myTaskTitle.text.toString().trim()
        val description = taskBinding.myTaskDesc.text.toString().trim()
        val priority = taskViewModel.selectedPriority
        val date = taskViewModel.selectedDate
        taskBinding.myTaskTitle.error = null
        taskBinding.myTaskDescContainer.error = null
        taskBinding.priorityLevelContainer.error = null
        taskBinding.datePickerContainer.error = null

        if (title.isEmpty()) {
            taskBinding.myTaskTitleContainer.error = "Title is required"
            isValid = false
        }

        if (description.isEmpty()) {
            taskBinding.myTaskDescContainer.error = "Description is required"
            isValid = false
        }

        if (priority == null) {
            taskBinding.priorityLevelContainer.error = "Please select a priority"
            isValid = false
        }

        if (date == null) {
            taskBinding.datePickerContainer.helperText = "Please select a date"
            taskBinding.datePickerContainer.setHelperTextColor(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this,
                        R.color.red
                    )
                )
            )
            isValid = false
        } else {
            taskBinding.datePickerContainer.helperText = null
        }
        if (taskViewModel.selectedImage == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }

    private fun getCreateTodoItem() = CreateTodoItemRequestEntity(
        taskViewModel.selectedImage.toString(),
        taskBinding.myTaskTitle.text.toString(),
        taskBinding.myTaskDesc.text.toString(),
        taskViewModel.selectedPriority!!,
        taskViewModel.selectedDate!!
    )

    private fun getUpdateTodoItem() = UpdateTodoItemRequestModel(
        userId = authModel.id,
        imageLink = taskViewModel.selectedImage.toString(),
        title = taskBinding.myTaskTitle.text.toString(),
        subTitle = taskBinding.myTaskDesc.text.toString(),
        priority = taskViewModel.selectedPriority!!,
        status = taskViewModel.selectedStatus!!
    )

    fun doTaskAction(view: View) {
        if (validate()) {
            taskBinding.actionButton.visibility = View.GONE
            taskBinding.circularProgressBar.visibility = View.VISIBLE
            lifecycleScope.launch {
                if (updateItemEntity == null) {
                    taskViewModel.createTodoItem(
                        getCreateTodoItem(),
                        authModel.accessToken
                    )
                } else {
                    taskViewModel.updateTodoItem(
                        getUpdateTodoItem(),
                        updateItemEntity!!.id,
                        authModel.accessToken
                    )
                }
                finish()
            }

        }
    }
}
